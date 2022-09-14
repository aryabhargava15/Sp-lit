public class Person {
  String name;
  int introversion;
  int agreeableness;
  int neuroticism;
  int conscientiousness;
  int openness;

  public Person(String name, int introversion, int agreeableness, int neuroticism, int conscientiousness, int openness) {
    this.name = name;
    this.introversion = introversion;
    this.agreeableness = agreeableness;
    this.neuroticism = neuroticism;
    this.conscientiousness = conscientiousness;
    this.openness = openness;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Person && ((Person)obj).introversion == introversion && ((Person)obj).agreeableness == agreeableness && ((Person)obj).neuroticism == neuroticism && ((Person)obj).conscientiousness == conscientiousness && ((Person)obj).openness == openness;
   }

   @Override
   public int hashCode() {
     return introversion + (100 * agreeableness) + (1000 * neuroticism) + (10000 * conscientiousness) + (openness * 100000);
   }

   public String toString() {
     return name + " (" + introversion + "I, " + agreeableness + "A, " + neuroticism + "N, " + conscientiousness + "C, " + openness + "O)";
   }
}