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

public class CharacterListListener implements ActionListener {

	JComboBox<String> characterList;
	boolean delete;

	public CharacterListListener(JComboBox<String> characterList, boolean delete) {
		this.characterList = characterList;
		this.delete = delete;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (delete) {
			new File("character/" + characterList.getSelectedItem() + ".txt").delete();
			characterList.removeItem(characterList.getSelectedItem());

		} else {
			String item = (String) (JOptionPane
					.showInputDialog("Character Name:"));
			characterList.addItem(item);
			FileWriter fw = null;
			try {
				fw = new FileWriter("character/" + item + ".txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
