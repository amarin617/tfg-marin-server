package marin.tfg.server;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import marin.tfg.server.libprovider.LibProvider;
import marin.tfg.server.objects.Data;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RESTController {

	@RequestMapping("/")
	@ResponseBody
	public HttpEntity<Data> requestData(Data request) {
		try {
			System.out.println("\n------------> New Request at "
					+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
			System.out.println("Received Request: " + request.toString());
			LibProvider mProvider = new LibProvider();
			System.out.println("Processing Data with Library Provider...");
			byte[] result = mProvider.libProvider(request.getType(),
					DatatypeConverter.parseBase64Binary(request.getData()));
			System.out.println("Get Data result: " + Arrays.toString(result));
			Data toSend = new Data(request.getType(), DatatypeConverter.printBase64Binary(result));
			System.out.println("Generating response: "
					+ toSend.toString());
			return new ResponseEntity<Data>(toSend, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Data>(new Data(null, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
