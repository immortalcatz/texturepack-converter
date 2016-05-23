# Development in hibernation for now

# texturepack-converter
Designed to convert older texture packs to the more modern Minecraft file structure, intends to provide some more functionality than Dinnerbones converter.

Currently will convert terrain.png from 1.4.5 format, has maps set up for 1.4.5/1.3/1.2.4/1.2.3 although a way to switch between them and a nice GUI is still in the works. Currently if you wish to use a different texture format the names variable in Main will need to be changed to the appropriate version map.

Note that this will convert any terrain.png regardless of format, however the resulting files may not be perfectly named or in the right place. Some of the textures are not easily converted as they require metadata or a major restructure, these are usually put in the workable directory as they could be updated manually with a bit of elbow grease.

Misc directory contains the blank or unknown textures, it may contain other textures if terrain.png is in a format other than 1.4.5.

<h2>How does it work?</h2>
Put terrain_converter.jar in the same folder as the desired terrain.png and run terrain_converter.
