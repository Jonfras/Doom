package net.htlgkr.krejo.doom.weapons;

import net.htlgkr.krejo.doom.weapons.*;

public sealed class Weapon permits Sword, Bow, Dagger, Wand, Hammer, Claw, Elfbar{
    protected String description;
    protected int damage;

    public Weapon(String description, int damage) {
        this.description = description;
        this.damage = damage;
    }

    public String getDescription() {
        return description;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
