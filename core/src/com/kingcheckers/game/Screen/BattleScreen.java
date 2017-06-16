package com.kingcheckers.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kingcheckers.game.GUI.DrawBox;
import com.kingcheckers.game.GUI.DrawChecker;
import com.kingcheckers.game.GUI.PlayerInfo;
import com.kingcheckers.game.KingCheckers;
import com.kingcheckers.game.Model.*;

import java.util.ArrayList;

/**
 * Created by Maikkeyy on 5-6-2017.
 */
public class BattleScreen extends AbstractScreen {
    private final int maxBoardSize = 400; // based on 8x8 board
    private Board board;
    private Table boardContainer;
    private DrawBox[][] drawBoxes;
    private ArrayList<DrawChecker> checkers;
    private Player player;
    private ActiveChecker activeChecker;

    private PlayerInfo playerBrown, playerBeige;

    private int boxSize = 50;

    public BattleScreen(KingCheckers game) {
        super(game);
        board = new Board();
        player = new Player(this);
        activeChecker = new ActiveChecker(this, board);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(KingCheckers.ScreenMode.MENU);
        }

        stage.draw();
    }

    public void startBattle(String[] playerNames, String boardName) {
        // Clear screen of actors from other game states

        boardContainer = new Table();
        boardContainer.setFillParent(true);

        // Load board
        board.loadFromFile(boardName);
        countBoardCellSize();
        loadBoard();

        checkers = new ArrayList<DrawChecker>();
        stage.addActor(boardContainer);

        /* Call validate, so information about child elements will be cached
        and that information can be accessed from methods that compare local coords to parent coords */
        boardContainer.validate();

        loadPawns();
        loadPlayerInfo(playerNames);
        countPieces();
        player.setPlayerSide(PlayerSide.BEIGE);
        updateActivePlayer();
    }

    private void loadBoard() {
        int width = board.getWidth();
        int height = board.getHeight();

        drawBoxes = new DrawBox[width][height];

        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                drawBoxes[x][y] = new DrawBox(player, activeChecker, board.getValue(x, y), x, y);
                /* A cell<DrawBox> is added to the table, the type which fills the cell must be an Actor
                   The .size method sets boxSize as the only accepted value for the DrawBox, which inherits from Image,
                   which in turn inherits from Actor
                 */
                boardContainer.add(drawBoxes[x][y]).size(boxSize);
            }
            boardContainer.row();
        }
    }

    private void loadPawns() {
        for(int y = 0; y < board.getHeight(); ++y) {
            for(int x = 0; x < board.getWidth(); ++x) {
                int boardVal = board.getValue(x, y);

                if(boardVal != 0 && boardVal != 1) {
                    DrawChecker drawChecker = new DrawChecker(player, activeChecker, boardVal, x, y);
                    // Set position of Actor(Image) on the screen with the coords of the real drawed images
                    drawChecker.setPosition(drawBoxes[x][y].getPosition().x, drawBoxes[x][y].getPosition().y);
                    drawChecker.setSize(boxSize, boxSize);
                    checkers.add(drawChecker);
                    stage.addActor(drawChecker);
                }
            }
        }
    }

    private void loadPlayerInfo(String[] playerNames) {
        playerBeige = new PlayerInfo(playerNames[0], "pawnBright");
        playerBeige.setPosition(670, 460);
        stage.addActor(playerBeige);
        playerBeige.validate();

        playerBrown = new PlayerInfo(playerNames[1], "pawnDark");
        playerBrown.setPosition(10, 40);
        stage.addActor(playerBrown);
        playerBrown.validate();
    }

    public void countPieces() {
        int pawnsBeige = 0, pawnsBeigeKings = 0, pawnsBrown = 0, pawnsBrownKings = 0;

        for(DrawChecker dc : checkers) {
            if(dc.getPlayerSide() == PlayerSide.BEIGE) {
                if(dc.getType() == DrawChecker.PawnType.STANDARD) {
                    pawnsBeige++;
                }
                else pawnsBeigeKings++;
            }
            else {
                if(dc.getType() == DrawChecker.PawnType.STANDARD) {
                    pawnsBrown++;
                }
                else pawnsBrownKings++;
            }
        }

        playerBeige.setValue(pawnsBeige, pawnsBeigeKings);
        playerBrown.setValue(pawnsBrown, pawnsBrownKings);
    }

    public void updateActivePlayer() {
        playerBeige.setColor(Color.WHITE);
        playerBrown.setColor(Color.WHITE);

        if(player.getPlayerSide() == PlayerSide.BEIGE) {
            playerBeige.setColor(Color.YELLOW);
        }
        else {
            playerBrown.setColor(Color.YELLOW);
        }
    }

    private void countBoardCellSize() {
        // Scaling based on board size
        while(board.getWidth() * boxSize > maxBoardSize) {
            boxSize -= 1;
        }
        while(board.getHeight() * boxSize > maxBoardSize) {
            boxSize -= 1;
        }
    }

    public void removePawn(int x, int y) {
        for(int i = 0; i < checkers.size(); ++i) {
            if(checkers.get(i).getBoardPosition().isEqual(x, y)) {
                checkers.get(i).remove(); // remove actor from parent
                checkers.remove(i); // remove actor from checkerlist
                break;
            }
        }
        countPieces();
       // checkEndGame(); TODO
    }

    public DrawBox getBox(BoardPosition pos) {
        return drawBoxes[pos.x][pos.y];
    }
}
