package net.htlgkr.krejo.doom;

public class Doom {
    private Entity[][] playfield;
    private static final int xLENGTH = 40;
    private static final int yLENGTH = 15;

    public static void main(String[] args){
        createPlayfield();
    }

    private static void createPlayfield() {
        for (int i = 0; i < yLENGTH; i++) {
            if (i == 0 || i == yLENGTH-1)
            for (int j = 0; j < xLENGTH; j++) {
                //TODO: außen wand befüllen
            }

        }
    }

}
