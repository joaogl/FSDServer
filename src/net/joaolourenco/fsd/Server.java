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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.joaolourenco.fsd.data.ServerLogger;
import net.joaolourenco.fsd.data.ServerValues;

public class Server implements Runnable {

	private ServerSocket server = null;
	private ServerClient clients[];
	private Thread thread = null;
	private static int clientCount = 0;
	private IncommingHandler inhandler = new IncommingHandler();

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
		clients = new ServerClient[ServerValues.Max_Clients.getPropertie()];
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
		if (clientCount < clients.length) {
			String[] name = new String[3];
			try {
				DataInputStream streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				String inputLine;

				while ((inputLine = streamIn.readUTF()) != null) {
					if (inputLine.contains("newcom ") || inputLine.contains("newmancom ")) {
						name = inputLine.split(" ");
						if (name.length >= 3) {
							if (name[1] != null && name[2] != null) {
								if (inputLine.contains("newcom ")) ProgramInfo.connectedPilots[socket.getPort()] = name[1];
								else if (inputLine.contains("newmancom ")) ProgramInfo.connectedManagers[socket.getPort()] = name[1];
								accepted = true;
								break;
							}
						} else {
							ServerLogger.println("Hacked client trying to join: " + socket.getInetAddress());
							if (socket != null) socket.close();
							if (streamIn != null) streamIn.close();
						}
					}
				}
			} catch (IOException e) {
				if (e.getMessage().contains("Stream closed")) ServerLogger.println("Hacked client connection ended.");
				else e.printStackTrace();
			}

			if (accepted) {
				if (ProgramInfo.connectedPilots[socket.getPort()] == name[1]) ServerLogger.println("Client " + socket.getInetAddress() + " as connected with the PilotID " + name[1]);
				else ServerLogger.println("Manager " + socket.getInetAddress() + " as connected with the name " + name[1]);
				try {
					clients[clientCount] = new ServerThread(this, socket, name[2], name[1]);
					clients[clientCount].open();
					clients[clientCount].start();
					clientCount++;
				} catch (IOException e) {
					ServerLogger.println("Error opening thread: " + e);
				}
			}
			accepted = false;
		} else ServerLogger.println("Client refused: maximum " + clients.length + " reached.");
	}

	private static int findClient(int ID) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getID() == ID) return i;
		return -1;
	}

	public synchronized void handle(int ID, String input) {
		if (input.equals("disc")) {
			clients[findClient(ID)].send("disc");
			remove(ID);
		} else {
			inhandler.processInput(input, ID);
			if (ProgramInfo.connectedPilots[ID] != null) clients[findClient(ID)].send(ProgramInfo.connectedPilots[ID] + ": " + input);
			else clients[findClient(ID)].send(ProgramInfo.connectedManagers[ID] + ": " + input);
		}
	}

	public synchronized void handleForAll(int ID, String input) {
		if (input.equals("disc")) {
			clients[findClient(ID)].send("disc");
			remove(ID);
		} else for (int i = 0; i < clientCount; i++) {
			if (ProgramInfo.connectedPilots[ID] != null) clients[i].send(ProgramInfo.connectedPilots[ID] + ": " + input);
			else clients[i].send(ProgramInfo.connectedManagers[ID] + ": " + input);
		}
	}

	public static synchronized void remove(int ID) {
		int pos = findClient(ID);
		if (pos >= 0) {
			ServerThread toTerminate = clients[pos];
			if (ProgramInfo.connectedPilots[ID] != null) ServerLogger.println("Removing client " + ProgramInfo.connectedPilots[ID] + ".");
			else ServerLogger.println("Removing Manager " + ProgramInfo.connectedManagers[ID] + ".");
			if (pos < clientCount - 1) for (int i = pos + 1; i < clientCount; i++)
				clients[i - 1] = clients[i];
			clientCount--;
			try {
				toTerminate.close();
				toTerminate.stop();
			} catch (IOException ioe) {
				ServerLogger.println("Error closing thread: " + ioe);
			}
		}
	}

}