import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ResizeListener implements ComponentListener {

	SpellSheet newSheet;

	public ResizeListener(SpellSheet newSheet) {
		this.newSheet = newSheet;
	}

	public void componentHidden(ComponentEvent arg0) {

	}

	public void componentMoved(ComponentEvent arg0) {

	}

	public void componentResized(ComponentEvent arg0) {
		this.newSheet.resize();
	}

	public void componentShown(ComponentEvent arg0) {

	}

}
