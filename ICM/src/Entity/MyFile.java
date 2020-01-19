package Entity;

import java.io.Serializable;
/**
 * MyFile ,Using it to save the data of specific file
 * represents a file in the database.
 * private variables of this class:
 * @param array :describes bytes of file
 * @author Arkan Muhammad
 *
 */
public class MyFile implements Serializable {

	private static final long serialVersionUID = 1L;
	private byte[] array;
/**
 * initArray: method to initialize the length of specific file
 * @param length
 */
	public void initArray(int length) {
		array = new byte[length];

	}
/**
 * getMybyterray : method doesn't received any parameters
 * @return array : array of bytes describes the file 
 */
	public byte[] getMybyterray() {
		return array;
	}

	/**
	 * setMybytearray(byte[] arr) method to Set arr of specific file
	 * @param arr : the method received a parameter with byte[] type that
	 * it describe the id bytes the File
	 */
	public void setMybytearray(byte[] arr) {
		array = arr;

	}

}
