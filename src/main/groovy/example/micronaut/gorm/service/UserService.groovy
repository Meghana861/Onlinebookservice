package example.micronaut.gorm.service

import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.model.UserModel
import grails.gorm.transactions.Transactional
import org.hibernate.SessionFactory

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService {

    @Inject
    SessionFactory sessionFactory
    def name="SELECT * FROM user_domain Where name like 'r%'"
    @Transactional
    def saveUser(UserModel userModel){
        UserDomain userDomain=new UserDomain()
        userDomain.name = userModel.name
        userDomain.email = userModel.email
        userDomain.password = userModel.password
        userDomain.phoneNumber = userModel.phoneNumber
        userDomain.address = userModel.address
        userDomain.save()
        return userDomain
    }

    /*Get All Users*/
    @Transactional
    def getAllUsers() {
        List<UserDomain> userDomain = UserDomain.findAll()
        return userDomain
    }

    /*login by email and password*/
    @Transactional
    def getUserByEmailAndPassword(String email,String password){
        UserDomain userDomain=UserDomain.findByEmailAndPassword(email, password)
        if(userDomain){
            return userDomain
        }
        else{
            return  "Invalid Credentials"
        }
    }

    /*Update By Id*/
    @Transactional
    def updateUser(Long id,UserModel updatedUserModel){
        UserDomain userDomain=UserDomain.findById(id)
        if(userDomain) {
            userDomain.name = updatedUserModel.name
            userDomain.email = updatedUserModel.email
            userDomain.password = updatedUserModel.password
            userDomain.phoneNumber = updatedUserModel.phoneNumber
            userDomain.address = updatedUserModel.address
            userDomain.save()
        }
        return updatedUserModel
    }

    /*Get By Id*/
    @Transactional
    def getUserById(Long id){
        UserDomain userDomain=UserDomain.findById(id)
        return userDomain
    }

    /*Delete By Id*/
    @Transactional
    def deleteById(Long id){
        UserDomain userDomain=UserDomain.findById(id)
        if(userDomain){
            userDomain.delete()
            return "Deleted Successfully"
        }
        else{
            return false
        }
    }

    /*Custom Query for names*/
    @Transactional
    def getByNames(){
        def session=sessionFactory.getCurrentSession()
        def query=session.createSQLQuery(name)
        def results=query.list()
        return results

    }

}
