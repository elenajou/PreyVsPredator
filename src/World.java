public class World {
    private final int width;
    private final int height;
    Land[][] lands;

    /**
     * Initializes a world of herbivores and plants. Prints the number of residents.
     */
    public World(int w, int h) {
        width = w;
        height = h;
        createWorld();
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    /**
     * Creates a world of herbivores and plants based on probability.
     */
    public void createWorld() {
        lands = new Land[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int number = RandomGenerator.nextNumber(99);

                Land land;
                if (number >= 80) {
                    land = new Land(new Herbivore());
                } else if (number >= 60) {
                    land = new Land(new Plant());
                } else if (number >= 50) {
                    land = new Land(new Carnivore());
                } else if (number >= 45) {
                    land = new Land(new Omnivore());
                } else {
                    land = new Land(null);
                }

                lands[y][x] = land;
            }
        }
    }

    /**
     * @return world of the residents.
     */
    public Land[][] getWorld() {
        return lands;
    }

    /**
     * Each living thing will perform one run of actions.
     */
    public void runWorld() {
        // Iterate over each piece of land
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Land land = lands[y][x];
                if (land.getResident() != null) {
                    // Copy the 3x3 subgrid centered around (x, y)
                    Land[][] neighbors = new Land[3][3];

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int neighborX = x + i;
                            int neighborY = y + j;
                            // Ensure neighbor indices are within bounds of lands array
                            if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                                neighbors[i + 1][j + 1] = lands[neighborY][neighborX];
                            } else {
                                // If indices are out of bounds, assign null or handle appropriately
                                neighbors[i + 1][j + 1] = null; // Or any other default value
                            }
                        }
                    }
                    // Perform action on the resident of the current land
                    land.getResident().action(neighbors);

                }
            }
        }
        // set all residents of the land to their future resident
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Land land = lands[y][x];
                if (land != null) {
                    land.setResident();
                }
            }
        }
    }

}
