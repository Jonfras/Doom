package net.htlgkr.krejo.doom;

import net.htlgkr.krejo.doom.enemies.*;
import net.htlgkr.krejo.doom.weapons.Hammer;
import net.htlgkr.krejo.doom.weapons.Sword;

import java.util.*;

public class Doom {

    //TODO: FIGHT MOCHA DASS GEHT

    //Playfield Werte
    private static final int WIDTH = 39;
    private static final int LENGTH = 16;
    private static final int HIGHEST_INDEX = 639;

    private static final char PLAYER = '@';
    private static final List<Character> ENEMIES = List.of('D', 'E', 'H', 'U', 'R');
    private static final char SPACE = ' ';
    private static final char TREASURE = 'S';
    private static final char BONUS = 'B';
    private static final char WALL = '#';

    static GameFactory gameFactory = new GameFactory();
    private static Difficulty difficulty;

    private static String playfield;

    private static final int NUMBER_OF_ENEMIES = 5;

    private static Player player = new Player(10, new Sword("Starter-Sword", 4), 0.99D, 42);
    static {
        player.addWeaponToInventory(new Hammer("Hero-Hammer", 20));
    }

    private static final Enemy[] enemyTypes = {new Dwarf(), new Elf(), new HardWizard(), new UndeadWizard(), new Rouge()};


    private static final int N = -1 * (WIDTH + 1);
    private static final int NE = -1 * WIDTH;
    private static final int E = 1;
    private static final int SE = WIDTH + 2;
    private static final int S = WIDTH + 1;
    private static final int SW = WIDTH;
    private static final int W = -1;
    private static final int NW = -1 * (WIDTH + 2);

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


    private static final Scanner systemScanner = new Scanner(System.in);

    private static int moveCounter = 0;


