package net.joaolourenco.fsd.data;

import net.joaolourenco.fsd.ServerClient;

public class Clients {

	/**
	 * This is an Array of clients to hold all of our clients.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private static ServerClient clients[];

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
		// We are now creating a array of clients with the size from the config file
		clients = new ServerClient[max];
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
	 * This is used to check if there are free slots on the server.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return A boolean.
	 */
	public static boolean availableSlots() {
		return (clientCount < clients.length);
	}

}