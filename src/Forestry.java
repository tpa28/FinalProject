
import java.io.File;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Forestry class simulates forestry operations, allowing users to manage forests and trees.
 * Users can perform various actions such as printing forest details, adding trees, cutting trees, simulating growth,
 * reaping trees, saving forests to files, and loading forests from files.
 *
 * The application initializes forests from CSV files provided as command-line arguments.
 * It then presents a menu-driven interface for users to interact with the forests.
 * Users can navigate through multiple forests by entering forest names.
 *
 * @author Timileyin Ajayi
 * @version 1.0
 */
public class Forestry implements Serializable{
    private static final Scanner keyboard = new Scanner (System.in);

    /**
     * The main method is the entry point of the Forestry application.
     * It initializes forests from CSV files, presents a menu-driven interface for user interaction,
     * and handles forestry operations based on user input.
     *
     * @param args Command-line arguments containing the names of CSV files representing forests.
     * @throws IOException            If an I/O error occurs while reading forests from files.
     * @throws ClassNotFoundException If a class not found exception occurs during object deserialization.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {

//----Welcome message
        char inputFromUser;
        Forest newForestTest;
        /**
         * Iterates over the command-line arguments representing forest CSV files.
         * Initializes forests from these files and allows users to interact with them through a menu-driven interface.
         *
         * @param args The command-line arguments containing the names of CSV files representing forests.
         * @throws IOException            If an I/O error occurs while reading forests from files.
         * @throws ClassNotFoundException If a class not found exception occurs during object deserialization.
         */
        System.out.println("Welcome to the Forestry Simulation");
        System.out.println("----------------------------------");
        for (int argIndex = 0; argIndex < args.length; argIndex++) {
            File file = new File(args[argIndex] + ".csv");
            if (file.exists()) {

                System.out.println("Initializing from " + args[argIndex] + "\n");
                newForestTest = new Forest(args[argIndex]);
                readForest(args[argIndex], newForestTest);
                do {
                    printMenu();
                    String optionInput = keyboard.next().toUpperCase();

                    inputFromUser = optionInput.charAt(0);

                    // Use switch statement to perform the corresponding action based on the chosen inputFromUser
                    switch (inputFromUser) {
                        case 'P':
                        case 'p':
                            newForestTest.print();
                            break;
                        case 'A':
                        case 'a':
                            newForestTest.addTree();
                            break;
                        case 'C':
                        case 'c':
                            int treeNumber;
                            do {
                                try {
                                    System.out.print("Tree number to cut down: ");
                                    treeNumber = keyboard.nextInt();
                                    if (newForestTest != null) {
                                        newForestTest.cutTree(treeNumber);
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("That is not an integer");
                                    keyboard.next();
                                    treeNumber = -1;
                                }
                            } while (treeNumber < 0);
                            break;
                        case 'G':
                        case 'g':
                            if (newForestTest != null) {
                                newForestTest.simulateYearlyGrowth();
                            }
                            break;
                        case 'R':
                        case 'r':
                            double heightToReap;
                            do {
                                try {
                                    System.out.print("Height to reap from: ");
                                    heightToReap = keyboard.nextDouble();
                                    if (newForestTest != null) {
                                        newForestTest.reap(heightToReap);
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("That is not an integer");
                                    keyboard.nextLine();
                                    heightToReap = -1;
                                }
                            } while (heightToReap < 0);
                            break;
                        case 'S':
                        case 's':
                            Forest.save(newForestTest.getName(), newForestTest);
                            break;
                        case 'L':
                        case 'l':
                            System.out.print("Enter forest name: ");
                            String forestName = keyboard.next();

                            if (Forest.load(forestName) != null) {
                                newForestTest = Forest.load(forestName);
                                System.out.println("Forest loaded successfully.");
                            }
                            break;
                        case 'N':
                        case 'n':
                            System.out.println("Moving to the next forest");
                            break;
                        case 'X':
                        case 'x':
                            break;
                        default:
                            System.out.println("Invalid menu option, try again \n");

                    }// end of switch

                } while (inputFromUser != 'N' && inputFromUser != 'X'); // end of do while loop

                if(inputFromUser == 'X'){
                    break;
                }
            } else {
                System.out.println("Error opening " + args[argIndex] + ".csv");
            }

        }// end of for loop


//----Run menu
        System.out.println("\nExiting the Forestry Simulation\n");
    }// end on main method


    /**
     * Prints the menu options for the forestry simulation.
     */
    public static void printMenu() {
        System.out.print("(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it : ");
    }

    /**
     * Reads forest data from a CSV file and constructs a Forest object.
     * Each line in the CSV file represents a tree in the forest.
     *
     * @param fileName The name of the CSV file containing forest data.
     * @return A Forest object containing trees parsed from the CSV file.
     */
    public static void readForest(String fileName, Forest forest) {
        try {
            Scanner file = new Scanner(new File(fileName + ".csv"));

            while (file.hasNext()) {
                String line = file.nextLine();
                String[] data = line.split(",");
                String name = data[0];
                Tree.TreeSpecies species = switch (name) {
                    case "Birch" -> Tree.TreeSpecies.BIRCH;
                    case "Maple" -> Tree.TreeSpecies.MAPLE;
                    case "Fir" -> Tree.TreeSpecies.FIR;
                    default -> null;
                };

                int yearPlanting = Integer.parseInt(data[1]);
                double height = Double.parseDouble(data[2]);
                double growthRate = Double.parseDouble(data[3]);

                Tree tree = new Tree(species, yearPlanting, height, growthRate);
                forest.addTree(tree);
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error opening/reading " + fileName + ".csv");
        }
    }
}