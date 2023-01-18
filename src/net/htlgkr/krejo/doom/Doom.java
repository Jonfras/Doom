package net.htlgkr.krejo.doom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Doom {
    private static String playfield;
    private static final int xLENGTH = 40;
    private static final int yLENGTH = 17;

    private static final int N = -41;
    private static final int NE = -40;
    private static final int E = 1;
    private static final int SE = 42;
    private static final int S = 41;
    private static final int SW = 40;
    private static final int W = -1;
    private static final int NW = -42;

    private static final String NORTHWEST = "NW";
    private static final String NORTH = "N";
    private static final String NORTHEAST = "NE";
    private static final String EAST = "E";
    private static final String SOUTHEAST = "SE";
    private static final String SOUTH = "S";
    private static final String SOUTHWEST = "SW";
    private static final String WEST = "W";

    private static final String FIGHT = "F";

    private static final List<String> actions = new ArrayList<>(List.of(NORTHWEST, NORTH, EAST, SOUTH, WEST, FIGHT));


    private static final String PLAYER = "@";
    private static final String ENEMY = "X";
    private static final String TREASURE = "S";
    private static final String BONUS = "B";

    private static final Scanner systemScanner = new Scanner(System.in);


    public static void main(String[] args) {
        createPlayfield();
        do {
            System.out.println(playfield);
            String input = getInput();


        } while (playfield.indexOf("@") == playfield.indexOf("S"));
    }


    private static String getInput() {
        String input;
        do {
            showActions();
            input = systemScanner.next();
        } while (!checkInput(input));

        return input;
    }

    private static boolean checkInput(String input) {

        if (actions.contains(input.trim())) {

            ArrayList<char> fieldsAroundPlayer = new ArrayList<char>(List.of(
                    playfield.charAt(playfield.indexOf(PLAYER) + (NW)),
                    playfield.charAt(playfield.indexOf(PLAYER) + (N)),
                    playfield.charAt(playfield.indexOf(PLAYER) + (NE)),
                    playfield.charAt(playfield.indexOf(PLAYER) + (E)),
                    playfield.charAt(playfield.indexOf(PLAYER) + (SE)),
                    playfield.charAt(playfield.indexOf(PLAYER) + (S)),
                    playfield.charAt(playfield.indexOf(PLAYER) + (SW)),
                    playfield.charAt(playfield.indexOf(PLAYER) + (W))
            ));

            char charAt = switch (actions.get(actions.indexOf(input))) {
                case NORTHWEST -> fieldsAroundPlayer.get(0);

                case NORTH -> fieldsAroundPlayer.get(1);

                case NORTHEAST -> fieldsAroundPlayer.get(2);

                case EAST -> fieldsAroundPlayer.get(3);

                case SOUTHEAST -> fieldsAroundPlayer.get(4);

                case SOUTH -> fieldsAroundPlayer.get(5);

                case SOUTHWEST -> fieldsAroundPlayer.get(6);

                case WEST -> fieldsAroundPlayer.get(7);

                case FIGHT -> {
                    if (fieldsAroundPlayer.contains(ENEMY)){
                        //do bist
                    }
                }
            };
        } else {
            return false;
        }
    }

    private static void showActions() {
        System.out.println("Enter one of the following moves:");
        System.out.println("N...One Field north");
        System.out.println("E...One Field east");
        System.out.println("S...One Field south");
        System.out.println("W...One Field west");
        System.out.println("F...Fight");
    }


    private static void createPlayfield() {
        //TODO: ECKEN AUSFÜLLEN DAMIT MAN NICHT DURCH ECKEN GEHEN KANN
        playfield = """
                ########################################
                # @                                    #
                # #### #### #### #### #### #### #### # #
                # #    #    #    #    #    #    #    # #
                # ########  #### #### ####  ###  #######
                #      #    #    #    #    #    #    # #
                ###### #    ######    ######    #### # #
                # #    #    #    ## ###    #    #    # #
                # ############## #### #### #### #### # #
                #                                      #
                # #### #### #### #### ############## # #
                # #    #    #    #    # B       #    # #
                # ###  #### ###  #### #          ##### #
                # #    #    #    #    #         #    # #
                # #### #    #### #    ####      ###### #
                # #    #    #    #    #               S#
                ########################################
                """;

    }


}
