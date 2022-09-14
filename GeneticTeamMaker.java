import java.util.*;

public class GeneticTeamMaker {

    public static void main(String[] args) {
        String[] names = {"Richard Obama","Joe Roosevelt","Donald Johnson","Brick Washington","Richard Ali","Dan Roosevelt","Joe Smith","Bill Obama","John Ali","Frank Washington","John Smith","Joe Ali","Jack Obama","Joe Roosevelt","Brick Obama"};
        int[][] values = {{57,0,71,4,35},{4,45,63,71,47},{74,94,8,15,28},{11,28,19,81,72},{8,2,46,4,12},{8,1,8,76,76},{69,19,96,25,97},{90,53,28,35,34},{44,96,36,49,92},{80,80,25,49,74},{44,29,4,82,57},{31,4,27,7,96},{36,56,22,100,95},{47,64,98,25,93},{88,63,90,90,21}};
        Person[] people = new Person[15];
        for (int i = 0; i < 15; i++) {
            people[i] = new Person(names[i], values[i][0], values[i][1], values[i][2], values[i][3], values[i][4]);
        }
        int streak = 0;
        int last = 0;
        ArrayList<ArrayList<ArrayList<Person>>> creations = new ArrayList<>();
        for(int x = 0; x < 200; x++) {
            creations.add(milesRandomAttempt(people, 5));
        }
        while(streak < 4) {
            int s = creations.size();
            for(int x = 0; x < s - 1; x++) {
                for(int y = x + 1; y < s; y++) {
                    creations.add(reproduce(creations.get(x), creations.get(y)));
                }
            }
            creations.sort(Comparator.comparingInt(GeneticTeamMaker::fitness));
            creations = new ArrayList<>(creations.subList(Math.max(0, creations.size() - 200), creations.size()));
            int f = fitness(creations.get(creations.size() - 1));
            if(f == last) {
                streak++;
            } else {
                streak = 0;
            }
            last = f;
        }
        ArrayList<ArrayList<Person>> finale = (creations.get(creations.size() - 1));
        int num = 1;
        for(ArrayList<Person> team : finale) {
          System.out.println("TEAM " + num++);
          System.out.println();
          for(Person p : team) {
            System.out.println(p.name);
          }
          System.out.println();
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
        HashSet<Person> untaken = new HashSet<>();
        Queue<Pair> myqueue = new LinkedList<>();
        for (ArrayList<Person> people : g1) {
            for(Person p : people) {
                if(!untaken.add(p)) {
                    System.out.println(p);
                    System.out.println(g1);
                }
            }
        }
        for(int y = 0; y < g1.size(); y++) {
            ret.add(new ArrayList<>());
            for(int x = 0; x < g1.get(y).size(); x++) {
                if(taken.contains(g1.get(y).get(x)) && taken.contains(g2.get(y).get(x))) {
                    myqueue.add(new Pair(y, x));
                    continue;
                } else if(taken.contains(g1.get(y).get(x))) {
                    Person p = g2.get(y).get(x);
                    taken.add(p);
                    untaken.remove(p);
                    ret.get(ret.size() - 1).add(p);
                    continue;
                } else if (taken.contains(g2.get(y).get(x))) {
                    Person p = g1.get(y).get(x);
                    taken.add(p);
                    untaken.remove(p);
                    ret.get(ret.size() - 1).add(p);
                    continue;
                }
                double rand = Math.random();
                if(rand > 0.5) {
                    Person p = g2.get(y).get(x);
                    taken.add(p);
                    untaken.remove(p);
                    ret.get(ret.size() - 1).add(p);
                } else {
                    Person p = g1.get(y).get(x);
                    taken.add(p);
                    untaken.remove(p);
                    ret.get(ret.size() - 1).add(p);
                }
            }
        }
        while(!myqueue.isEmpty()) {
            Iterator<Person> i = untaken.iterator();
            Person next = i.next();
            i.remove();
            Pair temp = myqueue.poll();
            ret.get(temp.n1).add(temp.n2, next);
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

class Pair {
    int n1;
    int n2;
    public Pair(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
    }
    public String toString() {
        return "(" + n1 + ", " + n2 + ")";
    }
}
