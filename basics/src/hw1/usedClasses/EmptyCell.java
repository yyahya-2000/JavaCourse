/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

/**
 * EmptyCell Class represent an empty cell at the game
 */
public class EmptyCell extends Monopoly {

  /**
   * toString method says that player can relax here
   *
   * @return "Just relax there"
   */
  @Override
  public String toString() {
    return "Just relax there";
  }

  /**
   * tosteing method
   *
   * @return symbol of EmptyCell (E)
   */
  @Override
  public String myOutputToString() {
    return "E";
  }

  ;
}
