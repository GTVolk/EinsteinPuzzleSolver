package ru.devvault;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PuzzleSolver {
    public final int capacity;
    private final ArrayList<Integer> orders;
    private final ArrayList<String> nations;
    private final ArrayList<String> colors;
    private final ArrayList<String> cigarettes;
    private final ArrayList<String> animals;
    private final ArrayList<String> drinks;

    private final PuzzleSet<PuzzleLine> puzzleTable;

    public PuzzleSolver(int capacity) {
        this.capacity = capacity;
        this.orders = new ArrayList<>(capacity);
        this.nations = new ArrayList<>(capacity);
        this.colors = new ArrayList<>(capacity);
        this.cigarettes = new ArrayList<>(capacity);
        this.animals = new ArrayList<>(capacity);
        this.drinks = new ArrayList<>(capacity);

        this.puzzleTable = new PuzzleSet<>();
    }

    public List<Integer> getOrders() {
        return this.orders;
    }

    public List<String> getNations() {
        return this.nations;
    }

    public List<String> getColors() {
        return this.colors;
    }

    public List<String> getCigarettes() {
        return this.cigarettes;
    }

    public List<String> getAnimals() {
        return this.animals;
    }

    public List<String> getDrinks() {
        return this.drinks;
    }

    public void generalRuleSetValidation(PuzzleSet<PuzzleLine> ruleSet) {
        boolean validLine = true;

        //Creating all possible combination of a puzzle line.
        //The maximum number of lines is 5^^6 (15625).
        //Each combination line is checked against a set of knowing facts, thus
        //only a small number of line result at the end.
        for (Integer orderId : getOrders()) {
            for (String nation : getNations()) {
                for (String color : getColors()) {
                    for (String cigarette : getCigarettes()) {
                        for (String animal : getAnimals()) {
                            for (String drink : getDrinks()) {
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
                            } //drinks end
                        } //animal end
                    } //cigarette end
                } //color end
            } //nations end
        } //order end

        System.out.println("After general rule set validation, remains " + puzzleTable.size() + " lines.");
    }

    public void removeOutOfBoundNeighbors() {
        boolean validLine;

        for (Iterator<PuzzleLine> it = puzzleTable.iterator(); it.hasNext();){
            validLine = true;

            PuzzleLine lineOfPuzzle = it.next();

            if (lineOfPuzzle.hasLeftNeighbor()){
                PuzzleLine neighbor = lineOfPuzzle.getLeftNeighbor();
                if (neighbor.getOrder() < 1 || neighbor.getOrder() > this.capacity){
                    validLine = false;
                    it.remove();

                }
            }
            if (validLine && lineOfPuzzle.hasRightNeighbor()){
                PuzzleLine neighbor = lineOfPuzzle.getRightNeighbor();
                if (neighbor.getOrder() < 1 || neighbor.getOrder() > this.capacity){
                    it.remove();
                }
            }
        }

        System.out.println("After removing out of bound neighbors, remains " + puzzleTable.size() + " lines.");
    }

    public void makeLeftNeighbor(PuzzleLine puzzleLine, PuzzleLine leftNeighbor) {
        leftNeighbor.setOrder(puzzleLine.getOrder() - 1);
        if (puzzleTable.contains(leftNeighbor)) {
            if (puzzleLine.hasLeftNeighbor())
                puzzleLine.getLeftNeighbor().merge(leftNeighbor);
            else
                puzzleLine.setLeftNeighbor(leftNeighbor);
        }
    }

    public void makeRightNeighbor(PuzzleLine puzzleLine, PuzzleLine rightNeighbor) {
        rightNeighbor.setOrder(puzzleLine.getOrder() + 1);
        if (puzzleTable.contains(rightNeighbor)) {
            if (puzzleLine.hasRightNeighbor())
                puzzleLine.getRightNeighbor().merge(rightNeighbor);
            else
                puzzleLine.setRightNeighbor(rightNeighbor);
        }
    }

    public void setLeftAndRightNeighbors() {
        //Setting left and right neighbors
        for (PuzzleLine puzzleLine : puzzleTable) {
            if (puzzleLine.hasUndefNeighbors()) {
                for (PuzzleLine leftNeighbor : puzzleLine.getUndefNeighbors()) {
                    PuzzleLine rightNeighbor = new PuzzleLine(leftNeighbor);

                    makeLeftNeighbor(puzzleLine, leftNeighbor);
                    makeRightNeighbor(puzzleLine, rightNeighbor);
                }
            }
        }
    }

    // Recursively checks the input set to ensure each line has right neighbor.
    // Neighbors can be of three type, left, right or undefined.
    // Direction: -1 left, 0 undefined, 1 right
    private boolean recursiveSearch(
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

    public void validateNeighborsRules(PuzzleSet<PuzzleLine> ruleSet) {
        int iteration = 1;
        int lastSize = 0;

        //Recursively validate against neighbor rules
        while (puzzleTable.size() > this.capacity && lastSize != puzzleTable.size()) {
            lastSize = puzzleTable.size();
            puzzleTable.clearLineCountFlags();

            recursiveSearch(null, puzzleTable, -1);

            ruleSet.clear();
            // Assuming we'll get at leas one valid line each iteration, we create
            // a set of new rules with lines which have no more then one instance of same OrderId.
            for (int i = 1; i < (this.capacity + 1); i++) {
                if (puzzleTable.getLineCountByOrderId(i) == 1) {
                    ruleSet.addAll(puzzleTable.getSimilarLines(new PuzzleLine(i, null, null, null, null, null)));
                }
            }

            puzzleTable.removeIf(puzzleLine -> !ruleSet.accepts(puzzleLine));

            System.out.println("After " + iteration + " recursive iteration, remains " + puzzleTable.size() + " lines");

            iteration += 1;
        }
    }

    public void printResults() {
        // Print the results
        System.out.println("-------------------------------------------");
        if (puzzleTable.size() == this.capacity) {
            for (PuzzleLine puzzleLine : puzzleTable) {
                System.out.println(puzzleLine.getWholeLine());
            }
        } else {
            System.out.println("Sorry, solution not found!");
        }
    }
}
