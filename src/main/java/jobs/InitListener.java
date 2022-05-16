package jobs;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class is called when the application is initialized.
 * It launches the jobs to be executed.
 *
 * @author Riccardo Forzan
 */
public class InitListener implements ServletContextListener {

    /**
     * Initializes the listener for different jobs to be executed
     *
     * @param sce the context
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //Create a scheduler
        var scheduler = Executors.newSingleThreadScheduledExecutor();

        //Add job to check medical certificate expiration, it runs once a day
        scheduler.scheduleAtFixedRate(new MedicalCertificateExpiration(), 0, 1, TimeUnit.DAYS);

        //Add job to drop user's registration when expired, it runs once a day
        scheduler.scheduleAtFixedRate(new DropUncompletedRegistrations(), 0, 1, TimeUnit.DAYS);


        scheduler.scheduleAtFixedRate(new DropUncompletedPasswordChanged(), 0, 30, TimeUnit.MINUTES);

        //Super call
        jakarta.servlet.ServletContextListener.super.contextInitialized(sce);

    }

}