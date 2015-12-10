with Ada; use Ada;
with Ada.Text_IO;
with Ada.Streams.Stream_IO;
with Ada.Unchecked_Deallocation;
use Ada.Streams;
with BMP;

package body BMP is
	function Load (bmp32_file_name : String) return Image is
		File   : Stream_IO.File_Type;
		Stream : Stream_IO.Stream_Access;
	begin
		if (load_called_once) then 
			raise ALREADY_CALLED;
		end if;

		--This function needs to be called once only since we save the header
		load_called_once := true;		

		--Open a stream on the image file
		Stream_IO.Open(File, Stream_IO.In_File, bmp32_file_name);
		Stream := Stream_IO.Stream(File);
		
		--Read and save the header
		BMP.Header'Read(Stream, saved_header);

		--Read and save the info header
		BMP.Info'Read(Stream, saved_info);

		-- Move read pointer to where the image data starts.
		--Stream_IO.Set_Index(File, Stream_IO.Positive_Count(saved_header.Offset));

		--Read and save the part of the header containing the color map
		saved_color := new BMP.ColorMap(1..Integer(saved_info.Struct_Size-(saved_info'Size/8)));
		BMP.ColorMap'Read(Stream,saved_color.all);
		
		--Create the image and returns it to the user
		declare
			I : BMP.Image(1..Integer(saved_info.Height),1..Integer(saved_info.Width));
		begin
			BMP.Image'Read(Stream, I);
			Stream_IO.Close(File);
			return I;
		end;
	end Load;


	procedure Store (im : Image) is
		bmp32_file_name : String := "out.bmp";
		imFile   : Text_IO.File_Type;
		File   : Stream_IO.File_Type;
		Stream : Stream_IO.Stream_Access;
		script : Text_IO.File_Type;

		procedure Free_ColorMap is new Ada.Unchecked_Deallocation
      			(Object => ColorMap, Name => pColorMap);
	begin
		--This function needs to be called after load has been called
		--since we need the header
		if (not load_called_once) then
			raise LOAD_NOT_CALLED;
		end if;

		--This function needs to be called once only since we save the header
		if (store_called_once) then 
			raise ALREADY_CALLED;
		end if;
		store_called_once := true;		

		--Create the output file to store the image
		Text_IO.Create(imFile, Text_IO.Out_File, bmp32_file_name);
		Text_IO.Close(imFile);

		--Open it with the stream to store the data
		Stream_IO.Open(File, Stream_IO.Out_File, bmp32_file_name);
		Stream := Stream_IO.Stream(File);

		--Write the header
		BMP.Header'Write(Stream, saved_header);

		--Write the info
		BMP.Info'Write(Stream, saved_info);

		--Write the color
		BMP.ColorMap'Write(Stream, saved_color.all);
		
		--write the image
		BMP.Image'Write(Stream, im);

		--Close the stream
		Stream_IO.Close(File);

		--Free the color map
		Free_ColorMap(saved_color);

		--Writing in the script file
		Text_IO.Create(script, Text_IO.Out_File, "client_turtle_script.js");
		Text_IO.Put_Line(script,"draw_image(600,500);");
		Text_IO.Close(script);
	end Store;

	procedure PrintHeader is
	begin
		--This function needs to be called after load has been called
		--since we need the header		
		if (not load_called_once) then
			raise LOAD_NOT_CALLED;
		end if;
		Text_IO.Put_Line("----------");
		Text_IO.Put_Line("Signature " & saved_header.Signature'Img);
		Text_IO.Put_Line("Size " & saved_header.Size'Img);
		Text_IO.Put_Line("Reserved1 " & saved_header.Reserved1'Img);
		Text_IO.Put_Line("Reserved2 " & saved_header.Reserved2'Img);
		Text_IO.Put_Line("Offset " & saved_header.Offset'Img);
		Text_IO.Put_Line("Struct_Size " & saved_info.Struct_Size'Img);
		Text_IO.Put_Line("Width " & saved_info.Width'Img);
		Text_IO.Put_Line("Height " & saved_info.Height'Img);
		Text_IO.Put_Line("Planes " & saved_info.Planes'Img);
		Text_IO.Put_Line("Pixel_Size " & saved_info.Pixel_Size'Img);
		Text_IO.Put_Line("Compression " & saved_info.Compression'Img);
		Text_IO.Put_Line("Image_Size " & saved_info.Image_Size'Img);
		Text_IO.Put_Line("PPMX " & saved_info.PPMX'Img);
		Text_IO.Put_Line("PPMY " & saved_info.PPMY'Img);
		Text_IO.Put_Line("Palette_Size " & saved_info.Palette_Size'Img);
		Text_IO.Put_Line("Important " & saved_info.Important'Img);
		Text_IO.Put_Line("----------");

	end PrintHeader;

end BMP;
