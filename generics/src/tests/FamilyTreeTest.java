package tests;

import classes.FamilyTree;
import classes.InnovatorMartian;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FamilyTreeTest {

  private static FamilyTree family1;
  private static FamilyTree family2;
  private static String report;

  @BeforeAll
  static void initialization() {
    report = "InnovatorMartian (Integer:0)\n" +
        "    InnovatorMartian (Integer:4)\n" +
        "        InnovatorMartian (Integer:41)\n" +
        "            InnovatorMartian (Integer:411)\n" +
        "    InnovatorMartian (Integer:5)\n" +
        "    InnovatorMartian (Integer:6)\n" +
        "    InnovatorMartian (Integer:1)\n" +
        "        InnovatorMartian (Integer:11)\n" +
        "        InnovatorMartian (Integer:12)\n" +
        "        InnovatorMartian (Integer:13)\n" +
        "    InnovatorMartian (Integer:2)\n" +
        "        InnovatorMartian (Integer:21)\n" +
        "        InnovatorMartian (Integer:22)\n" +
        "        InnovatorMartian (Integer:23)\n" +
        "    InnovatorMartian (Integer:3)\n";

  }

  @Test
  void provideTreeAsTextReport() {
    InnovatorMartian<Integer> parent = InitializeFamily();
    FamilyTree.allFamilyTrees.add(parent);
    family1 = new FamilyTree(parent);
    family1.ProvideTreeAsTextReport();
    assertEquals( report,family1.getReport());
  }

  private InnovatorMartian<Integer> InitializeFamily() {
    InnovatorMartian<Integer> parent = new InnovatorMartian<>(0);
    InnovatorMartian<Integer> child1 = new InnovatorMartian<>(1);
    child1.AddChild(new InnovatorMartian<>(11));
    child1.AddChild(new InnovatorMartian<>(12));
    child1.AddChild(new InnovatorMartian<>(13));
    InnovatorMartian<Integer> child2 = new InnovatorMartian<>(2);
    child2.AddChild(new InnovatorMartian<>(21));
    child2.AddChild(new InnovatorMartian<>(22));
    child2.AddChild(new InnovatorMartian<>(23));
    InnovatorMartian<Integer> child3 = new InnovatorMartian<>(3);
    InnovatorMartian<Integer> child4 = new InnovatorMartian<>(4);
    InnovatorMartian<Integer> child41 = new InnovatorMartian<>(41);
    child41.AddChild(new InnovatorMartian<>(411));
    child41.SetParent(child4);
    child4.SetParent(parent);
    parent.AddChild(new InnovatorMartian<>(5));
    parent.AddChild(new InnovatorMartian<>(6));
    child1.SetParent(parent);
    child2.SetParent(parent);
    child3.SetParent(parent);
    return parent;
  }

  @Test
  void translateReportIntoTree() {
    family2 = new FamilyTree(report);
    family2.TranslateReportIntoTree();
    FamilyTree familyTree2 = new FamilyTree(family2.getRoot());
    familyTree2.ProvideTreeAsTextReport();
    assertEquals(report, familyTree2.getReport());
  }
}