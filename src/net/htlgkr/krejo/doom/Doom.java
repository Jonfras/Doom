package net.htlgkr.krejo.doom;

import net.htlgkr.krejo.doom.enemies.Dwarf;
import net.htlgkr.krejo.doom.enemies.Enemy;
import net.htlgkr.krejo.doom.enemies.Player;
import net.htlgkr.krejo.doom.weapons.Sword;

import java.util.*;

public class Doom {

    //Schwierigkeitsgrad zurzeit: unmöglich
    private static String playfield;

    private static final int NUMBER_OF_ENEMIES = 5;

    private static Player player = new Player(1, new Sword("Starter-Sword", 4), 0.5D, 42);

    private static final List<Enemy> enemyList = new ArrayList<>();

    private static final int WIDTH = 40;
    private static final int LENGTH = 17;

    private static final int N = -41;
    private static final int NE = -40;
    private static final int E = 1;
    private static final int SE = 42;
    private static final int S = 41;
    private static final int SW = 40;
    private static final int W = -1;
    private static final int NW = -42;

    private static final List<Integer> values = new ArrayList<>(List.of(NW, N, NE, E, SE, S, SW, W));

    private static final String NORTHWEST = "NW";
    private static final String NORTH = "N";
    private static final String NORTHEAST = "NE";
    private static final String EAST = "E";
    private static final String SOUTHEAST = "SE";
    private static final String SOUTH = "S";
    private static final String SOUTHWEST = "SW";
    private static final String WEST = "W";

    private static final String FIGHT = "F";

    private static final List<String> actions = new ArrayList<>(List.of(NORTHWEST, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, FIGHT));

    private static List<Enemy> enemies = new ArrayList<>(NUMBER_OF_ENEMIES);

    private static final char PLAYER = '@';
    private static final char ENEMY = 'X';
    private static final String WALL = "#";
    private static final char SPACE = ' ';
    private static final char TREASURE = 'S';
    private static final String BONUS = "B";

    private static final Scanner systemScanner = new Scanner(System.in);

    private static int moveCounter = 0;


    public static void main(String[] args) {
        createPlayfield();
        showActions();
        System.out.println(playfield);
        do {
            System.out.println("Enter ? if you want to see the possible moves again.");
            String nextInput = systemScanner.next();
            if (nextInput.equals("?")) {
                showActions();
            } else {

                try {
                    MoveToPosition nextMove = checkInput(nextInput);
                    makeMove(nextMove);
                    System.out.println(playfield);
                    moveCounter++;
                } catch (IllegalStateException e) {
                    System.err.println("Please enter a valid move");
                } catch (RuntimeException r){
                    System.err.println("No one wants to fight");
                }
            }

        } while (true);
    }


    private static void makeMove(MoveToPosition nextMove) throws RuntimeException {
        if (nextMove.isValidMove()) {
            if (nextMove.getValueOfPosition() == 'F') {
                fight();
            } else {
                rewritePlayfield(nextMove);
            }
        }
    }

