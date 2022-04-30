package service;

import constants.exceptions.*;
import dao.person.GetTraineeByEmailDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.rest.Trainee;
import utils.InputValidation;

import javax.sql.DataSource;
import java.sql.SQLException;
/**
 * @author Harjot Singh
 */
public class TraineeService {

  private final DataSource dataSource;

  public TraineeService(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public Trainee getTraineeByEmail(String traineeEmail) throws SQLException, TraineeNotFound {
    if (InputValidation.isValidEmailAddress(traineeEmail)) {
      Trainee trainee = new GetTraineeByEmailDatabase(dataSource.getConnection(), traineeEmail).execute();
      if (trainee == null) throw new TraineeNotFound();
      return trainee;
    } else throw new TraineeNotFound();
  }

}
