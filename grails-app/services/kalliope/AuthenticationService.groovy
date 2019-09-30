package kalliope

class AuthenticationService {
	
    Serializable login(String loginName, String password) {
		User user = User.get(loginName)
		
		if (!user) {
			log.error("Unknown username: '${loginName}'")
			throw new SecurityException("default.invalid.credentials")
		}
		
		String digested = digestPassword(password)
		
		if (digested != user.passwordDigest) {
			log.error("Wrong password - received '${digested}', but expected '${user.passwordDigest}'")
			throw new SecurityException("default.invalid.credentials")
		}
		
		return user.ident()
    }
	
	String digestPassword(password) {
		return Digester.sha512(password)
	}
	
}
