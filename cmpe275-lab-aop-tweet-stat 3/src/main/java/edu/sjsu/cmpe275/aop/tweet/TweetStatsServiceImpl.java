package edu.sjsu.cmpe275.aop.tweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TweetStatsServiceImpl implements TweetStatsService {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */
	
	public static int counter=0; // this is just counter which we can use for generating unique id by incrementing in every-time.
	public static int lengthLongestTweet = 0;	
	public static Map<String, Integer > messageLengthCountHashMap = new HashMap<String, Integer >();//message length for last function statistics
	//public static Map<Integer, String > tweetUserCounterDetails = new HashMap<Integer, String >(); // user with message unique id
	public static Map<Integer, String > tweetRetweetUserCounterDetails = new HashMap<Integer, String >(); // user with message unique id
	public static Map<Integer, String > tweetMessageIdDetails = new HashMap<Integer, String >(); // message with message unique id
	public static Map<String, ArrayList<String> > followerListStoreHashMap = new HashMap<String, ArrayList<String> >();
	public static Map<String, ArrayList<String> > blockListStoreHashMap = new HashMap<String, ArrayList<String> >();
	public static Map<Integer, Integer> mostProductiveMessage = new HashMap<Integer, Integer >();
	public static Map<Integer, Integer > tweetUserMessageDetails = new HashMap<Integer, Integer >(); // user with message unique id
	public static Map<Integer, HashMap<String,Integer>> mostProductiveMessageTrialFinal = new HashMap<Integer, HashMap<String,Integer> >();
	
	
	public static Map<Integer, ConcurrentHashMap<String,Integer>> mostProductiveMessageUser100 = new HashMap<Integer, ConcurrentHashMap<String,Integer> >();
	
	
	public static boolean reTweetAllow = false;
	
	public void resetStatsAndSystem() {
		//lots of HashMap declared as the requirement got changes I created new HashMap I haven't mingled with the old one. 
		TweetStatsServiceImpl.counter = 0;
		TweetStatsServiceImpl.lengthLongestTweet = 0;
		TweetStatsServiceImpl.messageLengthCountHashMap.clear();
		TweetStatsServiceImpl.tweetRetweetUserCounterDetails.clear();
		TweetStatsServiceImpl.tweetMessageIdDetails.clear();
		TweetStatsServiceImpl.followerListStoreHashMap.clear();
		TweetStatsServiceImpl.blockListStoreHashMap.clear();
		TweetStatsServiceImpl.mostProductiveMessage.clear();
		TweetStatsServiceImpl.tweetUserMessageDetails.clear();
		TweetStatsServiceImpl.mostProductiveMessageTrialFinal.clear();
		TweetStatsServiceImpl.mostProductiveMessageUser100.clear();
		TweetStatsServiceImpl.reTweetAllow = false;
	}
    
	public int getLengthOfLongestTweet() {

		return TweetStatsServiceImpl.lengthLongestTweet;
	}

	public String getMostFollowedUser() {
		int max = 0;
		ArrayList<String> mostFollowerList = new ArrayList<String>();
		for (java.util.Map.Entry<String, ArrayList<String>> ee : TweetStatsServiceImpl.followerListStoreHashMap.entrySet()) {
		    String key = ee.getKey();
		    ArrayList<String> values = ee.getValue();

		   if(values.size()>max)
		   {
			   max = values.size();
			   mostFollowerList.clear();
			   mostFollowerList.add(key);
		   }else
			   if(values.size() == max && values.size()!=0) {
				   mostFollowerList.add(key);
			   }
		}
		
		Collections.sort(mostFollowerList);
		return mostFollowerList.size()!=0 ? mostFollowerList.get(0): null;
	}

	public String getMostPopularMessage() {
		
		int max = 0;
		ArrayList<String> popularMessage = new ArrayList<String>();
		for (Entry<Integer, Integer> ee : TweetStatsServiceImpl.mostProductiveMessage.entrySet()) {
			
		    int key = ee.getKey();
		    int values = ee.getValue();

		   if(values > max)
		   {
			   popularMessage.clear();
			   popularMessage.add(TweetStatsServiceImpl.tweetMessageIdDetails.get(key));
			   max = values;
		   }else
			   if(values == max && values!=0) {
				  popularMessage.add(TweetStatsServiceImpl.tweetMessageIdDetails.get(key));
			   }
		   
		}
		Collections.sort(popularMessage);
		return popularMessage.size()!=0 ? popularMessage.get(0): null;
	}
	
	public String getMostProductiveUser() {
		
		int max = 0;
		ArrayList<String> productiveUserList = new ArrayList<String>();
		
		for (java.util.Map.Entry<String, Integer> ee : TweetStatsServiceImpl.messageLengthCountHashMap.entrySet()) {
		    String key = ee.getKey();
		    int values = ee.getValue();

		   if(values > max)
		   {
			   max = values;
			   productiveUserList.clear();
			   productiveUserList.add(key);
		   }else
			   if(values == max && values!=0) 
				   productiveUserList.add(key);
		}
		Collections.sort(productiveUserList);
		return productiveUserList.size()!=0 ? productiveUserList.get(0): null;
	}
	
	public static int getCountOfFollowersAndBlockers(String user,int currentCnt) {
		
		ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
		
		HashMap<String,Integer> mostFollowerListtrialFinal = new HashMap<String,Integer>();
		mostFollowerListtrialFinal = TweetStatsServiceImpl.mostProductiveMessageTrialFinal.get(currentCnt);
		String originalUser = "";
		for(Entry<String, Integer> hm : mostFollowerListtrialFinal.entrySet()) {
			String key = hm.getKey();
			Integer m = hm.getValue();
				if(m == 1) {
					originalUser = key;
				}
			}	
		if(TweetStatsServiceImpl.mostProductiveMessageUser100.containsKey(TweetStatsServiceImpl.counter)) {	
			ConcurrentHashMap<String,Integer> lst = TweetStatsServiceImpl.mostProductiveMessageUser100.get(TweetStatsServiceImpl.counter);
			for(String str:lst.keySet()) {
					if(!originalUser.equals(str)) {
						concurrentHashMap.put(str,"a");
					}
			}
		}
		
		for(Entry<String, String> s:concurrentHashMap.entrySet()) {
			String key = s.getKey();
			if(TweetStatsServiceImpl.blockListStoreHashMap.containsKey(key)) {
				ArrayList<String> lst = TweetStatsServiceImpl.blockListStoreHashMap.get(key);
				for(String str:lst) {
					if(str.equals(user)) {
						concurrentHashMap.remove(key);
					}
		    	}
			}
		}
		return concurrentHashMap.size();
	}
}



