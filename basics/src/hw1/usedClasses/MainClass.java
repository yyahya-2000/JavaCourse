/**
 * @author <a href="mailto:yyahya@edu.hse.ru"> Yanal Yahya</a>
 */
package hw1.usedClasses;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MainClass {

  static int length, width;
  static final PlayerPosition playerPosition = new PlayerPosition();
  static final PlayerPosition botPosition = new PlayerPosition();
  static boolean continueGame = true;

  /**
   * here is the base point of the program
   *
   * @param args the input arguments from command line
   */
  public static void main(String[] args) {
    Random random = new Random();
    while (true) {
      long sleepTime = random.nextInt(4000) + 1000;
      if (sleepTime <= 1000 || sleepTime >= 4999) {
        System.out.println(sleepTime);
      }
    }

    // try {
    //   while (continueGame) {
    //     if (checkInputCommand(args)) {
    //       Monopoly[][] gameArena = new Monopoly[Integer.parseInt(args[0])][Integer
    //           .parseInt(args[1])];
    //       generatePlayground(gameArena);
    //       printInfo(gameArena);
    //       pressAnyKeyToContinue();
    //       startTheGame(gameArena);
    //       continueGame = WantContinueGame();
    //     }
    //   }
    // } catch (Exception ex) {
    //   System.out.println(ex.getMessage());
    // }
  }

  /**
   * method to print the start information
   *
   * @param gameArena playing field
   */
  private static void printInfo(Monopoly[][] gameArena) {
    System.out.println("------------------------------------------------------------------------"
        + "---------------------------------------------");
    printGameArena(gameArena);
    System.out.println("\n" + InitializationRandomFields.firstToString());
    System.out.println("\nPlayer position: " + playerPosition);
    System.out.println("Bot position: " + botPosition);
    System.out.println("---------------------------------------------------------------------"
        + "------------------------------------------------");
  }

  /**
   * method help to don't start the game directly
   */
  private static void pressAnyKeyToContinue() {
    System.out.println("Press Enter key to start...");
    try {
      System.in.read();
    } catch (Exception e) {
    }
  }

  /**
   * method for getting an answer from the player if he wants to restart the game
   *
   * @return want to restart the game or not
   */
  private static boolean WantContinueGame() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Would you like to start new round with the same parameters, Yes or No?");
    String answer = sc.next();
    while (!answer.equals("No") && !answer.equals("Yes")) {
      System.out.println("Please only answer by Yes or NO!");
      answer = sc.next();
    }
    return answer.equals("Yes");
  }

  /**
   * Method to check the input which program gets from command line
   *
   * @param args the input arguments from command line
   * @return true if input arguments meet the conditions, and false otherwise
   */
  private static boolean checkInputCommand(String[] args) {
    try {
      if (!tryParseInt(args[0]) || !tryParseInt(args[1]) || !tryParseInt(args[2])) {
        throw new IllegalArgumentException("Incorrect input!");
      }
      final int args0 = Integer.parseInt(args[0]);
      final int args1 = Integer.parseInt(args[1]);
      final int args2 = Integer.parseInt(args[2]);
      if (args0 < 6 || args0 > 30 || args1 < 6 || args1 > 30 || args2 < 500 || args2 > 15000) {
        throw new IndexOutOfBoundsException("The input doesn't meet the requirements!");
      }
      Player.setBalance(args2);
      Bot.setBalance(args2);
      length = args0;
      width = args1;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }

  /**
   * method to determine the beginner, and start game's process
   *
   * @param gameArena playing field
   */
  private static void startTheGame(Monopoly[][] gameArena) {
    final Player player = new Player();
    final Bot bot = new Bot();
    boolean tempIsPlayer;
    int whoseTurn = InitializationRandomFields.random.nextInt(2);
    while (Player.getBalance() >= 0 && Bot.getBalance() >= 0) {
      final int firstNum = InitializationRandomFields.random.nextInt(6) + 1;
      final int secondNum = InitializationRandomFields.random.nextInt(6) + 1;
      tempIsPlayer = (whoseTurn == 1);
      System.out.println(tempIsPlayer ? "\nPLAYER TURN: " : "\nBOT TURN: ");
      move(firstNum + secondNum, tempIsPlayer);
      printMessage(gameArena, tempIsPlayer);
      System.out.println(player);
      System.out.println(bot);
      if (tempIsPlayer) {
        whatToDoAsPlayer(gameArena, player);
        whoseTurn = 0;
      } else {
        whatToDoAsBot(gameArena, bot);
        whoseTurn = 1;
      }
    }
    determineTheWinner(player, bot);
  }

  /**
   * method to determine the winner
   */
  private static void determineTheWinner(Player player, Bot bot) {
    System.out.println("\n" + player);
    System.out.println(bot + "\n");
    if (Bot.getBalance() < 0) {
      System.out.println("Congratulations, YOU are The winner! (^_^)");
    } else {
      System.out.println("Unfortunately, The winner is the bot! (-._.-)");
    }
    playerPosition.setX(0);
    playerPosition.setY(0);
    botPosition.setX(0);
    botPosition.setY(0);
  }

  /**
   * method for handling all possible cases actions that bot can do, at specific place
   *
   * @param gameArena playing field
   * @param bot       Bot class instance
   */
  private static void whatToDoAsBot(Monopoly[][] gameArena, Bot bot) {
    final int x = botPosition.getX(), y = botPosition.getY();
    if (gameArena[x][y] instanceof EmptyCell) {
      System.out.println((EmptyCell) gameArena[x][y]);
    } else if (gameArena[x][y] instanceof Taxi) {
      ((Taxi) gameArena[x][y]).setNewTaxiDistance();
      System.out.println(((Taxi) gameArena[x][y]).myToString(false));
      move(((Taxi) gameArena[x][y]).getTaxiDistance(), false);
    } else if (gameArena[x][y] instanceof Shop) {
      bot.visitShop((Shop) gameArena[x][y], x, y);
    } else if (gameArena[x][y] instanceof PenaltyCell) {//PenaltyCell
      ((PenaltyCell) gameArena[x][y]).imposePenalty(bot, x, y);
    }
  }

  /**
   * method for handling all possible cases actions that player can do, at specific place
   *
   * @param gameArena playing field
   * @param player    Player class instance
   */
  private static void whatToDoAsPlayer(Monopoly[][] gameArena, Player player) {
    final int x = playerPosition.getX(), y = playerPosition.getY();
    if (gameArena[x][y] instanceof EmptyCell) {
      System.out.println((EmptyCell) gameArena[x][y]);
    } else if (gameArena[x][y] instanceof Taxi) {
      ((Taxi) gameArena[x][y]).setNewTaxiDistance();
      System.out.println(((Taxi) gameArena[x][y]).myToString(true));
      move(((Taxi) gameArena[x][y]).getTaxiDistance(), true);
    } else if (gameArena[x][y] instanceof Bank) {
      ((Bank) gameArena[x][y]).receiveClient(player);
    } else if (gameArena[x][y] instanceof Shop) {
      player.visitShop((Shop) gameArena[x][y], x, y);
    } else {//PenaltyCell
      ((PenaltyCell) gameArena[x][y]).imposePenalty(player, x, y);
    }
  }

  /**
   * method for printing the location of the players and its symbol
   *
   * @param gameArena playing field
   * @param isPlayer  true if for printing player's information, and false otherwise
   */
  private static void printMessage(Monopoly[][] gameArena, boolean isPlayer) {
    if (isPlayer) {
      System.out.println(
          "Your current position is " + playerPosition + ": " + gameArena[playerPosition
              .getX()][playerPosition.getY()].myOutputToString());

    } else {
      System.out.println("Bot's current position is " + botPosition + ": " + gameArena[botPosition
          .getX()][botPosition.getY()].myOutputToString());
    }
    printGameArena(gameArena);
  }

  /**
   * this method is responsible for players movement
   *
   * @param count    number cells that player should move
   * @param isPlayer true if the player should move, and false otherwise (bot)
   */
  private static void move(int count, boolean isPlayer) {
    int x, y;
    if (isPlayer) {
      x = playerPosition.getX();
      y = playerPosition.getY();
    } else {
      x = botPosition.getX();
      y = botPosition.getY();
    }
    if (x == 0 && y < width - 1) {// player in the top side of playing field
      moveFromTop(count, isPlayer);
    } else if (y == 0 && x <= length - 1) {// player in the left side of playing field
      moveFromLeft(count, isPlayer);

    } else if (x == length - 1 && y > 0) {// player in the lower side of playing field
      moveFromDown(count, isPlayer);
    } else {// player in the right side of playing field
      moveFromRight(count, isPlayer);
    }
  }

  /**
   * method for moving players with start position in the right side of the playing field
   *
   * @param count    movement distance
   * @param isPlayer true if the player should move, and false otherwise (bot)
   */
  private static void moveFromRight(int count, boolean isPlayer) {
    final int[] valueOfIJ = getIJ(isPlayer);
    int x = valueOfIJ[0], y = valueOfIJ[1], temp;
    x += count;
    if (x >= length) {
      temp = x - (length - 1);
      x = length - 1;
      y -= temp;
      if (y < 0) {
        temp = -y;
        y = 0;
        x -= temp;
        if (x < 0) {
          temp = -x;
          x = 0;
          y += temp;
        }
      }
    }
    setNewPosition(isPlayer, x, y);
  }

  /**
   * method for moving players with start position in the lower side of the playing field
   *
   * @param count    movement distance
   * @param isPlayer true if the player should move, and false otherwise (bot)
   */
  private static void moveFromDown(int count, boolean isPlayer) {
    final int[] valueOfIJ = getIJ(isPlayer);
    int x = valueOfIJ[0], y = valueOfIJ[1], temp;
    y -= count;
    if (y < 0) {
      temp = -y;
      y = 0;
      x -= temp;
      if (x < 0) {
        temp = -x;
        x = 0;
        y += temp;
        if (y >= width) {
          temp = y - (width - 1);
          y = width - 1;
          x += temp;
        }
      }
    }
    setNewPosition(isPlayer, x, y);
  }

  /**
   * method for moving players with start position in the left side of the playing field
   *
   * @param count    movement distance
   * @param isPlayer true if the player should move, and false otherwise (bot)
   */
  private static void moveFromLeft(int count, boolean isPlayer) {
    final int[] valueOfIJ = getIJ(isPlayer);
    int x = valueOfIJ[0], y = valueOfIJ[1], temp;
    x -= count;
    if (x < 0) {
      temp = -x;
      x = 0;
      y += temp;
      if (y >= width) {
        temp = y - (width - 1);
        y = width - 1;
        x += temp;
        if (x >= length) {
          temp = x - (length - 1);
          x = length - 1;
          y -= temp;
        }
      }
    }
    setNewPosition(isPlayer, x, y);
  }

  /**
   * method for moving players with start position in the top side of the playing field
   *
   * @param count    movement distance
   * @param isPlayer true if the player should move, and false otherwise (bot)
   */
  private static void moveFromTop(int count, boolean isPlayer) {
    final int[] valueOfIJ = getIJ(isPlayer);
    int x = valueOfIJ[0], y = valueOfIJ[1], temp;
    y += count;
    if (y >= width) {
      temp = y - (width - 1);
      y = width - 1;
      x += temp;
      if (x >= length) {
        temp = x - (length - 1);
        x = length - 1;
        y -= temp;
        if (y < 0) {
          temp = -y;
          y = 0;
          x -= temp;
        }
      }
    }
    setNewPosition(isPlayer, x, y);
  }

  /**
   * method to set a new position to specific player
   *
   * @param isPlayer Player of Bot?
   * @param x        new x position
   * @param y        new y position
   */
  private static void setNewPosition(boolean isPlayer, int x, int y) {
    if (isPlayer) {
      playerPosition.setX(x);
      playerPosition.setY(y);
    } else {
      botPosition.setX(x);
      botPosition.setY(y);
    }
  }

  /**
   * method to get the current position of specific player
   *
   * @param isPlayer Player or Bot?
   * @return array of two integer values the 1st is x position and the 2nd is y position of specific
   * player
   */
  private static int[] getIJ(boolean isPlayer) {
    int[] valueOfIJ = new int[2];
    if (isPlayer) {
      valueOfIJ[0] = playerPosition.getX();
      valueOfIJ[1] = playerPosition.getY();
    } else {
      valueOfIJ[0] = botPosition.getX();
      valueOfIJ[1] = botPosition.getY();
    }
    return valueOfIJ;
  }

  /**
   * method for drawing the playing field in correct format
   *
   * @param gameArena
   */
  private static void printGameArena(Monopoly[][] gameArena) {
    for (int i = 0; i < gameArena.length; i++) {
      for (int j = 0; j < gameArena[i].length; j++) {
        if (i == 0 || i == length - 1 || j == 0 || j == width - 1) {
          System.out.print(gameArena[i][j].myOutputToString() + "  ");
        } else {
          System.out.print("   ");
        }
      }
      System.out.println();
    }
  }

  /**
   * method for generating the required elements in playing field, with meeting some conditions
   *
   * @param playground playing field
   */
  private static void generatePlayground(Monopoly[][] playground) {
    playground[0] = generateLine(width);
    playground[length - 1] = generateLine(width);
    Monopoly[] leftSide = generateLine(length);
    Monopoly[] rightSide = generateLine(length);
    for (int i = 1; i < length - 1; i++) {
      playground[i][width - 1] = rightSide[i];
      playground[i][0] = leftSide[i];
    }
  }

  /**
   * method for generating the sides of playing field
   *
   * @param size the number of cells in that side
   * @return filled side of cells, that meets some conditions
   */
  private static Monopoly[] generateLine(int size) {
    Monopoly[] line = new Monopoly[size];
    line[0] = new EmptyCell();
    line[size - 1] = new EmptyCell();
    line[1] = new Bank();
    generateTheRestElements(line);
    shuffleSide(line);
    return line;

  }

  /**
   * method helps generateLine method in filling the sides of playing field, here will be filled the
   * cells between second and last cells of playing field side
   *
   * @param line filled side of cells, that meets some conditions
   */
  private static void generateTheRestElements(Monopoly[] line) {
    final int numOfTaxi = InitializationRandomFields.random.nextInt(3);
    final int numOfPenaltyCell = InitializationRandomFields.random.nextInt(3);
    for (int i = 2; i < 2 + numOfTaxi; i++) {
      line[i] = new Taxi();
    }
    if (2 + numOfTaxi + numOfPenaltyCell >= line.length) {
      line[line.length - 2] = new PenaltyCell();
      return;
    }
    for (int i = 2 + numOfTaxi; i < 2 + numOfTaxi + numOfPenaltyCell; i++) {
      line[i] = new PenaltyCell();
    }
    for (int i = 2 + numOfTaxi + numOfPenaltyCell; i < line.length - 1; i++) {
      line[i] = new Shop();
    }
  }

  /**
   * method for shuffling the cells in each side if playing field
   *
   * @param side side of playing field
   */
  private static void shuffleSide(Monopoly[] side) {
    Random rnd = ThreadLocalRandom.current();
    for (int i = side.length - 2; i > 1; i--) {
      int index = rnd.nextInt(i) + 1;
      // Simple swap
      Monopoly temp = side[index];
      side[index] = side[i];
      side[i] = temp;
    }
  }

  /**
   * method to check if an input parameter can convert it to an integer type
   *
   * @param value an input parameter
   * @return if an input number can be converted to integer type
   */
  private static boolean tryParseInt(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
