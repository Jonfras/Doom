package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.weapons.*;

public sealed abstract class Entity permits Enemy, Player {
    protected double hp;
    protected Weapon weapon;
    protected double armor;
    protected char symbol;

    public Entity(double hp, Weapon primaryWeapon, double armor, char symbol) {
        this.hp = hp;
        this.weapon = primaryWeapon;
        this.armor = armor;
        this.symbol = symbol;
    }

    public Entity(){
        hp = 1;
        weapon = new Weapon("dummy_weapon", 1);
        armor = 1;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    //TODO: IMPLEMENT THE TAKE DAMAGE METHOD

    public Weapon getPrimaryWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public void takeDamage(Weapon weapon) {
        if (weapon instanceof Hammer){
            this.hp = hp - weapon.getDamage() * armor;
            armor = 1;

        } else if (weapon instanceof Dagger) {
            this.hp = hp - (((Dagger) weapon).getTurnsOfAttack() * weapon.getDamage()) * armor;

        } else if (this.weapon instanceof Sword && weapon instanceof Sword) {
            this.hp = hp - ((weapon.getDamage() * armor) / 2);

        } else if (weapon instanceof Wand) {
            this.hp = hp - weapon.getDamage();

        } else {
            this.hp = hp - weapon.getDamage() * armor;
        }
    }



}
