import java.io.IOException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpellSliderListener implements ChangeListener {

	NewSheet newSheet;
	
	public SpellSliderListener(NewSheet newSheet){
		this.newSheet = newSheet;
	}
	
	public void stateChanged(ChangeEvent arg0) {
		newSheet.scale = newSheet.slider.getValue() / 100.0;
		try {
			newSheet.addSpells();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}