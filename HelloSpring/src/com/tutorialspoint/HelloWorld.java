package com.tutorialspoint;

public class HelloWorld implements Greeter{
   private String message;

   public void setName(String message){
      this.message  = message;
   }
   public String getGreeting(){
      System.out.println("Hello world from " + message + "!");
      return message;
   }
}
