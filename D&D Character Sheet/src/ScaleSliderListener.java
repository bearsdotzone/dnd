import java.io.IOException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ScaleSliderListener implements ChangeListener {

	SpellSheet newSheet;

	public ScaleSliderListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void stateChanged(ChangeEvent arg0) {
		newSheet.scale = newSheet.slider.getValue() / 100.0;
		newSheet.resizeSpells();
	}
}
