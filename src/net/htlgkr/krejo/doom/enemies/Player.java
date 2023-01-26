package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.enemies.Entity;
import net.htlgkr.krejo.doom.weapons.Sword;
import net.htlgkr.krejo.doom.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public final class Player extends Entity {

    public static final char SYMBOL = '@';
    private List<Weapon> weapons = new ArrayList<>();


    private int index;

    public Player(double hp, Weapon primaryWeapon, double armor, int index) {
        super(hp, primaryWeapon, armor, SYMBOL);
        weapons.add(primaryWeapon);
        this.index = index;
    }

    public Player() {

    }

    public void addWeaponToInventory(Weapon weapon){
        weapons.add(weapon);
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void switchPrimaryWeapon(int index){
        Weapon weapon = weapons.get(0);
        weapons.set(0, weapons.get(index));
        weapons.add(weapon);
    }

    @Override
    public void takeDamage(Weapon weapon) {
        if (this.weapon instanceof Sword && weapon instanceof Sword) {
            this.hp = hp - ((weapon.getDamage() * armor) / 2);
        } else {
            super.takeDamage(weapon);
        }
    }
}

