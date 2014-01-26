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
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import net.joaolourenco.fsd.data.Clients;
import net.joaolourenco.fsd.data.ServerLogger;
import net.joaolourenco.fsd.data.sockets.InHandler;

public class ServerClient implements Runnable {

	/**
	 * This is a variable to keep the client loop running.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private boolean running = false;

	/**
	 * This is the thread that is going to be used for each client.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private Thread thread;

	/**
	 * This is the socket used to communicate to one client.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private final Socket socket;

	/**
	 * This is where the password will be kept for login purposes.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private final String pw;

	/**
	 * This is the ID in the clients class.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private final int ID;

	/**
	 * This is the name of the client.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private final String Name;

	/**
	 * This is the stream where communication will came from.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private DataInputStream streamIn = null;

	/**
	 * This is the stream where communication will go to.
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	private DataOutputStream streamOut = null;

	/**
	 * This is where we create the new Client.
	 * 
	 * @author João Lourenço
	 * @category Constructors
	 * @param _socket
	 *            The client socket.
	 * @param _pw
	 *            The client password.
	 * @param _cID
	 *            The client name.
	 * @param _rID
	 *            The client ID.
	 */
	public ServerClient(Socket _socket, String _pw, String _cID, int _rID) {
		// We are setting the local variables to be the same as the parameters. 
		this.socket = _socket;
		this.pw = _pw;
		this.ID = _rID;
		this.Name = _cID;
		// We are checking for a login.
		if (!Clients.checkLogin(this.Name, this.pw)) {
			// Because the login wasn't valid lets close and remove the client.
			Clients.removeClient(this.ID);
		} else this.start();
	}

	/**
	 * This is where the client thread is created and started as Client Thread.
	 * 
	 * @author João Lourenço
	 * @category Threads
	 */
	public synchronized void start() {
		if (this.thread == null) {
			// Starting the variable used to keep the client loop running.
			this.running = true;
			// Creating a new thread if it's not created yet.
			this.thread = new Thread(this, "Client Thread");
			// Starting the thread.
			this.thread.start();
		}
	}

	/**
	 * This is where the client thread is stopped.
	 * 
	 * @author João Lourenço
	 * @category Threads
	 */
	public synchronized void stop() {
		// Stopping the Client loop
		this.running = false;
		if (this.thread != null) {
			try {
				// Making the thread to stop if there is a thread.
				this.thread.join();
			} catch (InterruptedException e) {
				// Printing an error if soemthing goes wrong.
				e.printStackTrace();
			}
		}
	}

	/**
	 * This is where the client loop is running.
	 * 
	 * @author João Lourenço
	 * @category Threads
	 */
	public void run() {
		while (this.running) {
			try {
				// Creating a new Handler for the input
				new InHandler(this.ID, this.streamIn.readUTF());
			} catch (IOException ioe) {
				// Printing and removing the client in case something goes wrong.
				ServerLogger.println(this.ID + " ERROR reading: " + ioe.getMessage());
				Clients.removeClient(this.ID);
			}
		}
	}

	/**
	 * This is used to send a message to the client.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @param msg
	 *            Message we want to send as a String.
	 */
	public void send(String msg) {
		try {
			// Printing the message and sending through the stream.
			ServerLogger.println(msg);
			this.streamOut.writeUTF(msg);
			this.streamOut.flush();
		} catch (IOException ioe) {
			// Printing and removing the client in case something goes wrong.
			ServerLogger.println(this.ID + " ERROR sending: " + ioe.getMessage());
			Clients.removeClient(this.ID);
		}
	}

	/**
	 * This is used to get te client ID.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return ID as an integer.
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * This is used to get te client Name.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return Name as a String.
	 */
	public String getName() {
		return this.Name;
	}

	/**
	 * This is used to get te client Address.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 * @return Address as an InetAddress.
	 */
	public InetAddress getAddress() {
		return this.socket.getInetAddress();
	}

	/**
	 * This is used to open the streams.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 */
	public void open() {
		try {
			// Creating the client streams.
			this.streamIn = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
			this.streamOut = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
		} catch (IOException e) {
			// Printing and removing the client in case something goes wrong.
			ServerLogger.println(this.ID + " ERROR opening streams: " + e.getMessage());
			Clients.removeClient(this.ID);
		}
	}

	/**
	 * This is used to close the sockets.
	 * 
	 * @author João Lourenço
	 * @category Clients
	 */
	public void close() {
		try {
			// Closing the sockets if they haven't been closed already.
			if (socket != null) socket.close();
			if (streamIn != null) streamIn.close();
			if (streamOut != null) streamOut.close();
		} catch (IOException e) {
			// Printing the error.
			ServerLogger.println(this.ID + " ERROR closing streams: " + e.getMessage());
		}
	}

}