package classes;

import java.util.Scanner;

public class ParkView {
    private final Scanner input;

    public ParkView() 
    {
        input = new Scanner(System.in);
    }

    /**
     * Shows the main options the user can select
     */
    public void showOptions() 
    {
        throw new UnsupportedOperationException("TODO");
    }
    
    
    /**
     * Shows the list of attractions that are currently in the park
     */
    public void showAttractions(Attraction[] attractions)
    {
        
    }

    
    /**
     * Shows the details of a given attraction. 
     * User is instructed to enter the ID of the ride
     * 
     * @param attraction what attraction to display the details for
     */
    public void showAttractionDetails(Attraction attraction) 
    {
        throw new UnsupportedOperationException("TODO");
    }

    
    /**
     * Shows the list of visitors that are currently in the park
     */
    public void showVisitors(Visitor[] visitors) 
    {
        throw new UnsupportedOperationException("TODO");
    }

    
    /**
     * 
     */
    public void showMessage(String message) 
    {
        throw new UnsupportedOperationException("TODO");
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
