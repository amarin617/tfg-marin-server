package marin.tfg.server;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// Start application
		SpringApplication.run(Application.class, args);
		// Initialize log file
		initLog();
	}

	/**
	 * Create the log file if not exists in $userhome/tfgServer/log.txt.
	 */
	private static void initLog() {
		try {
			File homeLoggingDir = new File(System.getProperty("user.home")
					+ "/tfgServer/");
			if (!homeLoggingDir.exists()) {
				homeLoggingDir.mkdirs();
			}
			File f1;
			f1 = new File(System.getProperty("user.home")
					+ "/tfgServer/log.txt");
			if (!f1.exists()) {
				f1.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
