package edu.sjsu.cmpe275.aop.tweet.aspect;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;

@Aspect
@Order(0)
public class StatsAspect {
	
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Autowired TweetStatsServiceImpl stats;
	
	@AfterReturning("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))")
	public int tweetAfterRetrunning(JoinPoint joinPoint)  throws IOException{
		
		
		System.out.printf("Before the executuion of the method %s\n", joinPoint.getSignature().getName());
		String user = joinPoint.getArgs()[0].toString();
		String message = joinPoint.getArgs()[1].toString();
		
		if (message.length()>140 || message.isEmpty() || user.isEmpty())
			throw new IllegalArgumentException();

		HashMap<String,Integer> mostFollowerListtrialFinal = new HashMap<String,Integer>();
		mostFollowerListtrialFinal.clear();

		if(message.length()>TweetStatsServiceImpl.lengthLongestTweet)
			TweetStatsServiceImpl.lengthLongestTweet = message.length();

		TweetStatsServiceImpl.tweetRetweetUserCounterDetails.put(TweetStatsServiceImpl.counter,user);
		TweetStatsServiceImpl.tweetMessageIdDetails.put(TweetStatsServiceImpl.counter,message);
		
		
		mostFollowerListtrialFinal.put(user,1);
		TweetStatsServiceImpl.mostProductiveMessageTrialFinal.put(TweetStatsServiceImpl.counter,mostFollowerListtrialFinal);
		   
		//below logic is for last question
		if(TweetStatsServiceImpl.messageLengthCountHashMap.containsKey(user)) {
			int cnt = TweetStatsServiceImpl.messageLengthCountHashMap.get(user);
			cnt = cnt + message.length();
			TweetStatsServiceImpl.messageLengthCountHashMap.put(user,cnt);
		}
		else
			TweetStatsServiceImpl.messageLengthCountHashMap.put(user,message.length());

    	System.out.printf("User %s tweeted message: %s\n", user, message);
    	
    	ConcurrentHashMap<String,Integer> hash_Set = new ConcurrentHashMap<String,Integer>();    	
       	ArrayList<String> ar = new ArrayList<String>();
       			
       	if(TweetStatsServiceImpl.followerListStoreHashMap.containsKey(user))
       		ar = TweetStatsServiceImpl.followerListStoreHashMap.get(user);
    	
    	for(String a: ar)
    		hash_Set.put(a,0);
    	
		for(Entry<String, Integer> s:hash_Set.entrySet()) {
			String key = s.getKey();
			if(TweetStatsServiceImpl.blockListStoreHashMap.containsKey(key)) {
				ArrayList<String> lst = TweetStatsServiceImpl.blockListStoreHashMap.get(key);
				for(String str:lst) {
					if(str.equals(user)) {
						hash_Set.remove(key);
					}
		    	}
			}
		}
    	
    	TweetStatsServiceImpl.mostProductiveMessageUser100.put(TweetStatsServiceImpl.counter,hash_Set);
    	
    	int i = TweetStatsServiceImpl.getCountOfFollowersAndBlockers(user,TweetStatsServiceImpl.counter);
    	TweetStatsServiceImpl.mostProductiveMessage.put(TweetStatsServiceImpl.counter,i);
    	TweetStatsServiceImpl.tweetUserMessageDetails.put(TweetStatsServiceImpl.counter, 0);
    
    	return TweetStatsServiceImpl.counter;  	
	}
	
