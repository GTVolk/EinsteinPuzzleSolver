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

    // TODO: EXTRACT RULES FROM HERE TO MAIN
    public boolean isValidRulesOfNeighbors(PuzzleLine puzzleLine) {
        if (puzzleLine.getCigarette().equalsIgnoreCase("Rothmans")
            && (puzzleLine.getAnimal().equalsIgnoreCase("Кошки")
            || puzzleLine.getDrink().equalsIgnoreCase("Вода")
        )
        ) {
            return false;
        }

        return !puzzleLine.getCigarette().equalsIgnoreCase("Dunhill")
            || !puzzleLine.getAnimal().equalsIgnoreCase("Лошади");
    }

    // TODO: EXTRACT RULES FROM HERE TO MAIN
    public void setNeighborsConstrains(PuzzleLine puzzleLine) {
        if (puzzleLine.getColor().equalsIgnoreCase("Зелёный")) {
            puzzleLine.setRightNeighbor(new PuzzleLine(null, null, "Белый", null, null, null));
        }

        if (puzzleLine.getColor().equalsIgnoreCase("Белый")) {
            puzzleLine.setLeftNeighbor(new PuzzleLine(null, null, "Зелёный", null, null, null));
        }

        if (
            puzzleLine.getAnimal().equalsIgnoreCase("Кошки")
                && !puzzleLine.getCigarette().equalsIgnoreCase("Rothmans")
        ) {
            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, null, null, "Rothmans"));
        }

        if (
            puzzleLine.getCigarette().equalsIgnoreCase("Rothmans")
                && !puzzleLine.getAnimal().equalsIgnoreCase("Кошки")
        ) {
            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, "Кошки", null, null));
        }

        if (
            puzzleLine.getDrink().equalsIgnoreCase("Вода")
                && !puzzleLine.getAnimal().equalsIgnoreCase("Кошки")
                && !puzzleLine.getCigarette().equalsIgnoreCase("Rothmans")
        ) {
            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, null, null, "Rothmans"));
        }

        if (
            puzzleLine.getCigarette().equalsIgnoreCase("Rothmans")
                && !puzzleLine.getDrink().equalsIgnoreCase("Вода")
        ) {
            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, null, "Вода", null));
        }

        if (
            puzzleLine.getAnimal().equalsIgnoreCase("Лошади")
                && !puzzleLine.getCigarette().equalsIgnoreCase("Dunhill")
        ) {
            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, null, null, "Dunhill"));
        }

        if (
            puzzleLine.getCigarette().equalsIgnoreCase("Dunhill")
                && !puzzleLine.getAnimal().equalsIgnoreCase("Лошади")
        ) {
            puzzleLine.addUndefinedNeighbor(new PuzzleLine(null, null, null, "Лошади", null, null));
        }
    }

    public void generalRuleSetValidation(PuzzleSet<PuzzleLine> ruleSet) {
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
                                // Checking rules of neighbors
                                if (ruleSet.accepts(puzzleLine) && isValidRulesOfNeighbors(puzzleLine)){
                                    puzzleTable.add(puzzleLine);

                                    //set neighbors constraints
                                    setNeighborsConstrains(puzzleLine);
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
        for (Iterator<PuzzleLine> it = puzzleTable.iterator(); it.hasNext();){
            PuzzleLine lineOfPuzzle = it.next();
            boolean validLine = true;

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
