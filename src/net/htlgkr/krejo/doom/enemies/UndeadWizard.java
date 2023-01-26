package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.weapons.Weapon;

public final class UndeadWizard extends Wizard {
    public UndeadWizard(double hp, Weapon weapon, double armor) {
        super(hp, weapon, armor, 'U');
    }

    public UndeadWizard() {
        super('U');
    }

    @Override
    public String toString() {
        return "Undead Wizard";
    }
}
