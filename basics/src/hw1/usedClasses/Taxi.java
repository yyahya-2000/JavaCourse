/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

/**
 * Taxi class represent the functionality of taxi into the game
 */
public class Taxi extends Monopoly {

  /**
   * getter of taxiDistance field
   *
   * @return the distance that player/bot should move by taxi
   */
  public int getTaxiDistance() {
    return taxiDistance;
  }

  /**
   * the distance that player/bot should move by taxi
   */
  private int taxiDistance = InitializationRandomFields.random.nextInt(2) + 3;

  /**
   * setter of taxiDistance field (randomly)
   *
   */
  public void setNewTaxiDistance() {
    taxiDistance = InitializationRandomFields.random.nextInt(2) + 3;
  }

  /**
   * toString method
   *
   * @return string contain the symbol of taxi cell
   */
  @Override
  public String myOutputToString() {
    return "T";
  }


  /**
   * toString method to report taxi use
   *
   * @param isPlayer player / bot
   * @return information for taxi user about the distance that he moved
   */
  public String myToString(boolean isPlayer) {
    if (isPlayer) {
      return "You are shifted forward by " + taxiDistance + " cells";
    } else {
      return "Bot is shifted forward by " + taxiDistance + " cells";
    }
  }
}
