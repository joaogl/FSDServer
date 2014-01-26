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

package net.joaolourenco.fsd.data.files;

public class FileManager {

	/**
	 * This is used to set the configs to the config file
	 * 
	 * @author João Lourenço
	 * @category FileManager
	 */
	public static void setConfigs() {
		/*
		 * try { FileWriter f = new FileWriter(new File(ServerValues.PATH_CONF.getPropertie())); f.write("hour=0\n"); f.close(); } catch (IOException e) { e.printStackTrace(); }
		 */
	}

	/**
	 * This is used to get the configs from the config file
	 * 
	 * @author João Lourenço
	 * @category FileManager
	 */
	public static void getConfigs() {
		/*
		 * try { Scanner sc = new Scanner(new BufferedReader(new FileReader(ServerValues.PATH_CONF.getPropertie()))); if (sc.hasNext()) { // if (sc.hasNextLine()) h = Integer.parseInt(sc.nextLine().split("=")[1]); sc.close(); } else { sc.close(); setConfigs(); } } catch (FileNotFoundException e) { if (e.getMessage().contains("(No such file or directory)")) setConfigs(); }
		 */
	}
}