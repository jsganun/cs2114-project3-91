package classes;

import java.util.Scanner;

/**
 *  Displays to and take information from the user regarding the park
 * 
 *  @author Katie Smith (ksmith08)
 *  @version Nov 12, 2026
 */
public class ParkView {
    private final Scanner input;

    /**
     * instantiates the scanner to take in user input
     */
    public ParkView() 
    {
        input = new Scanner(System.in);
    }

    /**
     * Shows the main options the user can select
     */
    public void showOptions() 
    {
        System.out.println("User options:");
        System.out.println("1. Show attractions");
        System.out.println("2. Show details of a specific attraction");
        System.out.println("3. Show visitors");
    }
    
    
    /**
     * Shows the list of attractions that are currently in the park
     * 
     * @param attractions array of attractions that are in the park
     */
    public void showAttractions(Attraction[] attractions)
    {
        if(attractions == null)
        {
            throw new IllegalArgumentException();
        }
            
        for(int i = 0; i < attractions.length; i++)
        {
            System.out.println(attractions[i]);
        }
    }

    
    /**
     * Shows the details of a specific attraction
     * 
     * @param attraction what attraction to display the details for
     */
    public void showAttractionDetails(Attraction attraction) 
    {
        System.out.println(attraction);
    }

    
    /**
     * Shows the list of visitors that are currently in the park
     * 
     * @param visitors array of visitors that are in the park
     */
    public void showVisitors(Visitor[] visitors) 
    {
        if(visitors == null)
        {
            throw new IllegalArgumentException();
        }
        
        for(int i = 0; i < visitors.length; i++)
        {
            System.out.println(visitors[i]);
        }
    }

    
    /**
     * displays a message to the user
     * 
     * @param message the message to be displayed
     */
    public void showMessage(String message) 
    {
        System.out.println(message);
    }

    
    /**
     * asks the user "yes or no" to receive confirmation
     * 
     * @return boolean true/false corresponds with the user's answer (yes/no)
     */
    public boolean promptConfirmation() 
    {
        System.out.println("Enter yes(Y) or no(N)");
        String response = input.nextLine();
        
        while(!response.equals("Y") && !response.equals("N"))
        {
            System.out.println("Your response must be formatted as Y or N. Please try again: ");
            response = input.nextLine();
        }
        
        if(response.equals("Y"))
        {
            return true;
        }
        return false;
    }

    
    /**
     * creates a string prompt
     * 
     * @param chars character limit
     * 
     * @return String the entered letters as a String
     */
    public String promptString(int chars) 
    {
        System.out.println("Enter your message: ");
        String string = input.nextLine();
        
        while(string.length() > chars )
        {
            System.out.println("That exceeds the character limit. Please try again: ");
            string = input.nextLine();
        }
        
        return string;
    }

    
    /**
     * establishes whether a given integer is within range
     * 
     * @param min low end of the range
     * 
     * @param max high end of the range
     * 
     * @return int the entered number when completed
     */
    public int promptIntRange(int min, int max) 
    {
        System.out.println("Enter a number: ");
        int value = input.nextInt();
        
        while(value < min || value > max)
        {
            System.out.println("That is not within range. Please try again:  ");
            value = input.nextInt();
        }
        
        return value;
    }

    
    /**
     * establishes whether a given float is within range
     * 
     * @param min low end of range
     * 
     * @param max high end of range
     * 
     * @return float the entered number when completed
     */
    public float promptFloatRange(float min, float max) 
    {
        System.out.println("Enter a number: ");
        float value = input.nextFloat();
        
        while(value < min || value > max)
        {
            System.out.println("That is not within range. Please try again:  ");
            value = input.nextFloat();
        }
        
        return value;
    }
}
