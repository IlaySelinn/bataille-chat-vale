package school.coda.ilay_luisa.bataillejavale.model;


import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;

//
public class Cat
{
    private final CatType name;
    private final int size;

    private int hits;
    private boolean isAsleep;


    public Cat(CatType name, int size)
    {
        this.name = name;
        this.size = size;
        this.hits = 0;
        this.isAsleep = false;
    }
    public CatType getName() {
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

    public AttackResult takeHit() {
        hits++;
        boolean sunk = hits >=size;

        if (sunk) {
            isAsleep = true;
        }

             return new AttackResult(true, sunk, name);
        /// si le chat est déjà endormi, on passe cette étape
    }
}
