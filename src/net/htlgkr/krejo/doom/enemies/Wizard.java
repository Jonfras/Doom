package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.weapons.Weapon;

public sealed class Wizard extends Enemy permits UndeadWizard, HardWizard{
    public Wizard(double hp, Weapon weapon, double armor, char symbol) {
        super(hp, weapon, armor, symbol);
    }
}
