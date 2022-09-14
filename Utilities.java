import java.util.*;
public class Utilities 
{
  public static void splitCheck(Scanner s) {
    double price = -1;
    int people = -1;
    while (true) {
      System.out.print("Please enter the total price: ");
      if (s.hasNextDouble()) {
        price = s.nextDouble();
      } else {
        s.nextLine();
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      if (price < 0) {
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      break;
    }
    s.nextLine();
    while (true) {
      System.out.print("Please enter the total number of people: ");
      if (s.hasNextInt()) {
        people = s.nextInt();
      } else {
        s.nextLine();
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      if (people <= 0) {
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      break;
    }
    
    double equal = split(price, people);
    
    System.out.printf("The equal split is: %.2f\n", equal);
  }


  public static void splitTime(Scanner s) {
    double time = 0;
    int tasks = 1;
    while (true) {
      System.out.print("Please enter the total time: ");
      if (s.hasNextDouble()) {
        time = s.nextDouble();
      } else {
        s.nextLine();
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      if (time < 0) {
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      break;
    }
    s.nextLine();
    while (true) {
      System.out.print("Please enter the total number of tasks: ");
      if (s.hasNextInt()) {
        tasks = s.nextInt();
      } else {
        s.nextLine();
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      if (tasks <= 0) {
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      break;
    }
    int variation;
    s.nextLine();
    while (true) {
      System.out.print("Please enter variation requested (0 - 10): ");
      if (s.hasNextInt()) {
        variation = s.nextInt();
      } else {
        s.nextLine();
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      if (variation < 0 || variation > 10) {
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      break;
    }
    double[] equal = splitTime(tasks, time, variation);
    
    System.out.println("The time you should spend on each task is:");

    for (int i = 1; i <= equal.length; ++i) {
      System.out.printf("Task %d: %.2f\n", i, equal[i - 1]);
    }
  }

  public static void splitGroups(Scanner s) {
    ArrayList<String> names = new ArrayList<String>();
    int groups = 0;
    
    while (true) {
      System.out.print("Please enter a name, or -1 if finished: ");
      int temp;
      String name = "";
      if (s.hasNextInt()) {
        temp = s.nextInt();
        if (temp == -1) {
          break;
        } else {
          s.nextLine();
          System.out.println("Invalid input, Please try again.");
          continue;
        }
      } 

      name = s.nextLine();
      //System.out.println(name);
      if (name.length() == 0 || names.contains(name)) {
        // nothing in here or if the name is the same
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      names.add(name);
      
    }

    s.nextLine();

    while (true) {
      System.out.print("Please enter the total number of groups: ");
      if (s.hasNextInt()) {
        groups = s.nextInt();
      } else {
        s.nextLine();
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      if (groups <= 0) {
        System.out.println("Invalid input, Please try again.");
        continue;
      }
      break;
    }
    
    ArrayList<ArrayList<String>> equal = splitTeamsRandom(names, groups);

    for (int i = 0; i < equal.size(); ++i) {
      String groupOut = "Group " + (i + 1) + ": ";
      for (String a : equal.get(i)) {
        groupOut += a + ", ";
      }
      groupOut = groupOut.substring(0, groupOut.length() - 2);
      System.out.println(groupOut);
    }
  }

  public static double split(double price, int people) {
    return 1.0 * price / people;
  }

  public static ArrayList<ArrayList<String>> splitTeamsRandom(ArrayList<String> arr, int numTeams) {
    ArrayList<Integer> teams = new ArrayList<Integer>(arr.size());
    ArrayList<ArrayList<String>> out = new ArrayList<ArrayList<String>>();
    for (int i = 0; i < numTeams; ++i) {
      out.add(new ArrayList<String>());
    }
    for (int i = 0; i < numTeams; ++i) {
      for (int j = 0; j < arr.size() / numTeams; ++j) {
        teams.add(i);
      }
    }
    for (int i = 0; i < arr.size() % numTeams; ++i) {
      teams.add(i); 
    }
    for (int i = 0; i < arr.size(); ++i) {
      int random = (int)(Math.random() * teams.size());
      int team = teams.remove(random);
      out.get(team).add(arr.get(i));
    }
    return out;
  }
  
  // Splits given amount of time among a given amout of tasks with added variation if necessary
  // inputs: tasks = # of tasks, time = amount of time, variation (scale of 1 - 10) of how much variation is desired
  // outputs: double[] time designated per task
  public static double[] splitTime(int tasks, double time, int variation) {
    // Variation is on a scale of 0 - 10;
    // The variation factor will determine the amount of variation in results per task
    double equal = 1.0 * time / tasks;
    int actualVariation = (int) (tasks * variation / 7);
    double[] var = new double[tasks];
    // make list to keep track of variation, must add to 0;
    for (int i = 0; i < tasks; ++i) {
      var[i] = equal;
    }
    for (int i = 0; i < actualVariation; ++i) {
      // for every iteration, we take some 
      double ran = Math.random() * time / 10;
      int index = (int) (Math.random() * tasks);

      int index2 = (int) (Math.random() * tasks);
      if (var[index2] - ran > 0 && var[index] + ran < time) {
        var[index2] -= ran;
        var[index] += ran;
      }
    }
    return var;
  }
}