package applicationFX;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewService extends Service<Void> {
	
	
	private Label frameNumber;
	private Slider cursorFrame1;
	private ImageView view_0;
	protected double f;
	private Image nouvelle ;
	private VCGUIController uiController;

	public ViewService(VCGUIController vcguiController) {
		this.uiController = vcguiController;
	}

	@Override
	protected Task<Void> createTask() {
		
		System.out.println("create");
		
		
		return new Task<Void>() {
			
			@Override
		    public Void call() throws Exception{
				f = uiController.getF();
				view_0 = uiController.getView_0();
				cursorFrame1 = uiController.getCursorFrame1();
				frameNumber = uiController.getFrameNumber();
				
				System.out.println("play !");
				boolean run = uiController.isRun();
				
				while (run){
					Platform.runLater(new Runnable() {
						@Override public void run() {
							nouvelle = new Image(String.format("file:///home/autor/git/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/frames_WakeApp/image2_%05d.png", (int)f));
							view_0.setImage(nouvelle);
							frameNumber.setText("" + (int)f);
							cursorFrame1.setValue(f);
						}
					});
					
					Thread.sleep(40);
					f++;
					run = uiController.isRun();
				}
				return null;
			}
	};

}
}
