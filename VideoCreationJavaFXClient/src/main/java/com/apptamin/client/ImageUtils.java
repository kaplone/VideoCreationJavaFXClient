package com.apptamin.client;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	static final int posXDevice = 0;
	static final int posYDevice = 0;
	static final int posXContent = 171;
	static final int posYContent  = 98;
	
	public static BufferedImage scale(BufferedImage bi, double scaleValue) {
        AffineTransform tx = new AffineTransform();
        tx.scale(scaleValue, scaleValue);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage biNew = new BufferedImage( (int) (bi.getWidth() * scaleValue),
                (int) (bi.getHeight() * scaleValue),
                bi.getType());
        return op.filter(bi, biNew);
	}
	
	public static Image scaleFX(Image bi, double scaleValue) {
        AffineTransform tx = new AffineTransform();
        tx.scale(scaleValue, scaleValue);

        return bi;
	}
	
	public static BufferedImage compose4Layers(BufferedImage background,
			                                        BufferedImage device,
			                                        BufferedImage content,
			                                        BufferedImage hand, double posXMain, double posYMain,
			                                        BufferedImage finaleComp){
		Graphics2D g = finaleComp.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(background, 0, 0, null);
		g.drawImage(content, posXContent, posYContent, null);
		g.drawImage(device, posXDevice, posYDevice, null);
		g.drawImage(hand, (int)posXMain, (int)posYMain, null);
		g.dispose();
		
		return finaleComp;
	}
	
	public static Image compose4LayersFX(Image background,
							             Image device,
							             Image content,
							             Image hand,
							             double posXMain,
							             double posYMain,
							             Image finaleComp){
		return finaleComp;
}
	
	public static void writeImage (BufferedImage image, File path, String name) throws IOException{
		ImageIO.write(image, "PNG", new File(path, name));
	}

}
