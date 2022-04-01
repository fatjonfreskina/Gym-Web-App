package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SHTestingServlet extends AbstractServlet{
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      res.setContentType("text/plain");
      DataSource dataSource = getDataSource();

      String roomName = "roomName";
      List<LectureTimeSlot> list = new dao.GetLectureTimeSlotFromRoomName(dataSource.getConnection(), roomName).doGet();
      System.out.println("LIST:" + list.toString());
      res.getWriter().write(list.toString());

      res.getWriter().write("\n\n");

      /*List<LectureTimeSlot> list = new dao.GetLectureTimeSlotFromDate(dataSource.getConnection(), Date.valueOf(LocalDate.now())).getLectureTimeSlotFromDate();
      System.out.println("LIST:" + list.toString());
      res.getWriter().write(list.toString());

      res.getWriter().write("\n\n");*/

      /*List<LectureTimeSlot> list = new dao.GetLectureTimeSlotCurrentWeek(dataSource.getConnection()).getLectureTimeSlotCurrentWeek();
      System.out.println("LIST:" + list.toString());
      res.getWriter().write(list.toString());

      res.getWriter().write("\n\n");*/

      /*LectureTimeSlot lts = new LectureTimeSlot("roomName", Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), 2, "Calisthenics", null);
      new dao.InsertLectureTimeSlot(dataSource.getConnection(),lts).insertMedicalCertificate();
      res.getWriter().write(lts+" saved successfully");

      res.getWriter().write("\n\n");*/
    } catch (SQLException | NamingException e) {
      res.getWriter().write(e.getMessage());
      e.printStackTrace();
    }

  }
}