    public static void main(String[] args) {
        selectDifficulty();
        createPlayfield();
        showActions();
        printStats(player);
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
                } catch (RuntimeException r) {
                    System.out.println("test");
                    throw new RuntimeException();
                }
            }

        } while (true);
    }


    private static void makeMove(MoveToPosition nextMove)
            throws RuntimeException {
        if (nextMove.isValidMove()) {
            if (nextMove.getValueOfPosition() == 'F') {
                fight();
            } else if (nextMove.getValueOfPosition() == BONUS) {
                getBonus();
            } else {
                rewritePlayfield(nextMove);
            }
        } else {
            moveEnemies();
        }
    }

    private static void fight()
            throws RuntimeException {
        System.out.println("Fighting Phase:");


//        int playerX = (int) Math.floor(player.getIndex() % WIDTH);
//        int playerY = (int) Math.floor(player.getIndex() / WIDTH);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);

            if (isEnemyAround(enemy)) {

                printStats(player);
                System.out.println();
                printStats(enemy);
                do {
                    //show Fighting Menu:

                    if (player.getWeapons().size() > 1) {
                        int index = getFightingWeapon();
                        if (index != 0) {
                            player.switchPrimaryWeapon(index);
                        }
                    }

                    enemy.takeDamage(player.getPrimaryWeapon());

                    try {
                        System.out.print("Fighting");
                        Thread.sleep(1000);
                        System.out.print(".");
                        Thread.sleep(1000);
                        System.out.print(".");
                        Thread.sleep(1000);
                        System.out.println(".");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (enemy.getHp() < 1) {
                        enemies.remove(i);
                        placeChar(SPACE, enemy.getIndex());
                        System.out.println("You won against "+ enemy +  "!");
                        System.out.println();
                        printStats(player);
                        break;

                    } else {
                        player.takeDamage(enemy.getPrimaryWeapon());
                        if (player.getHp() < 1) {
                            loose();
                        }
                    }
                    System.out.println();
                    printStats(player);
                    System.out.println();
                    printStats(enemy);
                    System.out.println();

                } while (enemy.getHp() > 0 || player.getHp() > 0);
            }
        }
    }

    private static boolean isEnemyAround(Enemy enemy) {
        int idx = player.getIndex();
        if (idx + NW == enemy.getIndex() ||
            idx + N  == enemy.getIndex() ||
            idx + NE == enemy.getIndex() ||
            idx + E  == enemy.getIndex() ||
            idx + SE == enemy.getIndex() ||
            idx + S  == enemy.getIndex() ||
            idx + SW == enemy.getIndex() ||
            idx + W  == enemy.getIndex()) {

                return true;
        }

        return false;
    }


    private static void rewritePlayfield(MoveToPosition nextMove) {

        moveChar(PLAYER, nextMove.getIndexOfPosition(), playfield.indexOf(PLAYER));

        player.setIndex(nextMove.getIndexOfPosition());

        moveEnemies();

    }

    private static MoveToPosition checkInput(String input)
            throws IllegalStateException {
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
                    for (MoveToPosition moveToPosition :
                            fieldsAroundPlayer) {
                        if (ENEMIES.contains(moveToPosition.getValueOfPosition())) {
                            yield new MoveToPosition('F', playfield.indexOf(PLAYER));
                        }
                    }
                    yield new MoveToPosition('0', playfield.indexOf((PLAYER)));
                }

                default -> new MoveToPosition('0', playfield.indexOf((PLAYER)));
            };

            if (nextPosition.getValueOfPosition() == 'F' || nextPosition.getValueOfPosition() == SPACE || nextPosition.getValueOfPosition() == BONUS) {
                nextPosition.setValidMove(true);
                return nextPosition;
            } else if (nextPosition.getValueOfPosition() == TREASURE) {
                win();
            } else {
                nextPosition.setValidMove(false);
                return nextPosition;
            }

        } else {
            throw new IllegalStateException();
        }
        return null;
    }


    private static void getBonus() {
        System.out.println("Bonus Chest contained: ");
        player = gameFactory.getBonus(player);
        printStats(player);
        //TODO: ondane chest irgendwo platzieren nachn öffnen
        placeChar(SPACE, playfield.indexOf(BONUS));
    }

    private static void printStats(Entity entity) {
        if (entity instanceof Player) {
            System.out.println("----------Your stats----------");
        } else {
            System.out.println("----------Enemy stats----------");
        }
        System.out.println("HP: " + entity.getHp());
        System.out.println("Weapon: " + entity.getPrimaryWeapon());
        System.out.println("Damage: " + entity.getPrimaryWeapon().getDamage());
        System.out.println("Armor: " + entity.getArmor());
    }

    private static int getFightingWeapon() {
        int numberOfWeapon;
        System.out.println("What weapon do you want to use? Enter the number of the Weapon:");

        for (int j = 0; j < player.getWeapons().size(); j++) {
            System.out.println(j + "..." + player.getWeapons().get(j).getDescription());
        }
        do {
            try {
                numberOfWeapon = Integer.parseInt(systemScanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number");
                numberOfWeapon = -1;
            }

        } while (numberOfWeapon < 0 || numberOfWeapon >= player.getWeapons().size());

        return numberOfWeapon;

    }


    private static void win() {
        System.out.println("Congrats!!! You got the treasure!");
        System.exit(1234);
    }

    private static void loose() {
        System.out.println("You lost. Play better next Time");
        System.exit(-1234);
    }


    private static void moveEnemies() {
        char[] playfieldArr = playfield.toCharArray();
        Random r = new Random();
        int randomInt;
        Enemy enemy;

        for (int i = 0; i < enemies.size(); i++) {

            randomInt = r.nextInt(7);
            enemy = enemies.get(i);

            int direction = values.get(randomInt);

            if (enemy.getSymbol() == 'R'){
                direction *= 2;
                if (enemy.getIndex() + direction <= 0 || enemy.getIndex() + direction > HIGHEST_INDEX){
                    System.out.println("schlecht");
                }
            }

            MoveToPosition nextMove = new MoveToPosition(playfield.charAt(enemy.getIndex() + direction), enemy.getIndex() + direction);


            if (nextMove.isValidMoveOnPlayfield(playfieldArr, HIGHEST_INDEX)){
                moveChar(enemy.getSymbol(), nextMove.getIndexOfPosition(),enemy.getIndex());
                enemy.setIndex(nextMove.getIndexOfPosition());
                enemies.set(i, enemy);
            } else {
                i--;
            }
        }
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

    private static void spawnEntities() {
        Random r = new Random();
        while (true) {
            int bonusIndex = r.nextInt(HIGHEST_INDEX);
            if (playfield.charAt(bonusIndex) == SPACE) {
                placeChar(BONUS, bonusIndex);
                break;
            }
        }

        for (int i = 0; i < NUMBER_OF_ENEMIES; i++) {
            Enemy e = enemyTypes[i];
            int index = r.nextInt(HIGHEST_INDEX);
            if (playfield.charAt(index) == SPACE) {
                placeChar(e.getSymbol(), index);
                e.setIndex(index);
                e.setHp(difficulty.hp());
                e.setArmor(difficulty.armor());
                e.setPrimaryWeapon(gameFactory.giveWeapon(e));
                enemies.add(e);
            } else {
                i--;
            }
        }
    }


    private static void createPlayfield() {
        //TODO: ECKEN AUSFÜLLEN DAMIT MAN NICHT DURCH ECKEN GEHEN KANN
        playfield = """
                #######################################
                #               @                     #
                #      B                              #
                #                                     #
                #                                     #
                #                                     #
                #                                     #
                #                                     #
                #                                     #
                #                                     #
                #                                     #
                #                                     #
                #                                     #
                #                                     #
                #                          S          #
                #######################################
                """;;
//        enemies.add(new Dwarf(20.0, new Hammer("gummi hammer", 1), 1));
//        enemies.get(0).setIndex(playfield.indexOf('D'));
        spawnEntities();
    }

    private static void selectDifficulty() {

        do {
            System.out.println("Select a difficulty between 1 and 3");
            try {
                int dif = Integer.parseInt(systemScanner.next());
                difficulty = gameFactory.getDifficulty(dif);
            } catch (NumberFormatException e) {
                System.err.println("Enter a valid number");
                difficulty = null;
            }
        } while (difficulty == null);
    }

    private static void moveChar(char symbol, int newIndex, int oldIndex) {
        char[] playfieldArr = playfield.toCharArray();

        playfieldArr[newIndex] = symbol;
        playfieldArr[oldIndex] = SPACE;
        playfield = String.valueOf(playfieldArr);
    }

    private static void placeChar(char symbol, int newIndex) {
        char[] playfieldArr = playfield.toCharArray();

        playfieldArr[newIndex] = symbol;
        playfield = String.valueOf(playfieldArr);
    }

}
