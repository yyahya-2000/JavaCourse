/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

/**
 * class Вот represent the bot functionality
 */
public class Bot {

  /**
   * how much bot has money
   */
  private static int balance;

  /**
   * getter of balance field
   *
   * @return the value of balance field
   */
  public static int getBalance() {
    return Bot.balance;
  }

  /**
   * setter of balance field
   *
   * @param balance new balance value
   */
  public static void setBalance(int balance) {
    Bot.balance = balance;
  }

  /**
   * method for visiting shop by bot and determine its request
   *
   * @param shop the shop which was visited by bot
   * @param x    x shop position
   * @param y    y shop position
   */
  public void visitShop(Shop shop, int x, int y) {
    final int buyImproveOrNot = InitializationRandomFields.random.nextInt(2);
    if (shop.getOwner() == 0) {//if there is no owner for the shop
      if (Bot.getBalance() - shop.getN() > 0 && buyImproveOrNot == 0) {
        buyShop(shop, x, y);
      }
    } else if (shop.getOwner() == -1) { // if the owner is the bot
      if (Bot.getBalance() - shop.getN() > 0 && buyImproveOrNot == 0) {
        improveShop(shop, x, y);
      }
    } else { //if the owner is the player
      payingCompensation(shop, x, y);
    }
  }

  /**
   * method for paying compensation in case of locating in player's shop
   *
   * @param shop intended shop
   * @param x    shop x position
   * @param y    shop y position
   */
  private void payingCompensation(Shop shop, int x, int y) {

    Bot.setBalance(Math.round(Bot.getBalance() - shop.getK()));
    Player.setBalance(Math.round(Player.getBalance() + shop.getK()));
    System.out.println(
        "The bot has already paid compensation: " + Math.round(shop.getK()) + "$ to you in shop <"
            + x + "><"
            + y + ">");
  }

  /**
   * method for improving the shop in case of locating in bot's shop
   *
   * @param shop intended shop
   * @param x    x shop position
   * @param y    y shop position
   */
  private void improveShop(Shop shop, int x, int y) {
    if (Bot.getBalance() - shop.getN() >= 0) {
      Bot.setBalance(Math.round(Bot.getBalance() - shop.getImprovementCoeff() * shop.getN()));
      shop.setN(Math.round(shop.getN() + shop.getN() * shop.getImprovementCoeff()));
      shop.setK(shop.getK() + shop.getK() * shop.getCompensationCoeff());
      System.out.println("The shop <" + x + "><" + y + "> has upgraded by the bot!");
    } else {
      System.out.println("The bot wanted to upgrade the shop <" + x + "><" + y
          + ">, but couldn't because of insufficient its money!");
    }
  }


  /**
   * method for buying a shop by bot in the absence of an owner for the shop
   *
   * @param shop intended shop
   * @param x    x shop position
   * @param y    y shop position
   */
  private void buyShop(Shop shop, int x, int y) {
    if (Bot.getBalance() - shop.getN() >= 0) {
      Bot.setBalance(Bot.getBalance() - shop.getN());
      System.out.println("Bot is already the owner of the shop <" + x + "><" + y + ">");
      shop.setOwner(-1);
    } else {
      System.out.println("The bot wanted to buy the shop <" + x + "><" + y
          + ">, but couldn't because of insufficient its money!");
    }

  }


  /**
   * to string method for getting information about bot balance
   *
   * @return string contain bot balance
   */
  @Override
  public String toString() {
    return "Bot{\nbalance= " + Bot.getBalance() + "$\n}";
  }
}
