package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.weapons.Weapon;

public final class Rouge extends Enemy {

    public Rouge(double hp, Weapon weapon, double armor) {
        super(hp, weapon, armor, 'B');
    }
}