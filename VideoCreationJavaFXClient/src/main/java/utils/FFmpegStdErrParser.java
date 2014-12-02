package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FFmpegStdErrParser implements Runnable {
	
	int h = 0;
	int w = 0;

    private final InputStream inputStream;

    FFmpegStdErrParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private BufferedReader getBufferedReader(InputStream is) {
        return new BufferedReader(new InputStreamReader(is));
    }

    @Override
    public void run() {
        BufferedReader br = getBufferedReader(inputStream);
        String ligne = "";
        try {
            while ((ligne = br.readLine()) != null) {
                System.out.println(ligne);
                if (ligne.startsWith("    Stream #0:0(eng): Video: png,")){
	                w = Integer.parseInt(ligne.split(", ")[2].split(" ")[0].split("x")[0]);
	                h = Integer.parseInt(ligne.split(", ")[2].split(" ")[0].split("x")[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int[] getSize(){
    	return new int[] {w, h};
    }
}