	@AfterReturning("execution(* edu.sjsu.cmpe275.aop.tweet.TweetService.retweet(..))")
	public int retweetAfterRetrunning(JoinPoint joinPoint) throws IOException {
		
		System.out.printf("Before the executuion of the method %s\n", joinPoint.getSignature().getName());
		String user = joinPoint.getArgs()[0].toString();
		int messageId = (Integer) joinPoint.getArgs()[1]; // you need reference of this for most productive message check it out
		
		if (messageId == 0 || user.isEmpty())
			throw new IllegalArgumentException();

				
		TweetStatsServiceImpl.tweetRetweetUserCounterDetails.put(TweetStatsServiceImpl.counter,user);
		
		String str = TweetStatsServiceImpl.tweetMessageIdDetails.get(messageId);
		TweetStatsServiceImpl.tweetMessageIdDetails.put(TweetStatsServiceImpl.counter,str);
		
		HashMap<String,Integer> mostFollowerListtrialFinal = new HashMap<String,Integer>();
		
		
		if(TweetStatsServiceImpl.mostProductiveMessageTrialFinal.containsKey(messageId)) {
			mostFollowerListtrialFinal.clear();
			mostFollowerListtrialFinal = TweetStatsServiceImpl.mostProductiveMessageTrialFinal.get(messageId);
			mostFollowerListtrialFinal.put(user,0);
			TweetStatsServiceImpl.mostProductiveMessageTrialFinal.put(TweetStatsServiceImpl.counter,mostFollowerListtrialFinal);	
		}
		
		
		int mm = 0;
		if(TweetStatsServiceImpl.tweetUserMessageDetails.containsKey(messageId))
			mm = TweetStatsServiceImpl.tweetUserMessageDetails.get(messageId);
		int k = 0;
		
		if(mm==0) {
			k = messageId;
			TweetStatsServiceImpl.tweetUserMessageDetails.put(k, TweetStatsServiceImpl.counter);
		}else
			TweetStatsServiceImpl.tweetUserMessageDetails.put(messageId, TweetStatsServiceImpl.counter);
		
		
		ConcurrentHashMap<String,Integer> hash_Set = new ConcurrentHashMap<String,Integer>(); 
       	ArrayList<String> ar = new ArrayList<String>();	
       	
       	hash_Set = TweetStatsServiceImpl.mostProductiveMessageUser100.get(messageId);

       	if(TweetStatsServiceImpl.followerListStoreHashMap.containsKey(user))
       		ar = TweetStatsServiceImpl.followerListStoreHashMap.get(user);
       	
    	for(String a: ar)
    		hash_Set.put(a,0);
		
		

		for(Entry<String, Integer> s:hash_Set.entrySet()) {
			String key = s.getKey();
			if(TweetStatsServiceImpl.blockListStoreHashMap.containsKey(key)) {
				ArrayList<String> lst = TweetStatsServiceImpl.blockListStoreHashMap.get(key);
				for(String str1:lst) {
					if(str1.equals(user)) {
						hash_Set.remove(key);
					}
		    	}
			}
		}
    	TweetStatsServiceImpl.mostProductiveMessageUser100.put(TweetStatsServiceImpl.counter,hash_Set);
    	

    	if(TweetStatsServiceImpl.mostProductiveMessage.containsKey(messageId)) {
    		int i = TweetStatsServiceImpl.getCountOfFollowersAndBlockers(user,messageId);
    		TweetStatsServiceImpl.mostProductiveMessage.put(TweetStatsServiceImpl.counter,i);
    	} 
    	
		return TweetStatsServiceImpl.counter;		
	}
	
	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	public void dummyBeforeAdvice(JoinPoint joinPoint) {
		System.out.printf("Before the executuion of the method %s\n", joinPoint.getSignature().getName());
		
		String follower = joinPoint.getArgs()[0].toString();
		String followee = joinPoint.getArgs()[1].toString();
		
		
    	if(follower.isEmpty() || followee.isEmpty())
    		throw new IllegalArgumentException();
    	
    	if(!follower.equals(followee)) {
    	ArrayList<String> arrayOfFollowers = new ArrayList<String>();
    	
    	if(TweetStatsServiceImpl.followerListStoreHashMap.containsKey(followee)) {

    		ArrayList<String> values = TweetStatsServiceImpl.followerListStoreHashMap.get(followee);
			
			boolean val = false;
			for(String str:values) {
			    arrayOfFollowers.add(str);
			    if(str.equals(follower))
			    	val = true;
			    }
			    if(!val) {
			    	arrayOfFollowers.add(follower);
			    	TweetStatsServiceImpl.followerListStoreHashMap.put(followee,arrayOfFollowers);
			    }else
			    	TweetStatsServiceImpl.followerListStoreHashMap.put(followee,arrayOfFollowers);
		
    	}else {
    		arrayOfFollowers.clear();
    		arrayOfFollowers.add(follower);
    		TweetStatsServiceImpl.followerListStoreHashMap.put(followee,arrayOfFollowers);
    	}
    	
      }
	}
	


@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
public void dummyBeforeAdvice1(JoinPoint joinPoint) {
	
	System.out.printf("Before the executuion of the method %s\n", joinPoint.getSignature().getName());
	
	String user = joinPoint.getArgs()[0].toString();
	String follower = joinPoint.getArgs()[1].toString();
	
	if(user.isEmpty() || follower.isEmpty())
		throw new IllegalArgumentException();
	
	ArrayList<String> blockUserDetails = new ArrayList<String>();
	
	if(TweetStatsServiceImpl.blockListStoreHashMap.containsKey(follower)) {
		ArrayList<String> values = TweetStatsServiceImpl.blockListStoreHashMap.get(follower);
		ArrayList<String> arrayOfBlock = new ArrayList<String>();
		boolean val = false;
		for(String str:values) {
		    arrayOfBlock.add(str);
		    if(str.equals(user))
		    	val = true;
		}
		if(!val) {
		    arrayOfBlock.add(user);
		    TweetStatsServiceImpl.blockListStoreHashMap.put(follower,arrayOfBlock);
		}else
		    TweetStatsServiceImpl.blockListStoreHashMap.put(follower,arrayOfBlock);		
	}else {
		blockUserDetails.clear();
		blockUserDetails.add(user);
		TweetStatsServiceImpl.blockListStoreHashMap.put(follower,blockUserDetails);
	}
  }
}

