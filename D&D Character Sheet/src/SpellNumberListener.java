import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


public class SpellNumberListener implements KeyListener {
	
	NewSheet newSheet;
	
	public SpellNumberListener(NewSheet newSheet){
		this.newSheet = newSheet;
	}
	
	public void keyPressed(KeyEvent arg0) {
		if(newSheet.textField.getText().length() == 0){
			newSheet.textField.setText("0");
		}
		newSheet.textField.setText(newSheet.textField.getText().replaceAll("\\D+", ""));
		try {
			newSheet.addSpells();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void keyReleased(KeyEvent arg0) {
		if(newSheet.textField.getText().length() == 0){
			newSheet.textField.setText("0");
		}
		newSheet.textField.setText(newSheet.textField.getText().replaceAll("\\D+", ""));
		try {
			newSheet.addSpells();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void keyTyped(KeyEvent arg0) {
		if(newSheet.textField.getText().length() == 0){
			newSheet.textField.setText("0");
		}
		newSheet.textField.setText(newSheet.textField.getText().replaceAll("\\D+", ""));
		try {
			newSheet.addSpells();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
