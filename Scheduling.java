import java.io.*;
import java.util.*;
import java.text.*;

public class Scheduling extends FitnessFunction{

/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/


/*******************************************************************************
*                            STATIC VARIABLES                                  *
*******************************************************************************/


/*******************************************************************************
*                              CONSTRUCTORS                                    *
*******************************************************************************/
    int num_people = 7;
    int num_times = 35;

	public Scheduling(){
		name = "Scheduling Problem";
	}

/*******************************************************************************
*                                MEMBER METHODS                                *
*******************************************************************************/

//  COMPUTE A CHROMOSOME'S RAW FITNESS *************************************

	public void doRawFitness(Chromo X){
        /*
		X.rawFitness = 0;
		for (int z=0; z<Parameters.numGenes * Parameters.geneSize; z++){
			if (X.chromo.charAt(z) == '1') X.rawFitness += 1;
		}
        */

        // Chromosome contains a bit string of length: log_2P * T, where P is the number
        // of people being scheduled and T is the number of possible times
        // Each time has log_2P bits to tell what person is being scheduled there

        // Example Chromo:
        // 011 010 011 100 100 001 101 010 100 001 110 001 .........
        // First 3 bits are Monday 8-10, then Monday 10-12, etc. throughout the week

        int top_choice_reward = 10;
        int second_choice_reward = 7;
        int third_choice_reward = 3;
        int available = 1;
        int unavailable = -50;

        int[] num_assigned = new int[7];

        for (int day = 0; day < 7; day++)
        {
            for (int time = 0; time < 5; time++)
            {
                // System.out.println(day*15 + 3*time);
                
                int person_num = gray_to_decimal(X.chromo.substring(day*15 + 3*time, day*15 + 3*time + 3));
                
                if (person_num == 7)
                {
                    X.rawFitness = 0;
                    return;
                }

                int[][] person_preferences = InputSchedule.people.get(person_num).preferences;

                // Keep track of how many times a person has been assigned
                
                if (++num_assigned[person_num] > 5)
                {
                    X.rawFitness = 0;
                    return;
                }
                
                if (person_preferences[day][time] == 0)
                    X.rawFitness += unavailable;
                if (person_preferences[day][time] == 1)
                    X.rawFitness += top_choice_reward;
                if (person_preferences[day][time] == 2)
                    X.rawFitness += second_choice_reward;
                if (person_preferences[day][time] == 3)
                    X.rawFitness += third_choice_reward;
                if (person_preferences[day][time] == 4)
                    X.rawFitness += available;
            }
        }
	}

    public static int gray_to_decimal(String gray)
    {
        int b2 = Character.getNumericValue(gray.charAt(0)); // MSB
        int b1 = Character.getNumericValue(gray.charAt(1)) ^ b2;
        int b0 = Character.getNumericValue(gray.charAt(2)) ^ b1;

        return (b2 * 4) + (b1 * 2) + (b0);
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

//  PRINT OUT AN INDIVIDUAL GENE TO THE SUMMARY FILE *********************************

	public void doPrintGenes(Chromo X, FileWriter output) throws java.io.IOException{
        /*
        System.out.println("Gene Alpha:");
		for (int i=0; i<Parameters.numGenes; i++){
			Hwrite.right(X.getGeneAlpha(i),11,output);
            System.out.println(X.getGeneAlpha(i));
		}
		output.write("   RawFitness");
		output.write("\n        ");
		for (int i=0; i<Parameters.numGenes; i++){
			Hwrite.right(X.getPosIntGeneValue(i),11,output);
		}
		Hwrite.right((int) X.rawFitness,13,output);
		output.write("\n\n");
        */

        String[] days = new String[]{"Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun"};
        String[] times = new String[]{"(8-10)", "(10-12)", "(12-2)", "(2-4)", "(4-6)"};
        // Print genes in a time table friendly format
        for (int day = 0; day < 7; day++)
        {
            for (int time = 0; time < 5; time++)
            {
                // System.out.println(day*15 + 3*time);
                int person_num = gray_to_decimal(X.chromo.substring(day*15 + 3*time, day*15 + 3*time + 3));


                System.out.printf("%30s", days[day] + " " + times[time] + ": " + InputSchedule.people.get(person_num).name);
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
		return;
	}

/*******************************************************************************
*                             STATIC METHODS                                   *
*******************************************************************************/

}