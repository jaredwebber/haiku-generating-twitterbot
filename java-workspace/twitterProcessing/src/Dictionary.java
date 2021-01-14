import java.util.ArrayList;

public class Dictionary {
	private ArrayList<Entry> dictionary = null;
	
	/**
	 * Dictionary class contructor
	 */
	protected Dictionary() {
		dictionary = new ArrayList<Entry>();
	}
	
	
	/**
	 * Return a new ArrayList with most frequently used words first
	 * 
	 * @return sorted
	 */
	private ArrayList<Entry> getSortedDictionary() {
		ArrayList<Entry> sorted = new ArrayList<Entry>(dictionary.size());
		int max = getLargestFrequency();
		//int indices[] = new int[dictionary.size()];
		int index = 0;
		
		for(int j = max; j>=1; j--) {
			for(int i = 0; i<dictionary.size();i++) {
				if(dictionary.get(i).getFrequency() == j) {
					sorted.add(index,new Entry((int)(dictionary.get(i).getFrequency()), new String(dictionary.get(i).getWord())));
					index++;
				}
			}
		}
		return sorted;

	}
	
	
	/**
	 * Returns the highest frequency value from dictionary
	 * 
	 * @return value
	 */
	private int getLargestFrequency() {
		int value = -1;
		
		for(int i = 0; i<dictionary.size();i++) {
			

			if(dictionary.get(i).getFrequency() > value) {
				value = dictionary.get(i).getFrequency();
			}
		}
		return value;
	}
	
	
	/**
	 * Either adds the new word to the dictionary with frequency 1, or if its not
	 * a new word, incremements the frequency	 * 
	 * 
	 * @param word
	 */
	protected void addInstance(String word) {
		int index = foundWord(word);		
		
		if(!isBanned(word)) {
			System.out.println(word);
			if(index == -1) {
				dictionary.add(new Entry(1, word));
			}else {
				dictionary.get(index).incrementFrequency();
			}
		}
		
	}
	
	/**
	 * Checks input words against 'banned' words
	 * Current list is unimportant/uninteresting words
	 * 
	 * @param word
	 * @return true if word is banned
	 */
	private static boolean isBanned(String word) {
		boolean result = false;
		String bannedWords[] = {"AND", 
								"OR", 
								"THE", 
								"BUT", 
								"IF", 
								"A", 
								"SO", 
								"AT", 
								"WAS",
								"TO", 
								"OF", 
								"FOR", 
								"WITH", 
								"ARE", 
								"THEY", 
								"IN", 
								"IS", 
								"BE", 
								"THAT", 
								"AS",
								"WILL",
								"ON", 
								"GET", 
								"&AMP;", 
								"IT", 
								"AN", 
								"BY",
								"UP",
								"THIS",
								"—",
								"NOT"
								};
		
		
		for(int r = 0; r<bannedWords.length;r++) {
			if(word.trim().equalsIgnoreCase(bannedWords[r].trim())) {
				result = true;
			}
		}
		
		return result;
	}

	/**
	 * Returns index of the passed word in the arraylist, or -1 if word isnt found
	 * 
	 * @param word
	 * @return found, index of word in arraylist
	 */
	private int foundWord(String word) {
		int found = -1;
		
		if(word!=null) {
			for(int i = 0; i<dictionary.size();i++) {
				if(dictionary.get(i).getWord().equals(word)) {
					found = i;
				}
			}
		}

		return found;
	}
	
	
	/**
	 * Inserts array of frequency/word Strings into dictionary
	 * 
	 * @param words (format 000Word) with first 3 digits being the frequency its used
	 */
	protected void buildDictionary(String[] words) {
		
		if(words != null) {//non-empty dictionary file
			for(int i = 0; i<words.length;i++) {
				if(words[i].length()>4) {
					dictionary.add(new Entry(Integer.valueOf(words[i].substring(0, 3)), words[i].substring(3)));
				}
			}
		}
		
	}
	
		
	
	/**
	 * Compiles the dictionary data into a single string of form 000Word
	 * 
	 * First 3 places are the frequency the word is used, and the string is concatenated
	 * 
	 * @return combine
	 */
	protected String createString() {
		ArrayList<Entry> sortedDictionary = getSortedDictionary();
		
		String combine = "";
		
		for(int i= 0; i< sortedDictionary.size();i++) {
			combine += "-"+String.format("%03d", sortedDictionary.get(i).getFrequency())+sortedDictionary.get(i).getWord();
		}
		
		return combine;
	}

}
