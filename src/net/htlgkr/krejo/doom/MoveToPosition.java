package net.htlgkr.krejo.doom;

public class MoveToPosition {
    boolean validMove = false;
    char valueOfPosition;
    int indexOfPosition;

    public MoveToPosition() {
    }

    public MoveToPosition(char valueOfPosition, int indexOfPosition) {
        this.valueOfPosition = valueOfPosition;
        this.indexOfPosition = indexOfPosition;
    }

    public boolean isValidMove() {
        return validMove;
    }

    public void setValidMove(boolean validMove) {
        this.validMove = validMove;
    }

    public char getValueOfPosition() {
        return valueOfPosition;
    }

    public void setValueOfPosition(char valueOfPosition) {
        this.valueOfPosition = valueOfPosition;
    }

    public int getIndexOfPosition() {
        return indexOfPosition;
    }

    public void setIndexOfPosition(int indexOfPosition) {
        this.indexOfPosition = indexOfPosition;
    }
}
