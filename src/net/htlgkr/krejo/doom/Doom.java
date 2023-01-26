package net.htlgkr.krejo.doom;

import net.htlgkr.krejo.doom.enemies.*;
import net.htlgkr.krejo.doom.weapons.Sword;

import java.util.*;
import java.util.stream.Collectors;

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

    private static Player player = new Player(1, new Sword("Starter-Sword", 4), 0.5D, 42);

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
        System.out.println(playfield);
        do {
            System.out.println("Enter ? if you want to see the possible moves again.");
            String nextInput = systemScanner.nextLine();
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
                    System.err.println("No one wants to fight");
                }
            }

        } while (true);
    }



    private static void makeMove(MoveToPosition nextMove) throws RuntimeException {
        if (nextMove.isValidMove()) {
            if (nextMove.getValueOfPosition() == 'F') {
                fight();
            }else if (nextMove.getValueOfPosition() == BONUS){
                getBonus();
            } else {
                rewritePlayfield(nextMove);
            }
        } else {
            moveEnemies(playfield.toCharArray());
        }
    }

    private static void fight() throws RuntimeException {
        System.out.println("Fighting Phase:");
        printStats(player);
        int playerX = (int) Math.floor(player.getIndex() % WIDTH);
        int playerY = (int) Math.floor(player.getIndex() / WIDTH);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.getIndex() / 40 - playerY < 2 && enemy.getIndex() / 40 - playerY > -2 ||
                    enemy.getIndex() % 40 - playerX < 2 && enemy.getIndex() % 40 - playerX > -2) {
                printStats(enemy);
                System.out.println("What weapon do you want to use?");

                enemy.setHp(player.getWeapon().getDamage());
                if (enemy.getHp() < 1) {
                    enemies.remove(i);
                    char[] playfieldArr = playfield.toCharArray();
                    playfieldArr[enemy.getIndex()] = SPACE;
                    playfield = String.valueOf(playfieldArr);
                } else {
                    player.setHp(enemy.getWeapon().getDamage());
                    if (player.getHp() < 1) {
                        loose();
                    }
                }
            }
        }
    }

    private static void rewritePlayfield(MoveToPosition nextMove) {

        moveChar(PLAYER, nextMove.getIndexOfPosition(), playfield.indexOf(PLAYER));

        player.setIndex(nextMove.getIndexOfPosition());

        moveEnemies(playfield.toCharArray());

    }

    private static MoveToPosition checkInput(String input) throws IllegalStateException{
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
                    System.out.println("fieldsAroundPlayer that are not space or wall:");
                    for (MoveToPosition moveToPosition :
                            fieldsAroundPlayer) {
                        if (ENEMIES.contains(moveToPosition.getValueOfPosition())){
                            yield new MoveToPosition('F', playfield.indexOf(PLAYER));
                        }
                    }
                        yield new MoveToPosition('0', playfield.indexOf((PLAYER)));
                }

                default -> new MoveToPosition('0', playfield.indexOf((PLAYER)));
            };

            if (nextPosition.getValueOfPosition() == 'F' || nextPosition.getValueOfPosition() == SPACE) {
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
    }

    private static void printStats(Entity entity) {
        if (entity instanceof Player){
            System.out.println("----------Your stats----------");
        } else {
            System.out.println("----------Enemy stats----------");
        }
        System.out.println("HP: " + entity.getHp());
        System.out.println("Weapon: " + entity.getWeapon());
        System.out.println("Damage: " + entity.getWeapon().getDamage());
        System.out.println("Armor: " + entity.getArmor());
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
            int randomInt = r.nextInt(7);
            Enemy enemy = enemies.get(i);
            int nextIndex = enemy.getIndex() + values.get(randomInt);
            if (enemy.getSymbol() == 'R'){
                nextIndex = enemy.getIndex() + (values.get(randomInt)*2);
            }
            if (playfieldArr[nextIndex] == SPACE) {
                moveChar(enemy.getSymbol(), nextIndex, enemy.getIndex());
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

    private static void spawnEntities() {
        Random r = new Random();
        while (true) {
            int bonusIndex = r.nextInt(HIGHEST_INDEX);
            if (playfield.charAt(bonusIndex) == SPACE) {
                placeChar(BONUS, bonusIndex);
                break;
            }
        }

        for (int i = 0; i < enemyTypes.length; i++) {
            Enemy e = enemyTypes[i];
            int index = r.nextInt(HIGHEST_INDEX);
            if (playfield.charAt(index) == SPACE){
                placeChar(e.getSymbol(), index);
                e.setIndex(index);
                e.setHp(difficulty.hp());
                e.setArmor(difficulty.armor());
                e.setWeapon(gameFactory.giveWeapon(e));
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
                # @                                   #
                # #    #    #    #    #    #    #    ##
                # ########  #### #### #### #### #######
                #      #    #    #    #    #    #    ##
                ###### #    ######    ######    #### ##
                # #    #    #    ## ###    #    #    ##
                # ############## #### #### # ####### ##
                #                                     #
                # ######### #### #### ############## ##
                # #         #    #    #         #    ##
                # ###  #### ###  #### #         ## ####
                # #    #              #         #    ##
                # #### #    #### #########      #######
                # #                        S          #
                #######################################
                """;
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
