import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class BigListListener extends MouseAdapter {

	SpellSheet newSheet;

	public BigListListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			try {
				newSheet.addSpellsToList(newSheet.bigList
						.getSelectedValuesList());
				newSheet.bigList.clearSelection();
			} catch (IOException e1) {

			}
		}
	}

}
