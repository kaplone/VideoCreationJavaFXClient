package utils;

import java.io.File;
import java.io.IOException;

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


	public static int split(File media, File outDir) throws IOException, JCodecException {
		
		FileChannelWrapper ch = null;
		try {
		    ch = NIOUtils.readableFileChannel(media);
		    FrameGrab frameGrab = new FrameGrab(ch);
		    int i = 0;
		    while (true) {
		    	try {
				  ImageIO.write(frameGrab.getFrame(), "png",
				    new File(outDir, String.format("frame_%08d.png", i)));
		    	}
		    	catch (NullPointerException n){
		    		break;
		    	}

				  i++;
		    }
		    return i;


		} finally {
		    NIOUtils.closeQuietly(ch);
		    
		    temp = new Image("file://" + outDir + String.format("/frame_%08d.png", 0));
		    width.set(temp.getWidth());
		    height.set(temp.getHeight());
		}
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
