package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.weapons.Hammer;
import net.htlgkr.krejo.doom.weapons.Sword;
import net.htlgkr.krejo.doom.weapons.Weapon;

public final class Dwarf extends Enemy {

    public Dwarf(double hp, Weapon weapon, double armor) {
        super(hp, weapon, armor, 'D');
    }


    public Dwarf() {
        super('D');
    }

    @Override
    public String toString() {
        return "Dwarf";
    }
}
