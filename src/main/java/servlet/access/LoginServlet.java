package servlet.access;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.NamingException;

import constants.Constants;
import dao.person.GetUserByEmailAndPasswordDatabase;
import dao.person.GetUserRolesDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.Person;
import resource.TypeOfRoles;
import servlet.AbstractServlet;
import utils.EncryptionManager;

public class LoginServlet extends AbstractServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.getRequestDispatcher(Constants.PATH_LOGIN).forward(req, res);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    System.out.println("In post of login");
    try {
      //take from the request, the parameters (email and password)
      String email = req.getParameter("email");
      String password = req.getParameter("password");

      if (email == null || email.equals("")) {
        //the email is empty
        System.out.println("invalid email");
        throw new IllegalArgumentException("invalid email");
      } else if (password == null || password.equals("")) {
        System.out.println("invalid password");
        throw new IllegalArgumentException("invalid password");
      } else {
        //try to authenticate the user
        //check if email exists and the password matches

        System.out.println(EncryptionManager.encrypt(password));
        Person person = new Person(email, "", "", EncryptionManager.encrypt(password), "", null, "", "", "");
        Person loggedPerson = new GetUserByEmailAndPasswordDatabase(getDataSource().getConnection(), person).execute();

        if (loggedPerson == null) {
          System.out.println("credentials are wrong");
          throw new IllegalArgumentException("invalid credentials");
        }
        // retrieve role
        List<TypeOfRoles> roles = new GetUserRolesDatabase(getDataSource().getConnection(), person).execute();
        List rolesAsList = roles.stream().map(role -> role.getRole()).collect(Collectors.toList());
        System.out.println(rolesAsList);

        // activate a session to keep the user data
        HttpSession session = req.getSession();

        // insert in the session the email an the role
        session.setAttribute("email", loggedPerson.getEmail());
        session.setAttribute("roles", rolesAsList);

        // login credentials were correct: we redirect the user to the homepage
        // now the session is active and its data can used to change the homepage

        String homePath = rolesAsList.contains("secretary") ? "secretary" : rolesAsList.contains("trainer") ? "trainer" : rolesAsList.contains("trainee") ? "trainee" : "";
        System.out.println("homepath:"+homePath);
        res.sendRedirect("/" + homePath);
      }
    } catch (NamingException | SQLException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    //Here control input and then redirect to trainee, trainer or secretary page
  }
}
