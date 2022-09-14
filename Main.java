import java.util.*;

class Main {
  /*
  After looking at this, I think a lot of ideas can be grouped together
  */
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    doit(s);
    


  }

  public static void doit(Scanner s) {
    while (true){
      System.out.println("Welcome to Split, please choose your action");
      
      System.out.println("Split Check?  Enter 1\nSplit Time?   Enter 2\nSplit Groups? Enter 3\nExit?         Enter 4");
      int selection = -1;
      while(s.hasNextInt()) {
        selection = s.nextInt();
        if (selection > 0 && selection <= 4) {
          break;
        } else {
          System.out.print("Invalid selection, Please try again: ");
        }
      }

      s.nextLine();

      System.out.print("You have selected: ");
      switch(selection) {
      case 1:
        System.out.println("Split Check");
        Utilities.splitCheck(s);
        break;
      case 2:
        System.out.println("Split Time");
        Utilities.splitTime(s);
        break;
      case 3:
        System.out.println("Split Groups");
        Utilities.splitGroups(s);
        break;
      case 4:
        System.out.println("Exit Program");
        System.out.println("Hope you enjoyed using Split!");
        return;
      default:
        System.out.println("Something went wrong");
        return;
      }
      System.out.println("Use another functionality? Enter 1\nExit?                      Enter 2");
      while(s.hasNextInt()) {
        selection = s.nextInt();
        if (selection > 0 && selection <= 2) {
          break;
        } else {
          System.out.print("Invalid selection, Please try again: ");
        }
      }
      switch (selection) {
      case 1:
        break;
      case 2:
        System.out.println("Hope you enjoyed using Split!");
        return;
      default:
        System.out.println("Something went wrong");
        return;
      }
    }
  }

  
  

  public static ArrayList<ArrayList<Person>> milesRandomAttempt(Person[] arr, int numTeams) {
    ArrayList<ArrayList<Person>> ret = new ArrayList<>();
    HashSet<Integer> used = new HashSet<>();
    int peeps = arr.length;
    int temp = numTeams;
    for(int t = 0; t < numTeams; t++) {
      ret.add(new ArrayList<>());
      for(int x = 0; x < peeps / temp; x++) {
        int r;
        do {
          r = (int)(Math.random() * arr.length);
        } while (used.contains(r));
        ret.get(t).add(arr[r]);
        used.add(r);
      }
      peeps -= peeps / temp;
      temp--;
    }
    return ret;
  }

  public static int fitness(ArrayList<ArrayList<Person>> teams) {
    int pen = 0;
    for(ArrayList<Person> team : teams) {
      int averagei = 0;
      int averagea = 0;
      int averagen = 0;
      int averagec = 0;
      int averageo = 0;
      for(int x = 0; x < team.size(); x++) {
        averagei += team.get(x).introversion;
        averagea += team.get(x).agreeableness;
        averagen += team.get(x).neuroticism;
        averagec += team.get(x).conscientiousness;
        averageo += team.get(x).openness;
      }
      averagei /= team.size();
      averagea /= team.size();
      averagen /= team.size();
      averagec /= team.size();
      averageo /= team.size();
      for(int x = 0; x < team.size(); x++) {
        pen -= Math.abs(team.get(x).introversion - averagei);
        pen -= Math.abs(team.get(x).agreeableness - averagea);
        pen -= Math.abs(team.get(x).neuroticism - averagen);
        pen -= Math.abs(team.get(x).conscientiousness - averagec);
        pen -= Math.abs(team.get(x).openness - averageo);
      }
    }
    return pen;
  }

  public static ArrayList<ArrayList<Person>> reproduce(ArrayList<ArrayList<Person>> g1, ArrayList<ArrayList<Person>> g2) {
    ArrayList<ArrayList<Person>> ret = new ArrayList<>();
    HashSet<Person> taken = new HashSet<>();
    for(int y = 0; y < g1.size(); y++) {
      ret.add(new ArrayList<>());
      for(int x = 0; x < g1.get(y).size(); x++) {
        if(taken.contains(g1.get(y).get(x))) {
          taken.add(g2.get(y).get(x));
          ret.get(ret.size() - 1).add(g2.get(y).get(x));
          continue;
        } else if (taken.contains(g2.get(y).get(x))) {
          taken.add(g1.get(y).get(x));
          ret.get(ret.size() - 1).add(g1.get(y).get(x));
          continue;
        }
        double rand = Math.random();
        if(rand > 0.5) {
          taken.add(g2.get(y).get(x));
          ret.get(ret.size() - 1).add(g2.get(y).get(x));
        } else {
          taken.add(g1.get(y).get(x));
          ret.get(ret.size() - 1).add(g1.get(y).get(x));
        }
      }
    }
    mutate(ret);
    return ret;
  }

  public static void mutate(ArrayList<ArrayList<Person>> original) {
    int prob = (2 / (original.size() * original.get(0).size()));
    for(int y = 0; y < original.size(); y++) {
      for(int x = 0; x < original.get(y).size(); x++) {
        double rand = Math.random();
        if(rand < prob) {
          int ry = (int)(Math.random() * original.size());
          int rx = (int)(Math.random() * original.get(ry).size());
          Person temp = original.get(ry).get(rx);
          original.get(ry).set(rx, original.get(y).get(x));
          original.get(y).set(x, temp);
        }
      }
    }
  }
}
