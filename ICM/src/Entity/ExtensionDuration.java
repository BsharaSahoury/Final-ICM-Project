package Entity;

import java.io.Serializable;

public class ExtensionDuration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int request_id;
	long duration;
	public ExtensionDuration(int int1, long l) {
		request_id=int1;
		duration=l;
	}
	public int getRequest_id() {
		return request_id;
	}
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

}
