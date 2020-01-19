package Entity;

import java.io.Serializable;

public class MyFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] array;

	public void initArray(int length) {
		array = new byte[length];

	}

	public byte[] getMybyterray() {
		return array;
	}

	public void setMybytearray(byte[] arr) {
		array = arr;

	}

}
