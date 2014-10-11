package someTests;

import java.io.File;
import java.io.IOException;

public class FfmpegBuilder {

	public static void main(String[] args) {
		
		 ProcessBuilder pb = new ProcessBuilder("ffmpeg",
                                                "-i",
                                                "/home/david/TESTS_racket/move/video_sources/mjpeg_544x948.avi",
                                                "-f",
                                                "image2",
                                                "-qscale",
                                                "1",
                                                "/home/david/VideoCreation/medias/mjpeg_544x948.avi/frame_%08d.png");
		 //Map<String, String> env = pb.environment();
		 pb.redirectErrorStream(); 
//		 env.put("VAR1", "myValue");
//		 env.remove("OTHERVAR");
//		 env.put("VAR2", env.get("VAR1") + "suffix");
		 
		 //File repertoire = new File("/home/david/VideoCreation/medias/mjpeg_544x948.avi");
		 //repertoire.mkdir();
		 try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

	}

}
