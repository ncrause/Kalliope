package kalliope

import kalliope.fonts.*

class BootStrap {

    Closure init = { servletContext ->
		// make sure we have at least Encode Sans, which is a nice free font
		// which has multiple "stretch" types (apparently the only one which
		// does).
		if (!Font.findByName("Encode Sans")) {
			new EncodeSans().run()
		}
		
		// Ubuntu font has combination of weight + italic
		if (!Font.findByName("Ubuntu")) {
			new Ubuntu().run()
		}
		
		// Simple serif font with no variants
		if (!Font.findByName("Bree Serif")) {
			new BreeSerif().run()
		}
		
		// Simple monospace font with no variants
		if (!Font.findByName("PT Mono")) {
			new PTMono().run()
		}
		
		// Simple cursive font with no variants
		if (!Font.findByName("Pinyon Script")) {
			new PinyonScript().run()
		}
		
		environments {
			development {
				// create a simple user just for development
				if (!User.exists("test")) {
					// password is "changeme"
					new User(name: "test", passwordDigest: "f1891cea80fc05e433c943254c6bdabc159577a02a7395dfebbfbc4f7661d4af56f2d372131a45936de40160007368a56ef216a30cb202c66d3145fd24380906").save()
				}
			}
		}
    }
	
    Closure destroy = {
    }
}
