package net.htlgkr.krejo.doom.enemies;

import net.htlgkr.krejo.doom.weapons.Weapon;

public sealed abstract class Entity permits Enemy, Player {
    protected double hp;
    protected Weapon weapon;
    protected double armor;
    protected char symbol;

    public Entity(double hp, Weapon weapon, double armor, char symbol) {
        this.hp = hp;
        this.weapon = weapon;
        this.armor = armor;
        this.symbol = symbol;
    }

    public Entity(){
        hp = 10;
        weapon = new Weapon("dummy_weapon", 1);
        armor = 1;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(int takenDamage) {
        this.hp = this.hp - takenDamage * armor;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

}
