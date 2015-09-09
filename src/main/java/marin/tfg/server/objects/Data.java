package marin.tfg.server.objects;

public class Data {
	private Types type;
	private String data;

	public Data(Types type, String data) {
		super();
		this.type = type;
		this.data = data;
	}

	public Data() {
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
