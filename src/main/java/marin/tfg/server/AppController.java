package marin.tfg.server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.bind.DatatypeConverter;

import marin.tfg.server.libprovider.LibProvider;
import marin.tfg.server.objects.Data;
import marin.tfg.server.objects.Types;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {

	private static final Logger LOGGER = Logger.getLogger(AppController.class
			.getName());
	private static final LibProvider PROVIDER = new LibProvider();

	long startTime;
	long elapsedTimeMillis;

	@RequestMapping("/")
	@ResponseBody
	public HttpEntity<Data> requestData(@RequestBody String request) {
		try {
			// Check file to add a Log Handler
			addLogHandler();
			// Return response with generated Data and Code 200 OK
			return new ResponseEntity<Data>(process(request), HttpStatus.OK);
		} catch (Exception ex) {
			// Return an empty object Data and Code 500 Internal Server Error
			return new ResponseEntity<Data>(new Data(null, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method gets FileHandler object from path file and checks if
	 * previously has been added to the Log. If not, the File Handler is added
	 * to the Log.
	 */
	private void addLogHandler() {
		try {
			// Get FileHandler object from file path
			FileHandler fh = new FileHandler(System.getProperty("user.home")
					+ "/tfgServer/log.txt");
			// Check if is handler exists
			if (LOGGER.getHandlers().equals(fh)) {
				// Add handler file to save log
				LOGGER.addHandler(fh);
				// Set a formatter text
				fh.setFormatter(new SimpleFormatter());
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Data process(String mRequest) throws Exception {
		// Parse string to JSONObject
		JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		JSONObject jObject = (JSONObject) parser.parse(mRequest);
		// Start timer
		startTimer();
		// Get result from library
		byte[] result = PROVIDER
				.libProvider((Types) Enum.valueOf(Types.class,
						(String) jObject.get("type")), DatatypeConverter
						.parseBase64Binary((String) jObject.get("data")));
		// Generate new Data Object with the same Type Enum and with encoded
		// Base64 data
		Data response = new Data((Types) Enum.valueOf(Types.class,
				(String) jObject.get("type")),
				DatatypeConverter.printBase64Binary(result));
		// Stop Timer
		stopTimer();
		// Return response Data
		return response;
	}

	private void startTimer() {
		// Get and set current time
		startTime = System.currentTimeMillis();
	}

	private void stopTimer() {
		// Calculates the difference
		elapsedTimeMillis = System.currentTimeMillis() - startTime;
		// Add entry to log
		LOGGER.info("" + elapsedTimeMillis);
	}
}
