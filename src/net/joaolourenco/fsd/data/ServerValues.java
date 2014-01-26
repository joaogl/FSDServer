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

import net.joaolourenco.fsd.data.files.IntPropertie;
import net.joaolourenco.fsd.data.files.StringPropertie;

public class ServerValues {

	/**
	 * Server Name
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	public static String ServerName = "WTGServer";

	/**
	 * Server Description
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	public static String ServerDesc = "Wild Tigers Server";

	/**
	 * The maximum clients capacity for the server
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	public static IntPropertie Max_Clients = new IntPropertie(50, false);

	/**
	 * The server port
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	public static IntPropertie Server_Port = new IntPropertie(6809, false);

	/**
	 * Server Config Path
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	public static StringPropertie PATH_CONF = new StringPropertie("fsd.conf", true);

	/**
	 * Server Motd Path
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	public static StringPropertie PATH_MOTD = new StringPropertie("motd.txt", false);

	/**
	 * Server Log Path
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	public static StringPropertie PATH_LOG = new StringPropertie("log.txt", false);

	/**
	 * Server Metar Path
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	public static StringPropertie PATH_METAR = new StringPropertie("metar.txt", false);

	/**
	 * Server Metar new Path
	 * 
	 * @author João Lourenço
	 * @category Variables
	 */
	public static StringPropertie PATH_METARNEW = new StringPropertie("metarnew.txt", false);

}