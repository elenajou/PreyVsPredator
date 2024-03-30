public class Land {
    LivingThing resident;
    LivingThing futureResident;
    boolean isDead = false;
    public Land(LivingThing resident) {
        this.resident = resident;
        this.futureResident = resident;
    }
    public LivingThing getResident() {
        return resident;
    }
    public void setResident() {
        this.resident = this.futureResident;
//        this.futureResident = null;
    }
    public LivingThing getFutureResident() {
        return futureResident;
    }
    public void setFutureResident(LivingThing futureResident) {
        this.futureResident = futureResident;
    }
    public void setFutureResident() {
        this.futureResident = null;
    }
    public static int countEmptyLands(Land[][] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                Land currentLand = array[i][j];
                if (currentLand != null) { // Check if the land is not null
                    if (currentLand.getResident() == null)
                        count++;
                }
            }
        }
        return count;
    }

    public static int countMovableLands(Land[][] array) {
        int countNotMovable = 0;
        int countNotNull = 0;
        LivingThing original = array[1][1].getFutureResident();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                Land currentLand = array[i][j];
                if (currentLand != null) { // Check if the land is not null
                    if (currentLand.getFutureResident() != null
                            && currentLand.getFutureResident().getClass().equals(original.getClass()))
                        countNotMovable++;
                    countNotNull++;
                }
            }
        }
        return (countNotNull - countNotMovable);
    }
}
