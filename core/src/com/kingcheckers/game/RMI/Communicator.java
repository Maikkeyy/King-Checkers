package com.kingcheckers.game.RMI;

import com.badlogic.gdx.math.Vector2;
import com.kingcheckers.game.GUI.DrawChecker;
import com.kingcheckers.game.Model.BoardPosition;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maikkeyy on 16-6-2017.
 */
public class Communicator extends UnicastRemoteObject
        implements IRemotePropertyListener {

    // Reference to client
    private final Client client;

    // Remote publisher
    private IRemotePublisherForDomain publisherForDomain;
    private IRemotePublisherForListener publisherForListener;
    private static int portNumber = 1099;
    private static String bindingName = "publisher";
    private boolean connected = false;

    // Thread pool
    private final int nrThreads = 10;
    private ExecutorService threadPool = null;

    public Communicator(Client client) throws RemoteException {
        this.client = client;
        threadPool = Executors.newFixedThreadPool(nrThreads);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        String property = evt.getPropertyName();

        if(property.equals("select")) {
            BoardPosition boardPos = (BoardPosition) evt.getNewValue();
            client.requestSelectChecker(property, boardPos);
        }
        else if(property.equals("move")) {
            MoveEvent moveEvent = (MoveEvent) evt.getNewValue();
            client.requestMovePiece(property, moveEvent);
        }
        else if(property.equals("capture")) {
            MoveEvent moveEvent = (MoveEvent) evt.getNewValue();
            client.requestCapturePiece(property, moveEvent);
        }

    }

    /**
     * Establish connection with remote publisher.
     */
    public void connectToPublisher() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", portNumber);
            publisherForDomain = (IRemotePublisherForDomain) registry.lookup(bindingName);
            publisherForListener = (IRemotePublisherForListener) registry.lookup(bindingName);
            connected = true;
            System.out.println("Connection with remote publisher established");
        } catch (RemoteException | NotBoundException re) {
            connected = false;
            System.err.println("Cannot establish connection to remote publisher");
            System.err.println("Run WhiteBoardServer to start remote publisher");
        }
    }

    /**
     * Register property at remote publisher.
     * @param property  property to be registered
     */
    public void register(String property) {
        if (connected) {
            try {
                // Nothing changes at remote publisher in case property was already registered
                publisherForDomain.registerProperty(property);
            } catch (RemoteException ex) {
                Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Subscribe to property.
     * @param property property to subscribe to
     */
    public void subscribe(String property) {
        if (connected) {
            final IRemotePropertyListener listener = this;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        publisherForListener.subscribeRemoteListener(listener, property);
                    } catch (RemoteException ex) {
                        Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    /**
     * Broadcast click event.
     * @param property  color of draw event
     * @param moveEvent checker
     */
    public void broadcast(String property, MoveEvent moveEvent) {
        if (connected) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        publisherForDomain.inform(property, null, moveEvent);
                    } catch (RemoteException ex) {
                        Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    public void broadcast(String property, BoardPosition boardPos) {
        if (connected) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        publisherForDomain.inform(property, null, boardPos);
                    } catch (RemoteException ex) {
                        Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }
}

