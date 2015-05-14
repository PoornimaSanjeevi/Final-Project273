package servletManager.functions;

import servletManager.model.UserModel;

/**
 * Created by Spurthy on 5/4/2015.
 */
public class CheckLogin {

     public static UserModel isValidLogin(String userName, String pwd)  {

         if (userName.equalsIgnoreCase("admin") && pwd.equalsIgnoreCase("123")) {
             UserModel userModel = new UserModel();
             userModel.setUserName("admin");
             userModel.setPassword("123");
             userModel.setUserType("admin");
             return userModel;
         } else if (userName.equalsIgnoreCase("user") && pwd.equalsIgnoreCase("123")) {
             UserModel userModel = new UserModel();
             userModel.setUserName("user");
             userModel.setPassword("123");
             userModel.setUserType("user");
             return userModel;
         }

         else return null;
     }
}

