package school.coda.ilay_luisa.bataillejavale.model;


//
public abstract class Cat
{
    private final String name;
    private final int size;
    private int hits;
    private boolean isAsleep;
    private int HP;

    public Cat(String name, int size)
    {
        this.name = name;
        this.size = size;
        this.hits = 0;
        this.isAsleep = false;
        this.HP = 0;
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

    public void takeHit()
    {
        if (isAsleep) //
        {
            return;
        }
        this.hits++;

        if (this.hits >= size)
        {
            this.isAsleep = true; // pour couler le chat!
            System.out.println("est coulé! (Zzz...)");
        }
    }
}
