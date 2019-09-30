/*
 * Copyright (C) 2019 Nathan Crause <nathan@crause.name>
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

import javax.naming.InitialContext

// This was a grand idea, but in reality it won't even compile, as the compile
// process tries to actually execute the InitialContext, which it can't because
// we're not in a real environment yet.
//environments {
//	production {
//		dataSource {
//			String dbKalliope = "jdbc/kalliope"
//			String lookupName = "java:comp/env/${dbKalliope}"
//			
//			if (new InitialContext().lookup(lookupName)) {
//				jndiName = lookupName
//			}
//			else {
//				dbCreate = "create-drop"
//				driverClassName = "org.apache.derby.jdbc.EmbeddedDriver"
//				dialect = "org.hibernate.dialect.DerbyDialect"
//				username = "sa"
//				password = ""
//				url = "jdbc:derby:directory:${dbKalliope};create=true"
//			}
//		}
//	}
//}