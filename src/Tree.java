
import java.io.Serializable;

/**
 * The Tree class represents a tree with attributes such as species, year of planting, height, and growth rate.
 * It provides methods to simulate the growth of the tree and generate random trees.
 *
 * @author Timileyin Ajayi
 * @version 1
 * @see Tree
 */
public class Tree implements Serializable {
    private static final int MIN_YEAR = 2000;
    private static final int MAX_YEAR = 2024;
    private static final int MIN_HEIGHT = 10;
    private static final int MAX_HEIGHT = 20;
    private static final double MIN_GROWTH_RATE = 10;
    private static final double MAX_GROWTH_RATE = 20;

    /**
     * Enum representing different species of trees.
     */
    enum TreeSpecies{UNKNOWN, MAPLE, BIRCH, FIR};

    private TreeSpecies species;
    private int yearPlanting;
    private double height, growthRate;

    /**
     * Default constructor.
     * Initializes a Tree object with default values for attributes.
     */
    public Tree(){
        species = TreeSpecies.UNKNOWN;
        yearPlanting = 0;
        height = 0.0;
        growthRate = 0.0;

    }

    /**
     * Parameterized constructor.
     * Initializes a Tree object with the specified values for attributes.
     *
     * @param species the species of the tree
     * @param yearPlanting the year the tree was planted
     * @param height the height of the tree
     * @param growthRate the growth rate of the tree
     */
    public Tree(TreeSpecies species, int yearPlanting, double height, double growthRate) {
        this.species = species;
        this.yearPlanting = yearPlanting;
        this.height = height;
        this.growthRate = growthRate;
    }

    /**
     * Retrieves the height of the tree.
     *
     * @return the height of the tree
     */
    public double getHeight() {
        return height;
    }

    /**
     * Retrieves the species of the tree.
     *
     * @return the species of the tree
     */
    public TreeSpecies getSpecies() {
        return species;
    }

    /**
     * Retrieves the year the tree was planted.
     *
     * @return the year the tree was planted
     */
    public int getYearPlanting() {
        return yearPlanting;
    }

    /**
     * Retrieves the growth rate of the tree.
     *
     * @return the growth rate of the tree
     */
    public double getGrowthRate() {
        return growthRate;
    }

    /**
     * Simulates yearly growth of the tree by updating its height based on the growth rate.
     */
    public void grow() {
        // Calculate the new height based on the growth rate
        double newHeight;
        newHeight = height + (height * growthRate)/100;
        height = newHeight;
        // Return the new height
    }

    /**
     * Generates a random Tree object with random values for species, year of planting, height, and growth rate.
     *
     * @return a randomly generated Tree object
     */
    public static Tree makeRandomTree(){
        Tree newTree;
        newTree = new Tree(TreeSpecies.values()[1 + (int)(Math.random() * (TreeSpecies.values().length -1))],
                MIN_YEAR + (int)(Math.random() * (MAX_YEAR - MIN_YEAR)),
                MIN_HEIGHT + (Math.random() * (MAX_HEIGHT - MIN_HEIGHT)),
                MIN_GROWTH_RATE + (Math.random() * (MAX_GROWTH_RATE - MIN_GROWTH_RATE)));
        return(newTree);
    }

    /**
     * Returns a string representation of the Tree object.
     *
     * @return a string representation of the Tree object
     */
    public String toString() {
        return String.format("%-5s %-5d %5.2f' %5.2f%%", species, yearPlanting, height, growthRate);
    }
}
