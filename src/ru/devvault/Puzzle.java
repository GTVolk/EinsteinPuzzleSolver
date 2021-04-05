package ru.devvault;

import java.util.ArrayList;
import java.util.Iterator;

public class Puzzle {
    private static final ArrayList<Integer> orders = new ArrayList<>(5);
    private static final ArrayList<String> nations = new ArrayList<>(5);
    private static final ArrayList<String> animals = new ArrayList<>(5);
    private static final ArrayList<String> drinks = new ArrayList<>(5);
    private static final ArrayList<String> cigarettes = new ArrayList<>(5);
    private static final ArrayList<String> colors = new ArrayList<>(5);

    private static PuzzleSet<PuzzleLine> puzzleTable;

    static {
        // House orders
        orders.add(1);
        orders.add(2);
        orders.add(3);
        orders.add(4);
        orders.add(5);

        // Man nations
        nations.add("English");
        nations.add("Danish");
        nations.add("German");
        nations.add("Swedesh");
        nations.add("Norwegian");

        //Animals
        animals.add("Zebra");
        animals.add("Horse");
        animals.add("Birds");
        animals.add("Dog");
        animals.add("Cats");

        //Drinks
        drinks.add("Coffee");
        drinks.add("Tea");
        drinks.add("Beer");
        drinks.add("Water");
        drinks.add("Milk");

        //Smokes
        cigarettes.add("Pall Mall");
        cigarettes.add("Blend");
        cigarettes.add("Blue Master");
        cigarettes.add("Prince");
        cigarettes.add("Dunhill");

        //Colors
        colors.add("Red");
        colors.add("Green");
        colors.add("White");
        colors.add("Blue");
        colors.add("Yellow");
    }

