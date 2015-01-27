import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CharacterChangeListener implements ActionListener {

	public NewSheet newSheet;

	public CharacterChangeListener(NewSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			newSheet.loadFromText();
		} catch (IOException e) {

		}

	}

}
