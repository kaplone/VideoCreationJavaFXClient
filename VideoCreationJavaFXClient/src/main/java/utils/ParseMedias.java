package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

import org.jcodec.api.JCodecException;

public class ParseMedias {
	
	private static IntegerProperty duration = new SimpleIntegerProperty(0);
	
	public static ArrayList<ImportedMedia> ParseEvent (DragEvent event){
		
		Dragboard db = event.getDragboard();
		boolean success = false;
		
		ArrayList<ImportedMedia> newMedias = new ArrayList<ImportedMedia>();
		
        if (db.hasFiles()) {
            success = true;
            String filePath = null;
            for (File file:db.getFiles()) {
                filePath = file.getAbsolutePath();
                
                try {
                	newMedias.add(new ImportedMedia(file));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JCodecException e) {
					e.printStackTrace();
				}
            }	
        }
        event.setDropCompleted(success);
        event.consume();
        return newMedias;
	}

}
