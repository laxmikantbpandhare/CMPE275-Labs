package edu.sjsu.cmpe275.aop.tweet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetService");
        TweetStatsService stats = (TweetStatsService) ctx.getBean("tweetStatsService");

        try {
//            int msg = tweeter.tweet("alex", "first tweet");
//            tweeter.follow("bob", "alex");
//            tweeter.retweet("bob", msg);
//            tweeter.block("alex", "bob");
            
//            int msg = tweeter.tweet("alex", "first tweet");
//
//    		tweeter.follow("bob", "alex");
//
//    		tweeter.follow("c", "alex");
//
//    		tweeter.follow("d", "alex");
//
//    		tweeter.follow("d", "bob");
//
//    		tweeter.follow("e", "alex");
//
//    		tweeter.follow("f", "c");
//
//    		tweeter.tweet("c", "C's tweet");//       
//
//    		tweeter.tweet("c", "C's tweet again");//        
//
//    		tweeter.tweet("c", "C's tweet yet again");
//
//    		tweeter.retweet("bob", msg);
//
//    		tweeter.follow("d", "bob");
//
//    		tweeter.block("alex", "e");
//
//    		tweeter.block("alex", "d");
//
//    		int msg2=tweeter.tweet("alex", "hi");
//
//    		tweeter.tweet("alex", "ai");
//
//    		int msg3=tweeter.tweet("c", "ai");  		
//
//    		tweeter.retweet("c",msg3);
        	
//        	
//        	int msg1 = tweeter.tweet("alex", "first tweet1");
//
//            tweeter.follow("bob1", "alex");
//
//            tweeter.follow("bob100", "bob1");
//
//            tweeter.follow("bob2", "alex");
//
//            tweeter.follow("bob3", "alex");
//
//            tweeter.follow("bob4", "alex");
//
//            int msg = tweeter.tweet("alex", "first tweet");
//
//            tweeter.follow("bob1", "wlex");
//
//            tweeter.follow("bob2", "wlex");
//
//            tweeter.follow("bob3", "wlex");
//
//            tweeter.follow("bob4", "wlex");
//
//            tweeter.follow("bob5", "wlex");
//
//            tweeter.follow("bob6", "wlex");
//
//            tweeter.follow("bob7", "wlex");
//
//            tweeter.retweet("bob1", msg);
//
//            tweeter.block("alex", "bob1");
//
//  	 	    int msg4 = tweeter.tweet("alex", "first tweet2");
        	
//        	
//        	tweeter.follow("bob", "raman");
//            tweeter.follow("bob", "alex");
//            tweeter.follow("zen", "bob");
//            tweeter.follow("yen", "bob");
//           
//            tweeter.follow("ranga", "yen");
//            tweeter.follow("alex", "zen");
//           
//           
//        int t0 = tweeter.tweet("alex", "alex test tweet");
//        int t1 = tweeter.tweet("raman", "raman test tweet");
//
//        int r0 = tweeter.retweet("bob", t0);
//        int r1 = tweeter.retweet("bob", t1);
//
//
//        int r3 = tweeter.retweet("zen", r0);
//        int r4 = tweeter.retweet("yen", r1);
        	
        	
        	

        	
//        
//  	 	    
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Most productive user: " + stats.getMostProductiveUser());
        System.out.println("Most popular user: " + stats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());
        System.out.println("Most popular message: " + stats.getMostPopularMessage());
        ctx.close();
    }
}
