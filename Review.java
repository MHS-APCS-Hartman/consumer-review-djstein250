import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/*
1a: public static double sentimentVal(String word)
1b: Yes. A String variable. Won't compile if you input a different type.
1c: Yes. Double

2: SentimentVal("awesome"); --> 0.92

3a: Yes. Right return and variable type
3b: No. Wrong return and variable type
3c: No. Only one parameter

4: The totalSentiment() method works by using a for loop to iterate file.length() times.
   It adds each character to a seperate string until we get to a space which means it's the end of a word
   It then removes the punctuation and finds the sentiment value of the word and adds it to a running total.
   It then returns the total

5a: The rating make sense because it doesn't give a 4 star unless it's really high and doesn't give 1 star unless it's really low
5b: One way to adjust totalSentiment would be to make a maximum and minimum total so that the numbers cant exceed or go below
    a certain amount. Another way to adjust would be to cast the total to an int to make all the totals more standardized and
    easier to give a star rating

6a: The logical error he made was starting his if - else if statements with the boolean expression "totalSentiment < 15"
    and going down. This is bad because if totalSentiment is 0, it will still return four.
6b: The easiest way to fix it would be to reverse the order of his boolean expressions to start at <0 and go up to <15
 */



/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {

  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
    
  
  /*
   * Method to determine the total sentiment value of a review.
   */
  public static double totalSentiment(String fileName) 
  {
      //to be completed during Activity 2!
      
      //COnvert filename to string
      //Create a string variable called currentWord
      //Create a double variable called total
      //Loop through file
         //Add characters to currentWord until you get to a space
         //Remove punctuation
         //Get sentiment value of current word and add to total
         //Reset currentWord
     
      String file = textToString(fileName);
      String currentWord = "";
      double total = 0;
      for(int i = 0; i < file.length(); i++)
      {
         String c = file.substring(i, i+1);
         if(c.equals(" "))
         {
            currentWord = removePunctuation(currentWord);
            total = total + sentimentVal(currentWord);
            currentWord = "";
         }
         else
         {
            currentWord = currentWord + c;
         }       
      }
      return total;
  }
  
  /**
   * Method to determine the star rating of a review found in the text file provided
   * by the parameter.
   * 
   * @param fileName - the name of the file containing the review
   * @return the star rating of the review, which is an integer value between 0 and 4
   */
  public static int starRating( String fileName )
  {
    //to be completed during Activity 2!
    
    double sent = totalSentiment(fileName);
    if(sent < -3)
    {
      return 0;
    } 
    else if(sent < 2) 
    {
      return 1;
    } 
    else if(sent < 8) 
    {
      return 2;
    } 
    else if(sent < 20) 
    {
      return 3;
    } 
    else 
    {
      return 4;
    }
    
    
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }
  
   /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }

  /*
    Inputs and file and returns a String with all the starred
    turned into random ones.
   */
  public static String fakeReview(String fileName)
  {
    String text = textToString(fileName);
    String word = "";
    String end = "";
    //Loop through the String adding each character to the variable word until you get to a space
    for(int i = 0; i < text.length(); i++)
    {
      String c = text.substring(i, i+1);
      
      if(!c.equals(" "))
      {
        word += c;
      }
      else
      {
        //If "*" isn't in the word, word will be added to end plus the space. Reset word.
        if(word.indexOf("*") == -1)
        {
          end += word + c;
          word = "";
        }
        //If "*" is in the word, a random adjective, word's punctuation, and the space will be added to end. Reset word.
        else
        {
          end += randomAdjective() + getPunctuation(word) + c;
          word = "";
        }
      }
    }
    //Checks for "*" one last time
    if(word.indexOf("*") == -1)
    {
      end += word + getPunctuation(word);
    }
    else
    {
      end += randomAdjective() + getPunctuation(word);
    }
    return end;
  }

  public static String fakeReviewStronger(String fileName)
  {
    String text = textToString(fileName);
    String word = "";
    String end = "";
    //Loop through the String adding each character to the word variable
    for(int i = 0; i < text.length(); i++)
    {
      String c = text.substring(i, i+1);
      if(!c.equals(" "))
      {
        word += c;
      }
      //Once there's a space, the following code runs.
      else
      {
        //if "*" isn't in the word, it will add the word, its punctuation and the space to the end variable. Resets word
        if(word.indexOf("*") == -1)
        {
          end += word + c;
          word = "";
        }
        /*
          When an adjective is found, it removes the "*", calls the makeStronger method, then adds word and its
          punctuation to end. Reset word
         */
        else
        {
          word = removePunctuation(word);
          word = makeStronger(word);
          end += word + getPunctuation(word) + c;
          word = "";
        }
      }
    }
    //Check for "*" one last time
    if(word.indexOf("*") == -1)
    {
      end += word;
    }
    else
    {
      String adj = removePunctuation(word);
      adj = makeStronger(adj);
      end += adj + getPunctuation(word);
    }
    return end;
  }

  /**
   * Makes an input adjective stronger. If the adjective is negative, it'll be more negative and vice versa.
   */
  public static String makeStronger(String word)
  {
    String adj = "";
    if(sentimentVal(word) >= 0)
    {
      while(sentimentVal(adj) <= sentimentVal(word))
      {
        adj = randomPositiveAdj();
      }
    }
    else
    {
      while(sentimentVal(adj) >= sentimentVal(word))
      {
        adj = randomNegativeAdj();
      }
    }
    return adj;
  }
  
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }
}
