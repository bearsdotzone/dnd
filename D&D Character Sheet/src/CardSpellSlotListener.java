import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class CardSpellSlotListener implements MouseListener {

	JButton temp;
	int index;
	NewSheet newSheet;

	public CardSpellSlotListener(JButton temp, int index, NewSheet newSheet) {
		this.temp = temp;
		this.index = index - 1;
		this.newSheet = newSheet;
	}

	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			JLabel usedSlot = JLabel.class.cast(newSheet.spellSlots
					.getComponent(index + 18));
			if (Integer.parseInt(usedSlot.getText()) + 1 <= newSheet.maxSlots[index]) {
				usedSlot.setText(Integer.parseInt(usedSlot.getText()) + 1 + "");
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
