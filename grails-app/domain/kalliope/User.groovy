/*
 * Copyright (C) 2019 Nathan Crause
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package kalliope

class User {
	
	String name
	
	String password
	
	String passwordDigest
	
	// this is really just to force a dirty state during password edits
	int passwordVersion = 0
	
	Date dateCreated
	
	Date lastUpdated
	
	static transients = ['password']

    static constraints = {
		name(blank: false, maxSize: 255, matches: "\\w+")
		passwordDigest(blank: false)
    }
	
	static mapping = {
		table(name: "users")
		id(name: "name", generator: "assigned")
	}
	
	def beforeValidate() {
		// if there's a password, digest it!
		if (password) {
			passwordDigest = Digester.sha512(password)
		}
	}
	
	def afterInsert() {
		afterSave()
	}
	
	def afterUpdate() {
		afterSave()
	}
	
	def afterSave() {
		// make sure to clear out the plaintext password ASAP
		password = null
	}
	
}
