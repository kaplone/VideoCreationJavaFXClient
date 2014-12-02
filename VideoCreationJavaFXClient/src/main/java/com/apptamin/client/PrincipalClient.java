package com.apptamin.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class PrincipalClient {

	public static void principalClient(Point[] PP) throws IOException {
		
		File path = new File("images/source_pictures");
		File pathOut = new File("/home/kaplone/VideoCreation/temp/mjpeg_544x948.avi");
		
		BufferedImage device = ImageIO.read(new File(path, "Main_F_iPhones_1.png"));
		int w = device.getWidth();
		int h = device.getHeight();
		Point pointDevice = new Point(w/2, h/2);
		
		BufferedImage main = ImageIO.read(new File(path, "Main_F_Clic_1_.png"));
		main = ImageUtils.scale(main, 0.4);
		Point pointDoigt = new Point(650, 120);
		
		BufferedImage fond = ImageIO.read(new File(path, "vert.png"));
		
		BufferedImage composition = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		List<String> frames = OtherUtils.sortedList(new File("/home/kaplone/VideoCreation/medias/mjpeg_544x948.avi"));
		
		Point temp;
		
		for (Point p : PP){
			ImageUtils.writeImage(ImageUtils.compose4Layers(fond, device, contenu(p.getImageNumber()), main, p.getCoordX(), p.getCoordY(), composition), pathOut, String.format("frame_mvt_%08d.png", p.getImageNumber()));
		}		
		for (int i = 1; i < frames.size(); i++){
		    temp = pointDoigt.fingerRelativeToDevice(0, 0);// remplacer  (0, 0) par les accesseurs à la liste des coordonnees
		    ImageUtils.writeImage(ImageUtils.compose4Layers(fond, device, contenu(i), main, temp.getCoordX(), temp.getCoordY(), composition), pathOut, String.format("frame_%08d.png", i));
		}
		
	}
	
	public static BufferedImage contenu(int i) throws IOException {
		File pathFrames = new File("/home/kaplone/VideoCreation/medias/mjpeg_544x948.avi");
		BufferedImage contenu = ImageIO.read(new File(pathFrames, String.format("frame_%08d.png", i)));
		contenu = ImageUtils.scale(contenu, 0.7);
		
		return contenu;
	}

}
