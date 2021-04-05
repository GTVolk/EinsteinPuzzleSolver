package ru.devvault;

import java.util.LinkedHashSet;

public class PuzzleSet<T extends PuzzleLine> extends LinkedHashSet<T> {

    private int countOfOne = 0;
    private int countOfTwo = 0;
    private int countOfThree = 0;
    private int countOfFour = 0;
    private int countOfFive = 0;

    PuzzleSet() {
        super();
    }

    public T get(int index) {
        return ((T) this.toArray()[index]);
    }

    public PuzzleSet<T> getSimilarLines(T searchLine) {
        PuzzleSet<T> puzzleSubSet = new PuzzleSet<>();
        for (T lineOfPuzzle : this) {
            if (lineOfPuzzle.getCommonFactsCount(searchLine) == searchLine.getFactsCount())
                puzzleSubSet.add(lineOfPuzzle);
        }
        if (puzzleSubSet.isEmpty())
            return null;

        return puzzleSubSet;
    }

    public boolean contains(T searchLine) {
        for (T puzzleLine : this) {
            if (puzzleLine.getCommonFactsCount(searchLine) == searchLine.getFactsCount())
                return true;
        }
        return false;
    }

    public boolean accepts(T searchLine) {
        int passed = 0;
        int notpassed = 0;

        for (T puzzleSetLine : this) {
            int lineFactsCnt = puzzleSetLine.getFactsCount();
            int commonFactsCnt = puzzleSetLine.getCommonFactsCount(searchLine);

            if (lineFactsCnt != commonFactsCnt && lineFactsCnt != 0 && commonFactsCnt != 0) {
                notpassed++;
            }

            if (lineFactsCnt == commonFactsCnt)
                passed++;
        }
        return (passed >= 0 && notpassed == 0);
    }

    public void riseLineCountFlags(int lineOrderId) {
        switch (lineOrderId){
            case 1: countOfOne++; break;
            case 2: countOfTwo++; break;
            case 3: countOfThree++; break;
            case 4: countOfFour++; break;
            case 5: countOfFive++; break;
            default:
        }
    }

    public void clearLineCountFlags() {
        countOfOne = 0;
        countOfTwo = 0;
        countOfThree = 0;
        countOfFour = 0;
        countOfFive = 0;
    }

    public int getLineCountByOrderId(int lineOrderId) {
        switch (lineOrderId){
            case 1: return countOfOne;
            case 2: return countOfTwo;
            case 3: return countOfThree;
            case 4: return countOfFour;
            case 5: return countOfFive;
            default:return -1;
        }
    }
}
