package edu.sjsu.cmpe275.aop.tweet.aspect;

import java.security.AccessControlException;
import java.util.ArrayList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;

@Aspect
@Order(2)
public class PermissionAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */
	@Autowired
	TweetStatsServiceImpl stats;
	
	@Before("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.retweet(..))")
	public void dummyBeforeAdvice(JoinPoint joinPoint) throws Throwable{
		
		String user = joinPoint.getArgs()[0].toString();
		int messageid = Integer.parseInt(joinPoint.getArgs()[1].toString());
		String userName = null;

		if(TweetStatsServiceImpl.tweetRetweetUserCounterDetails.containsKey(messageid)) {
			userName = TweetStatsServiceImpl.tweetRetweetUserCounterDetails.get(messageid);
		}

		if(TweetStatsServiceImpl.followerListStoreHashMap.containsKey(userName)) {
			ArrayList<String> values = TweetStatsServiceImpl.followerListStoreHashMap.get(userName);
			    for(String str:values) {
			    	if(str.equals(user)) {
			    		TweetStatsServiceImpl.reTweetAllow = true;
			    	}
			    }
		}
		
		if(TweetStatsServiceImpl.blockListStoreHashMap.containsKey(user)) {
			ArrayList<String> values = TweetStatsServiceImpl.blockListStoreHashMap.get(user);
			    for(String str:values) {
			    	if(str.equals(userName)) {
			    		TweetStatsServiceImpl.reTweetAllow = false;
			    	}
			    }	
		}	
		
		if(TweetStatsServiceImpl.reTweetAllow == false)
			throw new AccessControlException("An access control violation was attempted.");
		
		System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());

	}
	
}
