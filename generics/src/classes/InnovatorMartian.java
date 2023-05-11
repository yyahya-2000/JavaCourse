package classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InnovatorMartian<T> extends Martian<T> {

  public InnovatorMartian(T code) {
    this.code = code;
  }

  /**
   * code that every martian should has
   */
  private T code;
  /**
   * each martian can have parent or not, so if martian has parent the value of that parent should
   * be saved here
   */
  private Martian<T> parent;
  /**
   * collection of children of cur martian, and it can be empty
   */
  private List<Martian<T>> children = new ArrayList<>();

  /**
   * Public override method to get the collection of children of current martian
   *
   * @return collection of children of current martian
   */
  @Override
  public List<Martian<T>> GetChildren() {
    return children;
  }

  /**
   * public override method to get code value of cur martian
   *
   * @return code value of cur martian
   */
  @Override
  public T GetCode() {
    return code;
  }

  /**
   * Public override method to get the collection of descendants of current martian
   *
   * @return collection of descendants of current martian
   */
  @Override
  public Collection<Martian<T>> GetDescendants() {
    if (children.equals(null)) {
      return new ArrayList<>();
    }
    List<Martian<T>> descendants = new ArrayList<>();
    List<Martian<T>> roots = new ArrayList<>();
    roots.add(this);
    for (int i = 0; i < roots.size(); i++) {
      for (Martian<T> child : roots.get(i).GetChildren()) {
        descendants.add(child);
        if (!child.GetChildren().isEmpty()) {
          roots.add(child);
        }
      }
    }
    return descendants;
  }

  /**
   * public override method to get the parent of current martian
   *
   * @return current martian's parent
   */
  @Override
  public Martian<T> GetParent() {
    return parent;
  }

  /**
   * public override method for checking if current martian has child with code value equal to given
   * value
   *
   * @param value given code value which we use in checking process
   * @return true, if martian has child with code value equal to given value / false,otherwise
   */
  @Override
  public boolean hasChildWithValue(T value) {

    return children.stream().anyMatch(tMartian -> ((InnovatorMartian<T>) tMartian).code.equals(value));
    //If you want to find a child among all the descendants
    //return GetDescendants().stream().anyMatch(tMartian -> tMartian.code.equals(value));
  }

  /**
   * public override method for checking if current martian has descendant with code value equal to
   * given value
   *
   * @param value given code value which we use in checking process
   * @return true, if martian has descendant with code value equal to given value / false,otherwise
   */
  @Override
  public boolean hasDescendantWithValue(T value) {
    return GetDescendants().stream()
        .anyMatch(tMartian -> ((InnovatorMartian<T>) tMartian).code.equals(value));
  }

  /**
   * public method to set or change code of martian
   *
   * @param code code value which we need to set
   */
  public void SetCode(T code) {
    this.code = code;
  }

  /**
   * public method to set new parent to the current martian
   *
   * @param newParent the new parent
   * @return if setting new parent operation was done successfully
   */
  public boolean SetParent(Martian<T> newParent) {
    if (newParent.getClass() != this.getClass() ||
        newParent.GetCode().getClass() != this.code.getClass() ||
        GetDescendants().contains(newParent)) {
      return false;
    }
    if (parent != null) {
      ((InnovatorMartian<T>) this.parent).children.remove(this);
      /*
       * if we are setting a new parent from another tree,
       * so in order to don't lose current tree,
       * we add the root of current tree to the static variable, where all trees are saved*/
      if (!GetFamilyMembers().contains(newParent)) {
        FamilyTree.allFamilyTrees.add(GetFamilyTreeRoot(this));
      }
    }
    this.parent = newParent;
    return ((InnovatorMartian<T>) newParent).children.add(this);
  }

  /**
   * private method to get the root of family tree
   *
   * @param innovatorMartian can be any member of intended tree
   * @return the root of family tree
   */
  private Martian<T> GetFamilyTreeRoot(InnovatorMartian<T> innovatorMartian) {
    Martian<T> familyTreeRoot = innovatorMartian;
    while (familyTreeRoot.GetParent() != null) {
      familyTreeRoot = familyTreeRoot.GetParent();
    }
    return familyTreeRoot;
  }

  /**
   * public method to add new collection of children to this martian
   *
   * @param descendants the new collection of children
   * @return if the adding process was successfully
   */
  public boolean SetDescendant(Collection<Martian<T>> descendants) {
    // if any child is one of the fathers of this martian
    for (Martian<T> child : descendants) {
      if (GetFathers().contains(child) ||
          child.GetCode().getClass() != this.code.getClass()) {
        return false;
      }
    }
    for (Martian<T> child : children) {
      ((InnovatorMartian<T>) child).parent = null;
    }
    FamilyTree.allFamilyTrees.addAll(children);
    children.clear();
    for (Martian<T> newChild : descendants) {
      ((InnovatorMartian<T>) newChild).SetParent(this);

    }
    children = new ArrayList<>(descendants);
    return true;
  }

  /**
   * private method was used only inside this class for getting the all tree family members
   *
   * @return collection of all members in family tree
   */
  private Collection<Martian<T>> GetFamilyMembers() {
    Collection<Martian<T>> familyMembers = new ArrayList<>();
    Martian<T> mainParent = GetFamilyTreeRoot(this);
    familyMembers.add(mainParent);
    familyMembers.addAll(mainParent.GetDescendants());
    return familyMembers;
  }


  /**
   * adding child to this parent into his children collection
   *
   * @param child new child who we need to add
   * @return if the child was added successfully or not
   */
  public boolean AddChild(Martian<T> child) {
    if (child.getClass() != this.getClass() || child.GetCode().getClass() != this.code.getClass() ||
        GetFathers().contains(child)) {
      return false;
    }
    if (child.GetParent() != null) {
      ((InnovatorMartian<T>) child.GetParent()).children.remove(child);
    }
    ((InnovatorMartian<T>) child).parent = this;
    children.add(child);
    return true;
  }

  /**
   * private method to get parent, parent parent, parent parent parent,.....
   *
   * @return collection of parents
   */
  private Collection<Martian<T>> GetFathers() {
    Collection<Martian<T>> fathers = new ArrayList<>();
    Martian<T> mainParent = this;
    while (mainParent.GetParent() != null) {
      mainParent = mainParent.GetParent();
      fathers.add(mainParent);
    }
    return fathers;
  }

  /**
   * remove specific child from the collection of children
   *
   * @param child a child who we want to remove
   * @return if the child was removed successfully or not
   */
  public boolean RemoveChild(Martian<T> child) {

    if (child.GetCode().getClass() != this.code.getClass() || !GetDescendants().contains(child)) {
      return false;
    }
    children.remove(child);
    ((InnovatorMartian<T>) child).parent = null;
    FamilyTree.allFamilyTrees.add(child);
    return true;
  }
}
