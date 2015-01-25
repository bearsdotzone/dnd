import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NewSheet {

	public JFrame mainWindow;
	public JPanel contentPane;
	public JPanel menuBar;
	public JScrollPane scrollPane;
	public JButton refresh;
	public JSlider slider;
	public JTextField textField;
	public double scale;
	public JPanel scrollPanel;
	public JPanel spellSlots;
	public JPanel key;
	public int[] maxSlots;

	public NewSheet() {

		mainWindow = new JFrame("Spell Sheet");
		mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setSize(mainWindow.getWidth(), mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top);
		contentPane.setPreferredSize(new Dimension(mainWindow.getWidth(),
				mainWindow.getHeight() - mainWindow.getInsets().bottom
						- mainWindow.getInsets().top));
		contentPane.setLocation(0, 0);
		contentPane.setBackground(Color.RED);

		menuBar = new JPanel(null);
		menuBar.setSize(mainWindow.getWidth(), 50);
		menuBar.setPreferredSize(new Dimension(mainWindow.getWidth(), 50));
		menuBar.setLocation(0, 0);
		menuBar.setBackground(Color.GREEN);
		
		scale = 0.5;
		
		key = new JPanel(new GridLayout(3,1));
		key.setSize(100, 50);
		key.setLocation(400,0);
		
		String[] words = {"Level", "Max", "Used"};
		
		for(String a : words){
			JLabel temp = new JLabel(a);
			temp.setHorizontalAlignment(JLabel.CENTER);
			key.add(temp);
		}
		
		menuBar.add(key);
		
		maxSlots = new int[9];
		
		spellSlots = new JPanel(new GridLayout(3,9));
		spellSlots.setSize(600,50);
		spellSlots.setLocation(500,0);
		spellSlots.setBackground(Color.MAGENTA);
		
		for(int a = 0; a < 9; a++){
			JLabel temp = new JLabel(a+1+"");
			temp.setHorizontalAlignment(JLabel.CENTER);
			spellSlots.add(temp);
		}
		for(int a = 0; a < 9; a++){
			JLabel temp = new JLabel("0");
			temp.setHorizontalAlignment(JLabel.CENTER);
			temp.addMouseListener(new SpellSlotListener(temp, a, this));
			spellSlots.add(temp);
		}
		for(int a = 0; a < 9; a++){
			JLabel temp = new JLabel("0");
			temp.setHorizontalAlignment(JLabel.CENTER);
			temp.addMouseListener(new UsedSpellSlotListener(temp, a, this));
			spellSlots.add(temp);
		}
		
		menuBar.add(spellSlots);
		
		slider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 50);
		slider.setSize(200, 50);
		slider.setLocation(0, 0);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(new SpellSliderListener(this));

		menuBar.add(slider);

		textField = new JTextField("0");
		textField.setSize(200, 50);
		textField.setLocation(200, 0);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		textField.addKeyListener(new SpellNumberListener(this));

		menuBar.add(textField);

		scrollPanel = new JPanel(new WrapLayout());
		scrollPanel.setBackground(Color.CYAN);
		scrollPanel.setMinimumSize(new Dimension(800,600));
		
		scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setSize(mainWindow.getWidth(), mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top
				- 50);
		scrollPane.setPreferredSize(new Dimension(mainWindow.getWidth(),
				mainWindow.getHeight() - mainWindow.getInsets().bottom
						- mainWindow.getInsets().top - 50));
		scrollPane.setLocation(0, 0);
		scrollPane.setBackground(Color.ORANGE);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		contentPane.add(menuBar, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.revalidate();
		scrollPane.repaint();
		scrollPanel.revalidate();
		scrollPanel.repaint();

		mainWindow.setContentPane(contentPane);
	}

	public void addSpells() {
		DecimalFormat df = new DecimalFormat("00");
		scrollPanel.removeAll();
		for (double a = 1; a <= Integer.parseInt(textField.getText()); a++) {
			ImageIcon ii = scale("Spell_" + df.format(a) + ".gif", scale);
			JButton temp = new JButton(ii);
			temp.setSize((int)(730 * scale), (int)(1020 * scale));
			scrollPanel.add(temp);
		}
		scrollPane.revalidate();
		scrollPane.repaint();
		scrollPanel.revalidate();
		scrollPanel.repaint();
	}

	public ImageIcon scale(String s, double scale) {
		ImageIcon temp = null;
		try {
			temp = new ImageIcon(NewSheet.class.getResource(s));
			if(scale==0.0)
				scale=0.01;
			temp.setImage(temp.getImage().getScaledInstance(
					(int) (temp.getIconWidth() * scale),
					(int) (temp.getIconHeight() * scale), Image.SCALE_DEFAULT));
		} catch (NullPointerException e) {

		}
		return temp;
	}

	public static void main(String[] args) {
		NewSheet ns = new NewSheet();
	}
}
