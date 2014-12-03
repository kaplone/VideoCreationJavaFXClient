package applicationFX;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Slider;

public class ViewService2 extends Service<Void> {
	
	private Slider slider2;
	protected double f;
	private VCGUIController uiController;

	public ViewService2(VCGUIController vcguiController) {
		this.uiController = vcguiController;
	}

	@Override
	protected Task<Void> createTask() {	
		
		return new Task<Void>() {
			
			@Override
		    public Void call() throws Exception{
				f = uiController.getF2();
				slider2 = uiController.getSlider2();
				
				while (uiController.isRun2()){
					
					Platform.runLater(new Runnable() {
						@Override public void run() {
							slider2.setValue(f);
						}
					});
					Thread.sleep(40);
					f++;
				}
				return null;
			}
	};

}
}
