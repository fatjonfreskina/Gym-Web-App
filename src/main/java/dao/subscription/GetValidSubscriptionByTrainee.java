package dao.subscription;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.CourseEdition;
import resource.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetValidSubscriptionByTrainee {
    private static final String STATEMENT = "SELECT * FROM subscription WHERE courseEditionId = ? and courseName = ?";
    private final Connection conn;
    private final String trainee_email;
    private static final Logger logger = LogManager.getLogger("riccardo_tumiati_logger");
    private final String loggerClass = "gwa.dao.subscription.GetSByCourseD: ";

    public GetValidSubscriptionByTrainee(final Connection conn, final String email) {
        this.conn = conn;
        this.trainee_email = email;
    }

    public void execute() throws SQLException {
        //TODO
    }
}
