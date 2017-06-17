package com.kingcheckers.game.RMI;

import com.badlogic.gdx.math.Vector2;
import com.kingcheckers.game.GUI.DrawChecker;
import com.kingcheckers.game.Model.Board;
import com.kingcheckers.game.Model.BoardPosition;
import com.kingcheckers.game.Screen.BattleScreen;
import javafx.application.Platform;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maikkeyy on 16-6-2017.
 */
public class Client {
    private Communicator communicator;
    private BattleScreen screen;

    // Current property to publish
    private String currentProperty = "move";
    private String selectProperty = "select";
    private String captureProperty = "capture";

    public Client() {
        // Create communicator to communicate with other clients
        try {
            this.communicator = new Communicator(this);

            // Establish connection with remote publisherForDomain
            communicator.connectToPublisher();

            // Register properties to be published
            communicator.register(currentProperty);
            communicator.register(selectProperty);
            communicator.register(captureProperty);

            // Subscribe communicator to property
            communicator.subscribe(currentProperty);
            communicator.subscribe(selectProperty);
            communicator.subscribe(captureProperty);

        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void broadcastSelectChecker(String property, BoardPosition boardPos) {
        communicator.broadcast(property, boardPos);
    }

    // Broadcast move event to other clients
    public void broadcastMovePiece (String property, Vector2 oldScreenPos, BoardPosition newPos) {
        MoveEvent moveEvent = new MoveEvent(oldScreenPos.x, oldScreenPos.y, newPos.x, newPos.y);
        communicator.broadcast(property, moveEvent);
    }

    public void broadcastCapturePiece(String property, Vector2 oldScreenPos, BoardPosition newPos) {
        MoveEvent moveEvent = new MoveEvent(oldScreenPos.x, oldScreenPos.y, newPos.x, newPos.y);
        communicator.broadcast(property, moveEvent);
    }

    public void requestSelectChecker(String property, BoardPosition boardPos) {
        DrawChecker checker = screen.getDrawChecker(boardPos);
        System.out.println("Pressed pawn on position: " + boardPos);

        if(screen.getActiveChecker().isSelected()) {
            screen.getActiveChecker().unselect();
        } else {
            screen.getActiveChecker().unselect(); // unselect rest of checkers, so only one can be selected
            screen.getActiveChecker().select(checker);
        }
    }

    public void requestMovePiece(String property, MoveEvent moveEvent) {
        System.out.println("Moved pawn to position: [" + moveEvent.getNewPosX() + ", " + moveEvent.getNewPosY() + "]");
        screen.getActiveChecker().move(new Vector2((float)moveEvent.getOldPosX(), (float)moveEvent.getOldPosY()),
               new BoardPosition((int)moveEvent.getNewPosX(), (int)moveEvent.getNewPosY()));

        screen.getActiveChecker().unselect(); // unselect current piece
        screen.getPlayer().change(); // change player turn
    }

    public void requestCapturePiece(String property, MoveEvent moveEvent) {
        System.out.println("Moved pawn to position: [" + moveEvent.getNewPosX() + ", " + moveEvent.getNewPosY() +
                        "] - PAWN CAPTURED");
        screen.getActiveChecker().captureAndMove(new Vector2((float)moveEvent.getOldPosX(), (float)moveEvent.getOldPosY()),
                new BoardPosition((int)moveEvent.getNewPosX(), (int)moveEvent.getNewPosY()));

        if(!screen.getActiveChecker().anyCapturesLeft()) { // turn over
            screen.getActiveChecker().unselect();
            screen.getPlayer().change();
        }
    }

    public void setBattleScreen(BattleScreen screen) {
        this.screen = screen;
    }
}
