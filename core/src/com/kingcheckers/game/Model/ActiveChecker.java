package com.kingcheckers.game.Model;

import com.badlogic.gdx.graphics.Color;
import com.kingcheckers.game.GUI.DrawBox;
import com.kingcheckers.game.GUI.DrawChecker;
import com.kingcheckers.game.Screen.BattleScreen;
import com.kingcheckers.game.GUI.DrawChecker.PawnType;

/**
 * Created by Maikkeyy on 31-5-2017.
 */
public class ActiveChecker {
    private CheckerType checkerType;
    private Box box;

    private BattleScreen screen;
    private Board board;

    private DrawChecker selectedChecker;
    private DrawBox selectedBox;

    public ActiveChecker(BattleScreen screen, Board board) {
        this.screen = screen;
        this.board = board;
    }

    public CheckerType getCheckerType() {
        return this.checkerType;
    }

    public boolean canMoveTo(Box b) {

        if (this.box.getColor() == b.getColor() && b.getActiveChecker() == null) {
            //TODO Write other check move logic here
            return true;
        }

        return false;
    }

    public Box getBox() {
        return this.box;
    }

    public DrawChecker getDrawChecker() {
        return this.selectedChecker;
    }

    public void select(DrawChecker checker) {
        selectedChecker = checker;
        selectedBox = screen.getBox(selectedChecker.getBoardPosition());
        selectedBox.setColor(Color.GOLD);
    }

    public void unselect() {
        if(selectedBox != null) {
            selectedBox.setColor(Color.WHITE);
        }
        selectedChecker = null;
        selectedBox = null;
    }

    public boolean canMove(DrawBox box) {
        BoardPosition distance = BoardPosition.getDistance(box.getBoardPosition(), selectedChecker.getBoardPosition());

        // If it's a standard PawnType, the movement range always is 1 horizontal and 1 vertical
        // because it goes oblique, not in a straight way: \ or /
        if(selectedChecker.getType() == PawnType.STANDARD) {
            return (distance.x == 1) && (distance.y == 1); // Check if the movement range is same as the distance
        }
        else { // It's a king
            if(distance.x == distance.y) { // the movement is always oblique, so the x and y values of the distance have to be the same
                return canMoveKing(selectedChecker.getBoardPosition(), box);
            }
        }
        return false;
    }

    private boolean canMoveKing(BoardPosition checkPos, DrawBox destination) {
        if(destination.getBoardPosition().isEqual(checkPos)) {
            return true;
        }
       /* else if(board.getValue(checkPos) != 1 && !selected.getBoardPosition().isEqual(checkPos)) {
            return false;
        } */

        BoardPosition dir = BoardPosition.getDirection(destination.getBoardPosition(), selectedChecker.getBoardPosition());
        BoardPosition newCheckPos = new BoardPosition();

        if(dir.x < 0 && dir.y < 0) {
            newCheckPos =  new BoardPosition(checkPos.x - 1, checkPos.y - 1); //Top left
        }
        else if(dir.x > 0 && dir.y < 0) {
            newCheckPos =  new BoardPosition(checkPos.x + 1, checkPos.y - 1); //Top right
        }
        else if(dir.x < 0 && dir.y > 0) {
            newCheckPos =  new BoardPosition(checkPos.x - 1, checkPos.y + 1); //Bottom left
        }
        else if(dir.x > 0 && dir.y > 0) {
            newCheckPos =  new BoardPosition(checkPos.x + 1, checkPos.y + 1); //Bottom right
        }

        return canMoveKing(newCheckPos, destination);
    }

    public boolean isSelected() {
        if(selectedChecker == null) {
            return false;
        }
        else {
            return true;
        }
    }

}
