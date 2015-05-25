package marin.tfg.server.libprovider;

import marin.tfg.server.objects.Types;

public class LibProvider {
	
	public byte[] libProvider(Types type, byte[] data){
		return new byte[] {0x10, (byte) 0x90, 0x00};
	}

}
