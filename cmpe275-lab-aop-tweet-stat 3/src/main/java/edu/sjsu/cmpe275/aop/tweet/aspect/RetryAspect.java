package edu.sjsu.cmpe275.aop.tweet.aspect;

import java.io.IOException;
import java.security.AccessControlException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;
import org.aspectj.lang.annotation.Around;

@Aspect
@Order(1)
public class RetryAspect {
    /***
     * Following is a tweet() function implementation of this aspect.
     * The below method will take care of network failure. If IOException occurred then below aspect will try 3 times.
     * @throws Throwable 
     */

	@Around("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))")
	public int retryTweet(ProceedingJoinPoint joinPoint) throws IOException {
		Integer result;
		try {
			System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
			TweetStatsServiceImpl.counter=TweetStatsServiceImpl.counter+1;
			result =  (Integer) joinPoint.proceed();

		}
		catch(Throwable e){
			try{
				result =  (Integer) joinPoint.proceed();
			}
			catch(Throwable e1){
				try{
					result =  (Integer) joinPoint.proceed();
				}
				catch(Throwable e2){
					try{
						result =  (Integer) joinPoint.proceed();
					}
					catch(Throwable e3){
						throw new IOException("Maximum retry exceeded...");
					}
				}
			}
		}
		
		return TweetStatsServiceImpl.counter;
	}
	
    /***
     * Following is a retweet() function implementation of this aspect.
     * The below method will take care of network failure. If IOException occurred then below aspect will try 3 times.
     * @throws Throwable 
     */

	@Around("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.retweet(..))")
	public int retryRetweet(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Integer result;
		try {
			System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
			TweetStatsServiceImpl.counter=TweetStatsServiceImpl.counter+1;
			result =  (Integer) joinPoint.proceed();
		}
		catch (AccessControlException e){
			throw new AccessControlException("An access control violation was attempted.sd");
		}
		catch(Throwable e){
			try{
				result =  (Integer) joinPoint.proceed();
			}
			catch(Throwable e1){
				try{
					result =  (Integer) joinPoint.proceed();
				}
				catch(Throwable e2){
					try{
						result =  (Integer) joinPoint.proceed();
					}
					catch(Throwable e3){
						throw new IOException("Maximum retry exceeded...");
					}
				}
			}
		}
		return TweetStatsServiceImpl.counter;
	}
	
    /***
     * Following is a follow() function implementation of this aspect.
     * The below method will take care of network failure. If IOException occurred then below aspect will try 3 times.
     * @throws Throwable 
     */	
	
	@Around("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	public void retryFollow(ProceedingJoinPoint joinPoint) throws Throwable {
		
		try {
			joinPoint.proceed();
			System.out.printf("Finished the executuion of the metohd %s with the Update in Follower Table\n", joinPoint.getSignature().getName());
		} 	
		catch(Throwable e){
			try{
				joinPoint.proceed();
			}
			catch(Throwable e1){
				try{
					joinPoint.proceed();
				}
				catch(Throwable e2){
					try{
						joinPoint.proceed();
					}
					catch(Throwable e3){
						throw new IOException("Maximum retry exceeded...");
					}
				}
			}
		}
	}
	
	
    /***
     * Following is a block() function implementation of this aspect.
     * The below method will take care of network failure. If IOException occurred then below aspect will try 3 times.
     * @throws Throwable 
     */	
	

	@Around("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
	public void retryBlock(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			joinPoint.proceed();
			System.out.printf("Finished the executuion of the metohd %s with the Update in Follower Table\n", joinPoint.getSignature().getName());
		} 	
		catch(Throwable e){
			try{
				joinPoint.proceed();
			}
			catch(Throwable e1){
				try{
					joinPoint.proceed();
				}
				catch(Throwable e2){
					try{
						joinPoint.proceed();
					}
					catch(Throwable e3){
						throw new IOException("Maximum retry exceeded...");
					}
				}
			}
		}
	}
}
