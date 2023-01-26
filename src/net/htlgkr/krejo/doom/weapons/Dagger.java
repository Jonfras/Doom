package net.htlgkr.krejo.doom.weapons;

public final class Dagger extends Weapon {

    public int getTurnsOfAttack() {
        return turnsOfAttack;
    }

    public void setTurnsOfAttack(int turnsOfAttack) {
        this.turnsOfAttack = turnsOfAttack;
    }

    private int turnsOfAttack;
    public Dagger(String description, int damage, int turnsOfAttack) {
        super(description, damage);
        this.turnsOfAttack = turnsOfAttack;
    }
}
