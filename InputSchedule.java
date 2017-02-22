import java.util.*;
import java.io.*;

class Person
{
    // 7 days, 5 times per day
    int[][] preferences;
    String name;

    public Person(String name)
    {
        this.name = name;
        preferences = new int[7][5];
    }
}

public class InputSchedule
{
    String filename;
    public static ArrayList<Person> people;

    public InputSchedule(String filename)
    {
        this.filename = filename;    
        people = new ArrayList<>();

    }

    public void read_preferences() throws FileNotFoundException
    {
        Scanner in = new Scanner(new File(filename));

        for (int i = 0; i < 7; i++)
        {
            String name = in.next();
            Person p = new Person(name);

            for (int time = 0; time < 5; time++)
            {
                for (int day = 0; day < 7; day++)
                {
                    p.preferences[day][time] = in.nextInt();
                }
            }

            people.add(p);
        }
    }

    public static String decimal_to_gray(int decimal)
    {
        switch (decimal){
        case 0:
            return "000";
        case 1:
            return "001";
        case 2:
            return "011";
        case 3:
            return "010";
        case 4:
            return "110";
        case 5:
            return "111";
        case 6:
            return "101";
        case 7:
            return "100";
        default:
            return "000";
        }

    }

    public static int gray_to_decimal(String gray)
    {
        int b2 = Character.getNumericValue(gray.charAt(0)); // MSB
        int b1 = Character.getNumericValue(gray.charAt(1)) ^ b2;
        int b0 = Character.getNumericValue(gray.charAt(2)) ^ b1;

        return (b2 * 4) + (b1 * 2) + (b0);
    }

    public boolean valid_schedule(Chromo X)
    {
        // How many times a person has had a time assigned
        int[] assigned = new int[7];

        for (int day = 0; day < 7; day++)
        {
            for (int time = 0; time < 5; time++)
            {
                // System.out.println(day*15 + 3*time);
                int person_num = gray_to_decimal(X.chromo.substring(day*15 + 3*time, day*15 + 3*time + 3));
                
                if (person_num == 7)
                    return false;

                int[][] person_preferences = InputSchedule.people.get(person_num).preferences;
                if (person_preferences[day][time] == 0)
                    return false;

                assigned[person_num] += 1;

                if (assigned[person_num] > 5)
                    return false;
            }
        }

        return true;
    }
}