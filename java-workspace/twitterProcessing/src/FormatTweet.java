import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class FormatTweet {

	protected static void main(String[] args) {
		//Unused in current application	
	}
	
	/**
	 * Processor method which calls the functional methods to make the tweet
	 * 
	 * @param newTweets, true if there were new tweets found in the timeline log
	 */
	protected static void CreateTweet(boolean newTweets) {
		String dictionary[] = null;
		if(newTweets) {
			//Read dictionary.txt file into arraylist
			dictionary = readDictionary();
						
			//Write the tweet text to tweet.txt
			writeTweet(dictionary);
		
		}else {
			//Write the null text to tweet.txt
			writeTweet(dictionary);
		}
	}
	
	/**
	 * Writes the desired tweet to the txt file
	 * 
	 * @param topWords, String array containing the top words in the dictionary
	 */
	private static void writeTweet(String topWords[]) {
		String intro = "Most commonly tweeted words from @POTUS to date are:\n";
		
		if(topWords == null) {
			try {
				PrintWriter writer = new PrintWriter("../../txt/tweet.txt");
				writer.print("null");
				writer.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}else {
			try {
				PrintWriter writer = new PrintWriter("../../txt/tweet.txt");
				writer.print(intro);
				for(int i = 0; i<topWords.length && i<11 ; i++) {
					if(topWords[i].length()>3) {
						writer.print(i+". "+capitalize(topWords[i].substring(3))+": "+Integer.valueOf(topWords[i].substring(0,3))+" mentions\n");
					}
				}
				writer.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Capitalizes the first letter of the passed word
	 * 
	 * @param word
	 * @return fixed string
	 */
	private static String capitalize(String word) {
		String fixed = "";
		try {
			fixed = word.substring(0,1).toUpperCase()+word.substring(1).toLowerCase();
		}catch(Exception e){
			return word;
		}
		
		return fixed;
	}
	
	/**
	 * Read dictionary.txt contents into ArrayList
	 * 
	 * @return dictionary string
	 */
	private static String[] readDictionary() {
		String dict = "";
		String entries[] = null;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("../../txt/dictionary.txt"));
			dict = reader.readLine();
			reader.close();
						
			if(dict!=null) {
			
				entries = dict.split("-");
				
				for(int i = 0; i<entries.length;i++) {
					entries[i].trim();
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return entries;
	}
	

	
	
}