    private static void fight() throws RuntimeException {
        int playerX = (int) Math.floor(player.getIndex() % WIDTH);
        int playerY = (int) Math.floor(player.getIndex() / WIDTH);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.getIndex() / 40 - playerY < 2 && enemy.getIndex() / 40 - playerY > -2 ||
                    enemy.getIndex() % 40 - playerX < 2 && enemy.getIndex() % 40 - playerX > -2){
                enemy.setHp(player.getWeapon().getDamage());
                if (enemy.getHp() < 1){
                    enemies.remove(i);
                    char[] playfieldArr = playfield.toCharArray();
                    playfieldArr[enemy.getIndex()] = SPACE;
                    playfield = String.valueOf(playfieldArr);
                } else {
                    player.setHp(enemy.getWeapon().getDamage());
                    if (player.getHp() < 1){
                        loose();
                    }
                }
            }
        }
    }




    private static void rewritePlayfield(MoveToPosition nextMove) {
        int playerIndex = playfield.indexOf(PLAYER);
        char[] playfieldArr = playfield.toCharArray();

        playfieldArr[nextMove.getIndexOfPosition()] = PLAYER;
        playfieldArr[playerIndex] = SPACE;
        player.setIndex(nextMove.getIndexOfPosition());

        playfieldArr = Arrays.copyOf(moveEnemies(playfieldArr), WIDTH*LENGTH);

        playfield = String.valueOf(playfieldArr);

    }

    private static MoveToPosition checkInput(String input) throws IllegalStateException {
//TODO: Mocha dass ma ned in enemies einegeh ko
        if (actions.contains(input.toUpperCase())) {

            ArrayList<MoveToPosition> fieldsAroundPlayer = new ArrayList<>(List.of(
                    new MoveToPosition(playfield.charAt(playfield.indexOf(PLAYER) + (NW)), playfield.indexOf(PLAYER) + NW),
                    new MoveToPosition(playfield.charAt(playfield.indexOf(PLAYER) + (N)), playfield.indexOf(PLAYER) + N),
                    new MoveToPosition(playfield.charAt(playfield.indexOf(PLAYER) + (NE)), playfield.indexOf(PLAYER) + NE),
                    new MoveToPosition(playfield.charAt(playfield.indexOf(PLAYER) + (E)), playfield.indexOf(PLAYER) + E),
                    new MoveToPosition(playfield.charAt(playfield.indexOf(PLAYER) + (SE)), playfield.indexOf(PLAYER) + SE),
                    new MoveToPosition(playfield.charAt(playfield.indexOf(PLAYER) + (S)), playfield.indexOf(PLAYER) + S),
                    new MoveToPosition(playfield.charAt(playfield.indexOf(PLAYER) + (SW)), playfield.indexOf(PLAYER) + SW),
                    new MoveToPosition(playfield.charAt(playfield.indexOf(PLAYER) + (W)), playfield.indexOf(PLAYER) + W)
            ));

            MoveToPosition nextPosition = switch (actions.get(actions.indexOf(input.toUpperCase()))) {
                case NORTHWEST -> fieldsAroundPlayer.get(0);

                case NORTH -> fieldsAroundPlayer.get(1);

                case NORTHEAST -> fieldsAroundPlayer.get(2);

                case EAST -> fieldsAroundPlayer.get(3);

                case SOUTHEAST -> fieldsAroundPlayer.get(4);

                case SOUTH -> fieldsAroundPlayer.get(5);

                case SOUTHWEST -> fieldsAroundPlayer.get(6);

                case WEST -> fieldsAroundPlayer.get(7);

                case FIGHT -> {
                    if (fieldsAroundPlayer.stream().anyMatch(x -> ENEMY == x.getValueOfPosition())) {
                        yield new MoveToPosition('F', playfield.indexOf(PLAYER));
                    } else {
                        yield new MoveToPosition('0', playfield.indexOf((PLAYER)));
                    }
                }

                default -> new MoveToPosition('0', playfield.indexOf((PLAYER)));
            };

            if (nextPosition.getValueOfPosition() == 'F' || nextPosition.getValueOfPosition() == SPACE) {
                nextPosition.setValidMove(true);
                return nextPosition;
            }
            else if (nextPosition.getValueOfPosition() == TREASURE){
                win();
            }
                else {
                nextPosition.setValidMove(false);
                return nextPosition;
            }

        } else {
            throw new IllegalStateException();
        }
        return null;
    }

    private static void win() {
        System.out.println("Congrats!!! You got the treasure!");
        System.exit(1234);
    }

    private static void loose() {
        System.out.println("You lost. Play better next Time");
        System.exit(-1234);
    }


    private static char[] moveEnemies(char[] playfieldArr) {
        for (int i = 0; i < enemies.size(); i++) {
            Random r = new Random();
            int randomInt = r.nextInt(8);
            int nextIndex = enemies.get(i).getIndex() + values.get(randomInt);
            if (playfieldArr[nextIndex] == SPACE) {
                playfieldArr[nextIndex] = ENEMY;
                playfieldArr[enemies.get(i).getIndex()] = SPACE;
                enemies.get(i).setIndex(nextIndex);
            } else {
                i--;
            }
        }
        return playfieldArr;
    }


    private static void showActions() {
        System.out.println("Enter one of the following moves:");

        System.out.println("NW...One Field north-west");
        System.out.println("N....One Field north");
        System.out.println("NE...One Field north-east");
        System.out.println("E....One Field east");
        System.out.println("SE...One Field south-east");
        System.out.println("S....One Field south");
        System.out.println("SW...One Field south-west");
        System.out.println("W....One Field west");
        System.out.println("F....Fight");
    }

    private static void spawnEnemies() {
        Random r = new Random();
        char[] playfieldArr = playfield.toCharArray();
        for (int i = 0; i < NUMBER_OF_ENEMIES; i++) {
            int random = r.nextInt(playfield.length());
            if (playfieldArr[random] == SPACE) {
                enemies.add(new Enemy(random));
                playfieldArr[random] = ENEMY;
            } else {
                i--;
            }
        }
        playfield = String.valueOf(playfieldArr);
    }


    private static void createPlayfield() {
        //TODO: ECKEN AUSFÜLLEN DAMIT MAN NICHT DURCH ECKEN GEHEN KANN
        playfield = """
                ########################################
                # @                                    #
                # #    #    #    #    #    #    #    # #
                # ########  #### #### #### #### ########
                #      #    #    #    #    #    #    # #
                ###### #    ######    ######    #### # #
                # #    #    #    ## ###    #    #    # #
                # ############## #### #### # ####### # #
                #                                      #
                # #### #### #### #### ############## # #
                # #    #    #    #    #         #    # #
                # ###  #### ###  #### #  B      ###### #
                # #    #    #    #    #         #    # #
                # #### #    #### #    ####      ###### #
                # #    #         #    #    S           #
                ########################################
                """;
        spawnEnemies();
    }
}
