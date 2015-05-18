package marin.tfg.server;

import marin.tfg.server.libprovider.LibProvider;
import marin.tfg.server.objects.Data;
import marin.tfg.server.objects.Types;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RESTController {

	@RequestMapping("/")
	@ResponseBody
	public HttpEntity<Data> requestData(
			@RequestParam(value = "type", required = false) Types type,
			@RequestParam(value = "data", required = false) byte[] data) {

		try {
			LibProvider mProvider = new LibProvider();
			byte[] result = mProvider.libProvider(type, data);
			Data toSend = new Data(type, result);
			return new ResponseEntity<Data>(toSend, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Data>(new Data(type, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
