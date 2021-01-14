public class Tweet {
	private long id;
	private String text;
	private boolean newTweet;
	
	/**
	 * Tweet object constructor
	 * 
	 * @param id
	 * @param text
	 * @param oldRecent (id of newest previously processed tweets)
	 */
	protected Tweet(long id, String text, long oldRecent) {
		this.id = id;
		this.text = text;
		this.newTweet = (this.id>oldRecent);
	}
	
	/**
	 * Accessor method for the tweet object's id
	 * 
	 * @return id
	 */
	protected long getId() {
		return this.id;
	}
	
	/**
	 * Tweet object text accessor
	 * 
	 * @return text
	 */
	protected String getText() {
		return this.text;
	}
	
	/**
	 * Boolean check if the tweet is new and tweet is not a retweet
	 * 
	 * @return true if tweet is more recent (new) and not a retweet
	 */
	protected boolean isNewTweet() {
		return this.newTweet && text!="RT";
	}
}