    public static void main (String[] args){
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

        boolean validLine = true;
        puzzleTable = new PuzzleSet<>();

        //Creating all possible combination of a puzzle line.
        //The maximum number of lines is 5^^6 (15625).
        //Each combination line is checked against a set of knowing facts, thus
        //only a small number of line result at the end.
        for (Integer orderId : Puzzle.orders) {
            for (String nation : Puzzle.nations) {
                for (String color : Puzzle.colors) {
                    for (String animal : Puzzle.animals) {
                        for (String drink : Puzzle.drinks) {
                            for (String cigarette : Puzzle.cigarettes) {
                                PuzzleLine puzzleLine = new PuzzleLine(
                                    orderId,
                                    nation,
                                    color,
                                    animal,
                                    drink,
                                    cigarette
                                );

                                // Checking against a set of knowing facts
                                if (ruleSet.accepts(puzzleLine)){
                                    // Adding rules of neighbors
                                    if (cigarette.equalsIgnoreCase("Blend")
                                        && (
                                            animal.equalsIgnoreCase("Cats")
                                            || drink.equalsIgnoreCase("Water")
                                        )
                                    ) {
                                        validLine = false;
                                    }

                                    if (
                                        cigarette.equalsIgnoreCase("Dunhill")
                                        && animal.equalsIgnoreCase("Horse")
                                    ) {
                                        validLine = false;
                                    }

                                    if (validLine){
                                        puzzleTable.add(puzzleLine);

                                        //set neighbors constraints
                                        if (color.equalsIgnoreCase("Green")) {
                                            puzzleLine.setRightNeighbor(new PuzzleLine(null, null, "White", null, null, null));
                                        }

                                        if (color.equalsIgnoreCase("White")) {
                                            puzzleLine.setLeftNeighbor(new PuzzleLine(null, null, "Green", null, null, null));
                                        }

                                        //
                                        if (
                                            animal.equalsIgnoreCase("Cats")
                                            && !cigarette.equalsIgnoreCase("Blend")
                                        ) {
                                            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, null, null, "Blend"));
                                        }

                                        if (
                                            cigarette.equalsIgnoreCase("Blend")
                                            && !animal.equalsIgnoreCase("Cats")
                                        ) {
                                            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, "Cats", null, null));
                                        }

                                        //
                                        if (
                                            drink.equalsIgnoreCase("Water")
                                            && !animal.equalsIgnoreCase("Cats")
                                            && !cigarette.equalsIgnoreCase("Blend")
                                        ) {
                                            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, null, null, "Blend"));
                                        }

                                        if (
                                            cigarette.equalsIgnoreCase("Blend")
                                            && !drink.equalsIgnoreCase("Water")
                                        ) {
                                            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, null, "Water", null));
                                        }

                                        //
                                        if (
                                            animal.equalsIgnoreCase("Horse")
                                            && !cigarette.equalsIgnoreCase("Dunhill")
                                        ) {
                                            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, null, null, "Dunhill"));
                                        }

                                        if (
                                            cigarette.equalsIgnoreCase("Dunhill")
                                            && !animal.equalsIgnoreCase("Horse")
                                        ) {
                                            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, "Horse", null, null));
                                        }
                                    }
                                    validLine = true;
                                }
                            } //cigarette end
                        } //drinks end
                    } //animal end
                } //color end
            } //nations end
        } //order end

        System.out.println("After general rule set validation, remains "+ puzzleTable.size() + " lines.");

        for (Iterator<PuzzleLine> it = puzzleTable.iterator(); it.hasNext();){
            validLine = true;

            PuzzleLine lineOfPuzzle = it.next();

            if (lineOfPuzzle.hasLeftNeighbor()){
                PuzzleLine neighbor = lineOfPuzzle.getLeftNeighbor();
                if (neighbor.getOrder() < 1 || neighbor.getOrder() > 5){
                    validLine = false;
                    it.remove();

                }
            }
            if (validLine && lineOfPuzzle.hasRightNeighbor()){
                PuzzleLine neighbor = lineOfPuzzle.getRightNeighbor();
                if (neighbor.getOrder() < 1 || neighbor.getOrder() > 5){
                    it.remove();
                }
            }
        }

        System.out.println("After removing out of bound neighbors, remains " + puzzleTable.size() + " lines.");

        //Setting left and right neighbors
        for (PuzzleLine puzzleLine : puzzleTable) {
            if (puzzleLine.hasUndefNeighbors()) {
                for (PuzzleLine leftNeighbor : puzzleLine.getUndefNeighbors()) {
                    PuzzleLine rightNeighbor = new PuzzleLine(leftNeighbor);

                    //make it left neighbor
                    leftNeighbor.setOrder(puzzleLine.getOrder() - 1);
                    if (puzzleTable.contains(leftNeighbor)) {
                        if (puzzleLine.hasLeftNeighbor())
                            puzzleLine.getLeftNeighbor().merge(leftNeighbor);
                        else
                            puzzleLine.setLeftNeighbor(leftNeighbor);
                    }
                    rightNeighbor.setOrder(puzzleLine.getOrder() + 1);
                    if (puzzleTable.contains(rightNeighbor)) {
                        if (puzzleLine.hasRightNeighbor())
                            puzzleLine.getRightNeighbor().merge(rightNeighbor);
                        else
                            puzzleLine.setRightNeighbor(rightNeighbor);
                    }
                }
            }
        }

        int iteration = 1;
        int lastSize = 0;

        //Recursively validate against neighbor rules
        while (puzzleTable.size() > 5 && lastSize != puzzleTable.size()) {
            lastSize = puzzleTable.size();
            puzzleTable.clearLineCountFlags();

            recursiveSearch(null, puzzleTable, -1);

            ruleSet.clear();
            // Assuming we'll get at leas one valid line each iteration, we create
            // a set of new rules with lines which have no more then one instance of same OrderId.
            for (int i = 1; i < 6; i++) {
                if (puzzleTable.getLineCountByOrderId(i) == 1) {
                    ruleSet.addAll(puzzleTable.getSimilarLines(new PuzzleLine(i, null, null, null, null, null)));
                }
            }

            puzzleTable.removeIf(puzzleLine -> !ruleSet.accepts(puzzleLine));

            //
            System.out.println("After " + iteration + " recursive iteration, remains " + puzzleTable.size() + " lines");

            iteration += 1;
        }

        // Print the results
        System.out.println("-------------------------------------------");
        if (puzzleTable.size() == 5) {
            for (PuzzleLine puzzleLine : puzzleTable) {
                System.out.println(puzzleLine.getWholeLine());
            }
        } else {
            System.out.println("Sorry, solution not found!");
        }
    }

    // Recursively checks the input set to ensure each line has right neighbor.
    // Neighbors can be of three type, left, right or undefined.
    // Direction: -1 left, 0 undefined, 1 right
    private static boolean recursiveSearch(
        PuzzleLine puzzleNodeLine,
        PuzzleSet<PuzzleLine> puzzleSet,
        int direction
    ) {
        boolean validLeaf = false;
        boolean hasNeighbor;
        PuzzleSet<PuzzleLine> puzzleSubSet = null;

        for (Iterator<PuzzleLine> it = puzzleSet.iterator(); it.hasNext();) {
            PuzzleLine puzzleLeafLine = it.next();
            validLeaf = false;

            hasNeighbor = puzzleLeafLine.hasNeighbor(direction);

            if (hasNeighbor){
                puzzleSubSet = puzzleTable.getSimilarLines(puzzleLeafLine.getNeighbor(direction));
                if (puzzleSubSet != null){
                    if (puzzleNodeLine != null)
                        validLeaf = puzzleSubSet.contains(puzzleNodeLine);
                    else
                        validLeaf = recursiveSearch(puzzleLeafLine, puzzleSubSet, -1 * direction);
                }
                else
                    validLeaf = false;
            }

            if (!validLeaf && puzzleLeafLine.hasNeighbor(-1 * direction)){
                hasNeighbor = true;
                puzzleSubSet = puzzleTable.getSimilarLines(puzzleLeafLine.getNeighbor(-1 * direction));
                if (puzzleSubSet != null){
                    if (puzzleNodeLine !=null)
                        validLeaf = puzzleSubSet.contains(puzzleNodeLine);
                    else
                        validLeaf = recursiveSearch(puzzleLeafLine, puzzleSubSet, direction);
                }
                else
                    validLeaf = false;
            }

            if (puzzleNodeLine != null && validLeaf)
                return true;

            if (puzzleNodeLine == null && hasNeighbor && !validLeaf){
                it.remove();
            }

            if (puzzleNodeLine == null){
                if (hasNeighbor && validLeaf){
                    puzzleSet.riseLineCountFlags(puzzleLeafLine.getOrder());
                }
                if (!hasNeighbor){
                    puzzleSet.riseLineCountFlags(puzzleLeafLine.getOrder());
                }
            }
        }
        return validLeaf;
    }
}
