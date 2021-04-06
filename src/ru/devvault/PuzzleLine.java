package ru.devvault;

public class PuzzleLine {

    private Integer order;
    private String nation;
    private String color;
    private String cigarette;
    private String animal;
    private String drink;

    private PuzzleLine rightNeighbor;
    private PuzzleLine leftNeighbor;
    private PuzzleSet<PuzzleLine> undefNeighbors;

    public PuzzleLine (
        Integer order,
        String nation,
        String color,
        String animal,
        String drink,
        String cigarette
    ) {
        this.order = order;
        this.nation = nation;
        this.color = color;
        this.cigarette = cigarette;
        this.animal = animal;
        this.drink = drink;

        this.rightNeighbor = null;
        this.leftNeighbor = null;
        this.undefNeighbors = null;
    }

    public PuzzleLine(PuzzleLine obj) {
        this.order = obj.getOrder();
        this.nation = obj.getNation();
        this.color = obj.getColor();
        this.cigarette = obj.getCigarette();
        this.animal = obj.getAnimal();
        this.drink = obj.getDrink();

        this.rightNeighbor = obj.getRightNeighbor();
        this.leftNeighbor = obj.getLeftNeighbor();
        this.undefNeighbors = obj.getUndefNeighbors();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getCigarette() {
        return cigarette;
    }

    public void setCigarette(String cigarette) {
        this.cigarette = cigarette;
    }

    /**
     * Overrides object equal method
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PuzzleLine){
            PuzzleLine searchLine = (PuzzleLine) obj;
            return getWholeLine().equalsIgnoreCase(searchLine.getWholeLine());
        } else {
            return false;
        }
    }

    public int getFactsCount() {
        int facts = 0;
        facts += getOrder() != null ? 1 : 0;
        facts += getNation() != null ? 1 : 0;
        facts += getColor() != null ? 1 : 0;
        facts += getAnimal() != null ? 1 : 0;
        facts += getCigarette() != null ? 1 : 0;
        facts += getDrink() != null ? 1 : 0;
        return facts;
    }

    public int getCommonFactsCount(PuzzleLine factsLine) {
        int orderCmp = (getOrder() != null && factsLine.getOrder() != null &&
            getOrder().intValue() == factsLine.getOrder().intValue()) ? 1 : 0;

        int nationCmp = (getNation() != null && factsLine.getNation() != null &&
            getNation().equalsIgnoreCase(factsLine.getNation())) ? 1 : 0;

        int colorCmp = (getColor() != null && factsLine.getColor() != null &&
            getColor().equalsIgnoreCase(factsLine.getColor())) ? 1 : 0;

        int animalCmp = (getAnimal() != null && (factsLine.getAnimal() != null &&
            getAnimal().equalsIgnoreCase(factsLine.getAnimal()))) ? 1 : 0;

        int cigaretteCmp = (getCigarette() != null && factsLine.getCigarette() != null &&
            getCigarette().equalsIgnoreCase(factsLine.getCigarette())) ? 1 : 0;

        int drinkCmp = (getDrink() != null && factsLine.getDrink() != null &&
            getDrink().equalsIgnoreCase(factsLine.getDrink())) ? 1 : 0;

        return (orderCmp + nationCmp + colorCmp + animalCmp + cigaretteCmp + drinkCmp);
    }

    public void addUndefinedNeighbor(PuzzleLine newNeighbor) {
        if (this.undefNeighbors == null)
            this.undefNeighbors = new PuzzleSet<>();

        this.undefNeighbors.add(newNeighbor);
    }

    public boolean hasUndefNeighbors() {
        return (this.undefNeighbors!=null);
    }

    public PuzzleSet<PuzzleLine> getUndefNeighbors() {
        return this.undefNeighbors;
    }

    public void setLeftNeighbor(PuzzleLine leftNeighbor) {
        this.leftNeighbor = leftNeighbor;
        this.leftNeighbor.setOrder(getOrder() - 1);
    }

    public void setRightNeighbor(PuzzleLine rightNeighbor) {
        this.rightNeighbor = rightNeighbor;
        this.rightNeighbor.setOrder(getOrder() + 1);
    }

    public boolean hasLeftNeighbor() {
        return (leftNeighbor != null);
    }

    public PuzzleLine getLeftNeighbor() {
        return this.leftNeighbor;
    }

    public boolean hasNeighbor(int direction) {
        if (direction < 0)
            return (leftNeighbor != null);
        else
            return (rightNeighbor != null);
    }

    public boolean hasRightNeighbor() {
        return (rightNeighbor!=null);
    }

    public PuzzleLine getRightNeighbor() {
        return this.rightNeighbor;
    }

    public PuzzleLine getNeighbor(int direction) {
        if (direction < 0)
            return this.leftNeighbor;
        else
            return this.rightNeighbor;
    }

    public String getWholeLine() {
        return getOrder() + " - " +
            getNation() + " - " +
            getColor() + " - " +
            getAnimal() + " - " +
            getDrink() + " - " +
            getCigarette();
    }

    @Override
    public int hashCode() {
        return (getOrder() + " - " +
            getNation() + " - " +
            getColor() + " - " +
            getAnimal() + " - " +
            getDrink() + " - " +
            getCigarette()
        ).hashCode();
    }

    public void merge(PuzzleLine mergedLine) {
        if (getOrder() == null) setOrder(mergedLine.getOrder());
        if (getNation() == null) setNation(mergedLine.getNation());
        if (getColor() == null) setColor(mergedLine.getColor());
        if (getCigarette() == null) setCigarette(mergedLine.getCigarette());
        if (getAnimal() == null) setAnimal(mergedLine.getAnimal());
        if (getDrink() == null) setDrink(mergedLine.getDrink());
    }
}
