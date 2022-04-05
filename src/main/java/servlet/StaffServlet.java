package servlet;

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

public class StaffServlet extends AbstractServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Trainer> l_trainer = null;
        Message m = null;
        try{
            l_trainer = new GetStaffDatabase(getDataSource().getConnection()).execute();
        }catch(NamingException e){
            m = new Message("NamingException",true);
        }catch(SQLException e){
            m = new Message("SQLException", true);
        }

        req.setAttribute("trainerlist",l_trainer);
        req.setAttribute("message",m);

        req.getRequestDispatcher("/jsp/staff.jsp").forward(req,res);
    }
}
