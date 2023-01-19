package net.htlgkr.krejo.doom;

public class Player extends Entity{

    public static final char SYMBOL = '@';

    private int index;

    public Player(double hp, int damage, double armor, int index) {
        super(hp, damage, armor);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
