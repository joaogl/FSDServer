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

public abstract class Propertie {

	/**
	 * This is our propertie
	 * 
	 * @author João Lourenço
	 * @category Variable
	 */
	protected Object p = "";

	/**
	 * This defines if the propertie is static or not
	 * 
	 * @author João Lourenço
	 * @category Variable
	 */
	private final boolean Static;

	/**
	 * This allows us to create a new propertie as an Object
	 * 
	 * @author João Lourenço
	 * @category Properties
	 * @param p
	 *            The propertie we want as an Object
	 * @param isStatic
	 *            If the propertie can be changed as a boolean
	 */
	public Propertie(Object p, boolean isStatic) {
		this.p = p;
		this.Static = isStatic;
	}

	/**
	 * This allows us to change the propertie to an Object if not static
	 * 
	 * @author João Lourenço
	 * @category Properties
	 * @param i
	 *            The new value for the propertie we want as an Object
	 * @return Returns an int, 0 if was successful, 1 if wasn't.
	 */
	public int setPropertie(Object i) {
		if (!this.isStatic()) {
			this.p = i;
			return 0;
		}
		return 1;
	}

	/**
	 * This tells us if the propertie is static or not
	 * 
	 * @author João Lourenço
	 * @category Properties
	 * @return Returns a boolean.
	 */
	public boolean isStatic() {
		return this.Static;
	}

}