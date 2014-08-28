package utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import org.jcodec.api.JCodecException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	final private static ObjectMapper mapper = new ObjectMapper();
	

	public static void loadProject(File file, ObservableList<ImportedMedia> mediaArray, ListView<ImportedMedia> medias) {
		
		mediaArray.clear();
		
		JsonNode root;
		try {
			root = mapper.readTree(file);
			JsonNode mediasList = root.path("medias");
			Iterator<JsonNode> elements = mediasList.elements();
			while(elements.hasNext()){
				mediaArray.add(new ImportedMedia(elements.next()));
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JCodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListCellUtils.populateMediasCells(mediaArray, medias);
	}

}
