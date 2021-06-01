package sudoku;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class GameResources {
	 protected static void playAudioResource(String audioResourceName) {
		 ClassLoader cl = GameResources.class.getClassLoader();
		 URL resourceURL = cl.getResource(audioResourceName);
		 if (resourceURL != null) {
			 AudioClip sound = Applet.newAudioClip(resourceURL);
			 sound.play();
		 }
	 }

	 
	 protected static BufferedImage getBufferedImage(String urlString) {
		 try {
			 BufferedImage bufferedImage = ImageIO.read(new File(urlString));
			 return bufferedImage;
		 } 
		 catch (IOException e) { 
			 System.out.println("Image not found.");
			 return null;		   
		 }
	 }

	 
	 protected static BufferedImage getARGBImage(BufferedImage bufferedImage) {
		 BufferedImage ARGBImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		 ARGBImage.getGraphics().drawImage(bufferedImage, 0, 0, null);
		 return ARGBImage;
	 }
	 
	 
	 public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
	        BufferedImage scaledImage = null;
	        if (imageToScale != null) {
	            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
	            Graphics2D graphics2D = scaledImage.createGraphics();
	            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
	            graphics2D.dispose();
	        }
	        return scaledImage;
	    }
} // End of class GameResources
