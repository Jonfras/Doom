package net.htlgkr.krejo.doom.weapons;

public final class Bow extends Weapon {
    private int range;
    public Bow(String description, int damage, int range) {
        super(description, damage);
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
