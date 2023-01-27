package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.MoveToPosition;
import net.htlgkr.krejo.doom.enemies.Entity;
import net.htlgkr.krejo.doom.enemies.*;
import net.htlgkr.krejo.doom.weapons.Weapon;

public sealed abstract class Enemy extends Entity  permits Rouge, Wizard, Dwarf, Elf {

    private char symbol;



    private int index = -1;

    public Enemy(double hp, Weapon weapon, double armor, char symbol) {
        super(hp, weapon, armor, symbol);
    }

    public Enemy(char symbol){
        this.symbol = symbol;
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

    public char getSymbol() {
        return symbol;
    }


}
