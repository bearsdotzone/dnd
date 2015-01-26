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
			for (String a : newSheet.currentSpells.getSelectedValuesList()) {
				newSheet.hm.removeElement(a);
				if (!newSheet.lm.contains(a))
					newSheet.lm.addElement(a);
			}
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
			newSheet.currentSpells.clearSelection();
			newSheet.addSpells();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
