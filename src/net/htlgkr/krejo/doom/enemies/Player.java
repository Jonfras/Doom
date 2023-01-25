package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.enemies.Entity;
import net.htlgkr.krejo.doom.weapons.Weapon;

public final class Player extends Entity {

    public static final char SYMBOL = '@';

    private int index;

    public Player(double hp, Weapon weapon, double armor, int index) {
        super(hp, weapon, armor, SYMBOL);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
