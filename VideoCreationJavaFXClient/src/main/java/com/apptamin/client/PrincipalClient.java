package com.apptamin.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class PrincipalClient {
	
	

//	public static void principalClient(Point[] PP) throws IOException {
//
//		List<String> frames = OtherUtils.sortedList(new File("/home/kaplone/VideoCreation/medias/temp/tt_rr_001_720x1280_6.mp4"));
//		;
//		Point temp;
//		
//		for (Point p : PP){
//			ImageUtils.writeImage(ImageUtils.compose4Layers(fond, device, contenu(p.getImageNumber()), main, p.getCoordX(), p.getCoordY(), composition), pathOut, String.format("frame_mvt_%08d.png", p.getImageNumber()));
//		}		
////		for (int i = 1; i < frames.size(); i++){
////		    temp = pointDoigt.fingerRelativeToDevice(0, 0);// remplacer  (0, 0) par les accesseurs à la liste des coordonnees
////		    ImageUtils.writeImage(ImageUtils.compose4Layers(fond, device, contenu(i), main, temp.getCoordX(), temp.getCoordY(), composition), pathOut, String.format("frame_mvt_%08d.png", i));
////		}
//		
//	}
	
	public static BufferedImage getCompo(int number, Point [] points) throws IOException{
		
		File path = new File("images/source_pictures");
		File pathOut = new File("/home/kaplone/VideoCreation/temp/acer_catpure_003_720x1280@6.mp4");
		
		BufferedImage device = ImageIO.read(new File(path, "Main_F_iPhones_1_prev1.png"));
		int w = 768;//device.getWidth();
		int h = 432;//device.getHeight();
		Point pointDevice = new Point(w/2, h/2);
		
		BufferedImage main = ImageIO.read(new File(path, "Main_F_Clic_1_prev1.png"));
		Point pointDoigt = new Point(134, 30);
		
		BufferedImage fond = ImageIO.read(new File(path, "4217-fond_prev1.png"));
		
		BufferedImage composition = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
		try{
			return ImageUtils.compose4Layers(fond,
	                                         device,
	                                         contenu(number),
	                                         main,
	                                         points[number].getCoordX(),
	                                         points[number].getCoordY(),
	                                         composition);
		}catch (NullPointerException npe){
			number = 1;
			return ImageUtils.compose4Layers(fond,
                    device,
                    contenu(number),
                    main,
                    200,
                    200,
                    composition);
		}
	}
	
	public static BufferedImage contenu(int i) throws IOException {
		File pathFrames = new File("/home/kaplone/VideoCreation/medias/temp/acer_catpure_003_720x1280@6.mp4");
		BufferedImage contenu = ImageIO.read(new File(pathFrames, String.format("frame_%08d.png", i)));
		contenu = ImageUtils.scale(contenu, 0.39); // implémenter une fonction de calcul de la valeur exacte
		
		return contenu;
	}

}
