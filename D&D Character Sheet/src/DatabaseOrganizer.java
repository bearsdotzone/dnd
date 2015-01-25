import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class DatabaseOrganizer {
	public static void main(String[] args) throws IOException{
		FileReader fr = new FileReader(new File("omegaDatabase.txt"));
		BufferedReader scan = new BufferedReader(fr);
		ArrayList<String> output = new ArrayList<String>();
		int a = 0;
		String q = scan.readLine();
		while(q != null){
			String working = q.trim();
			output.add(working);
			if(working.matches(".* Cantrip") || working.matches("\\d.* level .*[ionntcy]")){
				a++;
				output.add(output.size()-2, "******");
			}
			q = scan.readLine();
		}
		scan.close();
		fr.close();
		System.out.println(a);
		FileWriter dave = new FileWriter(new File("newSpellDatabase.txt"));
		PrintWriter davest = new PrintWriter(dave);
		while(output.size()>0){
			davest.println(output.get(0));
			output.remove(0);
		}
		davest.close();
		dave.close();
	}
}
