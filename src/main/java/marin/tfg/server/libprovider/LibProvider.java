package marin.tfg.server.libprovider;

import marin.tfg.server.objects.Types;

public class LibProvider {

	public byte[] libProvider(Types type, byte[] data) throws Exception {
		switch (type) {
		case RTT:
			// Return request as response
			return data;
		case PROCESS:
			// Simulate same Android Process
			JavaDummyProcess.start();
			// Return request as response
			return data;
		default:
			throw new IllegalArgumentException();
		}
	}
}
