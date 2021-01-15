# vocabulary-tracking-twitterbot
Twitter bot written in node.js and java which reads all new tweets from a spcified user and tracks the frequancy of all unique words. The "favourite words" (most commonly seen) are then formatted in a tweet, and is posted once a week.

Requires nodejs axios package for requests, the dotenv package for managing environment variables, as well as twit package for posting.

Run call process:
1. node ReadTimeline.js > txt/log.txt   
   * Executes the request to twitter api and redirects the info into the log file  
2. java ParseLog  
   * Parses the log.txt data:  
   * Saves all tweet info in archive.txt
   * Saves the most recent tweet id in recentID.txt  
   * Saves the frequency of each word found in dictionary.txt  
   * Formats the desired tweet into tweet.txt
3. node PostTweet.js   
   * Reads tweet.txt, and if contents are not "null", posts a tweet with the file contents  
