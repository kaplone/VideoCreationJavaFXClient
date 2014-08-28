package utils;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ListCellUtils {
	
	public static void populateMediasCells(ObservableList<ImportedMedia> mediaArray, ListView<ImportedMedia> medias){
		
		
		
		medias.setItems(mediaArray);
		medias.setCellFactory(new Callback<ListView<ImportedMedia>, ListCell<ImportedMedia>>() {
			@Override
			public ListCell<ImportedMedia> call(ListView<ImportedMedia> param) {
				ListCell<ImportedMedia> cell = new ListCell<ImportedMedia>(){
					@Override
					public void updateItem(ImportedMedia item, boolean empty){
						super.updateItem(item, empty);
						if (item != null){
							setText(item.nameProperty().get());
						}
					}
				};
				return cell;
			}
		});	
	}
}
