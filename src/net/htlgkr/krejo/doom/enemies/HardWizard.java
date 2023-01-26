package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.weapons.Weapon;

public non-sealed class HardWizard extends Wizard {
    public HardWizard(double hp, Weapon weapon, double armor) {
        super(hp, weapon, armor, 'H');
    }

    public HardWizard() {
        super('H');
    }

    @Override
    public String toString() {
        return "Hard Wizard";
    }
}
