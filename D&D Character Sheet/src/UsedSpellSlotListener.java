import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class UsedSpellSlotListener implements MouseListener {

	JLabel temp;
	int index;
	NewSheet newSheet;

	public UsedSpellSlotListener(JLabel temp, int index, NewSheet newSheet) {
		this.temp = temp;
		this.index = index;
		this.newSheet = newSheet;
	}

	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			if (Integer.parseInt(temp.getText()) + 1 <= newSheet.maxSlots[index]) {
				temp.setText(Integer.parseInt(temp.getText()) + 1 + "");
			}
		} else if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (Integer.parseInt(temp.getText()) > 0) {
				temp.setText(Integer.parseInt(temp.getText()) - 1 + "");
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
