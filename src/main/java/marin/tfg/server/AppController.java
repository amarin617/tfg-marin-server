package marin.tfg.server;

import javax.xml.bind.DatatypeConverter;

import marin.tfg.server.libprovider.LibProvider;
import marin.tfg.server.objects.Data;
import marin.tfg.server.objects.Types;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppController.class.getName());
	private static final LibProvider PROVIDER = new LibProvider();

	long startTime;
	long elapsedTimeMillis;

	@RequestMapping("/")
	@ResponseBody
	public HttpEntity<Data> requestData(@RequestBody String request) {
		try {
			// Return response with generated Data and Code 200 OK
			return new ResponseEntity<Data>(process(request), HttpStatus.OK);
		} catch (Exception ex) {
			// Return an empty object Data and Code 500 Internal Server Error
			return new ResponseEntity<Data>(new Data(null, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
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
		// Base64 data and timer
		Data response = new Data((Types) Enum.valueOf(Types.class,
				(String) jObject.get("type")),
				DatatypeConverter.printBase64Binary(result) + stopTimer());
		// Stop Timer
		stopTimer();
		// Return response Data
		return response;
	}

	private void startTimer() {
		// Get and set current time
		startTime = System.currentTimeMillis();
	}

	private String stopTimer() {
		// Calculates the difference
		elapsedTimeMillis = System.currentTimeMillis() - startTime;
		// Add entry to log
		LOGGER.info(elapsedTimeMillis + " ms");
		// return value
		return ":" + elapsedTimeMillis;
	}
}
