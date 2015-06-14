package marin.tfg.server.libprovider;

import marin.tfg.server.objects.Types;

public class LibProvider {

	public byte[] libProvider(Types type, byte[] data) throws Exception {
		switch (type) {
		case FACEDETECTOR:
			return DummyProcess.dummyMethod(data);
		case TESTA:
			return DummyProcess.dummyMethod(data);
		case TESTB:
			return DummyProcess.dummyMethod(data);
		case TESTC:
			return DummyProcess.dummyMethod(data);
		case TESTD:
			return DummyProcess.dummyMethod(data);
		default:
			throw new IllegalArgumentException();
		}
	}
}
