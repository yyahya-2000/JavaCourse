/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

import java.util.*;

/**
 * class Player represent the player functionality
 */
public class Player {

  /**
   * how much player has money
   */
  private static int balance;
  /**
   * the amount of debt from bank
   */
  private int amountOfDebt = 0;
  /**
   * the amount of money that was spent for improving and buying shops
   */
  private float developmentExpense = 0;

  public float getDevelopmentExpense() {
    return developmentExpense;
  }

  /**
   * getter of balance field
   *
   * @return the value of balance field
   */
  public static int getBalance() {
    return Player.balance;
  }

  /**
   * setter of balance field
   *
   * @param balance new balance value
   */
  public static void setBalance(int balance) {
    Player.balance = balance;
  }

  /**
   * getter of amountOfDebt field
   *
   * @return the amount of debt that player get from bank
   */
  public int getAmountOfDebt() {
    return amountOfDebt;
  }

  /**
   * setter of amountOfDebt field
   *
   * @param newDebtAmount amount of new debt
   */
  public void setAmountOfDebt(int newDebtAmount) {
    this.amountOfDebt = newDebtAmount;
  }

  /**
   * method for visiting shop by player and determine his request
   *
   * @param shop the shop which was visited by player
   * @param x    x shop position
   * @param y    y shop position
   */
  public void visitShop(Shop shop, int x, int y) {
    if (shop.getOwner() == 0) {
      if (askIf("You are in shop cell <" + x + "><" + y + ">. This shop has no owner. "
          + "Would you like to buy it for " + shop.getN()
          + "$? Input ‘Yes’ if you agree or ‘No’ otherwise")) {
        buyShop(shop, x, y);
      }
    } else if (shop.getOwner() == 1) {
      if (askIf(
          "You are in your shop <" + x + "><" + y + ">."
              + " Would you like to upgrade it for " + Math
              .round(shop.getN() * shop.getImprovementCoeff()) + "$? "
              + "Input ‘Yes’ if you agree or ‘No’ otherwise")) {
        improveShop(shop, x, y);
      }
    } else {
      payingCompensation(shop, x, y);
    }
  }

  /**
   * method for paying compensation in case of locating in bot's shop
   *
   * @param shop intended shop
   * @param x    shop x position
   * @param y    shop y position
   */
  private void payingCompensation(Shop shop, int x, int y) {
    Player.setBalance(Math.round(Player.getBalance() - shop.getK()));
    Bot.setBalance(Math.round(Bot.getBalance() + shop.getK()));
    System.out.println(
        "You have already paid compensation: " + Math.round(shop.getK()) + "$ to the bot in shop <"
            + x + "><"
            + y + ">");

  }

  /**
   * method for improving the shop in case of locating in player's shop
   *
   * @param shop intended shop
   * @param x    x shop position
   * @param y    y shop position
   */
  private void improveShop(Shop shop, int x, int y) {
    if (Player.getBalance() - shop.getN() >= 0) {
      Player.setBalance(Math.round(Player.getBalance() - shop.getN() * shop.getImprovementCoeff()));
      developmentExpense += shop.getN() * shop.getImprovementCoeff();
      shop.setN(Math.round(shop.getN() + shop.getN() * shop.getImprovementCoeff()));
      shop.setK(shop.getK() + shop.getK() * shop.getCompensationCoeff());
      System.out.println("The shop <" + x + "><" + y + "> has upgraded by you!");
    } else {
      System.out.println("You wanted to upgrade the shop <" + x + "><" + y
          + ">, but you can't because your money is not enough!");
    }
  }

  /**
   * method for asking and getting answers from the player
   *
   * @param question question text
   * @return agree or not
   */
  private boolean askIf(String question) {
    Scanner sc = new Scanner(System.in);
    System.out.println(question);
    String answer = sc.next();
    while (!answer.equals("No") && !answer.equals("Yes")) {
      System.out.println("Please only answer by Yes or NO!");
      answer = sc.next();
    }
    return answer.equals("Yes");
  }

  /**
   * method for buying a shop by player in the absence of an owner for the shop
   *
   * @param shop intended shop
   * @param x    x shop position
   * @param y    y shop position
   */
  private void buyShop(Shop shop, int x, int y) {
    if (Player.getBalance() - shop.getN() >= 0) {
      Player.setBalance(Player.getBalance() - shop.getN());
      developmentExpense += shop.getN();
      System.out.println("You are already the owner of the shop <" + x + "><" + y + ">");
      shop.setOwner(1);
    } else {
      System.out.println("You wanted to buy the shop <" + x + "><" + y
          + ">, but you can't because your money is not enough!");
    }

  }

  /**
   * to string method for getting information about the  player
   *
   * @return string contain all information about the player(amountOfDebt, developmentExpense,
   * balance)
   */
  @Override
  public String toString() {
    return "Player{\n" +
        "amountOfDebt=" + amountOfDebt +
        "$,\ndevelopmentExpense=" + developmentExpense +
        "$,\nbalance= " + Player.getBalance() +
        "$\n}";
  }
}

