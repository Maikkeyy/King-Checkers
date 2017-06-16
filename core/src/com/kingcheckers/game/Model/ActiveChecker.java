package com.kingcheckers.game.Model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.kingcheckers.game.GUI.DrawBox;
import com.kingcheckers.game.GUI.DrawChecker;
import com.kingcheckers.game.Screen.BattleScreen;
import com.kingcheckers.game.GUI.DrawChecker.PawnType;

/**
 * Created by Maikkeyy on 31-5-2017.
 */
public class ActiveChecker {
    private static final float movementSpeed = 0.15f;
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

    public Box getBox() {
        return this.box;
    }

    public DrawChecker getDrawChecker() {
        return this.selectedChecker;
    }

    public void select(DrawChecker checker) {
        selectedChecker = checker;
        selectedBox = screen.getBox(selectedChecker.getBoardPosition());
        selectedBox.setColor(Color.BLUE);
    }

    public void unselect() {
        if(selectedBox != null) {
            selectedBox.setColor(Color.WHITE);
        }
        selectedChecker = null;
        selectedBox = null;
    }

    public boolean canMoveTo(DrawBox box) {
        BoardPosition distance = BoardPosition.getDistance(box.getBoardPosition(), selectedChecker.getBoardPosition());

        // If it's a standard CheckerType, the movement range always is 1 horizontal and 1 vertical
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

    private boolean canMoveKing(BoardPosition checkerPos, DrawBox destination) {
        // checkPos is the boardPosition of the selected checker atm
        if(destination.getBoardPosition().isEqual(checkerPos)) {
            return true;
        } // 1 is the value where checkers can be stored, beige boxes, there never can be checkers on black boxes
        else if(board.getValue(checkerPos.x, checkerPos.y) != 1
                /*&& !selectedChecker.getBoardPosition().isEqual(checkerPos)*/) {
            return false;
        }

        BoardPosition dir = BoardPosition.getDirection(destination.getBoardPosition(), selectedChecker.getBoardPosition());
        BoardPosition newCheckerPos = new BoardPosition();

        if(dir.x < 0 && dir.y < 0) {
            newCheckerPos =  new BoardPosition(checkerPos.x - 1, checkerPos.y - 1); //Top left
        }
        else if(dir.x > 0 && dir.y < 0) {
            newCheckerPos =  new BoardPosition(checkerPos.x + 1, checkerPos.y - 1); //Top right
        }
        else if(dir.x < 0 && dir.y > 0) {
            newCheckerPos =  new BoardPosition(checkerPos.x - 1, checkerPos.y + 1); //Bottom left
        }
        else if(dir.x > 0 && dir.y > 0) {
            newCheckerPos =  new BoardPosition(checkerPos.x + 1, checkerPos.y + 1); //Bottom right
        }

        return canMoveKing(newCheckerPos, destination);
    }

    public void move(Vector2 ScreenPos, BoardPosition boardPos) {
        board.setValue(selectedChecker.getBoardPosition(), 1); // clear box of current checker
        board.setValue(boardPos, selectedChecker.getPlayerInt()); // set checker on new destination box
        selectedChecker.setBoardPosition(boardPos);

        selectedChecker.addAction(Actions.moveTo(ScreenPos.x, ScreenPos.y, movementSpeed)); // Animate movement

        // Check if a checker piece can be transformed into a king
        if(canChangeToKing(boardPos.y)) {
            selectedChecker.setAsKing();
        }

        screen.countPieces(); // update amount of pieces
    }

    private boolean canChangeToKing(int posY) {
        if(selectedChecker.getPlayerSide() == PlayerSide.BEIGE && posY == board.getHeight() - 1) return true;
        else if(selectedChecker.getPlayerSide() == PlayerSide.BROWN && posY == 0) return true;
        else return false;
    }

    public boolean canCapturePawn(BoardPosition boxPos) {
        BoardPosition direction = BoardPosition.getDirection(boxPos, selectedChecker.getBoardPosition());
        int changePosX, changePosY;

        // direction must be seen from the box you select to your current checker, (opposite) direction
        if(direction.x > 0) {
            changePosX = -1;
        }
        else changePosX = 1;

        if(direction.y > 0) changePosY = -1;
        else changePosY = 1;

        BoardPosition pawnToCapture = new BoardPosition(boxPos.x + changePosX, boxPos.y + changePosY);
        BoardPosition boxToMove = new BoardPosition(boxPos.x + changePosX *2, boxPos.y + changePosY *2);

        if(canCapture(pawnToCapture, boxToMove)) {
            BoardPosition dist = BoardPosition.getDistance(boxPos, selectedChecker.getBoardPosition());

            // standard pieces can only move 2 horizontal/vertical units
            if(selectedChecker.getType() == PawnType.STANDARD && dist.x == 2 && dist.y == 2) return true;
            else if(selectedChecker.getType() == PawnType.KING && dist.x == dist.y) return true;
        }
        return false;
    }

    public boolean canCapture(BoardPosition pawnToCapture, BoardPosition boxToMove) {
        if(board.getValue(pawnToCapture) == 0 || board.getValue(pawnToCapture) == 1) return false;

        PlayerSide activePlayer = selectedChecker.getPlayerSide();
        PlayerSide pawnToCapturePlayer = board.getCheckerPlayer(pawnToCapture);

        if(activePlayer == pawnToCapturePlayer) return false;
        else {
            if(board.getValue(boxToMove) == 1 || board.getValue(boxToMove) == selectedChecker.getPlayerInt()) return true;
            else return false;
        }
    }

    public void captureAndMove(Vector2 ScreenPos, BoardPosition boardPos) {
        BoardPosition direction = BoardPosition.getDirection(boardPos, selectedChecker.getBoardPosition());
        int toRemoveX, toRemoveY;

        if(direction.x > 0) toRemoveX = boardPos.x - 1;
        else toRemoveX = boardPos.x + 1;

        if(direction.y > 0) toRemoveY = boardPos.y - 1;
        else toRemoveY = boardPos.y + 1;

        removePawn(new BoardPosition(toRemoveX, toRemoveY));
        move(ScreenPos, boardPos);
    }

    public void removePawn(BoardPosition pos) {
        board.setValue(pos.x, pos.y, 1);
        screen.removePawn(pos.x, pos.y);
    }

    public boolean anyCapturesLeft() {
        BoardPosition pos = selectedChecker.getBoardPosition();

        if(canCapture(new BoardPosition(pos.x - 1, pos.y - 1), new BoardPosition(pos.x - 2, pos.y - 2))) return true; //Top left
        if(canCapture(new BoardPosition(pos.x + 1, pos.y - 1), new BoardPosition(pos.x + 2, pos.y - 2))) return true; //Top right
        if(canCapture(new BoardPosition(pos.x - 1, pos.y + 1), new BoardPosition(pos.x - 2, pos.y + 2))) return true; //Bottom left
        if(canCapture(new BoardPosition(pos.x + 1, pos.y + 1), new BoardPosition(pos.x + 2, pos.y + 2))) return true; //Bottom right
        return false;
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
