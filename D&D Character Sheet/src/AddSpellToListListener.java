import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AddSpellToListListener implements ActionListener {

	SpellSheet newSheet;

	public AddSpellToListListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			newSheet.addSpellsToList(newSheet.bigList.getSelectedValuesList());
			newSheet.bigList.clearSelection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
