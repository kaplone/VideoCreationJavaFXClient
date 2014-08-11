package Multimedia.splitter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.SeekableByteChannel;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws IOException, JCodecException
    {
		for (int e = 0; e < 100; e++){
			BufferedImage frame = FrameGrab.getFrame(new File("/home/david/TESTS_racket/move/video_sources/Video_Final (2).mp4"), e);
			ImageIO.write(frame, "png", new File(String.format("/home/david/Bureau/img/frame_%08d.png", e)));
		}
		System.out.println( "Hello World!" );
		
//		//double startSec = 1.632;
//		FrameGrab grab = new FrameGrab( (SeekableByteChannel) new File("/home/david/TESTS_racket/move/video_sources/Video_Final (2).mp4"));
//		//grab.seekToSecondPrecise(startSec);
//		for (int i = 0; i < 100; i++) {
//		  ImageIO.write(grab.getFrame(), "png",
//		    new File(System.getProperty("user.home"), String.format("Desktop/img/frame_%08d.png", i)));
		
//		}
    }
}
