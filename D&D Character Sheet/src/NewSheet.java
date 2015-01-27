import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	public int[] usedSlots;
	public TreeMap<String, ArrayList<String>> spells;
	public JComboBox<String> characterList;
	public JButton plusButton;
	public JButton minusButton;
	public ArrayList<SpellCard> cards;
	public JPanel spellAdd;
	public JScrollPane spellPane;
	public JList<String> spellList;
	public JButton addButton;
	public JButton clearButton;

	public JScrollPane havePane;
	public JList<String> currentSpells;
	public JButton removeButton;
	public SortedListModel hm;
	public SortedListModel lm;

	public NewSheet() throws IOException {

		new File("character").mkdirs();

		spells = new TreeMap<String, ArrayList<String>>();
		FileReader databaseInit = null;
		try {
			databaseInit = new FileReader(new File("newSpellDatabase.txt"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"No database found. Try the readme.", "",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		BufferedReader database = new BufferedReader(databaseInit);

		String read = database.readLine();
		String current = new String();
		while (read != null) {
			if (read.equals("******")) {
				read = database.readLine();
				if (read != null) {
					current = read;
					if (!spells.containsKey(current)) {
						spells.put(current, new ArrayList<String>());
					}
				}
			} else {
				ArrayList<String> temp = spells.get(current);
				if (!temp.contains(read)) {
					temp.add(read);
					spells.put(current, temp);
				}
			}
			read = database.readLine();
		}
		database.close();
		databaseInit.close();

		mainWindow = new JFrame("Spell Sheet");
		mainWindow.setSize(1280, 720);
		mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.addComponentListener(new ResizeListener(this));
		mainWindow.addWindowStateListener(new ResizeStateListener(this));

		contentPane = new JPanel(new BorderLayout());
		contentPane.setSize(mainWindow.getWidth(), mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top);
		contentPane.setPreferredSize(new Dimension(mainWindow.getWidth(),
				mainWindow.getHeight() - mainWindow.getInsets().bottom
						- mainWindow.getInsets().top));
		contentPane.setLocation(0, 0);

		menuBar = new JPanel(null);
		menuBar.setSize(mainWindow.getWidth(), 50);
		menuBar.setPreferredSize(new Dimension(mainWindow.getWidth(), 50));
		menuBar.setLocation(0, 0);

		scale = 0.5;

		key = new JPanel(new GridLayout(3, 1));
		key.setSize(75, 50);
		key.setLocation(200, 0);

		String[] words = { "Level", "Max", "Used" };

		for (String a : words) {
			JLabel temp = new JLabel(a);
			temp.setHorizontalAlignment(JLabel.CENTER);
			key.add(temp);
		}

		menuBar.add(key);

		maxSlots = new int[9];
		usedSlots = new int[9];

		spellSlots = new JPanel(new GridLayout(3, 9));
		spellSlots.setSize(425, 50);
		spellSlots.setLocation(275, 0);

		for (int a = 0; a < 9; a++) {
			JLabel temp = new JLabel(a + 1 + "");
			temp.setHorizontalAlignment(JLabel.CENTER);
			spellSlots.add(temp);
		}
		for (int a = 0; a < 9; a++) {
			JLabel temp = new JLabel("0");
			temp.setHorizontalAlignment(JLabel.CENTER);
			temp.addMouseListener(new SpellSlotListener(temp, a, this));
			spellSlots.add(temp);
		}
		for (int a = 0; a < 9; a++) {
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

		characterList = new JComboBox<String>();
		characterList.setSize(200, 25);
		characterList.setPreferredSize(new Dimension(200, 25));
		characterList.setLocation(700, 12);

		for (File a : new File("character/").listFiles())
			characterList.addItem(a.getName().replaceAll(".txt", ""));

		menuBar.add(characterList);

		minusButton = new JButton("-");
		minusButton.setSize(40, 40);
		minusButton.setSize(new Dimension(40, 40));
		minusButton.setLocation(900, 5);
		minusButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		minusButton.addActionListener(new CharacterListListener(characterList,
				true, this));

		menuBar.add(minusButton);

		plusButton = new JButton("+");
		plusButton.setSize(40, 40);
		plusButton.setSize(new Dimension(40, 40));
		plusButton.setLocation(940, 5);
		plusButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		plusButton.addActionListener(new CharacterListListener(characterList,
				false, this));

		menuBar.add(plusButton);

		scrollPanel = new JPanel(new WrapLayout());
		scrollPanel.setMinimumSize(new Dimension(800, 600));

		scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setSize(mainWindow.getWidth(), mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top
				- 50);
		scrollPane.setPreferredSize(new Dimension(mainWindow.getWidth(),
				mainWindow.getHeight() - mainWindow.getInsets().bottom
						- mainWindow.getInsets().top - 50));
		scrollPane.setLocation(0, 0);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		contentPane.add(menuBar, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		spellAdd = new JPanel(null);
		spellAdd.setSize(300, mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top);
		spellAdd.setPreferredSize(new Dimension(300, mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top));
		spellAdd.setLocation(0, 0);

		spellList = new JList<String>();
		spellList.setMinimumSize(new Dimension(150, 150));
		spellList.setLayoutOrientation(JList.VERTICAL);
		spellList.setVisibleRowCount(-1);
		spellList.setLocation(0, 0);

		currentSpells = new JList<String>();
		currentSpells.setMinimumSize(new Dimension(150, 150));
		currentSpells.setLayoutOrientation(JList.VERTICAL);
		currentSpells.setVisibleRowCount(-1);
		currentSpells.setLocation(150, 0);

		spellPane = new JScrollPane(spellList);
		spellPane.setSize(150, mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top
				- 50);
		spellPane.setPreferredSize(new Dimension(150, mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top
				- 50));
		spellPane.setLocation(0, 0);
		spellPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spellPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		havePane = new JScrollPane(currentSpells);
		havePane.setSize(150, mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top
				- 50);
		havePane.setPreferredSize(new Dimension(150, mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top
				- 50));
		havePane.setLocation(150, 0);
		havePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		havePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		lm = new SortedListModel();
		spellList.setModel(lm);

		hm = new SortedListModel();
		currentSpells.setModel(hm);

		addButton = new JButton("Add Spells");
		addButton.setSize(140, 25);
		addButton.setPreferredSize(new Dimension(300, 50));
		addButton.setLocation(980, 0);
		addButton.addActionListener(new ListListener(this));

		removeButton = new JButton("Remove Spells");
		removeButton.setSize(140, 25);
		removeButton.setPreferredSize(new Dimension(140, 50));
		removeButton.setLocation(980, 25);
		removeButton.addActionListener(new RemoveListener(this));

		menuBar.add(addButton);

		menuBar.add(removeButton);

		spellAdd.add(spellPane);

		spellAdd.add(havePane);

		contentPane.add(spellAdd, BorderLayout.EAST);

		clearButton = new JButton("Clear Used");
		clearButton.setSize(140, 25);
		clearButton.setPreferredSize(new Dimension(140, 25));
		clearButton.setLocation(1120, 0);
		clearButton.addActionListener(new ClearListener(this));

		menuBar.add(clearButton);

		characterList.addActionListener(new CharacterChangeListener(this));

		mainWindow.setContentPane(contentPane);

		for (String a : spells.keySet())
			lm.addElement(a);

		cards = new ArrayList<SpellCard>();

		loadFromText();
	}

	public void resize() {
		if (spellAdd != null) {
			spellAdd.setSize(300,
					mainWindow.getHeight() - mainWindow.getInsets().bottom
							- mainWindow.getInsets().top);
			spellAdd.setPreferredSize(new Dimension(300, mainWindow.getHeight()
					- mainWindow.getInsets().bottom
					- mainWindow.getInsets().top));
			spellPane.setSize(150,
					mainWindow.getHeight() - mainWindow.getInsets().bottom
							- mainWindow.getInsets().top - 50);
			spellPane.setPreferredSize(new Dimension(150, mainWindow
					.getHeight()
					- mainWindow.getInsets().bottom
					- mainWindow.getInsets().top - 50));
			havePane.setSize(150,
					mainWindow.getHeight() - mainWindow.getInsets().bottom
							- mainWindow.getInsets().top - 50);
			havePane.setPreferredSize(new Dimension(150, mainWindow.getHeight()
					- mainWindow.getInsets().bottom
					- mainWindow.getInsets().top - 50));
		}
	}

	public void resizeSpells() {
		for (Component a : scrollPanel.getComponents()) {
			SpellCard b = (SpellCard) a;
			b.reSize();

			scrollPanel.revalidate();
			scrollPanel.repaint();
		}
	}

	public void addSpellsToList(List<String> list2) throws IOException {
		for (String a : list2)
			cards.add(new SpellCard(this, a));
		saveToText();
		updateCards();
	}

	public void updateCards() {
		for (Component a : scrollPanel.getComponents()) {
			if (!cards.contains((SpellCard) a)) {
				scrollPanel.remove(a);
				hm.removeElement(((SpellCard) a).title);
				lm.addElement(((SpellCard) a).title);
			}
		}
		for (SpellCard a : cards) {
			if (!Arrays.asList(scrollPanel.getComponents()).contains(a)) {
				scrollPanel.add(a);
				hm.addElement(((SpellCard) a).title);
				lm.removeElement(((SpellCard) a).title);
			}
		}

		scrollPanel.revalidate();
		scrollPanel.repaint();
	}

	public void removeSpellsFromList(List<String> selectedValuesList)
			throws IOException {
		int a = 0;
		while (a < cards.size()) {
			if (selectedValuesList.contains(cards.get(a).title)) {
				cards.remove(a);
			} else {
				a += 1;
			}
		}

		saveToText();
		updateCards();
	}

	public void loadFromText() throws IOException {
		FileReader fr = new FileReader(new File("character/"
				+ characterList.getSelectedItem() + ".txt"));
		BufferedReader br = new BufferedReader(fr);
		String m[] = br.readLine().split(" ");
		String u[] = br.readLine().split(" ");
		for (int a = 0; a < 9; a++) {
			maxSlots[a] = Integer.parseInt(m[a]);
			usedSlots[a] = Integer.parseInt(u[a]);
			((JLabel) spellSlots.getComponent(9 + a)).setText(m[a]);
			((JLabel) spellSlots.getComponent(18 + a)).setText(u[a]);
		}

		cards.clear();

		String spell = br.readLine();
		while (spell != null) {
			cards.add(new SpellCard(this, spell));
			spell = br.readLine();
		}
		br.close();
		fr.close();
		updateCards();
	}

	public void saveToText() throws IOException {
		FileWriter fw = new FileWriter(new File("character/"
				+ characterList.getSelectedItem() + ".txt"));
		PrintWriter writer = new PrintWriter(fw);
		for (int a : maxSlots) {
			writer.print(a + " ");
		}
		writer.println();
		for (int a : usedSlots) {
			writer.print(a + " ");
		}
		writer.println();
		for (SpellCard a : cards) {
			writer.println(a.title);
		}
		writer.close();
		fw.close();

	}

	public void clearUsed() throws IOException {
		for (int a = 0; a < 9; a++) {
			((JLabel) spellSlots.getComponent(a + 18)).setText("0");
			usedSlots[a] = 0;
		}
		saveToText();
	}

	public static void main(String[] args) throws IOException {
		new NewSheet();
	}

}
