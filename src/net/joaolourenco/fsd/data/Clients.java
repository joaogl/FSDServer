package net.joaolourenco.fsd.data;

import java.net.Socket;

import net.joaolourenco.fsd.Server;
import net.joaolourenco.fsd.ServerClient;

public class Clients {

	/**
	 * This is an Array of clients to hold all of our clients.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private static ServerClient clients[];
	private static int clientsRev[];

	/**
	 * This is a variable to keep track of how many clients are on.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private static int clientCount = 0;

	/**
	 * This is used to setup the clients Array.
	 * 
	 * @author João Lourenço
	 * @param max
	 *            Requires an Int to specify the maximum clients allowed.
	 * @category Clients
	 */
	public static void setupClients(int max) {
		// We are now creating a array of clients with the size from the config file.
		clients = new ServerClient[max];
		// We are now creating a array of clients with the size from the config file to hold the holding clients.
		clientsRev = new int[max];
	}

	/**
	 * This is used to get the number of online clients.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return The number of clients as an Integer
	 */
	public static int getConnectedClients() {
		return clientCount;
	}

	/**
	 * This is used to get the maximum of online clients.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return The maximum of clients as an Integer
	 */
	public static int getMaxClients() {
		return clients.length;
	}

	/**
	 * This is used to check if there are free slots on the server.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return A boolean.
	 */
	public static boolean availableSlots() {
		return (clientCount < clients.length);
	}

	/**
	 * This is used to add a new client to the list.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return 0 if was successful and 1 if it wasn't.
	 */
	public static int addClient(Socket _socket, Server _server) {
		// We need to get a free ID.
		int ID = getFreeID();
		// We are checking if its an error ID or not.
		if (ID != getErrorCode()) {
			// We are adding the new client, Openning the Streams and starting the threads.
			clients[ID] = new ServerClient(_socket, "FuturePassword", "FutureCertID", ID);
			clients[ID].open();
			clients[ID].start();
			// Then because everything is done we can add one more client and remove the reserve.
			clientCount++;
			clientsRev[ID] = 0;
			// Returning 0 because there where no errors.
			return 0;
		}
		// If something gone wrong, we are returning 1.
		return 1;
	}

	/**
	 * This is used to find an empty ID.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return An int corresponding to the empty slot.
	 */
	public static int getFreeID() {
		// Here we are running through all the indexes of the clients to check which is the first empty slot.
		for (int i = 0; i < clients.length; i++) {
			if (clients[i] == null && clientsRev[i] == 0) {
				// When we find the empty slot we reserve it and return its ID.
				clientsRev[i] = i;
				return i;
			}
		}
		// If no ID is found we return the error code.
		return getErrorCode();
	}

	/**
	 * This is used to get the Error code.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return An int corresponding to error code.
	 */
	public static int getErrorCode() {
		return (clients.length + 5);
	}

	/**
	 * This is used to remove a Client.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 */
	public static void removeClient(int ID) {
		// Printing the information.
		ServerLogger.println("Removing client " + clients[ID].getName() + "/" + clients[ID].getAddress() + ".");
		// Closing the Streams.
		clients[ID].close();
		// Stopping the Client Thread.
		clients[ID].stop();
		// Removing the client from the connected clients list.
		clients[ID] = null;
		// Removing one from the clientsCount because we have less one client.
		clientCount--;
	}

	/**
	 * This is used to check for a login.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return Boolean, true if account is valid, false if not.
	 */
	public static boolean checkLogin(String _name, String _pw) {
		// In the future there will be some kind of login check.
		return true;
	}

}