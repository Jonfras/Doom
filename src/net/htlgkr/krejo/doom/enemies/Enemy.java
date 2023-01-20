package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.Entity;
import net.htlgkr.krejo.doom.enemies.Dwarf;
import net.htlgkr.krejo.doom.enemies.Elf;
import net.htlgkr.krejo.doom.enemies.Rouge;
import net.htlgkr.krejo.doom.enemies.Wizard;
import net.htlgkr.krejo.doom.weapons.Weapon;

public sealed class Enemy extends Entity permits Rouge, Wizard, Dwarf, Elf {



    private int index = -1;

    public Enemy(double hp, Weapon weapon, double armor, char symbol) {
        super(hp, weapon, armor, symbol);
    }

    public Enemy(int index){
        super();
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
