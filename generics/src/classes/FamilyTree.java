package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represent tree of family
 */
public class FamilyTree {

  /**
   * here is the place where should be saved all families, in order to help special Commission to
   * get report for each family in every time they want. and here the new families resulting from
   * removing child, setting new parent and setting new Descendants operations,will be saved (not
   * always we will get these families)
   * <p>
   * the main purpose of this variable is for saving families resulting from operations, which was
   * mentioned above, but you can still use it for saving all roots of families(I mean you can use
   * your containers to save families roots , and when you do any danger operation you should check
   * this variable if you get new families, or you can use it as a container and there will be added
   * these new families automatically)
   */
  public static List<Martian> allFamilyTrees = new ArrayList<>();
  /**
   * it's enough to save only the root of each family
   */
  private Martian root;
  /**
   * where family report will be saved
   */
  private StringBuilder report;

  /**
   * getter to get the root of family
   *
   * @return root of the family
   */
  public Martian getRoot() {
    return root;
  }

  /**
   * getter to get the report of the damily
   *
   * @return the report of tree family
   */
  public String getReport() {
    return report.toString();
  }

  /**
   * constructor to represent family by martian object
   *
   * @param tree martian object
   */
  public FamilyTree(Martian tree) {
    root = tree;
    report = new StringBuilder();
  }

  /**
   * constructor to represent family by report
   *
   * @param report family report
   */
  public FamilyTree(String report) {
    this.report = new StringBuilder(report);
  }

  //just as a helper of MovingIntoAllTreeElementsUsingDFSAlg method
  private StringBuilder spaces = new StringBuilder();

  /**
   * DFS alg to move into the elements of tree, in order to create report
   *
   * @param root tree root
   */
  private void MovingIntoAllTreeElementsUsingDFSAlg(Martian root) {
    report.append(spaces.toString() + RepresentMartianIntoString((Martian) root) + "\n");
    for (Object ch : root.GetChildren()) {
      spaces.append("    ");
      MovingIntoAllTreeElementsUsingDFSAlg((Martian) ch);
      spaces.delete(0, 4);
    }
  }

  /**
   * Public method to represent a family tree in report(text) form
   */
  public void ProvideTreeAsTextReport() {
    report = new StringBuilder();
    MovingIntoAllTreeElementsUsingDFSAlg(root);
  }

  /**
   * method that helps in obtaining the required form of output for each family member
   *
   * @param martian some family member
   * @return required form of output
   */
  private String RepresentMartianIntoString(Martian martian) {
    StringBuilder stringBuilder = new StringBuilder();
    return stringBuilder
        .append(martian.getClass().getName().replace("classes.", "") + " (" + martian.GetCode()
            .getClass().getName().replace("java.lang.", "") + ":" + martian.GetCode() + ")")
        .toString();
  }

  /**
   * method to translate the report text into martians objects
   */
  public void TranslateReportIntoTree() {
    String[] reportLines = report.toString().split("\n");
    StringMartian first = new StringMartian(reportLines[0]);
    StringMartian second;
    StringMartian root = first;
    List<StringMartian> stringMartians = new ArrayList<>();
    stringMartians.add(root);
    for (int i = 1; i < reportLines.length; i++) {
      second = new StringMartian(reportLines[i]);
      if (first.getSpacesNum() > second.getSpacesNum() && root.getSpacesNum() >= second
          .getSpacesNum()) {
        StringMartian finalSecond = second;
        stringMartians.removeIf(ro -> ro.getSpacesNum() >= finalSecond.getSpacesNum());
        root = stringMartians.get(stringMartians.size() - 1);
      } else if (first.getSpacesNum() < second.getSpacesNum()) {
        root = first;
        stringMartians.add(root);
      }
      MakeConnection(root.getMartian(), second.getMartian());
      first = second;
    }
    this.root = GenerateFinalRoot(stringMartians.get(0).getMartian(), reportLines[0]);
  }

  /**
   * this method help to know if in report conservative or innovator, based on that, the final
   * result will be returned
   *
   * @param martian    the root of family
   * @param reportLine just it help to know the nature of the report
   * @return conservative or innovator family
   */
  private Martian GenerateFinalRoot(Martian martian, String reportLine) {
    String temp = reportLine.trim().substring(0, reportLine.indexOf(" "));
    if (!temp.equals("InnovatorMartian")) {
      switch (reportLine.substring(reportLine.indexOf("(") + 1, reportLine.indexOf(":"))) {
        case "Integer":
          return new ConservativeMartian<Integer>((InnovatorMartian<Integer>) martian);
        case "Double":
          return new ConservativeMartian<Double>((InnovatorMartian<Double>) martian);
        case "String":
          return new ConservativeMartian<String>((InnovatorMartian<String>) martian);
      }
    }
    return martian;
  }

  /**
   * private method to make connection between parent and child
   *
   * @param parent the parent
   * @param child  the child
   */
  private void MakeConnection(Martian parent, Martian child) {
    if (!((InnovatorMartian) parent).AddChild(child)) {
      throw new IllegalArgumentException("Invalid family tree");
    }
  }
}
