import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CharacterDropdownListener implements ActionListener {

	public SpellSheet newSheet;

	public CharacterDropdownListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			newSheet.loadFromText();
		} catch (IOException e) {

		}

	}

}
