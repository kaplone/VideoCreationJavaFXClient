package applicationFX;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Slider;

public class ViewService extends Service<Void> {
	
	private Slider cursorFrame1;
	protected double f;
	private VCGUIController uiController;

	public ViewService(VCGUIController vcguiController) {
		this.uiController = vcguiController;
	}

	@Override
	protected Task<Void> createTask() {		
		
		return new Task<Void>() {
			
			@Override
		    public Void call() throws Exception{
				f = uiController.getF();
				cursorFrame1 = uiController.getCursorFrame1();

				boolean run = uiController.isRun();
				
				while (run){
					Platform.runLater(new Runnable() {
						@Override public void run() {
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
