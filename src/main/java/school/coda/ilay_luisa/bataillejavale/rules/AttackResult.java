package school.coda.ilay_luisa.bataillejavale.rules;

import school.coda.ilay_luisa.bataillejavale.model.CatType;

public record AttackResult (
    boolean hits,
    boolean sunk,
    CatType sunkCat) {

    public String message()
    {
        /// si le chat est déjà endormie, on passe cette étape
        if (sunk)
        {
            return (sunkCat.name() + " est coulé! (Zzz...)");
        }
        if (hits) {
            return (" un chat a miaulé  (Toujours vivant!!)");
        }
        return " personne a été touché essaye encore !! ";

        }

    public boolean isHit() {
        return  hits;
    }
}
