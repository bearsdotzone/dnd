import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * The {@link AddSpellToListListener} listens for mouse clicks and adds the
 * selected spells from the spell list and adds them into the {@link SpellSheet}
 * 
 * @author Abneyus
 * 
 */
public class AddSpellToListListener implements ActionListener {

	private final Logger LOGGER = Logger.getLogger(AddSpellToListListener.class
			.getName());

	private SpellSheet newSheet;

	public AddSpellToListListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	/**
	 * Adds the selected elements from the large list into the spellsheet
	 */
	public void actionPerformed(ActionEvent arg0) {
		List<String> selectedValuesList = null;

		try {
			selectedValuesList = newSheet.bigList.getSelectedValuesList();
			newSheet.addSpellsToList(selectedValuesList);
			newSheet.bigList.clearSelection();
		} catch (IOException e) {
			String listValues = selectedValuesList == null
					|| selectedValuesList.isEmpty() ? "EMPTY_LIST" : Arrays
					.toString(selectedValuesList.toArray());
			LOGGER.severe("Failed when attempting to add spells: " + listValues);
			e.printStackTrace();
		}
	}

}
