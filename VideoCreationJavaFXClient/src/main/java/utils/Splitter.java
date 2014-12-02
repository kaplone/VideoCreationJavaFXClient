package utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.codecs.h264.io.model.Frame;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.NIOUtils;

public class Splitter {
	
	private static final DoubleProperty width = new SimpleDoubleProperty();
	private static final DoubleProperty height = new SimpleDoubleProperty();
	
	static Image temp;


	public static int split(File media, File outDirFull, File outDirPrev) throws IOException, JCodecException {
		
		int nbFrames = 0;
		
		try {
			//ProcessBuilder pb_full = new ProcessBuilder("ffmpeg","-i", media, outdir_full+"/frame_%05d.png");
            ProcessBuilder pb_prev = new ProcessBuilder("ffmpeg","-i", media.toString(),"-vf", "scale=iw/2.96:ih/2.96", outDirPrev+"/frame_%08d.png");
            //pb.directory(outDirFull);
            pb_prev.directory(outDirPrev);
            
            Process p_prev = pb_prev.start();
//            AfficheurFlux fluxSortie = new AfficheurFlux(p.getInputStream());
            FFmpegStdErrParser fluxErreur = new FFmpegStdErrParser(p_prev.getErrorStream());
//            new Thread(fluxSortie).start();
            new Thread(fluxErreur).start();
            
            p_prev.waitFor();
            
            nbFrames = outDirPrev.listFiles().length;
            width.set(fluxErreur.getSize()[0]);
            height.set(fluxErreur.getSize()[0]);
            
        } catch (IOException e) {
            e.printStackTrace();	
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		return nbFrames;
	}
	
	public static double getWidth(){
		return width.get();
	}
	public static double getHeight(){
		return height.get();
	}
	public static DoubleProperty widthProperty(){
		return width;
	}
	public static DoubleProperty heightProperty(){
		return height;
	}
}
