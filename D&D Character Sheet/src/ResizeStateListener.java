import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class ResizeStateListener implements WindowStateListener {

	SpellSheet newSheet;

	public ResizeStateListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void windowStateChanged(WindowEvent arg0) {
		newSheet.resize();
	}

}
