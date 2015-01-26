import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ListListener implements ActionListener {

	NewSheet newSheet;

	public ListListener(NewSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			newSheet.addSpellsToList(newSheet.spellList.getSelectedValuesList());
			for (String a : newSheet.spellList.getSelectedValuesList()) {
				newSheet.lm.removeElement(a);
				if (!newSheet.hm.contains(a))
					newSheet.hm.addElement(a);
			}
			// Sort lists.
			ArrayList<String> temp = new ArrayList<String>();
			for (int b = 0; b < newSheet.hm.size(); b++)
				temp.add(newSheet.hm.getElementAt(b));
			newSheet.hm.removeAllElements();
			Collections.sort(temp);
			for (String b : temp)
				newSheet.hm.addElement(b);
			temp.clear();
			for (int b = 0; b < newSheet.lm.size(); b++)
				temp.add(newSheet.lm.getElementAt(b));
			newSheet.lm.removeAllElements();
			Collections.sort(temp);
			for (String b : temp)
				newSheet.lm.addElement(b);
			// Done sorting.
			newSheet.spellList.clearSelection();
			newSheet.addSpells();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
