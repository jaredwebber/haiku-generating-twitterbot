public class Entry {
	private int frequency = -1;
	private String word = null;
	
	/**
	 * Dictionary Entry constructor
	 * 
	 * @param frequency
	 * @param word
	 */
	protected Entry(int frequency, String word) {
		this.frequency = frequency;
		this.word = word;
	}
	
	/**
	 * Word accessor method
	 * 
	 * @return word
	 */
	protected String getWord() {
		return this.word;
	}
	
	
	/**
	 * Frequency accessor method
	 * 
	 * @return frequency
	 */
	protected int getFrequency() {
		return this.frequency;
	}
	
	
	/**
	 * Frequency mutator method
	 */
	protected void incrementFrequency() {
		this.frequency++;
	}
	
}
