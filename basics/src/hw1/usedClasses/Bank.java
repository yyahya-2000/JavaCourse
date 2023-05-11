/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

import java.util.*;

/**
 * Bank class represent a Bank and its offices
 */
public class Bank extends Monopoly {

  //the amount of credit that client want to get
  int creditAmount;

  /**
   * method for receiving the player at the bank office and direct him to the appropriate place
   * for his request
   *
   * @param player the player
   */
  public void receiveClient(Player player) {

    if (player.getAmountOfDebt() == 0) {
      if (wantsCredit()) {
        makeCredit((Player) player);
      }
    } else {
      //backing the credit
      Player.setBalance(Math.round(Player.getBalance() - player.getAmountOfDebt()));
      player.setAmountOfDebt(0);
    }
  }

  /**
   * method for asking the player if he wants credit
   *
   * @return wants credit of not
   */
  private boolean wantsCredit() {
    Scanner sc = new Scanner(System.in);
    System.out.println(
        "You are in the bank office. Would you like to get a credit? Input how many you want to get or ’No’");
    String answer = sc.next();
    while (!answer.equals("No") && !tryParseInt(answer)) {
      System.out.println(
          "Please only input the amount of credit that you want to get in Integer, if you want to get credit, or ’No’ if you don't want");
      answer = sc.next();
    }
    if (tryParseInt(answer)) {
      creditAmount = Integer.parseInt(answer);
    }
    return tryParseInt(answer);
  }

  /**
   * method will be worked in case of agreeing player in "wantsCredit" method to take credit
   *
   * @param player the player who's want a credit
   */
  private void makeCredit(Player player) {
    if (!checkCreditAmount(player.getDevelopmentExpense())) {
      return;
    }
    final int balance = Player.getBalance();
    Player.setBalance(balance + creditAmount);
    player.setAmountOfDebt(Math.round(creditAmount * InitializationRandomFields.debtCoeff));
  }

  /**
   * method for Checking the allowable credit amount for the player
   *
   * @param developmentExpense the sum of money that player spent it on buying and developing the
   *                           shops
   * @return
   */
  private boolean checkCreditAmount(float developmentExpense) {
    final float maxValueOfCredit = developmentExpense * InitializationRandomFields.creditCoeff;
    Scanner sc = new Scanner(System.in);
    String creditAmountString = String.valueOf(creditAmount);
    while (!creditAmountString.equals("No") && (!tryParseInt(creditAmountString)
        || Integer.parseInt(creditAmountString) <= 0 ||
        Integer.parseInt(creditAmountString) > maxValueOfCredit)) {
      System.out.println(
          "Sorry, the maximum credit value that you can obtain is: " + maxValueOfCredit
              + "$, please take this into consideration, try again or Input 'No' to retreat");
      creditAmountString = sc.next();
    }
    if (creditAmountString.equals("No")) {
      return false;
    } else {
      creditAmount = Integer.parseInt(creditAmountString);
      return true;
    }
  }

  /**
   * method to check if an input parameter can convert it to an integer type
   *
   * @param value an input parameter
   * @return if an input number can be converted to integer type
   */
  boolean tryParseInt(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * toString method
   *
   * @return symbol of bank ($)
   */
  @Override
  public String myOutputToString() {
    return "$";
  }

  ;
}


