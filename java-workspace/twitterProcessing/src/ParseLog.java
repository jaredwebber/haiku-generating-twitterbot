import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ParseLog{
	
	//822521968465416192
	
	public static void main(String[] args) {
		
		//Read in mass info from file
		String log = readTweetLog();

		//Read in (old) recent tweet id
		long recentID = getRecentID();
			
		//Create a Tweet object for each instance found in log
		ArrayList<Tweet> tweets = makeTweetObjects(log, recentID);
		
		//Generate a new Dictionary
		Dictionary dictionary = new Dictionary();
		
		//Fill dictionary with existing words from file
		dictionary.buildDictionary(readDictionary());
		
		//Parse tweet object text content (if new) and add to dictionary.txt
		parseTweets(tweets, dictionary);
		
		//Archive all new tweets into archive.txt file
		archiveTweets(tweets);
		
		//update new recent tweet id in recentID.txt		
		updateRecentId(log);
		
		//update newly sorted dictionary into the dictionary.txt file		
		updateDictionary(dictionary.createString());
				
		//Call on FormatTweet class to create tweet.txt (whether or not new tweets found)
		FormatTweet.CreateTweet(areNewTweets(tweets));
		
	}
		
	/**
	 * Parse tweet object text into individual words and add to dictionary
	 * 
	 * @param tweets
	 * @param dictionary
	 */
	private static void parseTweets(ArrayList<Tweet> tweets, Dictionary dictionary) {
		
		if(tweets!=null && dictionary!=null) {
			Character forgetCharacters[] = {'.', '!', '?', ',', '(', ')', '{', '}', '_','-'};
			
			for(int i = 0; i<tweets.size();i++) {
				if(tweets.get(i).isNewTweet() && !tweets.get(i).getText().equals("RT")) {
					String currentText = tweets.get(i).getText();
					
				
					currentText = currentText.replace('\n', ' ');
				
					currentText = currentText.toUpperCase();
					
					for(int r = 0; r<forgetCharacters.length;r++) {
						currentText = currentText.replace(forgetCharacters[r], ' ');
					}
					
					currentText = currentText.replace("   ", " ");//triple space
					currentText = currentText.replace("  ", " ");//double space
					
					String currentWords[] = currentText.split(" ");
						
					for(int j = 0; j<currentWords.length;j++) {
						currentWords[j] = currentWords[j].trim();
						if(!currentWords[j].isBlank() && !currentWords[j].isEmpty()) {
							if(currentWords[j].length()<5 && currentWords[j].length()>0) {
								dictionary.addInstance(currentWords[j]);
							}else if(currentWords[j].length()>0){
								if(!currentWords[j].substring(0,4).equals("HTTP")) {
									dictionary.addInstance(currentWords[j]);
								}
							}
						}
					}
				}
			}	
		}
	}
	

			
	
	/**
	 * Retrieves the next text section from the passed string
	 * 
	 * @param toGo, remainder of tweet log to be assigned to tweets 
	 * @return text of tweet
	 */
	private static String getNextText(String toGo) {
		final String checkRT = "RT @";
		String text = "";
		
		if(toGo!=null) {
		
			int startScan = toGo.indexOf("\"text\":") + 8;//6 is # of characters before message starts
			char current = ' ';
	
			
			if(toGo.length()>startScan+4 && startScan!=-1) {
				String comp = toGo.substring(startScan, startScan+4);
				if(comp.equals(checkRT)) {
					text = "RT";
				}else {//not a retweet, iterate through text until " found
					while(current != '"' || startScan == toGo.length()) {
						text += current;
						current = toGo.charAt(startScan);
						startScan++;
					}
				}
			}
			return text.trim();
		}
		return "";
	}
	
	
	/**
	 * update recentID.txt file with most recent tweet-id (from bottom of log)
	 * 
	 * @param log, string containing all tweet info
	 */
	private static void updateRecentId(String log) {
		int firstInstance = log.indexOf("\"newest_id\":") + 13;//13 is # of characters before number starts
		long newestID = -1;
		
		char current = ' ';
		String firstID = "";
		
		
		//number is finished with "
		while(current != '"') {
			firstID += current;
			current = log.charAt(firstInstance);
			firstInstance++;
		}
		
	
		
		newestID= Long.parseLong(firstID.trim());

				
		try {
			PrintWriter writer = new PrintWriter("../../txt/recentID.txt");
			writer.print(newestID);
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
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
	
	
	/**
	 * Write the updated dictionary string to the dictionary.txt file
	 * 
	 * @param dictionary
	 */
	private static void updateDictionary(String dictionary) {
		if(dictionary!=null) {
			try {
				PrintWriter writer = new PrintWriter("../../txt/dictionary.txt");
				writer.print(dictionary.trim());
				writer.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Checks the tweet objects whether or not theyre new
	 * 
	 * @param tweets
	 * @return true if at least one tweet is new
	 */
	private static boolean areNewTweets(ArrayList<Tweet> tweets) {
		boolean value = false;
		
		for(int i = 0; i<tweets.size(); i++) {
			if(tweets.get(i).isNewTweet()) {
				value = true;
			}
		}

		return value;
	}
	
	
	/**
	 * Finds the next tweet id in the passed String
	 * 
	 * @param toGo, portion of log string which remains to be parsed
	 * @return value, id of the tweet being parsed
	 */
	
	private static long getNextID(String toGo) {
		long value = -1;
		
		int startScan = toGo.indexOf("\"id\":") + 6;//6 is # of characters before number starts
		char current = ' ';
		String stringID = "";
		
		if(toGo.indexOf("\"id\":") != -1) {
		
			//number is finished with "
			while(current != '"' && startScan < toGo.length()) {
				stringID += current;
				current = toGo.charAt(startScan);
				startScan++;
			}
			
			stringID = stringID.trim();
	
			if(!stringID.isBlank() && !stringID.isEmpty()) {
				value = Long.parseLong(stringID);
	
			}
		}
		
		return value;
	}
		
	
	/**
	 * Parses the log string, generating Tweet objects for each instance of id and text
	 * 
	 * @param log, string containing all original tweet info
	 * @param recentID, id of newest tweet from last program call
	 * @return ArrayList of all tweet objects
	 */
	private static ArrayList<Tweet> makeTweetObjects(String log, long recentID) {
		String toGo = log;//toGO signified there is 'toGo' amount of log left to parse
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		boolean active = true;
		
		while(active) {
			long id = getNextID(toGo);
			String text = getNextText(toGo);
			
			if(id == -1 || text==null) {
				active = false;
			}else {
				//add the new tweet to arraylist
				tweets.add(new Tweet(id, text, recentID)); 
				//remove parsed section from working string
				
				toGo = toGo.substring(toGo.indexOf("\"text\":")+text.length());				
			}
		}
	
		return tweets;
	}
	
	
	/**
	 * Read log.txt (generated from nodejs) and convert to readable java format (string)
	 * 
	 * @return log, String containing all info from log.txt
	 */
	private static String readTweetLog() {
		String log = null;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("../../txt/log.txt"));
			log = reader.readLine();
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return log;
	}
	
	
	
	/**
	 * Finds the most recent tweet id from recentID.txt file
	 * 
	 * @return value, long, twitter id found in file
	 */
	private static long getRecentID() {
		long value = -1;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("../../txt/recentID.txt"));
			value = Long.parseLong(reader.readLine());
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		return value;
	}
	
	
	
	/**
	 * Insert all new tweets into archive.txt file
	 * 
	 * @param tweets
	 */
	private static void archiveTweets(ArrayList<Tweet> tweets) {
		try {
			PrintWriter writer = new PrintWriter("../../txt/archive.txt");
			for(int i = 0; i<tweets.size(); i++) {
				if(tweets.get(i).isNewTweet()) {
					writer.append(tweets.get(i).getId()+": "+tweets.get(i).getText()+"\n");
				}
			}
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
