import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class CharacterRemoveListener implements ActionListener {

	JComboBox<String> characterList;
	SpellSheet newSheet;

	public CharacterRemoveListener(JComboBox<String> characterList,
			SpellSheet newSheet) {
		this.characterList = characterList;
		this.newSheet = newSheet;
	}

	public void actionPerformed(ActionEvent arg0) {
		new File("character/" + characterList.getSelectedItem() + ".txt")
				.delete();
		characterList.removeItem(characterList.getSelectedItem());

	}
}
