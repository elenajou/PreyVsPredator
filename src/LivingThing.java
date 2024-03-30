abstract class LivingThing {
    protected String color = "WHITE";

    public LivingThing() {
        color = "WHITE";
    }

    public String getColor() {
        return color;
    }

    abstract boolean die();

    abstract boolean checkIfDead();

    abstract void eat(Land[][] neighbors);

    abstract void reproduce(Land[][] neighbors);

    void action(Land[][] neighbors) {
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                Land currentLand = neighbors[j][i];
                if (currentLand != null) { // Ensure the land is not null
                    LivingThing current = currentLand.getResident();
                    if (current instanceof Herbivore) {
//                        System.out.print("H");
                    } else if (current instanceof Plant) {
//                        System.out.print("P");
                    } else {
//                        System.out.print("L");
                    }
                } else {
//                    System.out.print("N"); // Print empty space if land is null
                }
            }
//            System.out.println("");
        }
//        System.out.println("");
    }
}
