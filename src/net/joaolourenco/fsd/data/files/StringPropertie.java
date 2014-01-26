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

public class StringPropertie extends Propertie {

	/**
	 * This allows us to create a new propertie as a String
	 * 
	 * @author João Lourenço
	 * @category Properties
	 * @param i
	 *            The propertie we want as an String
	 * @param isStatic
	 *            If the propertie can be changed as a boolean
	 */
	public StringPropertie(String p, boolean isStatic) {
		super(p, isStatic);
	}

	/**
	 * This allows us to get the propertie
	 * 
	 * @author João Lourenço
	 * @category Properties
	 * @return The properpie as String
	 */
	public String getPropertie() {
		return (String) this.p;
	}

}