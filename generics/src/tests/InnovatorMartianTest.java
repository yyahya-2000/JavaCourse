package tests;

import static org.junit.jupiter.api.Assertions.*;

import classes.FamilyTree;
import classes.InnovatorMartian;
import classes.Martian;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InnovatorMartianTest {

  private static String report;
  private static InnovatorMartian<String> parent;
  private static String children;

  @BeforeAll
  static void initialization() {
    report = "InnovatorMartian (String:Egor)\n" +
        "    InnovatorMartian (String:Ivan Ivanovich)\n" +
        "        InnovatorMartian (String:Tariq)\n" +
        "            InnovatorMartian (String:Amir)\n" +
        "                InnovatorMartian (String:Amal)\n" +
        "                    InnovatorMartian (String:Kamil)\n" +
        "                        InnovatorMartian (String:Aram)\n" +
        "                        InnovatorMartian (String:Alexander)\n" +
        "                InnovatorMartian (String:Yazan)\n" +
        "    InnovatorMartian (String:Alex)\n" +
        "        InnovatorMartian (String:Tala)\n" +
        "            InnovatorMartian (String:Timur)\n" +
        "            InnovatorMartian (String:Yaman)\n" +
        "                InnovatorMartian (String:Soso)\n" +
        "    InnovatorMartian (String:Ali)\n" +
        "    InnovatorMartian (String:Max)\n" +
        "        InnovatorMartian (String:Adam)\n" +
        "        InnovatorMartian (String:Sari)\n" +
        "        InnovatorMartian (String:Aza)\n" +
        "    InnovatorMartian (String:Aram)\n" +
        "        InnovatorMartian (String:Anna)\n" +
        "        InnovatorMartian (String:Dasha)\n" +
        "        InnovatorMartian (String:Sham)\n" +
        "    InnovatorMartian (String:Sam)\n" +
        "        InnovatorMartian (String:Somar)\n" +
        "            InnovatorMartian (String:Sami)\n" +
        "                InnovatorMartian (String:Salim)\n" +
        "                    InnovatorMartian (String:Salum)\n" +
        "                        InnovatorMartian (String:Slam)\n" +
        "                        InnovatorMartian (String:Sarab)\n" +
        "                            InnovatorMartian (String:Roni)\n" +
        "                                InnovatorMartian (String:Vlad)\n" +
        "                                    InnovatorMartian (String:Vladimer)\n" +
        "                                        InnovatorMartian (String:Gdan)\n" +
        "                                            InnovatorMartian (String:Gamal)\n" +
        "                                            InnovatorMartian (String:Galil)\n";
    children =
        "InnovatorMartian (String:Ivan Ivanovich)\n" +
            "    InnovatorMartian (String:Tariq)\n" +
            "        InnovatorMartian (String:Amir)\n" +
            "            InnovatorMartian (String:Amal)\n" +
            "                InnovatorMartian (String:Kamil)\n" +
            "                    InnovatorMartian (String:Aram)\n" +
            "                    InnovatorMartian (String:Alexander)\n" +
            "            InnovatorMartian (String:Yazan)\n" +
            "InnovatorMartian (String:Alex)\n" +
            "    InnovatorMartian (String:Tala)\n" +
            "        InnovatorMartian (String:Timur)\n" +
            "        InnovatorMartian (String:Yaman)\n" +
            "            InnovatorMartian (String:Soso)\n" +
            "InnovatorMartian (String:Ali)\n" +
            "InnovatorMartian (String:Max)\n" +
            "    InnovatorMartian (String:Adam)\n" +
            "    InnovatorMartian (String:Sari)\n" +
            "    InnovatorMartian (String:Aza)\n" +
            "InnovatorMartian (String:Aram)\n" +
            "    InnovatorMartian (String:Anna)\n" +
            "    InnovatorMartian (String:Dasha)\n" +
            "    InnovatorMartian (String:Sham)\n" +
            "InnovatorMartian (String:Sam)\n" +
            "    InnovatorMartian (String:Somar)\n" +
            "        InnovatorMartian (String:Sami)\n" +
            "            InnovatorMartian (String:Salim)\n" +
            "                InnovatorMartian (String:Salum)\n" +
            "                    InnovatorMartian (String:Slam)\n" +
            "                    InnovatorMartian (String:Sarab)\n" +
            "                        InnovatorMartian (String:Roni)\n" +
            "                            InnovatorMartian (String:Vlad)\n" +
            "                                InnovatorMartian (String:Vladimer)\n" +
            "                                    InnovatorMartian (String:Gdan)\n" +
            "                                        InnovatorMartian (String:Gamal)\n" +
            "                                        InnovatorMartian (String:Galil)\n";
    FamilyTree family = new FamilyTree(report);
    family.TranslateReportIntoTree();
    parent = (InnovatorMartian<String>) family.getRoot();
  }

  @Test
  void getChildren() {
    StringBuilder result = new StringBuilder();
    for (Martian<String> child : parent.GetChildren()) {
      FamilyTree familyTree1 = new FamilyTree(child);
      familyTree1.ProvideTreeAsTextReport();
      result.append(familyTree1.getReport());
    }
    assertEquals(result.toString(), children);
  }

  @Test
  void getCode() {
    assertEquals(parent.GetCode(), "Egor");
  }

  @Test
  void getDescendants() {
    for (Martian<String> child : parent.GetDescendants()) {
      assertTrue(report.contains("InnovatorMartian (String:" + child.GetCode()));
    }
  }

  @Test
  void hasChildWithValue() {
    assertTrue(parent.hasChildWithValue("Alex"));
    assertFalse(parent.hasChildWithValue("Murad"));
  }

  @Test
  void hasDescendantWithValue() {
    assertTrue(parent.hasDescendantWithValue("Galil"));
    assertFalse(parent.hasDescendantWithValue("Neymar"));
  }

  @AfterAll
  static void setCode() {
    InnovatorMartian<String> temp = parent;
    parent.SetCode("Adam");
    assertEquals(temp.GetCode(), "Adam");
  }

  @AfterAll
  static void setParent() {
    InnovatorMartian<String> newParent = new InnovatorMartian<>("Boss");
    parent.SetParent(newParent);
    assertEquals(newParent, parent.GetParent());
  }

  @AfterAll
  static void setDescendant() {

    InnovatorMartian<String> temp = parent;
    Collection<Martian<String>> newChildren = initializeNewChildren();
    temp.SetDescendant(newChildren);
    assertEquals(temp.GetChildren(), newChildren);
  }

  private static Collection<Martian<String>> initializeNewChildren() {
    InnovatorMartian<String> child1 = new InnovatorMartian<>("A");
    child1.AddChild(new InnovatorMartian<>("Aa"));
    child1.AddChild(new InnovatorMartian<>("Ab"));
    child1.AddChild(new InnovatorMartian<>("Ac"));
    InnovatorMartian<String> child2 = new InnovatorMartian<>("B");
    child2.AddChild(new InnovatorMartian<>("Ba"));
    child2.AddChild(new InnovatorMartian<>("Bb"));
    child2.AddChild(new InnovatorMartian<>("Bc"));
    InnovatorMartian<String> child3 = new InnovatorMartian<>("C");
    InnovatorMartian<String> child4 = new InnovatorMartian<>("D");
    InnovatorMartian<String> child41 = new InnovatorMartian<>("Da");
    child41.AddChild(new InnovatorMartian<>("Daa"));
    child41.SetParent(child4);
    InnovatorMartian<String> child5 = new InnovatorMartian<>("E");
    InnovatorMartian<String> child6 = new InnovatorMartian<>("F");
    Collection<Martian<String>> newChildren = new ArrayList<>();
    newChildren.add(child1);
    newChildren.add(child2);
    newChildren.add(child3);
    newChildren.add(child4);
    newChildren.add(child5);
    newChildren.add(child6);
    return newChildren;
  }

  static InnovatorMartian<String> newChild = new InnovatorMartian<>("NewChild");

  @AfterAll
  static void addChild() {
    parent.AddChild(newChild);
    assertTrue(parent.GetChildren().contains(newChild));
  }

  @AfterAll
  static void removeChild() {
    parent.RemoveChild(newChild);
    assertFalse(parent.GetChildren().contains(newChild));
  }
}