package classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ConservativeMartian<T> extends Martian<T> {

  /**
   * code that every martian should has
   */
  private final T code;
  /**
   * each conservative martian can have parent or not, so if martian has parent, so the value of
   * that parent should be saved here
   */
  private final ConservativeMartian<T> parent;
  /**
   * collection of children of conservative martian martian, and it can be empty
   */
  private final Collection<ConservativeMartian<T>> children;

  /**
   * private method also help public constructor in making a copy of some tree, using depth-first
   * search algorithm
   *
   * @param parent
   * @param child
   */
  private void MakeCopyAndConnectionUsingDFSAlg(ConservativeMartian<T> parent,
      InnovatorMartian<T> child) {
    ConservativeMartian<T> tempch = new ConservativeMartian<>(child.GetCode(), parent);
    parent.children.add(tempch);
    for (Martian<T> ch : child.GetChildren()) {
      MakeCopyAndConnectionUsingDFSAlg(tempch, (InnovatorMartian<T>) ch);
    }
  }

  /**
   * private constructor help public constructor in copy process
   *
   * @param code   code value of martian
   * @param parent parent of martian
   */
  private ConservativeMartian(T code, ConservativeMartian<T> parent) {
    this.code = code;
    this.parent = parent;
    this.children = new ArrayList<>();
  }

  /**
   * constructor where will be made a martian conservative copy, for tree in which given
   * martianInnovator located
   *
   * @param innovatorMartian given martian innovator
   */
  public ConservativeMartian(InnovatorMartian<T> innovatorMartian) {
    //arriving to the main parent of family tree
    Martian<T> mainParent = innovatorMartian;
    while (mainParent.GetParent() != null) {
      mainParent = mainParent.GetParent();
    }
    this.code = mainParent.GetCode();
    this.parent = null;
    this.children = new ArrayList<>();
    for (Martian<T> child : mainParent.GetChildren()) {
      MakeCopyAndConnectionUsingDFSAlg(this, (InnovatorMartian<T>) child);
    }
  }

  /**
   * public override method to get code value of martian
   *
   * @return code value of martian
   */
  @Override
  public T GetCode() {
    return code;
  }

  /**
   * Public override method to get the collection of descendants of martian
   *
   * @return collection of descendants of martian
   */
  @Override
  public List<Martian<T>> GetDescendants() {
    if (CheckChildren()) {
      return new ArrayList<>();
    }
    List<Martian<T>> descendants = new ArrayList<>();
    List<Martian<T>> roots = new ArrayList<>();
    roots.add(this);
    for (int i = 0; i < roots.size(); i++) {
      for (Martian<T> child : roots.get(i).GetChildren()) {
        descendants.add(child);
        if (child.GetChildren() != null && !child.GetChildren().isEmpty()) {
          roots.add(child);
        }
      }
    }
    return descendants;
  }

  /**
   * private method to check if a children collection is empty or equal to null
   *
   * @return true, if children collection is empty or equal to null/ false otherwise
   */
  private boolean CheckChildren() {
    return children.equals(null) || children.isEmpty();
  }

  /**
   * Public override method to get the collection of children of martian
   *
   * @return collection of children of martian
   */
  @Override
  public List<Martian<T>> GetChildren() {
    if (CheckChildren()) {
      return new ArrayList<>();
    }
    return new ArrayList<>(children);
  }

  /**
   * public override method to get the parent of martian
   *
   * @return martian's parent
   */
  @Override
  public Martian<T> GetParent() {
    return parent;
  }

  /**
   * public override method for checking if martian has child with code value equal to given value
   *
   * @param value given code value which we use in checking process
   * @return true, if martian has child with code value equal to given value / false,otherwise
   */
  @Override
  public boolean hasChildWithValue(T value) {
    return GetChildren().stream().anyMatch(tMartian -> tMartian.GetCode().equals(value));
    //If you want to find a child among all the descendants
    //return GetDescendants().stream().anyMatch(tMartian -> tMartian.code.equals(value));
  }

  /**
   * public override method for checking if martian has descendant with code value equal to given
   * value
   *
   * @param value given code value which we use in checking process
   * @return true, if martian has descendant with code value equal to given value / false,otherwise
   */
  @Override
  public boolean hasDescendantWithValue(T value) {
    return GetDescendants().stream().anyMatch(tMartian -> tMartian.GetCode().equals(value));
  }
}