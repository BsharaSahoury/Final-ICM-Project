package Entity;

import java.io.Serializable;
/**
 * ExtensionDuration Class that represents an ExtensionDuration for a specific request 
 * @param request_id
 * @param duration
 * @author Arkan Muhammad
 *
 */
public class ExtensionDuration implements Serializable {

	private static final long serialVersionUID = 1L;
	int request_id;
	long duration;
/**
 * First Constructor
 * @param int1
 * @param l
 */
	public ExtensionDuration(int int1, long l) {
		request_id = int1;
		duration = l;
	}
	/**
	 * getRequest_id() method doesn't received any parameters
	 * @return request_id : the id of the Request
	 */
	public int getRequest_id() {
		return request_id;
	}
	/**
	 * setRequest_id method to Set id of request
	 * @param request_id : the method received a parameter with int type that
	 * it describe the request_id of the request
	 */
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	/**
	 * getDuration : method that returns the duration time that requested to extend
	 * @return duration
	 */
	public long getDuration() {
		return duration;
	}
/**
 * setDuration: method that sets the duration time that requested to extend
 * @param duration
 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

}
