import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClearListener implements ActionListener {
	SpellSheet newSheet;

	public ClearListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			newSheet.clearUsed();
		} catch (IOException e) {

		}
	}

}
