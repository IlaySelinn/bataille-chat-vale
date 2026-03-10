package school.coda.ilay_luisa.bataillejavale.model;

public enum CatType {
    TOM(5),
    PUFI(4),
    MISTACHE(3),
    UKULELE(3),
    GÜMÜŞ(2);

    private final int size;
    CatType(int size)
        {
        this.size = size;
        }
        public int getSize()
        {
        return size;
        }
}
