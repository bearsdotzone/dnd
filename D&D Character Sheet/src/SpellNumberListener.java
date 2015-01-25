import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


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
		newSheet.addSpells();
	}

	public void keyReleased(KeyEvent arg0) {
		if(newSheet.textField.getText().length() == 0){
			newSheet.textField.setText("0");
		}
		newSheet.textField.setText(newSheet.textField.getText().replaceAll("\\D+", ""));
		newSheet.addSpells();
	}

	public void keyTyped(KeyEvent arg0) {
		if(newSheet.textField.getText().length() == 0){
			newSheet.textField.setText("0");
		}
		newSheet.textField.setText(newSheet.textField.getText().replaceAll("\\D+", ""));
		newSheet.addSpells();
	}

}
