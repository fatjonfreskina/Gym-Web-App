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
 * Service used to retrieve a trainee by his/her email
 *
 * @author Harjot Singh
 */
public class TraineeService {

  private final DataSource dataSource;

  /**
   * The constructod
   * @param dataSource  the datasource used to retrieve the data
   */
  public TraineeService(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * Gets a trainee by his/her email
   * @param traineeEmail  the trainee's email
   * @return  the trainee
   * @throws SQLException
   * @throws TraineeNotFound
   */
  public Trainee getTraineeByEmail(String traineeEmail) throws SQLException, TraineeNotFound {
    if (InputValidation.isValidEmailAddress(traineeEmail)) {
      Trainee trainee = new GetTraineeByEmailDatabase(dataSource.getConnection(), traineeEmail).execute();
      if (trainee == null) throw new TraineeNotFound();
      return trainee;
    } else throw new TraineeNotFound();
  }

}
