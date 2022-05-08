package servlet;

import constants.Constants;
import dao.person.GetPersonByEmailDatabase;
import dao.subscriptiontype.GetListForPricesDatabase;
import dao.teaches.GetListTeacherByCourseEditionDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.CourseEdition;
import resource.Person;
import resource.view.PricesView;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet that handles http get requests for the Prices web page
 *
 * @author Francesco Caldivezzi
 */
public class PricesServlet extends AbstractServlet {
    private static final Logger logger = LogManager.getLogger("harjot_singh_logger");
    /**
     * Handles the get request by sending a response with various information for the courses (e.g. prices, trainers, subscriptions...)
     * @param req  the request
     * @param res  the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<PricesView> list = null;
        try {
            list = new GetListForPricesDatabase(getDataSource().getConnection()).execute();
        } catch (SQLException | NamingException ex) {

        }
        List<PricesView> toShow=new ArrayList<>();
        for(PricesView p:list){

            //get the trainers for a given courseedition
            List<Person> trainers=new ArrayList<>();
            try {
                List<Person> trainersEmail=new GetListTeacherByCourseEditionDatabase(getDataSource().getConnection(), new CourseEdition(p.getCourseEditionId(),p.getCourseName())).execute();
                for(Person trainer:trainersEmail){
                    trainers.add(new GetPersonByEmailDatabase(getDataSource().getConnection(),trainer.getEmail()).execute());
                }

            } catch (SQLException | NamingException ex) {
            }
            toShow.add(new PricesView(p.getCourseEditionId(),p.getCourseName(),p.getDuration(),p.getCost(),p.getMin(),p.getMax(),trainers,(float)p.getLecturesPerWeek()));
        }
        logger.error(toShow.toString());

        req.setAttribute("pricesView", toShow);
        req.getRequestDispatcher(Constants.PATH_PRICES).forward(req, res);

    }
}
