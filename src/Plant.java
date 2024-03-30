public class Plant extends LivingThing implements HerbFood, OmniFood{
    private String color = "GREEN";
    private boolean isDead = false;

    public String getColor() {
        return color;
    }

    @Override
    public boolean die() {
        isDead = true;
        return true;
    }
    @Override
    public void eat(Land[][] neighbors) {}
    @Override
    public boolean checkIfDead() {
        return isDead;
    }

    @Override
    void reproduce(Land[][] neighbors) {}
    @Override
    public void action(Land[][] neighbors) {
//        int[] countedNeighbors = {0, 0}; // [0] for herbivores, [1] for plants
//        int validLand = 0; // counts all the LivingThing instances within neighbors

//        for (int i = 0; i < neighbors.length; i++) {
//            for (int j = 0; j < neighbors[0].length; j++) {
//                Land currentLand = neighbors[i][j];
//                if (currentLand != null) { // Check if the land is not null
//                    LivingThing currentResident = currentLand.getResident();
//                    if (currentResident instanceof Herbivore)
//                        countedNeighbors[0]++; // Herbivores
//                    else if (currentResident instanceof Plant)
//                        countedNeighbors[1]++; // Plants
//                    validLand++;
//                }
//            }
//        }
        int herbivores = Herbivore.countInstances(neighbors);
        int plants = countInstances(neighbors);
        // must subtract the valid Herbivore and Plant neighbors to find number of empty cells
        int emptyLands = Land.countEmptyLands(neighbors);
        // if neighboring cells have at least 3 empty cells and 2 plants, it should seed
        if (emptyLands >= 3 && (plants - 1) >= 2) { // neighbors include the current resident
            seed(neighbors);
        }
    }

    /**
     * Find the an empty cell and populate it with a plant.
     * @param neighbors
     */
    public void seed(Land[][] neighbors) {
        outerloop:
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                Land currentLand = neighbors[j][i];
                // land cannot be null, while resident can be null
                if (currentLand != null) {
                    LivingThing currentResident = currentLand.getResident();
                    if (currentLand.getFutureResident() == null
                            && currentResident == null) {
                        currentLand.setFutureResident(new Plant());
                        break outerloop;
                    }
                }
            }
        }
    }

    public static int countInstances(Land[][] neighbors) {
        int count = 0;
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                Land currentLand = neighbors[j][i];
                // land cannot be null, while resident can be null
                if (currentLand != null && currentLand.getResident() instanceof Plant) {
                    count++;
                }
            }
        }
        return count;
    }
}
