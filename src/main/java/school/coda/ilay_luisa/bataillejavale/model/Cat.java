package school.coda.ilay_luisa.bataillejavale.model;


//
public abstract class Cat {
    private final String name;
    private final int size;
    private int hits;
    private boolean isAsleep;

    public Cat(String name, int size) {
        this.name = name;
        this.size = size;
        this.hits = 0;
        this.isAsleep = false;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getHits() {
        return hits;
    }

    public boolean isAsleep() {
        return isAsleep;
    }

    public void takeHit() {
        if (isAsleep) {
            this.hits++;
        }
        if (this.hits >= size) {
            this.isAsleep = false;
            System.out.println("Coulé!");
        } else {
            System.out.println("a miolé");
        }

    }
}
