package marin.tfg.server.objects;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data extends ResourceSupport{

	private Types type;
	private byte[] data;
	
	@JsonCreator
	public Data(@JsonProperty("content") Types type,
			@JsonProperty("content") byte[] data) {
		super();
		this.type = type;
		this.data = data;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
