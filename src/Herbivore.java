/**
 * Herbivores can eat plants. Thus, herbivores are PlantEaters.
 */
public class Herbivore extends LivingThing implements CarnFood, OmniFood{
    private String color = "YELLOW";
    private int turnsToDie;
    private boolean isDead;
    public String getColor() {
        return color;
    }
    public Herbivore() {
        turnsToDie = 5;
        isDead = false;
    }

    public void reduceLife() {
        turnsToDie--;
        if (turnsToDie <= 0)
            die();
    }
    @Override
    public boolean die() {
        isDead = true;
        turnsToDie = 0;
        return true;
    }
    @Override
    public boolean checkIfDead() {
        return isDead;
    }

    @Override
    void reproduce(Land[][] neighbors) {
        int plants = Plant.countInstances(neighbors);
        int emptyLands = Land.countEmptyLands(neighbors);
        int herbivores = countInstances(neighbors); // returns an array [total lands, herbivores]

        // countInstances will count the current resident too
        if (herbivores > 1 && emptyLands >= 2 && plants >= 2) {
            outerloop:
            for (int i = 0; i < neighbors.length; i++) {
                for (int j = 0; j < neighbors[0].length; j++) {
                    Land currentLand = neighbors[j][i];
                    // land cannot be null, while resident can be null
                    if (currentLand != null && currentLand.getResident() == null) {
                        currentLand.setFutureResident(new Herbivore());
                        break outerloop;
                    }
                }
            }
        }
    }

    @Override
    public void action(Land[][] neighbors) {
        eat(neighbors);
        reproduce(neighbors);
    }
    public static int countInstances(Land[][] neighbors) {
        int count = 0; // first value for the total valid lands, second for herbivores
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                Land currentLand = neighbors[j][i];
                // land cannot be null, while resident can be null
                if (currentLand != null) {
                    if (currentLand.getResident() instanceof Herbivore)
                        count++;
                }
            }
        }
        return count;
    }

    @Override
    public void eat(Land[][] neighbors) {
        Land currentLand = neighbors[1][1];

        if (checkIfDead()) {
            neighbors[1][1].setFutureResident();
            return;
        }
        if (Land.countMovableLands(neighbors) <= 0) {
            reduceLife();
            return;
        }

        Herbivore current = (Herbivore) neighbors[1][1].getResident();

        int deltaX, deltaY;
        Land randomLand;
        do {
            deltaX = RandomGenerator.nextNumber(3);
            deltaY = RandomGenerator.nextNumber(3);
            randomLand = neighbors[deltaY][deltaX];
        } while (randomLand == null
                || (deltaY == 1 && deltaX == 1)
                // if the future resident is a herbivore, cannot move here
                || randomLand.getFutureResident() instanceof Herbivore
                // make the current resident avoid predators if seen
                || randomLand.getFutureResident() instanceof HerbEater);

        // if the land moving to does not have food, reduce life
        if (!(randomLand.getResident() instanceof HerbFood)) {
            reduceLife();
        }

        // this herbivore has avoided predators in the while loop
        randomLand.setFutureResident(current);
        currentLand.setFutureResident();
    }
}
