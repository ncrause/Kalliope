package kalliope

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Service(User)
interface UserService {
	
	User get(Serializable id)
	
	List<User> list(Map args)
	
	Long count()
	
	@Transactional
	void delete(Serializable id)
	
	@Transactional
	User save(User record)
	
}
