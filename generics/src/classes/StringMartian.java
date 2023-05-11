package classes;

/**
 * class StringMartian helps convert family tree from report to martian root
 */
class StringMartian {

  /**
   * the number of spaces at the first of this line in report
   */
  private int spacesNum;
  /**
   * the martian object of this line
   */
  private Martian martian;

  /**
   * getter to spacesNum variable
   *
   * @return the num of spaces specific line
   */
  int getSpacesNum() {
    return spacesNum;
  }

  /**
   * getter to martian variable
   *
   * @return the martian who's created from specific line
   */
  Martian getMartian() {
    return martian;
  }

  /**
   * public constructor, which get a line from report and turn it into martian object, and num of
   * spaces at the first of the line
   *
   * @param martianString line from the report
   */
  StringMartian(String martianString) {
    this.spacesNum = martianString.length() - LTrim(martianString).length();
    this.martian = GenerateMartian(martianString);
  }

  /**
   * private method where the line of report will be converted into martian object
   *
   * @param reportLine line from the report
   * @return a martian object was created from string line from the report
   */
  private Martian GenerateMartian(String reportLine) {
    String codeType = reportLine.substring(reportLine.indexOf("(") + 1, reportLine.indexOf(":"));
    String codeValue = reportLine.substring(reportLine.indexOf(":") + 1, reportLine.indexOf(")"));
    switch (codeType) {
      case "String":
        return new InnovatorMartian<String>(codeValue);
      case "Integer":
        return new InnovatorMartian<Integer>(Integer.parseInt(codeValue));
      case "Double":
        return new InnovatorMartian<Double>(Double.parseDouble(codeValue));
    }
    throw new IllegalArgumentException(
        "Illegal report, please make sure, that you passed correct report!");
  }

  /**
   * private method helps trim string only from left side
   *
   * @param string can be any string
   * @return the same input with removing all spaces at the first of it
   */
  private String LTrim(String string) {
    int i = 0;
    while (i < string.length() && Character.isWhitespace(string.charAt(i))) {
      i++;
    }
    return string.substring(i, string.length());
  }
}
