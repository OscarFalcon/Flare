package com.flare.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

public class FlareUtils
{

	public static void saveImageAsJPEG(BufferedImage image,OutputStream stream, int qualityPercent) throws IOException {
	     
		   if ((qualityPercent < 0) || (qualityPercent > 100))
		   {
			   throw new IllegalArgumentException("Quality out of bounds!");
		   }
	     
		   float quality = qualityPercent / 100f;
		   ImageWriter writer = null;
		   Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
	     
		   
		   if (iter.hasNext())
		   {
			   writer = iter.next();
		   }
		   else
		   {
			   System.out.println("No Image Writer Available");
			   return;
		   }
		   
		   
		   ImageOutputStream ios = ImageIO.createImageOutputStream(stream);
		   writer.setOutput(ios);
		   
		   ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
		   iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		   iwparam.setCompressionQuality(quality);
		   writer.write(null, new IIOImage(image, null, null), iwparam);
		   ios.flush();
		   writer.dispose();
		   ios.close();
		   
	   }
	
	
	
	
}
