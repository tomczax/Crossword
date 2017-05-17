package crossword;

import java.io.BufferedReader;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class WordsList {
	public static ArrayList<String> wordsList = new ArrayList<String>();
	
	static {
		readWordsFromFile();
	}
	public static void readWordsFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("lemma.al"));
			String line;

			while ((line = reader.readLine()) != null) {
//				String[] words = line.split("\\.");
				String[] words = line.split(" ");
				if (words[3].equalsIgnoreCase("adv") || words[3].equalsIgnoreCase("v") || words[3].equalsIgnoreCase("a")
						|| words[3].equalsIgnoreCase("n")) {
//					if(!wordsList.contains(words[0]) && (words[0].length() <= Constants.HEIGHT && words[0].length() <= Constants.WIDTH) ) {
//						wordsList.add(words[0]);
//					}				
					if(!wordsList.contains(words[2]) && (words[2].length() <= Constants.HEIGHT && words[2].length() <= Constants.WIDTH) ) {
					wordsList.add(words[2]);
				}	
				}
			}
			reader.close();
			Collections.shuffle(WordsList.wordsList);
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.out.println("The file cannot be found");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("The file cannot be read");
		}
	}
	
//	public static void main(String[] args) {
//		System.out.println(WordsList.wordsList.size());
//	}
}
