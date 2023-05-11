/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

import java.util.Random;

/**
 * Shop class represent all shops at playing field
 */
public class Shop extends Monopoly {

  /**
   * simple random instance
   */
  static final Random random = new Random();

  /**
   * shop improvement cost increase factor determines randomly [0.1, 2)
   */
  private final float improvementCoeff = (random.nextInt(19) + 1 + random.nextFloat()) / 10;
  /**
   * shop price determines randomly from [50, 500]
   */
  private int n = random.nextInt(451) + 50;
  /**
   * entry tax and size shop determines randomly from [0.5*n, 0.9*n]
   */
  private float k = ((random.nextInt(4) + 5 + random.nextFloat()) / 10) * n;
  /**
   * compensation increase factor determines randomly [0.1, 1)
   */
  private final float compensationCoeff = (random.nextInt(9) + 1 + random.nextFloat()) / 10;

  /**
   * setter of n field
   *
   * @return price shop
   */
  public int getN() {
    return n;
  }

  /**
   * getter of k field
   *
   * @return entry tax of this shop or shop size
   */
  public float getK() {
    return k;
  }

  /**
   * setter of n filed
   *
   * @param n new price shop
   */
  public void setN(int n) {
    this.n = n;
  }

  /**
   * setter of k field
   *
   * @param k new entry tax and shop size
   */
  public void setK(float k) {
    this.k = k;
  }

  public float getCompensationCoeff() {
    return compensationCoeff;
  }

  public float getImprovementCoeff() {
    return improvementCoeff;
  }

  public int getOwner() {
    return owner;
  }

  public void setOwner(int owner) {
    this.owner = owner;
  }

  /**
   * 0 if there is no owner
   * 1 if the player is the owner
   * -1 if the bot is the owner
   */
  private int owner = 0;

  /**
   * toString method
   *
   * @return shop status symbol (S- no owner, M- the owner is the player, O- the owner is the bot)
   */
  @Override
  public String myOutputToString() {
    if (owner == 0) {
      return "S";
    } else if (owner == 1) {
      return "M";
    } else {
      return "O";
    }
  }

}
