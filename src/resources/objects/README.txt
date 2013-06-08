Author:
	David Střelák

Model(s):
	crane (divided into several parts)
	skybox 
	surface
File format(s):
	obj (with texture coordinates)
	max (resource file)
	jpeg (textures)

Properties:
	crane 
		parts fit together to form whole crane
		bottom is possitioned at [0,0,0]
		goes with texture_flipped.jpg if .obj is used, otherwise not textured properly
	skybox
		bottom is possitioned at [0,0,0]
		goes with skybox_flipped.jpg if .obj is used, otherwise not textured properly
	surface
		possitioned at [0,0,0], flat surface
		goes with asphalt.jpg
	NOTE: all objects are already in proper scale (skybox > surface > crane), however final size must be adjusted

Use policy:
	all models and texture and texture_flipped are a property of their author
	all models and texture and texture_flipped can be used in PV112 project for free, however author likes beer and spirits
	other use only after prior written consent