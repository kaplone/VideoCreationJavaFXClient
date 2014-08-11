package applicationFX;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class VCGUIController {
	
	
	@FXML
	private Label frameNumber;
	@FXML
	private Slider cursorFrame1;
	@FXML
	private ImageView view_0;
	@FXML
	private Button play;
	@FXML
	private Button stop;
	
	private double f;
	private Image nouvelle ;
	
	boolean run = true;
	Thread maj;
	
	
	@FXML
	 protected void CursorChanged() {
		
		f = cursorFrame1.getValue();
		
		System.out.println(f);
		frameNumber.setText("" + (int)f);
		nouvelle = new Image(String.format("file:///home/autor/git/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/frames_WakeApp/image2_%05d.png", (int)f));
		view_0.setImage(nouvelle);
		
	 }
	
	@FXML
	 protected void CursorKeyPressed() {
		
		f = cursorFrame1.getValue();
        System.out.println(f);
		nouvelle = new Image(String.format("file:///home/autor/git/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/frames_WakeApp/image2_%05d.png", (int)f));
		view_0.setImage(nouvelle);
		frameNumber.setText("" + (int)f);
		
	 }
	
	
	@FXML
	 protected void OnPlayClicked() {
		
		run = true;
		System.out.println("" + run);
		f = cursorFrame1.getValue();
		maj = new Thread(viewTask);
		maj.start();
	    }
	
	@FXML
	 protected void OnStopClicked() {
		
		run = false;
		
		System.out.println("" + run);

	    }


	
	public void miseAJour(){
		nouvelle = new Image(String.format("file:///home/autor/git/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/frames_WakeApp/image2_%05d.png", (int)f));
		view_0.setImage(nouvelle);
		frameNumber.setText("" + (int)f);
		cursorFrame1.setValue(f);
	}

	


	Task<?> viewTask = new Task<Object>() {
		@Override
	    public Void call() throws Exception{
			
			System.out.println("play");
			while (run){
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					f++;
					//System.out.println((int)f);
	    	
	    	        Platform.runLater(new Runnable() {
	    		        public void run(){
	    			        miseAJour();
	    		        }
	    	        });
			}
	    	
	    	
			return null;
	    }
	};
	

}
