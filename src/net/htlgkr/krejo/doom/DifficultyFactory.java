package net.htlgkr.krejo.doom;

import net.htlgkr.krejo.doom.enemies.Difficulty;

public class DifficultyFactory {

    public Difficulty getDifficulty(int dif){
        return switch (dif){
            case 1 -> new Difficulty(10, 3, 1);

            case 2 -> new Difficulty(20, 5, 0.5D);

            case 3 -> new Difficulty(40, 15, 0.3);

            default -> null;
        };
    }
}
