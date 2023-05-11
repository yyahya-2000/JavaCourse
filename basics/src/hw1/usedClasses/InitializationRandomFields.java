/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * this class initialize and represent all static fields which don't change into the game
 */
public class InitializationRandomFields {

  /**
   * format printing float values
   */
  static final DecimalFormat numberFormat = new DecimalFormat("#0.000");
  static final Random random = new Random();
  /**
   * random value located in [0.002, 0.2)
   */
  static final float creditCoeff = (random.nextInt(198) + 2 + random.nextFloat()) / 1000;
  /**
   * random value located in [1.0, 3.0)
   */
  static final float debtCoeff = random.nextInt(2) + 1 + random.nextFloat();
  /**
   * random value located in [0.01, 0.1)
   */
  static final float penaltyCoeff = (random.nextInt(9) + 1 + random.nextFloat()) / 100;

  /**
   * toString Method
   *
   * @return string contain the value of the fields in this class
   */
  public static String firstToString() {
    return "creditCoeff: " + numberFormat.format(InitializationRandomFields.creditCoeff) +
        "\ndebtCoeff: " + numberFormat.format(InitializationRandomFields.debtCoeff) +
        "\npenaltyCoeff: " + numberFormat.format(InitializationRandomFields.penaltyCoeff);
  }
}
