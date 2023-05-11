package classes;

import java.util.Collection;

/**
 * author: Yanal Yahya, Software Engineering, 197 group.
 */

public abstract class Martian<T> {

  /**
   * public abstract method to get code value of martian
   *
   * @return code value of martian
   */
  public abstract T GetCode();

  /**
   * Public abstract method to get the collection of descendants of martian
   *
   * @return collection of descendants of martian
   */
  public abstract Collection<Martian<T>> GetDescendants();

  /**
   * Public abstract method to get the collection of children of martian
   *
   * @return collection of children of martian
   */
  public abstract Collection<Martian<T>> GetChildren();

  /**
   * public abstract method to get the parent of martian
   *
   * @return martian's parent
   */
  public abstract Martian<T> GetParent();

  /**
   * public abstract method for checking if martian has child with code value equal to given value
   *
   * @param value given code value which we use in checking process
   * @return true, if martian has child with code value equal to given value / false,otherwise
   */
  public abstract boolean hasChildWithValue(T value);

  /**
   * public abstract method for checking if martian has descendant with code value equal to given
   * value
   *
   * @param value given code value which we use in checking process
   * @return true, if martian has descendant with code value equal to given value / false,otherwise
   */
  public abstract boolean hasDescendantWithValue(T value);

}
