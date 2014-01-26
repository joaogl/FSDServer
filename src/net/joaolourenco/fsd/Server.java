/**
 * This file is part of FSDServer.

    FSDServer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FSDServer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with FSDServer.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.joaolourenco.fsd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.joaolourenco.fsd.data.Clients;
import net.joaolourenco.fsd.data.ServerLogger;
import net.joaolourenco.fsd.data.ServerValues;

public class Server implements Runnable {

	private ServerSocket server = null;
	private Thread thread = null;

	/**
	 * This is where the main socket is created
	 * 
	 * @author João Lourenço
	 * @param port
	 *            Requires an Int to specify the server port.
	 * @category Constructors
	 */
	public Server(int port) {
		// Binding the Socket to the port.
		try {
			ServerLogger.println("Binding to port " + port + ", please wait  ...");
			server = new ServerSocket(port);
			ServerLogger.println("Server started: " + server.getInetAddress());
		} catch (IOException ioe) {
			// Printing an error if soemthing goes wrong.
			ServerLogger.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
		// We are now creating a array of clients with the size from the config file
		Clients.setupClients(ServerValues.Max_Clients.getPropertie());
	}

	/**
	 * This is where the main thread is created and started as Main Server
	 * 
	 * @author João Lourenço
	 * @category Threads
	 */
	public synchronized void start() {
		if (thread == null) {
			// Creating a new thread if it's not created yet.
			thread = new Thread(this, "Main Server");
			// Starting the thread.
			thread.start();
		}
	}

	/**
	 * This is where the main thread is stopped
	 * 
	 * @author João Lourenço
	 * @category Threads
	 */
	public synchronized void stop() {
		if (thread != null) {
			try {
				// Making the thread to stop if there is a thread.
				thread.join();
			} catch (InterruptedException e) {
				// Printing an error if soemthing goes wrong.
				e.printStackTrace();
			}
		}
	}

	/**
	 * This is where we create a new thread for each client
	 * 
	 * @author João Lourenço
	 * @category Threads
	 */
	public void run() {
		// We are creating a loop while the thread exists.
		while (thread != null) {
			try {
				// We are calling the addThread method to process if the incomming connection is valid.
				addThread(server.accept());
			} catch (IOException ioe) {
				// Printing an error if soemthing goes wrong.
				ServerLogger.println("Server accept error: " + ioe);
				stop();
			}
		}
	}

	/**
	 * This is where we varify if the new connection is valid to create a new thread for each client
	 * 
	 * @author João Lourenço
	 * @category Threads
	 */
	private void addThread(Socket socket) {
		boolean accepted = false;
		if (Clients.availableSlots()) {
			// In the future there will be some code to check if the client is valid.
			accepted = true;

			if (accepted) {
				// Printing the information that a client has connected.
				ServerLogger.println("Client " + socket.getInetAddress() + " as connected.");
				// Adding the client to the connected clients list.
				Clients.addClient(socket, this);
			}
		} else {
			ServerLogger.println("Client refused: maximum " + Clients.getConnectedClients() + " reached.");
			// Closing the socket.
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}