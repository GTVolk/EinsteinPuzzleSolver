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
        nations.add("Англичанин");
        nations.add("Датчанин");
        nations.add("Немец");
        nations.add("Швед");
        nations.add("Норвежец");
    }

    public static void initColors(PuzzleSolver puzzleSolver) {
        List<String> colors = puzzleSolver.getColors();

        // Colors
        colors.add("Красный");
        colors.add("Зелёный");
        colors.add("Белый");
        colors.add("Синий");
        colors.add("Жёлтый");
    }

    public static void initCigarettes(PuzzleSolver puzzleSolver) {
        List<String> cigarettes = puzzleSolver.getCigarettes();

        // Cigarettes
        cigarettes.add("Pall Mall");
        cigarettes.add("Rothmans");
        cigarettes.add("Marlboro");
        cigarettes.add("Philip Morris");
        cigarettes.add("Dunhill");
    }

    public static void initAnimals(PuzzleSolver puzzleSolver) {
        List<String> animals = puzzleSolver.getAnimals();

        // Animals
        animals.add("Рыбки");
        animals.add("Лошади");
        animals.add("Птицы");
        animals.add("Собаки");
        animals.add("Кошки");
    }

    public static void initDrinks(PuzzleSolver puzzleSolver) {
        List<String> drinks = puzzleSolver.getDrinks();

        // Drinks
        drinks.add("Кофе");
        drinks.add("Чай");
        drinks.add("Пиво");
        drinks.add("Вода");
        drinks.add("Молоко");
    }

    public static PuzzleSet<PuzzleLine> initRules() {
        //Rules
        PuzzleLine rule1 = new PuzzleLine(1, "Норвежец", null, null, null, null);
        PuzzleLine rule2 = new PuzzleLine(null, "Англичанин", "Красный", null, null, null);
        PuzzleLine rule4 = new PuzzleLine(null, "Датчанин", null, null, "Чай", null);
        PuzzleLine rule6 = new PuzzleLine(null, null, "Жёлтый", null, null, "Dunhill");
        PuzzleLine rule7 = new PuzzleLine(null, "Немец", null, null, null, "Marlboro");
        PuzzleLine rule8 = new PuzzleLine(3, null, null, null, "Молоко", null);
        PuzzleLine rule10 = new PuzzleLine(null, null, null, "Птицы", null, "Pall Mall");
        PuzzleLine rule11 = new PuzzleLine(null, "Швед", null, "Собаки", null, null);
        PuzzleLine rule12 = new PuzzleLine(2, null, "Синий", null, null, null);
        PuzzleLine rule13 = new PuzzleLine(null, null, "Синий", "Лошади", null, null);
        PuzzleLine rule14 = new PuzzleLine(null, null, null, null, "Пиво", "Philip Morris");
        PuzzleLine rule15 = new PuzzleLine(null, null, "Зелёный", null, "Кофе", null);

        PuzzleSet<PuzzleLine> ruleSet = new PuzzleSet<>();

        ruleSet.add(rule1);
        ruleSet.add(rule2);
        ruleSet.add(rule4);
        ruleSet.add(rule6);
        ruleSet.add(rule7);
        ruleSet.add(rule8);
        ruleSet.add(rule10);
        ruleSet.add(rule11);
        ruleSet.add(rule12);
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
