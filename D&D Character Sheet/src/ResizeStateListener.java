import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class ResizeStateListener implements WindowStateListener {

	NewSheet newSheet;

	public ResizeStateListener(NewSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void windowStateChanged(WindowEvent arg0) {
		newSheet.resize();
	}

}
