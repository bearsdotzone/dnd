import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class RemoveListener implements ActionListener {
	NewSheet newSheet;

	public RemoveListener(NewSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			newSheet.removeSpellsFromList(newSheet.currentSpells
					.getSelectedValuesList());
			newSheet.currentSpells.clearSelection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
