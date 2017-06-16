package com.kingcheckers.game.RMI;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maikkeyy on 16-6-2017.
 */
public class Client {
    private Communicator communicator;

    // Current property to publish
    private String currentProperty = "click";

    public Client() {
        // Create communicator to communicate with other clients
        try {
            this.communicator = new Communicator(this);

            // Establish connection with remote publisherForDomain
            communicator.connectToPublisher();

            // Register properties to be published
            communicator.register(currentProperty);

            // Subscribe communicator to property
            communicator.subscribe(currentProperty);

        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
}
