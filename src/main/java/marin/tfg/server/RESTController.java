package marin.tfg.server;

import java.util.Arrays;
import java.util.logging.Logger;

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
public class RESTController {
	
	private static final Logger log = Logger.getLogger(RESTController.class
			.getName());

	@RequestMapping("/")
	@ResponseBody
	public HttpEntity<Data> requestData(@RequestBody String mRequest) {
		try {
			Data toSend = processRequest(mRequest);
			return new ResponseEntity<Data>(toSend, HttpStatus.OK);
		} catch (Exception ex) {
			log.warning(ex.getMessage());
			return new ResponseEntity<Data>(new Data(null, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private Data processRequest(String mRequest) throws Exception {
		log.info("\n------------> New Request: " + mRequest);
		JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		JSONObject jObject = (JSONObject) parser.parse(mRequest);
		Data request = new Data((Types) Enum.valueOf(Types.class,
				(String) jObject.get("type")), (String) jObject.get("data"));
		log.info("Received Request: " + request.toString());
		LibProvider mProvider = new LibProvider();
		log.info("Processing Data Type: " + request.getType().toString());
		byte[] result = mProvider.libProvider(request.getType(),
				DatatypeConverter.parseBase64Binary(request.getData()));
		log.info("Data result: " + Arrays.toString(result));
		Data toSend = new Data(request.getType(),
				DatatypeConverter.printBase64Binary(result));
		log.info("Generating response: " + toSend.toString());
		return toSend;
	}
}
