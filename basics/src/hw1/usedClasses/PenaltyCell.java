/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

/**
 * this class represent penalty cell and its functionality at the game
 */
public class PenaltyCell extends Monopoly {

  /**
   * method to for imposing Penalty on player/bot, and make him pay
   *
   * @param unknownPlayer can be player of bot
   * @param x             x penalty cell position
   * @param y             y penalty cell position
   */
  public void imposePenalty(Object unknownPlayer, int x, int y) {
    final float penaltyCoeff = InitializationRandomFields.penaltyCoeff;
    if (unknownPlayer instanceof Player) {
      Player.setBalance(Math.round(Player.getBalance() - Player.getBalance() * penaltyCoeff));
      int fine = Math.round(Player.getBalance() - Player.getBalance() * penaltyCoeff);
      System.out.println(
          "You just got fined " + fine + "$ for standing on PenaltyCell <" + x + "><" + y + ">");
    } else {
      Bot.setBalance(Math.round(Bot.getBalance() - Bot.getBalance() * penaltyCoeff));
      int fine = Math.round(Bot.getBalance() - Bot.getBalance() * penaltyCoeff);
      System.out.println(
          "The robot was just fined " + fine + "$ for standing on PenaltyCell <" + x + "><" + y
              + ">");
    }
  }

  /**
   * toString method
   *
   * @return symbol of penalty cell
   */
  @Override
  public String myOutputToString() {
    return "%";
  }

  ;
}
