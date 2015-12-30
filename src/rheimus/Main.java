package rheimus;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import rheimus.reference.Directories;
import rheimus.reference.TerrainMaps;

public class Main {

	private static BufferedImage image;
	private static BufferedImage[] imgs;
	private static Path workingDirectory, terrainImage, output;
	private static String outputPath, mod = "";

	private static String[] names = TerrainMaps.v1_4_5; // pc_stage, blank,
														// cake_item, water,
														// anvil, unknown

	public static void main(String[] args) {
		workingDirectory = Paths.get("");
		terrainImage = Paths.get(workingDirectory.toAbsolutePath().toString() + File.separator + "terrain.png");
		output = Paths.get(workingDirectory.toAbsolutePath().toString() + File.separator + "terrain" + File.separator);
		try {
			Files.createDirectories(output);
			image = ImageIO.read(terrainImage.toFile());
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		int rows = 16;
		int cols = 16;
		int chunks = rows * cols;

		int chunkWidth = image.getWidth() / cols; // determines the chunk width
													// and height
		int chunkHeight = image.getHeight() / rows;
		int count = 0;
		imgs = new BufferedImage[chunks]; // Image array to hold
											// image chunks
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				// Initialize the image array with image chunks
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

				// draws the image chunk
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x,
						chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}
		System.out.println("Splitting done");

		int namesLength = names.length;
		int blanks = 0;
		outputPath = output.toAbsolutePath().toString();

		// writing mini images into image files
		for (int i = 0; i < imgs.length; i++) {
			try {
				if (i < namesLength) {
					String fileName = names[i];

					if (fileName.matches("(water_|lava_|anvil_|chest_)+.?")) {
						System.out.println("matched workable items");
						mod = "workable";
						writeImage(i, fileName);
					} else if (fileName.matches("(unknown_|blank)+.?")) {
						mod = "misc";
						writeImage(i, fileName + blanks++);
					} else if (fileName.matches("(pc_stage_)+.+")) {
						System.out.println("splitting potatoes and carrots");
						mod = Directories.BlocksDirectory;
						writeImage(i, fileName.replace("pc_", "potatoes_"));
						writeImage(i, fileName.replace("pc_", "carrots_"));
					} else if (fileName.matches("(cake_item)+")) {
						System.out.println("found cake");
						mod = Directories.ItemsDirectory;
						writeImage(i, "cake");
					} else {
						mod = Directories.BlocksDirectory;
						writeImage(i, fileName);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Mini images created");
	}

	private static void writeImage(int currentFile, String fileName) throws IOException {
		Path dir = Paths.get(outputPath + File.separator + mod + File.separator);
		Files.createDirectories(dir);
		ImageIO.write(imgs[currentFile], "PNG", new File(dir.toAbsolutePath().toString(), fileName + ".png"));
	}
}
