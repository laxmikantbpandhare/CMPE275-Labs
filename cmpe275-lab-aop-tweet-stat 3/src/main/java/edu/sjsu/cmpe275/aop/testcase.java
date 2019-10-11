package edu.sjsu.cmpe275.aop;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;import edu.sjsu.cmpe275.aop.tweet.TweetService;import edu.sjsu.cmpe275.aop.tweet.TweetStatsService;import org.junit.Test;import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class testcase {

	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml"); 
	TweetService tweeter = (TweetService) ctx.getBean("tweetService");
	TweetStatsService stats = (TweetStatsService) ctx.getBean("tweetStatsService");
	
	@Test
//Testing basic functionality    
	public void testGetMostPopularMessage1() throws IllegalArgumentException, IOException {
		try {
			System.out.println("---------------NEW TEST CASE : testGetMostPopularMessage1-------------------------------");
	 tweeter.follow("alex", "bob");
			 tweeter.follow("andria", "bob");
			 tweeter.follow("andria", "alex");
		int msgid = tweeter.tweet("bob", "hi, this is bob");
		 tweeter.retweet("alex",msgid);
		 System.out.println(stats.getMostPopularMessage().equals("hi, this is bob") ? "Pass" : "Fail" );
		 assertEquals(stats.getMostPopularMessage(),"hi, this is bob");
			 } catch (Exception e) {
				 System.out.println("");
			 }
		}
	
	
	
	@Test public void testGetMostPopularMessage2() 
	{
		try { 
			System.out.println("--------------NEW TEST CASE : testGetMostPopularMessage2-------------------------------");
			stats.resetStatsAndSystem();
			assertEquals(stats.getMostPopularMessage(), null);
			assertEquals(stats.getMostFollowedUser(), null);
			assertEquals(stats.getLengthOfLongestTweet(), 0); 
			assertEquals(stats.getMostProductiveUser(), null); 
			System.out.println(stats.getMostPopularMessage()); 
			tweeter.follow("alex", "bob");
			tweeter.follow("andria", "bob");
			tweeter.follow("andria", "alex");
			tweeter.follow("sun", "alex");
			int msgid = tweeter.tweet("bob", "hi, this is bob");
			int msgid1 = tweeter.tweet("alex", "hi, this is alex");
			tweeter.retweet("alex",msgid); 
			assertEquals(stats.getMostPopularMessage(),"hi, this is bob");
			System.out.println(stats.getMostPopularMessage() == "hi, this is bob" ? "Pass" : "Fail");
			} catch (Exception e) { e.printStackTrace();
			}
		}
	
	
	@Test public void testGetMostPopularMessage3() throws IllegalArgumentException, IOException {
	//	try { 
			System.out.println("---------------NEW TEST CASE : testGetMostPopularMessage3-------------------------------"); 
			stats.resetStatsAndSystem();
			tweeter.tweet("zeena","Hey! Good morning");
			System.out.println(stats.getMostFollowedUser() == null ? "Pass" : "Fail");
			System.out.println(stats.getMostPopularMessage());
		////alex, andria follows bob            
		tweeter.follow("alex", "bob");
		tweeter.follow("andria", "bob");
		//andria, sun follows alex            
		tweeter.follow("andria", "alex");
		tweeter.follow("sun", "alex");
		int msgid = tweeter.tweet("bob", "hi, this is bob");
		int msgid1 = tweeter.tweet("alex", "hi, this is alex");
		assertEquals(stats.getMostPopularMessage(), "hi, this is alex");
		System.out.println(stats.getMostPopularMessage() == "hi, this is alex" ? "Pass" : "Fail"); 
	}
	
	
	@Test public void testGetMostPopularMessage4() throws IllegalArgumentException, IOException {
		System.out.println("---------------NEW TEST CASE : testGetMostPopularMessage4-------------------------------"); stats.resetStatsAndSystem();
		 //alex, andria follows bob            
		tweeter.follow("alex", "bob");
		tweeter.follow("andria", "bob");
	 //andria, sun follows alex            
		tweeter.follow("andria", "alex");
		tweeter.follow("sun", "alex");
		 //moon follows sun            
		tweeter.follow("moon", "sun");

	 int msgid = tweeter.tweet("bob", "hi, this is bob");
		int msgid1 = tweeter.tweet("alex", "hi, this is alex");
		tweeter.retweet("alex", msgid);
		tweeter.retweet("sun", msgid1);
	assertEquals(stats.getMostPopularMessage(), "hi, this is alex"); System.out.println(stats.getMostPopularMessage() == "hi, this is alex" ? "Pass" : "Fail"); 
	}
	
	
	/*     *1. Testing when for tweets with BLOCK incorporated     * 2. Testing for resetStatsAndSystem     */
	@Test public void testGetMostPopularMessage5() throws IllegalArgumentException, IOException {
		 System.out.println("---------------NEW TEST CASE : testGetMostPopularMessage5-------------------------------");
		 stats.resetStatsAndSystem();
		 //alex, andria follows bob            
		tweeter.follow("alex", "bob");
		tweeter.follow("andria", "bob");
		//andria, sun follows alex            
		tweeter.follow("andria", "alex");
		tweeter.follow("sun", "alex");
		 //alex blocks andria            
		tweeter.block("alex", "andria");
	int msgid = tweeter.tweet("bob", "hi, this is bob");
		int msgid1 = tweeter.tweet("alex", "hi, this is alex");
		assertEquals(stats.getMostPopularMessage(), "hi, this is bob");
		 stats.resetStatsAndSystem();
		assertEquals(stats.getMostPopularMessage(), null); 
		System.out.println(stats.getMostPopularMessage() == null ? "Pass" : "Fail"); 
		}
	
	
	/*     *1. Testing when for tweets & RETWEETS with BLOCK incorporated     *     */
@Test public void testGetMostPopularMessage6() throws IllegalArgumentException, IOException {
		System.out.println("---------------NEW TEST CASE : testGetMostPopularMessage6-------------------------------");
		stats.resetStatsAndSystem();
		 //alex, andria follows bob            
		tweeter.follow("alex", "bob");
		tweeter.follow("andria", "bob");
		 //andria, sun, moon follows alex            
		tweeter.follow("andria", "alex");
		tweeter.follow("sun", "alex");
		tweeter.follow("moon", "alex");
		//stars follows moon         
		tweeter.follow("stars", "moon");
		//alex blocks andria         
	 tweeter.block("alex", "andria");
		//moon blocks stars          
	tweeter.block("moon","stars");
		 int msgid = tweeter.tweet("bob", "bob");
		int msgid1 = tweeter.tweet("alex", "hi, this is alex");
		tweeter.retweet("moon",msgid1);
		assertEquals(stats.getMostPopularMessage(), "bob");
		System.out.println(stats.getMostPopularMessage() == "bob" ? "Pass" : "Fail"); 
}	



/*     *1. Testing where original tweeter is a follower of retweeter (making sure it is not counted)     *     */
@Test
public void testGetMostPopularMessage7() throws IllegalArgumentException, IOException {

	System.out.println("---------------NEW TEST CASE : testGetMostPopularMessage6-------------------------------");
	stats.resetStatsAndSystem();
 //alex, andria follows bob            
tweeter.follow("alex", "bob");
tweeter.follow("andria", "bob");
 //galaxy, moon follows alex            
tweeter.follow("galaxy", "alex");
tweeter.follow("bob", "alex");
 //starts follow moon            
tweeter.follow("stars", "moon");
tweeter.follow("saturn", "moon");
tweeter.follow("galaxy", "moon");
 int msgid = tweeter.tweet("bob", "zzzzz");
tweeter.retweet("alex",msgid);
int msgid1 = tweeter.tweet("moon", "aaa");
assertEquals(stats.getMostPopularMessage(), "aaa");
System.out.println(stats.getMostPopularMessage() == "aaa" ? "Pass" : "Fail"); 
}
}
