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

import net.joaolourenco.fsd.data.ServerValues;
import net.joaolourenco.fsd.data.ServerLogger;
import net.joaolourenco.fsd.data.files.FileManager;

public class Main {

	/**
	 * Main method to start the server
	 * 
	 * @author João Lourenço
	 * @category MainServer
	 * @param args
	 *            Has an Array of strings not used for now.
	 */
	public static void main(String[] args) {
		ServerLogger.println("");
		ServerLogger.println("");
		ServerLogger.println("========== Starting FSD Server ==========");
		ServerLogger.println("by Joao Lourenco to WildTigers");
		ServerLogger.println("========= Setting up FSD Server =========");

		// This gets the configs from the file.
		FileManager.getConfigs();

		// Creating the Sockets to communicate.
		Server server = new Server(ServerValues.Server_Port.getPropertie());
		// Starting the main server thread.
		server.start();
	}

}