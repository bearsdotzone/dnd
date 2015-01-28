import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class RemoveSpellFromListListener implements ActionListener {
	SpellSheet newSheet;

	public RemoveSpellFromListListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			newSheet.removeSpellsFromList(newSheet.smallList
					.getSelectedValuesList());
			newSheet.smallList.clearSelection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
