import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;

public class CardSpellSlotListener implements MouseListener {

	JButton temp;
	int index;
	SpellSheet newSheet;

	public CardSpellSlotListener(JButton temp, int index, SpellSheet newSheet) {
		this.temp = temp;
		this.index = index - 1;
		this.newSheet = newSheet;
	}

	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			JLabel usedSlot = JLabel.class.cast(newSheet.spellSlots
					.getComponent(index + 18));
			if (Integer.parseInt(usedSlot.getText()) + 1 <= newSheet.maxSlots[index]) {
				temp.setBackground(new JButton().getBackground());
				usedSlot.setText(Integer.parseInt(usedSlot.getText()) + 1 + "");
				newSheet.usedSlots[index] = newSheet.usedSlots[index] + 1;
				try {
					newSheet.saveToText();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				temp.setBackground(Color.RED);
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
