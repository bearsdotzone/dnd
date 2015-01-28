import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class SmallListListener extends MouseAdapter {

	SpellSheet newSheet;

	public SmallListListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			try {
				newSheet.removeSpellsFromList(newSheet.smallList
						.getSelectedValuesList());
				newSheet.smallList.clearSelection();
			} catch (IOException e1) {

			}
		}
	}

}
