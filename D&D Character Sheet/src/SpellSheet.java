import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SpellSheet {

	private final Logger LOGGER = Logger.getLogger(SpellSheet.class.getName());

	public JFrame mainWindow;
	public JPanel contentPane;
	public JPanel optionsMenu;
	public JScrollPane scrollPane;
	public JButton refresh;
	public JSlider slider;
	public JTextField textField;
	public double scale;
	public JPanel spellContainer;
	public JPanel spellSlots;
	public JPanel key;
	public int[] maxSlots;
	public int[] usedSlots;
	public TreeMap<String, ArrayList<String>> spells;
	public JComboBox<String> characterList;
	public JButton plusButton;
	public JButton minusButton;
	public SortedSet<SpellCard> cards;
	public JPanel spellMenu;
	public JScrollPane bigListPane;
	public JList<String> bigList;
	public JButton addButton;
	public JButton clearButton;
	public JPanel sideBar;
	public JScrollPane smallListPane;
	public JList<String> smallList;
	public JButton removeButton;
	public SortedListModel smallListModel;
	public SortedListModel bigListModel;

	public SpellSheet() throws IOException {

		// Creates the character folder.
		new File("character").mkdirs();

		// This big old block turns the spell database into a tree map. This is
		// so I minimize read/write operations.
		spells = new TreeMap<String, ArrayList<String>>();

		FileReader databaseInit = retrieveDatabase();

		// according to retrieveDatabase, a null value indicates the user wishes
		// to exit
		if (databaseInit == null)
			System.exit(1);

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
		// End tree map block.

		// Basic setup of the main window.
		mainWindow = new JFrame("Spell Sheet");
		mainWindow.setSize(1280, 720);
		mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Adds listeners to trigger resizing of components when the main window
		// is resized.
		mainWindow.addComponentListener(new ResizeListener(this));
		mainWindow.addWindowStateListener(new ResizeStateListener(this));

		// This is where all the components end up. Putting them all in the main
		// window seems to be not best practice, or at least less than ideal.
		contentPane = new JPanel(new BorderLayout());
		contentPane.setSize(mainWindow.getWidth(), mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top);
		contentPane.setPreferredSize(new Dimension(mainWindow.getWidth(),
				mainWindow.getHeight() - mainWindow.getInsets().bottom
						- mainWindow.getInsets().top));
		contentPane.setLocation(0, 0);

		// Sets up borderlayout for east side of the screen.
		sideBar = new JPanel(null);
		sideBar.setSize(300, mainWindow.getHeight()
				- mainWindow.getInsets().top - mainWindow.getInsets().bottom);
		sideBar.setPreferredSize(new Dimension(300, mainWindow.getHeight()
				- mainWindow.getInsets().top - mainWindow.getInsets().bottom));
		sideBar.setLocation(0, 0);

		// This sets up the menu in the top right.
		optionsMenu = new JPanel(null);
		optionsMenu.setSize(300, 300);
		optionsMenu.setPreferredSize(new Dimension(300, 300));
		optionsMenu.setLocation(0, 0);
		// optionsMenu.setBackground(Color.RED);

		// This is the default UI scale for the spell cards.
		scale = 0.5;

		// This is the key to deciphering the level/max/used grid.
		key = new JPanel(new GridLayout(3, 1));
		key.setSize(30, 75);
		key.setLocation(5, 140);

		String[] words = { "L", "M", "U" };

		for (String a : words) {
			JLabel temp = new JLabel(a);
			temp.setHorizontalAlignment(JLabel.CENTER);
			temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			key.add(temp);
		}

		optionsMenu.add(key);

		maxSlots = new int[9];
		usedSlots = new int[9];

		// Simple grid with special listeners for the labels.
		spellSlots = new JPanel(new GridLayout(3, 9));
		spellSlots.setSize(255, 75);
		spellSlots.setLocation(40, 140);

		for (int a = 0; a < 9; a++) {
			JLabel temp = new JLabel(a + 1 + "");
			temp.setHorizontalAlignment(JLabel.CENTER);
			temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			spellSlots.add(temp);

		}

		for (int a = 0; a < 9; a++) {
			JLabel temp = new JLabel("0");
			temp.setHorizontalAlignment(JLabel.CENTER);
			temp.addMouseListener(new SpellSlotListener(temp, a, this));
			temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			spellSlots.add(temp);
		}

		for (int a = 0; a < 9; a++) {
			JLabel temp = new JLabel("0");
			temp.setHorizontalAlignment(JLabel.CENTER);
			temp.addMouseListener(new UsedSpellSlotListener(temp, a, this));
			temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			spellSlots.add(temp);
		}

		optionsMenu.add(spellSlots);

		// Scale slider.
		slider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 50);
		slider.setSize(290, 60);
		slider.setLocation(5, 80);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(new ScaleSliderListener(this));

		optionsMenu.add(slider);

		// Dropdown for characters.
		characterList = new JComboBox<String>();
		characterList.setSize(290, 25);
		characterList.setPreferredSize(new Dimension(290, 25));
		characterList.setLocation(5, 5);

		for (File a : new File("character/").listFiles())
			characterList.addItem(a.getName().replaceAll(".txt", ""));

		optionsMenu.add(characterList);

		// Remove characters button.
		minusButton = new JButton("Remove Selected");
		minusButton.setSize(140, 40);
		minusButton.setSize(new Dimension(140, 40));
		minusButton.setLocation(5, 35);
		minusButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		minusButton.addActionListener(new CharacterRemoveListener(
				characterList, this));

		optionsMenu.add(minusButton);

		// Add characters button.
		plusButton = new JButton("Add Character");
		plusButton.setSize(140, 40);
		plusButton.setSize(new Dimension(140, 40));
		plusButton.setLocation(155, 35);
		plusButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		plusButton.addActionListener(new CharacterAddListener(characterList,
				this));

		optionsMenu.add(plusButton);

		spellContainer = new JPanel(new WrapLayout());
		spellContainer.setMinimumSize(new Dimension(800, 600));

		scrollPane = new JScrollPane(spellContainer);
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

		// contentPane.add(optionsMenu, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		spellMenu = new JPanel(null);
		spellMenu.setSize(300, mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top
				- 300);
		spellMenu.setPreferredSize(new Dimension(300, mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top
				- 300));
		spellMenu.setLocation(0, 300);

		bigList = new JList<String>();
		bigList.setMinimumSize(new Dimension(150, 150));
		bigList.setLayoutOrientation(JList.VERTICAL);
		bigList.setVisibleRowCount(-1);
		bigList.setLocation(0, 0);

		smallList = new JList<String>();
		smallList.setMinimumSize(new Dimension(150, 150));
		smallList.setLayoutOrientation(JList.VERTICAL);
		smallList.setVisibleRowCount(-1);
		smallList.setLocation(150, 0);

		bigListPane = new JScrollPane(bigList);
		bigListPane.setSize(150,
				mainWindow.getHeight() - mainWindow.getInsets().bottom
						- mainWindow.getInsets().top - 300);
		bigListPane.setPreferredSize(new Dimension(150, mainWindow.getHeight()
				- mainWindow.getInsets().bottom - mainWindow.getInsets().top
				- 300));
		bigListPane.setLocation(0, 0);
		bigListPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		bigListPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		smallListPane = new JScrollPane(smallList);
		smallListPane.setSize(150,
				mainWindow.getHeight() - mainWindow.getInsets().bottom
						- mainWindow.getInsets().top - 300);
		smallListPane.setPreferredSize(new Dimension(150, mainWindow
				.getHeight()
				- mainWindow.getInsets().bottom
				- mainWindow.getInsets().top - 300));
		smallListPane.setLocation(150, 0);
		smallListPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		smallListPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		bigListModel = new SortedListModel();
		bigList.setModel(bigListModel);

		smallListModel = new SortedListModel();
		smallList.setModel(smallListModel);

		addButton = new JButton("Add Spells");
		addButton.setSize(140, 25);
		addButton.setPreferredSize(new Dimension(140, 25));
		addButton.setLocation(5, 270);
		addButton.addActionListener(new AddSpellToListListener(this));

		removeButton = new JButton("Remove Spells");
		removeButton.setSize(140, 25);
		removeButton.setPreferredSize(new Dimension(140, 25));
		removeButton.setLocation(155, 270);
		removeButton.addActionListener(new RemoveSpellFromListListener(this));

		optionsMenu.add(addButton);

		optionsMenu.add(removeButton);

		spellMenu.add(bigListPane);

		spellMenu.add(smallListPane);

		sideBar.add(spellMenu);
		sideBar.add(optionsMenu);

		// contentPane.add(spellAdd, BorderLayout.EAST);

		clearButton = new JButton("Clear Used");
		clearButton.setSize(140, 25);
		clearButton.setPreferredSize(new Dimension(140, 25));
		clearButton.setLocation(80, 220);
		clearButton.addActionListener(new ClearListener(this));

		optionsMenu.add(clearButton);

		characterList.addActionListener(new CharacterDropdownListener(this));

		contentPane.add(sideBar, BorderLayout.EAST);

		mainWindow.setContentPane(contentPane);

		for (String a : spells.keySet())
			bigListModel.addElement(a);

		cards = new TreeSet<SpellCard>();

		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		bigList.addMouseListener(new BigListListener(this));
		smallList.addMouseListener(new SmallListListener(this));

		loadFromText();
	}

	/**
	 * Attempts to read from SpellDatabase.txt, if the file doesn't exist, this
	 * method will prompt the user with 3 choices: (Use SmapleSpellDatabase,
	 * Provide own file, Exit).
	 * 
	 * @return a FileReader with the file that should be used as the database
	 */
	private FileReader retrieveDatabase() {
		FileReader databaseInit = null;
		File spellDB = new File("SpellDatabase.txt");
		if (spellDB.exists())
			try {
				databaseInit = new FileReader(spellDB);
			} catch (FileNotFoundException e) {
				LOGGER.severe("Could not find SpellDatabase.txt at the given location: "
						+ spellDB.getAbsolutePath());
				e.printStackTrace();
			}
		else {
			databaseInit = retrieveDatabaseDialog();
		}
		return databaseInit;
	}

	/**
	 * Creates a Dialog prompting the user for a selection of one of three
	 * choices : Use Default Database, Provide Own Database, or Exit. If any
	 * portion of the process fails while providing own database, the user will
	 * be prompted again with the same dialog.
	 * 
	 * @return a FileReader, could be <code>null</code> which means the user
	 *         selected cancel, or a non null file reader with the contents of a
	 *         file
	 */
	private FileReader retrieveDatabaseDialog() {
		File spellDb = null;
		FileReader databaseInit = null;
		String options[] = { "Use Default", "Provide Own", "Exit" };
		int option = JOptionPane.showOptionDialog(null, "No database found.",
				"Data Error", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options);

		switch (option) {

		// Use default
		case 0:
			spellDb = new File("SampleSpellDatabase.txt");
			try {
				databaseInit = new FileReader(spellDb);
			} catch (FileNotFoundException e) {
				LOGGER.severe("Could not SampleSpellDatabase.txt");
				e.printStackTrace();
			}
			break;
		// Supply own
		case 1:
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(null);
			File selectedFile = fileChooser.getSelectedFile();

			// ask again till the user hits cancel
			if (selectedFile == null) {
				return retrieveDatabaseDialog();
			}

			try {
				databaseInit = new FileReader(selectedFile);
			} catch (FileNotFoundException e) {
				LOGGER.severe("Could not read file: "
						+ selectedFile.getAbsolutePath());
				e.printStackTrace();
			}

			break;
		// Exit
		default:
			return null;
		}
		return databaseInit;
	}

	public void resize() {
		if (spellMenu != null) {
			spellMenu.setSize(300,
					mainWindow.getHeight() - mainWindow.getInsets().bottom
							- mainWindow.getInsets().top - 300);
			spellMenu.setPreferredSize(new Dimension(300, mainWindow
					.getHeight()
					- mainWindow.getInsets().bottom
					- mainWindow.getInsets().top - 300));
			bigListPane.setSize(150,
					mainWindow.getHeight() - mainWindow.getInsets().bottom
							- mainWindow.getInsets().top - 300);
			bigListPane.setPreferredSize(new Dimension(150, mainWindow
					.getHeight()
					- mainWindow.getInsets().bottom
					- mainWindow.getInsets().top - 300));
			smallListPane.setSize(150,
					mainWindow.getHeight() - mainWindow.getInsets().bottom
							- mainWindow.getInsets().top - 300);
			smallListPane.setPreferredSize(new Dimension(150, mainWindow
					.getHeight()
					- mainWindow.getInsets().bottom
					- mainWindow.getInsets().top - 300));
		}
	}

	public void resizeSpells() {
		for (Component a : spellContainer.getComponents()) {
			SpellCard b = (SpellCard) a;
			b.reSize();

			spellContainer.revalidate();
			spellContainer.repaint();
		}
	}

	public void addSpellsToList(List<String> list2) throws IOException {
		for (String a : list2)
			cards.add(new SpellCard(this, a));
		saveToText();
		updateCards();
	}

	public void updateCards() {
		for (Component a : spellContainer.getComponents()) {
			if (!cards.contains((SpellCard) a)) {
				spellContainer.remove(a);
				smallListModel.removeElement(((SpellCard) a).title);
				bigListModel.addElement(((SpellCard) a).title);
			}
		}
		for (SpellCard a : cards) {
			if (!Arrays.asList(spellContainer.getComponents()).contains(a)) {
				spellContainer.add(a);
				smallListModel.addElement(((SpellCard) a).title);
				bigListModel.removeElement(((SpellCard) a).title);
			}
		}

		spellContainer.revalidate();
		spellContainer.repaint();
	}

	public void removeSpellsFromList(List<String> selectedValuesList)
			throws IOException {

		cards.removeIf(new FilterForTitle(selectedValuesList));

		saveToText();
		updateCards();
	}

	public void loadFromText() throws IOException {

		if (characterList.getSelectedItem() == null) {
			LOGGER.severe("No character selected, an IOException will be thrown trying to read from null.txt");
			//TODO : provide option to create character or use a default
		}

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
		new SpellSheet();
	}

}