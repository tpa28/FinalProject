
import java.io.*;
import java.util.ArrayList;

/**
 * The Forest class represents a collection of Tree objects, managing them in an Array.
 * It allows users to add and remove trees from the forest, print the forest's details, and save and load the forest data.
 * The application ensures that the forest's data integrity is maintained throughout operations.
 *
 * @author Timileyin Ajayi
 * @version 1.0
 */
public class Forest implements Serializable {
    private String name;
    private static ArrayList<Tree> trees;

    /**
     * Constructs a new Forest object with a null name and initializes the ArrayList of trees.
     */
    public Forest() {
        name = null;
        trees = new ArrayList<>();
    }

    /**
     * Constructs a new Forest object with the specified name and initializes the ArrayList of trees.
     *
     * @param name the name of the forest
     */
    public Forest(String name) {
        this.name = name;
        trees = new ArrayList<>();
    }

    /**
     * Prints information about the forest, including its name, the list of trees with their index,
     * and the total number of trees along with their average height.
     */
    public void print() {
        System.out.println("\nForest name: " + name);
        for (int index = 0; index < trees.size(); index++) {
            System.out.printf("%4d %s%n", index, trees.get(index));

        }
        System.out.printf("There are %d trees, with an average height of %.2f%n%n", trees.size(), calculateAverageHeight());
    }

    /**
     * Adds a randomly generated tree to the forest.
     * This method generates a new random tree using the static method makeRandomTree() from the Tree class
     * and adds it to the forest.
     */
    public void addTree() {
        Tree newTree;
        newTree = Tree.makeRandomTree(); // Create a new random tree
        trees.add(newTree);
    }

    /**
     * Adds the specified tree to the forest.
     *
     * @param newTree the tree to add to the forest
     */
    public void addTree(Tree newTree) {
        trees.add(newTree);
    }

    /**
     * Removes the tree at the specified position in the forest.
     *
     * @param treeNumber the position of the tree to remove
     */
    public void cutTree(int treeNumber) {
        if (treeNumber >= 0 && treeNumber < trees.size()) {
            trees.remove(treeNumber);
            System.out.println();
        } else {
            System.out.println("Tree number " + treeNumber + " does not exist");
        }
    }

    /**
     * Simulates yearly growth for all trees in the forest.
     * This method calls the grow() method for each tree in the forest to simulate their growth.
     */
    public void simulateYearlyGrowth() {
        for (Tree tree : trees) {
            tree.grow();
        }
    }

    /**
     * Reaps trees in the forest that are taller than the specified height.
     * This method removes trees taller than the specified height and replaces them with new randomly generated trees.
     *
     * @param heightToReap the height above which trees should be reaped
     */
    public void reap(double heightToReap) {
        for (int index = 0; index < trees.size(); index++) {
            Tree tree = trees.get(index);
            if (tree.getHeight() > heightToReap) {
                System.out.printf("Reaping the tall tree  %-7s %5d  %6.2f'  %4.1f%%%n",
                        tree.getSpecies(),
                        tree.getYearPlanting(),
                        tree.getHeight(),
                        tree.getGrowthRate());
                addTree();
                trees.remove(index);
                System.out.printf("Replaced with new tree %-7s %5d  %6.2f'  %4.1f%%%n",
                        trees.get(trees.size() - 1).getSpecies(),
                        trees.get(trees.size() - 1).getYearPlanting(),
                        trees.get(trees.size() - 1).getHeight(),
                        trees.get(trees.size() - 1).getGrowthRate());
            }
        }
        System.out.println();
    }

    /**
     * Calculates the average height of all trees in the forest.
     *
     * @return the average height of the trees in the forest
     */
    public double calculateAverageHeight() {
        double totalHeight;

        if (trees.isEmpty()) {
            return 0;
        }

        totalHeight = 0;
        for (Tree tree : trees) {
            totalHeight += tree.getHeight();
        }
        return totalHeight / trees.size();
    }

    /**
     * Gets the list of trees in the forest.
     *
     * @return the list of trees in the forest
     */
    public ArrayList<Tree> getTrees() {
        return trees;
    }

    /**
     * Saves the forest data to a file.
     *
     * @param forestName the name of the file to save the forest data to
     * @param theForest the Forest object to save
     * @return true if the forest data is successfully saved, false otherwise
     */
    public static boolean save(String forestName, Forest theForest) {
        ObjectOutputStream output = null;
        theForest = new Forest(forestName);
        try {
            output = new ObjectOutputStream(new FileOutputStream(forestName + ".db"));
            output.writeObject(theForest);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
            return false;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.out.println("Error closing: " + e.getMessage());
                    return false;
                }
            }
        }
    }

    public static boolean writeForestToDB(Forest theForest){
        ObjectOutputStream toStream = null;
        String fileName = theForest.name + ".db";

        try{
            toStream = new ObjectOutputStream(new FileOutputStream(fileName));
            toStream.writeObject(theForest);
            return true;
        }
        catch (IOException e) {
            System.out. println("Error saving to " + fileName); return (false);
        } finally {
            if (toStream != null) {
                try {
                    toStream.close();
                } catch (IOException e) {
                    System.out.println("Error closing " + fileName);
                }
            }
        }
    }


    /**
     * Loads the forest data from a file.
     *
     * @param fileName the name of the file to load the forest data from
     * @return the loaded Forest object if successful, null otherwise
     * @throws IOException if an I/O error occurs
     */
    public static Forest load(String fileName) throws IOException {
        ObjectInputStream input = null;
        Forest forest;

        try {
            input = new ObjectInputStream(new FileInputStream(fileName + ".db"));
            forest = (Forest) input.readObject();

        } catch (IOException e) {
            System.out.println("Error opening/reading " + fileName + ".db");
            System.out.println("Old forest retained");
            return null;
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
        finally {
            if (input != null){
                try{
                    input.close();
                }
                catch (IOException e){
                    System.out.println("ERROR closing" + e.getMessage());
                    return (null);
                }
            }
        }
        return forest;
    }

    /**
     * Gets a new Forest object.
     *
     * @return a new Forest object
     */
    public static Forest getForest() {
        return new Forest();
    }

    /**
     * Gets the name of the forest.
     *
     * @return the name of the forest
     */
    public String getName() {
        return name;
    }
}

