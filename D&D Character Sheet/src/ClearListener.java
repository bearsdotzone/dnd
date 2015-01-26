import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClearListener implements ActionListener {
	NewSheet newSheet;

	public ClearListener(NewSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			newSheet.clearUsed();
		} catch (IOException e) {

		}
	}

}
