import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class SpellCard extends JPanel implements Comparable<SpellCard> {

	NewSheet newSheet;
	public JLabel header;
	public String title;
	public JPanel footer;
	public ArrayList<String> spell;
	public int level;
	public JTextPane description;
	public JScrollPane scroll;

	public SpellCard(NewSheet newSheet, String title) {
		this.newSheet = newSheet;
		this.title = title;

		setSize((int) (newSheet.scale * 730), (int) (newSheet.scale * 1020));
		setPreferredSize(new Dimension((int) (newSheet.scale * 730),
				(int) (newSheet.scale * 1020)));
		setLayout(new BorderLayout());

		header = new JLabel();
		header.setSize((int) (newSheet.scale * 730),
				(int) (newSheet.scale * 200));
		header.setPreferredSize(new Dimension((int) (newSheet.scale * 730),
				(int) (newSheet.scale * 200)));
		header.setLocation(0, 0);
		header.setText(title);
		header.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
				(int) (newSheet.scale * 80)));
		header.setHorizontalAlignment(JLabel.CENTER);

		add(header, BorderLayout.NORTH);

		boolean hasLevel = true;

		spell = newSheet.spells.get(title);

		if (spell.get(0).matches(".* Cantrip")) {
			hasLevel = false;
			level = 0;
		} else {
			level = Integer.parseInt(spell.get(0).replaceAll("\\D", ""));
		}

		if (hasLevel) {
			footer = new JPanel(new GridLayout(1, 10 - level));
			footer.setSize((int) (newSheet.scale * 730),
					(int) (newSheet.scale * 100));
			footer.setPreferredSize(new Dimension((int) (newSheet.scale * 730),
					(int) (newSheet.scale * 100)));
			footer.setLocation(0, 0);
			footer.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
					(int) (newSheet.scale * 80)));
			add(footer, BorderLayout.SOUTH);
			for (int a = level; a <= 9; a++) {
				JButton temp = new JButton(a + "");
				temp.addMouseListener(new CardSpellSlotListener(temp, a,
						newSheet));
				temp.setHorizontalAlignment(JLabel.CENTER);
				temp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
				temp.setToolTipText(a + "");
				footer.add(temp);
			}
			description = new JTextPane();
			for (int a = 1; a < spell.size(); a++) {
				description
						.setText(description.getText() + "\n" + spell.get(a));
			}
			description.setText(description.getText().trim());
			description.setEditable(false);

			scroll = new JScrollPane(description);
			scroll.setSize((int) (newSheet.scale * 730),
					(int) (newSheet.scale * 720));
			scroll.setPreferredSize(new Dimension((int) (newSheet.scale * 730),
					(int) (newSheet.scale * 720)));
			scroll.setMinimumSize(new Dimension((int) (newSheet.scale * 730),
					(int) (newSheet.scale * 720)));
			add(scroll, BorderLayout.CENTER);

		} else {
			description = new JTextPane();
			for (int a = 1; a < spell.size(); a++) {
				description
						.setText(description.getText() + "\n" + spell.get(a));
			}
			description.setText(description.getText().trim());
			description.setEditable(false);

			scroll = new JScrollPane(description);
			scroll.setSize((int) (newSheet.scale * 730),
					(int) (newSheet.scale * 820));
			scroll.setPreferredSize(new Dimension((int) (newSheet.scale * 730),
					(int) (newSheet.scale * 820)));
			scroll.setMinimumSize(new Dimension((int) (newSheet.scale * 730),
					(int) (newSheet.scale * 820)));
			add(scroll, BorderLayout.CENTER);
		}

	}

	public int compareTo(SpellCard arg0) {
		SpellCard compare = SpellCard.class.cast(arg0);
		if (compare.level < this.level) {
			return 1;
		} else if (compare.level > this.level) {
			return -1;
		} else {
			if (compare.title.compareTo(this.title) < 0)
				return 1;
			else
				return -1;
		}
	}
}
