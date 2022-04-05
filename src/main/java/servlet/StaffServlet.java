package servlet;

import constants.Constants;
import dao.person.GetStaffDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import resource.Trainer;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaffServlet extends AbstractServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Trainer> l_trainer = null;
        Message m = null;
        try{
            l_trainer = new GetStaffDatabase(getDataSource().getConnection()).execute();
            m = new Message("Trainers retrieved correctly");
        }catch(NamingException e){
            m = new Message("NamingException",true);
        }catch(SQLException e){
            m = new Message("SQLException", true);
        }

        req.setAttribute("trainerlist",l_trainer);
        req.setAttribute("message",m);

        req.getRequestDispatcher(Constants.PATH_STAFF).forward(req,res);
    }
}
