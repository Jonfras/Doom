package net.htlgkr.krejo.grades;

import java.util.Scanner;

import static net.htlgkr.krejo.grades.Grade.*;

public class Main {


    public static void main(String[] args) {

        showTranscript();

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("Geben sie die Punktezahl ein. -1 Beendet das Programm");
            double points = Double.parseDouble(s.next());

            if (points == -1) {
                return;

            } else {
                try {
                    System.out.println(getGrade(points));
                } catch (Exception e) {
                    System.err.println("Value was too high");
                }
            }
        }


    }

    private static void showTranscript() {
        String transcript = """
                |------------------|
                |Note | Punkte     |
                |5,0  | 0 - 49,5   |
                |4,7  | 50 - 59,5  |
                |4,0  | 60 - 64,5  |
                |3,7  | 65 - 69,5  |
                |3,3  | 70 - 74,5  |
                |3,0  | 75 - 79,5  |
                |2,7  | 80 - 84,5  |
                |2,3  | 85 - 89,5  |
                |2,0  | 90 - 94,5  |
                |1,7  | 95 - 99,5  |
                |1,3  | 100 - 104,5|
                |1,0  | 105 - 120  |
                |------------------|
                    """;
        System.out.println(transcript);
    }

    private static Grade getGrade(double points) throws IllegalStateException {
        if (points < 50) {
            return FIVE;
        }

        int p = (int) (points * 10);


        return switch (p) {
            case 500, 505, 510, 515, 520, 525, 530, 535, 540, 545, 550, 555, 560, 565, 570, 575, 580, 585, 590, 595 -> FOUR_SEVEN;

            case 600, 605, 610, 615, 620, 625, 630, 635, 640, 645 -> FOUR;

            case 650, 655, 660, 665, 670, 675, 680, 685, 690, 695 -> THREE_SEVEN;

            case 700, 705, 710, 715, 720, 725, 730, 735, 740, 745 -> THREE_THREE;

            case 750, 755, 760, 765, 770, 775, 780, 785, 790, 795 -> THREE;

            case 800, 805, 810, 815, 820, 825, 830, 835, 840, 845 -> TWO_SEVEN;

            case 850, 855, 860, 865, 870, 875, 880, 885, 890, 895 -> TWO_THREE;

            case 900, 905, 910, 915, 920, 925, 930, 935, 940, 945 -> TWO;

            case 950, 955, 960, 965, 970, 975, 980, 985, 990, 995 -> ONE_SEVEN;

            case 1000, 1005, 1010, 1015, 1020, 1025, 1030, 1035, 1040, 1045 -> ONE_THREE;

            case 1050, 1055, 1060, 1065, 1070, 1075, 1080, 1085, 1090, 1095, 1100, 1105, 1110, 1115, 1120, 1125, 1130, 1135, 1140, 1145,
                    1150, 1155, 1160, 1165, 1170, 1175, 1180, 1185, 1190, 1195, 1200 -> ONE;

            default -> throw new IllegalStateException("Unexpected value: " + points);
        };


    }
}
