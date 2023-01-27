package net.htlgkr.krejo.doom;

import net.htlgkr.krejo.doom.enemies.Enemy;

public class MoveToPosition {
    private boolean validMove = false;
    private char valueOfPosition;
    private int indexOfPosition;

    public MoveToPosition() {
    }

    public MoveToPosition(char valueOfPosition, int indexOfPosition) {
        if (indexOfPosition < 0){

        }
        this.valueOfPosition = valueOfPosition;
        this.indexOfPosition = indexOfPosition;
    }

    public boolean isValidMove() {
        return this.validMove;
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

    public boolean isValidMoveOnPlayfield(char[] playfieldArr, Enemy enemy,  int highestIndex) {
        if (enemy.getIndex() + getIndexOfPosition() <= 0 || enemy.getIndex() + getIndexOfPosition() > highestIndex) {
            if (valueOfPosition == ' ') {
                return true;
            }
        }
        return false;
    }
}
