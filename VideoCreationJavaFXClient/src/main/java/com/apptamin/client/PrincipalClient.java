package com.apptamin.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import utils.FFmpegStdErrParser;

public class PrincipalClient {
	
	public static void makeCompoMagick(String current, Point [] points) throws IOException{
	
	for (Point p : points){
		
		System.out.println(p.getImageNumber());
	
		try {			
	        ProcessBuilder pb_compoMagick = new ProcessBuilder("convert",
	        		                                           "-size",
                                                               "768x432",
                                                               "-composite",
                                                               "/home/kaplone/Apptamin_Eclipse/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/source_pictures/4217-fond_prev1.png",
                                                               String.format("/home/kaplone/VideoCreation/medias/temp/%s/frame_%08d.png",current, p.getImageNumber()),
                                                               "-geometry",
                                                               "83x147+171+98",
                                                               "/home/kaplone/Apptamin_Eclipse/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/source_pictures/Main_F_iPhones_1_prev1.png",
                                                               "-page",
                                                               String.format("+%f+%f", p.getCoordX(), p.getCoordY()),
                                                               "/home/kaplone/Apptamin_Eclipse/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/source_pictures/Main_F_Clic_1_prev1.png",
                                                               "-flatten",
                                                               String.format("/tmp/tt_rr_001_720x1280_6.mp4/frame_%08d.png",p.getImageNumber()));
	        
	        
	        
	        Process p_compoMagick = pb_compoMagick.start();
	        
	        p_compoMagick.waitFor();
	        
	    } catch (IOException e) {
	        e.printStackTrace();	
	    }
	    catch (InterruptedException e) {
	        e.printStackTrace();
	    }	
	}
}
	
	public static Image[] makeCompo(String current, Point [] points) throws IOException{
		
		Image[] imageList =new Image[points.length];
		
		File path = new File("images/source_pictures");
		File pathOut = new File("/home/kaplone/VideoCreation/temp", current);
		
		BufferedImage device = ImageIO.read(new File(path, "Main_F_iPhones_1_prev1.png"));
		int w = 768;//device.getWidth();
		int h = 432;//device.getHeight();
		Point pointDevice = new Point(w/2, h/2);
		
		BufferedImage main = ImageIO.read(new File(path, "Main_F_Clic_1_prev1.png"));
		Point pointDoigt = new Point(134, 30);
		
		BufferedImage fond = ImageIO.read(new File(path, "4217-fond_prev1.png"));
		
		BufferedImage composition = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		for (int j = 1; j < imageList.length; j++ ){
			imageList[j] = SwingFXUtils.toFXImage(ImageUtils.compose4Layers(fond,
	                                         device,
	                                         contenu(current, j),
	                                         main,
	                                         points[j].getCoordX(),
	                                         points[j].getCoordY(),
	                                         composition),null);
			}
		return imageList;	
	}
	
	public static BufferedImage contenu(String current, int i) throws IOException {
		File pathFrames = new File("/home/kaplone/VideoCreation/medias/temp", current);
		BufferedImage contenu = ImageIO.read(new File(pathFrames, String.format("frame_%08d.png", i)));
		contenu = ImageUtils.scale(contenu, 0.34); // implémenter une fonction de calcul de la valeur exacte
		
		return contenu;
	}
}
