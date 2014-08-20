package applicationFX;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.NIOUtils;

public class Splitter {


	public static int split(File media) throws IOException, JCodecException {
		
		FileChannelWrapper ch = null;
		try {
		    ch = NIOUtils.readableFileChannel(media);
		    FrameGrab frameGrab = new FrameGrab(ch);
		    int i = 0;
		    while (true) {
		    	try {
				  ImageIO.write(frameGrab.getFrame(), "png",
				    new File(System.getProperty("user.home"), String.format("Bureau/img/frame_%08d.png", i)));
		    	}
		    	catch (NullPointerException n){
		    		break;
		    	}

				  i++;
		    }
		    return i;


		} finally {
		    NIOUtils.closeQuietly(ch);
		}
		
	}

}
