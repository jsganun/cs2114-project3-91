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
        System.out.println();
        System.out.println("User options:");
        System.out.println("1. Show attractions");
        System.out.println("2. Show details of a specific attraction");
        System.out.println("3. Add an attraction");
        System.out.println("4. Remove an attraction");
        System.out.println("5. Open an attraction");
        System.out.println("6. Close an attraction");
        System.out.println("7. Open the park");
        System.out.println("8. Close the park");
        System.out.println("9. Add a visitor to an attraction's line");
        System.out.println("10. Advance time");
        System.out.println("0. Quit");
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
            Attraction attraction = attractions[i];
            System.out.println("ID " + attraction.getId() + ": "
                + attraction.getName() + " (" + openOrClosed(attraction)
                + ") - " + attraction.getCapacity() + " riders per round, "
                + attraction.getDuration() + " min ride");
        }
    }


    /**
     * Shows the details of a specific attraction
     *
     * @param attraction what attraction to display the details for
     */
    public void showAttractionDetails(Attraction attraction)
    {
        if(attraction == null)
        {
            throw new IllegalArgumentException();
        }

        System.out.println("Attraction " + attraction.getId() + ": "
            + attraction.getName());
        System.out.println("  Status: " + openOrClosed(attraction));
        System.out.println("  Capacity: " + attraction.getCapacity()
            + " riders per round");
        System.out.println("  Ride length: " + attraction.getDuration()
            + " minutes");
        System.out.println("  People waiting: " + attraction.getLineSize());
        System.out.println("  Time left in current round: "
            + attraction.getCurrDuration() + " minutes");
        System.out.println("  Wait if you join now: "
            + attraction.remainingTime() + " minutes ("
            + attraction.remainingRounds() + " rounds)");
    }


    /**
     * gives a word describing whether an attraction is open
     *
     * @param attraction the attraction to describe
     *
     * @return String "open" if the attraction is active, "closed" if not
     */
    private String openOrClosed(Attraction attraction)
    {
        if(attraction.getActive())
        {
            return "open";
        }
        return "closed";
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
        System.out.println("Enter a number (" + min + "-" + max + "): ");

        // Reads the whole line so the Enter key is never left behind for
        // the next prompt, and so letters re-prompt instead of crashing.
        while(true)
        {
            String line = input.nextLine().trim();
            try
            {
                int value = Integer.parseInt(line);
                if(value >= min && value <= max)
                {
                    return value;
                }
                System.out.println("That is not within range. Please try again:  ");
            }
            catch(NumberFormatException e)
            {
                System.out.println("That is not a whole number. Please try again:  ");
            }
        }
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
        System.out.println("Enter a number (" + min + "-" + max + "): ");

        // Reads the whole line so the Enter key is never left behind for
        // the next prompt, and so letters re-prompt instead of crashing.
        while(true)
        {
            String line = input.nextLine().trim();
            try
            {
                float value = Float.parseFloat(line);
                // Written this way so "NaN" is rejected too.
                if(value >= min && value <= max)
                {
                    return value;
                }
                System.out.println("That is not within range. Please try again:  ");
            }
            catch(NumberFormatException e)
            {
                System.out.println("That is not a number. Please try again:  ");
            }
        }
    }
}
