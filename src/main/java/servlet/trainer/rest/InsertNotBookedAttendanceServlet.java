package servlet.trainer.rest;

import com.google.gson.Gson;
import constants.ErrorCodes;
import dao.reservation.GetAllPeopleInReservationTimeSlotDatabase;
import dao.reservation.InsertReservationDatabase;
import dao.room.GetRoomSizeByNameDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import resource.Person;
import resource.Reservation;
import resource.Room;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class InsertNotBookedAttendanceServlet extends AbstractServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson gson=new Gson();
        Reservation reservation=gson.fromJson(req.getParameter("reservation"),Reservation.class);
        List<Person> reservations;
        ErrorCodes error = ErrorCodes.OK;

        //Check if there are enough spots available for the given room
        try{
            reservations= new GetAllPeopleInReservationTimeSlotDatabase(getDataSource().getConnection(),reservation).execute();
        }
        catch (SQLException | NamingException e){
            error=ErrorCodes.INTERNAL_ERROR;
            sendFeedback(res,error);
            return;
        }
        if(reservations==null){
            error=ErrorCodes.INTERNAL_ERROR;
            sendFeedback(res,error);
            return;
        }

        Room room;
        try{
            List<Room> rooms= new GetRoomSizeByNameDatabase(getDataSource().getConnection(),new Room(reservation.getRoom())).execute();
            if(rooms == null || rooms.size() != 1){
                error=ErrorCodes.INTERNAL_ERROR;
                sendFeedback(res,error);
                return;
            }
            room=rooms.get(0);
        }
        catch (SQLException | NamingException e){
            error=ErrorCodes.INTERNAL_ERROR;
            sendFeedback(res,error);
            return;
        }
        if(room==null){
            error=ErrorCodes.INTERNAL_ERROR;
            sendFeedback(res,error);
            return;
        }

        if(room.getSlots()-reservations.size()>=1){
            try{
                new InsertReservationDatabase(getDataSource().getConnection(),reservation).execute();
            }
            catch (SQLException | NamingException e){
                error=ErrorCodes.INTERNAL_ERROR;
                sendFeedback(res,error);
                return;
            }

            //Return positive feedback
            sendFeedback(res,error);
        }
        else{
            error=ErrorCodes.ROOM_ALREADY_FULL;
            sendFeedback(res,error);
        }
    }


    private void sendFeedback(HttpServletResponse res, ErrorCodes error) throws IOException {
        String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), true));
        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");
        out.print(messageJson);
    }
}
