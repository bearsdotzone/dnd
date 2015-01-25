import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


public class SpellSheet {

	public JFrame window;

	public SpellSheet() {
		int width = 730;
		int height = 1020;
		Dimension wh = new Dimension(730, 1020);
		Dimension mh = new Dimension(2560, 1400);
		double scale = 0.5;
		int spells = 16;
		window = new JFrame("Spell Sheet");
		window.setSize(mh);
		window.setPreferredSize(mh);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel jp = new JPanel(new GridLayout((spells/(window.getWidth()/(int)(scale*width)))+1,(int)Math.ceil(((double)window.getWidth())/(int)(scale*width))));
		System.out.println((int)Math.ceil(((double)window.getWidth())/width));
		JScrollPane sp = new JScrollPane(jp);
		window.setContentPane(sp);

		DecimalFormat df = new DecimalFormat("00");
		for (double a = 1.0; a <= spells; a += 1.0) {
			try {
				ImageIcon ic = scale(("Dave_" + df.format(a)
								+ ".gif"), 0.5);
				JButton jl = new JButton(ic);
				jl.setSize(ic.getIconWidth(), ic.getIconHeight());
				jl.setPreferredSize(new Dimension(ic.getIconWidth(), ic.getIconHeight()));
				jl.setLocation(0, 0);
				jp.add(jl);
			} catch (NullPointerException e) {

			}

		}
		jp.revalidate();
		jp.repaint();
		jp.revalidate();
	}

	public static void main(String[] args) {
		SpellSheet ss = new SpellSheet();
		Scaler s = new Scaler(ss);
	}
	
	public ImageIcon scale(String s, double scale) {
		ImageIcon temp = new ImageIcon(
				SpellSheet.class.getResource(s));
		temp.setImage(temp.getImage().getScaledInstance(
				(int) (temp.getIconWidth() * scale),
				(int) (temp.getIconHeight() * scale), Image.SCALE_DEFAULT));

		return temp;
	}
	
}
class Scaler {
	JFrame potato;
	JPanel contentPane;
	
	public Scaler(Object a) {
		potato = new JFrame();
		contentPane = new JPanel(new FlowLayout());
		potato.setSize(new Dimension(200,100));
		potato.setPreferredSize(new Dimension(200,100));
		potato.setVisible(true);
		potato.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		JButton jb = new JButton("Dispose");
		jb.addActionListener(new Al(a, this));
		contentPane.add(jb);
		potato.setContentPane(contentPane);
	}

	
}
class Al implements ActionListener {
	Object a, b;
	public Al(Object a, Object b) {
		this.a = a;
		this.b = b;
	}
	public void actionPerformed(ActionEvent arg0) {
		((SpellSheet)a).window.dispose();
		SpellSheet ss = new SpellSheet();
		Scaler s = new Scaler(ss);
		((Scaler)b).potato.dispose();
	}
}
