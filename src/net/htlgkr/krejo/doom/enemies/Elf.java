package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.weapons.Hammer;
import net.htlgkr.krejo.doom.weapons.Weapon;

public final class Elf extends Enemy {
    public Elf(double hp, Weapon weapon, double armor) {
        super(hp, weapon, armor, 'D');
    }

    public Elf() {
        super('E');
    }

    @Override
    public String toString() {
        return "Elf";
    }
}
