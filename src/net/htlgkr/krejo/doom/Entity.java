package net.htlgkr.krejo.doom;

public abstract class Entity {
    private double hp;
    private int damage;
    private double armor;

    public Entity(double hp, int damage, double armor) {
        this.hp = hp;
        this.damage = damage;
        this.armor = armor;
    }

    public Entity(){
        hp = 100;
        damage = 100;
        armor = 1;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(int takenDamage) {
        this.hp = this.hp - takenDamage * armor;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

}
