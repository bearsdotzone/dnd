import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class DatabaseOrganizer {
	public static void main(String[] args) throws IOException {
		FileReader fr = null;
		try{
			fr = new FileReader(new File("omegaDatabase.txt"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No database found. Try the readme.", "", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		BufferedReader scan = new BufferedReader(fr);
		ArrayList<String> output = new ArrayList<String>();
		String q = scan.readLine();
		while (q != null) {
			String working = q.trim();
			working = working.replaceAll("8/16/2014 Spellbook- DNDNS", "");
			working = working
					.replaceAll(
							"http://5espellbook.azurewebsites.net/Generate.*\\d+/\\d+.*",
							"");
			working = working
					.replaceAll(
							"© 2014 - Philip Vuong \\(mailto:dnd5spellbook@gmail.com\\) - 5e Spellbook Generator",
							"");
			working = working.replaceAll("^Spellbook$", "");
			if (working.length() != 0) {
				output.add(working);
				if (working.matches(".* Cantrip")
						|| working.matches("\\d.* level .*[ionntcy]")) {
					output.add(output.size() - 2, "******");
				}
			}
			q = scan.readLine();
		}
		scan.close();
		fr.close();
		FileWriter dave = new FileWriter(new File("testSpellDatabase.txt"));
		PrintWriter davest = new PrintWriter(dave);
		while (output.size() > 0) {
			davest.println(output.get(0));
			output.remove(0);
		}
		davest.close();
		dave.close();

		TreeMap<String, ArrayList<String>> spells = new TreeMap<String, ArrayList<String>>();
		FileReader databaseInit = new FileReader(new File(
				"testSpellDatabase.txt"));
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
				if (temp != null && !temp.contains(read)) {
					temp.add(read);
					spells.put(current, temp);
				}
			}
			read = database.readLine();
		}
		database.close();
		databaseInit.close();

		FileWriter optimalWriter = new FileWriter(new File("newSpellDatabase.txt"));
		PrintWriter optimal = new PrintWriter(optimalWriter);

		for (String a : spells.keySet()) {
			optimal.println("******");
			optimal.println(a);
			for (String b : spells.get(a)) {
				optimal.println(b);
			}
		}
		optimal.close();
		optimalWriter.close();
		
		FileWriter bears = new FileWriter(new File("spellList.txt"));
		bears.close();
	}
}
