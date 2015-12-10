PACKAGE BMP IS
	--#####################################
	--PACKAGE TYPES 
	type T_u8 is mod 2**8;
	type T_u16 is mod 2**16;
	type T_u32 is mod 2**32;

	type Header is record
		Signature : T_u16;
		Size      : T_u32; -- File size in bytes
		Reserved1 : T_u16;
		Reserved2 : T_u16;
		Offset    : T_u32; -- Start address in bytes where the image data can be found.
	end record; 
	type Info is record
		Struct_Size   : T_u32;
		Width         : T_u32; -- Image width in pixels
		Height        : T_u32; -- Image height in pixels
		Planes        : T_u16;
		Pixel_Size    : T_u16; -- Bits per pixel
		Compression   : T_u32; -- Zero means no compression
		Image_Size    : T_u32; -- Size of the image data in bytes
		PPMX          : T_u32; -- Pixels per meter in x led
		PPMY          : T_u32; -- Pixels per meter in y led
		Palette_Size  : T_u32; -- Number of colors
		Important     : T_u32;
	end record;

	--it is strange but when reading if we set the order to ARGB then R and B or switched
	--so I had to set the order to ABGR
	type Pixel is record -- 32 bit pixel (alpha, red, green, blue)
		 A, B, G, R : T_u8; -- 8 bit * 4 = 32 bit
	end record;
	
	type ColorMap is array (Integer range <>) of T_u8;
	type pColorMap is access ColorMap;
	type Image is array (Integer range <>,Integer range <>) of Pixel;

	--#####################################
	--PACKAGE FUNCTIONS 

	--Reads a BMP file, save the header and returns the image (matrix of pixels)
	function Load (bmp32_file_name : String) return Image;
	
	--Stores the image into "out.bmp" using the saved header.
	--Once saved, saved_color is freed, so this function needs to be called only once.
	procedure Store (im : Image);

	--Print the loaded image header
	--Needs to be called only once Load has been called
	procedure PrintHeader;



	--#####################################
	--PACKAGE VARIABLES : DO NOT TOUCH THIS
	saved_header : Header;
	saved_info : Info;
	saved_color : pColorMap;

	ALREADY_CALLED : EXCEPTION;
	LOAD_NOT_CALLED : EXCEPTION;

	load_called_once : Boolean := false;
	store_called_once : Boolean := false;
END BMP ;
