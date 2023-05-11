/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

/**
 * class represent player/bot position at the time of the game
 */
public class PlayerPosition {

  /**
   * getter of x field
   *
   * @return x player position
   */
  public int getX() {
    return x;
  }

  /**
   * setter of x field
   *
   * @param x new x player position
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * getter of y field
   *
   * @return y player position
   */
  public int getY() {
    return y;
  }

  /**
   * setter of y field
   *
   * @param y new y player position
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * x player position
   */
  private int x = 0;
  /**
   * y player position
   */
  private int y = 0;

  /**
   * toString method
   *
   * @return string contain the current player position
   */
  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
