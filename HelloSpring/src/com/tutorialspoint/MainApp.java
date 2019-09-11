package com.tutorialspoint;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
   public static void main(String[] args) {     
      
      ApplicationContext context =
    		  new ClassPathXmlApplicationContext("beans.xml");
      
      HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
      obj.getGreeting();
      
//      
//    		  SequenceGenerator generator =
//    		  (SequenceGenerator) context.getBean("sequenceGenerator");
//    		  System.out.println(generator.getSequence());
//    		  System.out.println(generator.getSequence());
      
      
   }
}