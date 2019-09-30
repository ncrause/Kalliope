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

import java.security.MessageDigest
import org.apache.commons.codec.binary.Hex

/**
 *
 * @author n.crause
 */
class Digester {
	
	static String sha512(String s) {
		MessageDigest digest = MessageDigest.getInstance("SHA-512")
		
		byte[] digested = digest.digest(s.bytes)
		
		return Hex.encodeHexString(digested)
	}
	
}

