package net.htlgkr.krejo.doom;

import net.htlgkr.krejo.doom.enemies.Difficulty;
import net.htlgkr.krejo.doom.enemies.Enemy;
import net.htlgkr.krejo.doom.weapons.*;

import java.util.Random;

public class GameFactory {

    //TODO: moch des beim Player a so;
    private static final String BASIC_PREFIX = "Starter-";
    private static final String ADVANCED_PREFIX = "Hero-";
    private static final String EXPERT_PREFIX = "Holy-";

    Difficulty difficulty;
    public Difficulty getDifficulty(int dif){
        difficulty =  switch (dif){
            case 1 -> new Difficulty(10, 3, 1);

            case 2 -> new Difficulty(20, 5, 0.5D);

            case 3 -> new Difficulty(40, 15, 0.3);

            default -> null;
        };
        return difficulty;
    }

    public Weapon giveWeapon(Enemy enemy){
        return switch (enemy.getSymbol()){
            case 'D' -> {
                int random = (int) (Math.random() * 2);
                if (random < 2){
                    yield new Hammer(getPrefix() + "Hammer", difficulty.damage());
                } else {
                    yield new Sword(getPrefix() + "Sword", difficulty.damage());
                }
            }
            case 'E' -> new Elfbar(getPrefix() + "Elfbar", 1);

            case 'H' -> new Wand(getPrefix() + "Wand", difficulty.damage() + 2);

            case 'R' -> new Claw("Claw" , difficulty.damage());

            case 'U' -> new Wand(getPrefix() + "Bone-Wand", difficulty.damage());

            default -> throw new IllegalStateException("Unexpected enemy: " + enemy.getSymbol() + " at index " + enemy.getIndex());
        };
    }

    private String getPrefix() {
       return switch (difficulty.hp()){
            case 10 -> BASIC_PREFIX;

            case 20 -> ADVANCED_PREFIX;

            case 40 -> EXPERT_PREFIX;

           default -> throw new IllegalStateException("Unexpected value: " + difficulty.hp());
       };
    }
}
