package applicationFX;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VCGUIController {
	
	
	@FXML
	private Label frameNumber;
	@FXML
	private Slider cursorFrame1;
	@FXML
	private ImageView view_0;
	@FXML
	private Button play;
	
	private double f;
	private Image nouvelle;
	
	@FXML
	 protected void CursorChanged() {
		
		f = cursorFrame1.getValue();
		
		System.out.println(f);
		frameNumber.setText("" + (int)f);
		nouvelle = new Image(String.format("file:///home/david/git/OVERLAY_ClientSide/OVERLAY_ClientSide/images/frames_WakeApp/image2_%05d.png", (int)f));
		view_0.setImage(nouvelle);
		
	 }
	
	@FXML
	 protected void CursorKeyPressed() {
		
		f = cursorFrame1.getValue();
        System.out.println(f);
		nouvelle = new Image(String.format("file:///home/david/git/OVERLAY_ClientSide/OVERLAY_ClientSide/images/frames_WakeApp/image2_%05d.png", (int)f));
		view_0.setImage(nouvelle);
		frameNumber.setText("" + (int)f);
		
	 }
	
	
	@FXML
	 protected void OnPlayClicked() {
		
		f = cursorFrame1.getValue();
		f=(int)f;
		boolean run = true;
		while (run){
			//nouvelle = new Image(String.format("file:///home/david/git/OVERLAY_ClientSide/OVERLAY_ClientSide/images/frames_WakeApp/image2_%05d.png", f));
			//view_0.setImage(nouvelle);
			
			try { Thread.sleep(200); }
            catch (Exception e){}

			f++;
			System.out.println(f);
			frameNumber.setText("" + f);
			
			if (f > 1000){
				run = false;
			}
		}
		
	 }
	

}
