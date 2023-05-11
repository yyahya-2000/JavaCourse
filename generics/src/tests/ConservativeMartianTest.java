package tests;

import static org.junit.jupiter.api.Assertions.*;

import classes.ConservativeMartian;
import classes.FamilyTree;
import classes.InnovatorMartian;
import classes.Martian;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConservativeMartianTest {

  static String report;
  static ConservativeMartian<Double> parent;
  static String children;

  @BeforeAll
  static void Initialization() {
    Thread th = new Thread();
    report = "ConservativeMartian (Double:0.0)\n" +
        "    ConservativeMartian (Double:1.0)\n" +
        "        ConservativeMartian (Double:1.1)\n" +
        "            ConservativeMartian (Double:1.11)\n" +
        "                ConservativeMartian (Double:1.111)\n" +
        "                    ConservativeMartian (Double:1.1111)\n" +
        "                        ConservativeMartian (Double:1.11111)\n" +
        "                        ConservativeMartian (Double:1.111111)\n" +
        "                ConservativeMartian (Double:11.0)\n" +
        "    ConservativeMartian (Double:2.0)\n" +
        "        ConservativeMartian (Double:2.1)\n" +
        "            ConservativeMartian (Double:2.11)\n" +
        "            ConservativeMartian (Double:2.111)\n" +
        "                ConservativeMartian (Double:2.222)\n" +
        "    ConservativeMartian (Double:3.3)\n" +
        "    ConservativeMartian (Double:4.4)\n" +
        "        ConservativeMartian (Double:4.1)\n" +
        "        ConservativeMartian (Double:4.1)\n" +
        "        ConservativeMartian (Double:4.2)\n" +
        "    ConservativeMartian (Double:5.12)\n" +
        "        ConservativeMartian (Double:5.1)\n" +
        "        ConservativeMartian (Double:5.7)\n" +
        "        ConservativeMartian (Double:5.9)\n" +
        "    ConservativeMartian (Double:6.3)\n" +
        "        ConservativeMartian (Double:6.6)\n" +
        "            ConservativeMartian (Double:6.61)\n" +
        "                ConservativeMartian (Double:6.111)\n" +
        "                    ConservativeMartian (Double:6.6666)\n" +
        "                        ConservativeMartian (Double:6.2941)\n" +
        "                        ConservativeMartian (Double:6.09)\n" +
        "                            ConservativeMartian (Double:6.234)\n" +
        "                                ConservativeMartian (Double:6.1234)\n" +
        "                                    ConservativeMartian (Double:6.11134)\n" +
        "                                        ConservativeMartian (Double:6.9)\n" +
        "                                            ConservativeMartian (Double:6.91)\n" +
        "                                            ConservativeMartian (Double:6.92)\n";
    children =
        "ConservativeMartian (Double:1.0)\n" +
            "    ConservativeMartian (Double:1.1)\n" +
            "        ConservativeMartian (Double:1.11)\n" +
            "            ConservativeMartian (Double:1.111)\n" +
            "                ConservativeMartian (Double:1.1111)\n" +
            "                    ConservativeMartian (Double:1.11111)\n" +
            "                    ConservativeMartian (Double:1.111111)\n" +
            "            ConservativeMartian (Double:11.0)\n" +
            "ConservativeMartian (Double:2.0)\n" +
            "    ConservativeMartian (Double:2.1)\n" +
            "        ConservativeMartian (Double:2.11)\n" +
            "        ConservativeMartian (Double:2.111)\n" +
            "            ConservativeMartian (Double:2.222)\n" +
            "ConservativeMartian (Double:3.3)\n" +
            "ConservativeMartian (Double:4.4)\n" +
            "    ConservativeMartian (Double:4.1)\n" +
            "    ConservativeMartian (Double:4.1)\n" +
            "    ConservativeMartian (Double:4.2)\n" +
            "ConservativeMartian (Double:5.12)\n" +
            "    ConservativeMartian (Double:5.1)\n" +
            "    ConservativeMartian (Double:5.7)\n" +
            "    ConservativeMartian (Double:5.9)\n" +
            "ConservativeMartian (Double:6.3)\n" +
            "    ConservativeMartian (Double:6.6)\n" +
            "        ConservativeMartian (Double:6.61)\n" +
            "            ConservativeMartian (Double:6.111)\n" +
            "                ConservativeMartian (Double:6.6666)\n" +
            "                    ConservativeMartian (Double:6.2941)\n" +
            "                    ConservativeMartian (Double:6.09)\n" +
            "                        ConservativeMartian (Double:6.234)\n" +
            "                            ConservativeMartian (Double:6.1234)\n" +
            "                                ConservativeMartian (Double:6.11134)\n" +
            "                                    ConservativeMartian (Double:6.9)\n" +
            "                                        ConservativeMartian (Double:6.91)\n" +
            "                                        ConservativeMartian (Double:6.92)\n";
    FamilyTree family = new FamilyTree(report);
    family.TranslateReportIntoTree();
    parent = (ConservativeMartian<Double>) family.getRoot();
  }

  @Test
  void getCode() {
    assertEquals(parent.GetCode(), 0.0);
  }

  @Test
  void getDescendants() {
    for (Martian<Double> child : parent.GetDescendants()) {
      assertTrue(report.contains("ConservativeMartian (Double:" + child.GetCode()));
    }
  }

  @Test
  void getChildren() {
    StringBuilder result = new StringBuilder();
    for (Martian<Double> child : parent.GetChildren()) {
      FamilyTree familyTree1 = new FamilyTree(child);
      familyTree1.ProvideTreeAsTextReport();
      result.append(familyTree1.getReport());
    }
    assertEquals(result.toString(), children);
  }

  @Test
  void getParent() {
    assertNull(parent.GetParent());
  }

  @Test
  void hasChildWithValue() {
    assertTrue(parent.hasChildWithValue(1.0));
    assertFalse(parent.hasChildWithValue(1.5));
  }

  @Test
  void hasDescendantWithValue() {
    assertTrue(parent.hasDescendantWithValue(3.3));
    assertFalse(parent.hasDescendantWithValue(300.8));
  }
}