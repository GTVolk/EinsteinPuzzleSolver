package ru.devvault;

import java.util.List;

public class Puzzle {
    public static void initOrders(PuzzleSolver puzzleSolver) {
        List<Integer> orders = puzzleSolver.getOrders();

        // House orders
        orders.add(1);
        orders.add(2);
        orders.add(3);
        orders.add(4);
        orders.add(5);
    }

    public static void initNations(PuzzleSolver puzzleSolver) {
        List<String> nations = puzzleSolver.getNations();

        // Nations
        nations.add("English");
        nations.add("Danish");
        nations.add("German");
        nations.add("Swedesh");
        nations.add("Norwegian");
    }

    public static void initColors(PuzzleSolver puzzleSolver) {
        List<String> colors = puzzleSolver.getColors();

        // Colors
        colors.add("Red");
        colors.add("Green");
        colors.add("White");
        colors.add("Blue");
        colors.add("Yellow");
    }

    public static void initCigarettes(PuzzleSolver puzzleSolver) {
        List<String> cigarettes = puzzleSolver.getCigarettes();

        // Cigarettes
        cigarettes.add("Pall Mall");
        cigarettes.add("Blend");
        cigarettes.add("Blue Master");
        cigarettes.add("Prince");
        cigarettes.add("Dunhill");
    }

    public static void initAnimals(PuzzleSolver puzzleSolver) {
        List<String> animals = puzzleSolver.getAnimals();

        // Animals
        animals.add("Zebra");
        animals.add("Horse");
        animals.add("Birds");
        animals.add("Dog");
        animals.add("Cats");
    }

    public static void initDrinks(PuzzleSolver puzzleSolver) {
        List<String> drinks = puzzleSolver.getDrinks();

        // Drinks
        drinks.add("Coffee");
        drinks.add("Tea");
        drinks.add("Beer");
        drinks.add("Water");
        drinks.add("Milk");
    }

    public static PuzzleSet<PuzzleLine> initRules() {
        //Rules
        PuzzleLine rule2 = new PuzzleLine(null, "English", "Red", null, null, null);
        PuzzleLine rule3 = new PuzzleLine(null, "Swedesh", null, "Dog", null, null);
        PuzzleLine rule4 = new PuzzleLine(null, "Danish", null, null, "Tea", null);
        PuzzleLine rule6 = new PuzzleLine(null, null, "Green", null, "Coffee", null);
        PuzzleLine rule7 = new PuzzleLine(null, null, null, "Birds", null, "Pall Mall");
        PuzzleLine rule8 = new PuzzleLine(null, null, "Yellow", null, null, "Dunhill");
        PuzzleLine rule9 = new PuzzleLine(3, null, null, null, "Milk", null);
        PuzzleLine rule10 = new PuzzleLine(1, "Norwegian", null, null, null, null);
        PuzzleLine rule13 = new PuzzleLine(null, null, null, null, "Beer", "Blue Master");
        PuzzleLine rule14 = new PuzzleLine(null, "German", null, null, null, "Prince");
        PuzzleLine rule15 = new PuzzleLine(2, null, "Blue", null, null, null);

        PuzzleSet<PuzzleLine> ruleSet = new PuzzleSet<>();

        ruleSet.add(rule2);
        ruleSet.add(rule3);
        ruleSet.add(rule4);
        ruleSet.add(rule6);
        ruleSet.add(rule7);
        ruleSet.add(rule8);
        ruleSet.add(rule9);
        ruleSet.add(rule10);
        ruleSet.add(rule13);
        ruleSet.add(rule14);
        ruleSet.add(rule15);

        return ruleSet;
    }

    public static PuzzleSet<PuzzleLine> initPuzzleItems(PuzzleSolver puzzleSolver) {
        initOrders(puzzleSolver);
        initNations(puzzleSolver);
        initColors(puzzleSolver);
        initCigarettes(puzzleSolver);
        initAnimals(puzzleSolver);
        initDrinks(puzzleSolver);

        return initRules();
    }

    public static void main (String[] args){
        PuzzleSolver puzzleSolver = new PuzzleSolver(5);

        PuzzleSet<PuzzleLine> ruleSet = initPuzzleItems(puzzleSolver);

        puzzleSolver.generalRuleSetValidation(ruleSet);
        puzzleSolver.removeOutOfBoundNeighbors();
        puzzleSolver.setLeftAndRightNeighbors();
        puzzleSolver.validateNeighborsRules(ruleSet);
        puzzleSolver.printResults();
    }
}
