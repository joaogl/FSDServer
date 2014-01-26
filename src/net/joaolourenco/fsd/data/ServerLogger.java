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

package net.joaolourenco.fsd.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerLogger {

	/**
	 * This method is used to write something to the console and to a log file as well
	 * 
	 * @author João Lourenço
	 * @category Loggers
	 * @param message
	 *            As Object must contaim the message to print.
	 */
	public static void println(Object message) {
		System.out.println(message);
		logger(message);
	}

	/**
	 * This method is used to write to the log file
	 * 
	 * @author João Lourenço
	 * @category Loggers
	 * @param message
	 *            As Object must contaim the message to print.
	 */
	public static void logger(Object message) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ServerValues.PATH_LOG.getPath(), true)));
			out.println(message);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}