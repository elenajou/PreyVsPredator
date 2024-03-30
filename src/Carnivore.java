/**
 * Carnivores can eat herbivores and omnivores. Thus, carnivores
 * are predators of the mentioned classes.
 */
public class Carnivore extends LivingThing implements OmniFood {
    private String color = "RED";
    private int turnsToDie;
    private boolean isDead;
    public String getColor() {
        return color;
    }
    public Carnivore() {
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
        int herbivores = Herbivore.countInstances(neighbors);
        int carnivores = countInstances(neighbors); // returns an array [total lands, herbivores]
        int emptyLands = Land.countEmptyLands(neighbors);
        // countInstances will count the current resident too
        if (carnivores > 1 && emptyLands >= 3 && herbivores >= 2) {
            outerloop:
            for (int i = 0; i < neighbors.length; i++) {
                for (int j = 0; j < neighbors[0].length; j++) {
                    Land currentLand = neighbors[j][i];
                    // land cannot be null, while resident can be null
                    if (currentLand != null && currentLand.getResident() == null) {
                        currentLand.setFutureResident(new Carnivore());
                        System.out.println("carnivore gave birth");
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
                    if (currentLand.getResident() instanceof Carnivore)
                        count++;
                }
            }
        }
        return count;
    }

    public void eat(Land[][] neighbors) {
        Land currentLand = neighbors[1][1];

        if (checkIfDead()) {
            currentLand.setFutureResident();
            return;
        }
        // if the current resident can't move to any neighboring lands
        if (Land.countMovableLands(neighbors) <= 0) {
            reduceLife();
            return;
        }

        Carnivore current = (Carnivore) neighbors[1][1].getResident();

        int deltaX, deltaY;
        Land randomLand;
        do {
            deltaX = RandomGenerator.nextNumber(3);
            deltaY = RandomGenerator.nextNumber(3);
            randomLand = neighbors[deltaY][deltaX];
        // if land is not null, I can move here if future resident is not a carnivore
        } while (randomLand == null
                || (deltaY == 1 && deltaX == 1)
                || randomLand.getFutureResident() instanceof Carnivore);

        // if the future resident is not food, reduce life
        if (!(randomLand.getFutureResident() instanceof CarnFood)) {
            reduceLife();
        }

        randomLand.setFutureResident(current);
        currentLand.setFutureResident();
    }
}
