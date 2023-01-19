package net.htlgkr.krejo.doom;

public class Enemy extends Entity{

    public static final char SYMBOL = '#';

    private int index = -1;

    public Enemy(double hp, int damage, double armor) {
        super(hp, damage, armor);
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
