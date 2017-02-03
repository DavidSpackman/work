package user;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/*
 * In browser , we will call this service with 
 * http://localhost:2020/UserManagement/rest/UserService/users 
 * 
 */

@SuppressWarnings("unused")
@Path("/UserService")
public class UserService {

   UserDao userDao = new UserDao();

   @GET
   @Path("/users")
   //@Produces(MediaType.APPLICATION_XML)
   @Produces("application/json")  
   public List<User> getUsers(){
      return userDao.getAllUsers();
   }	
}