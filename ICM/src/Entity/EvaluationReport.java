package Entity;

import java.io.Serializable;

public class EvaluationReport implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private String location;
	private String description;
	private String expectedResult;
	private String constraints;
	private String risks;
	private int estimatedPerfomanceDuration;
	private int requestID;
	private int id;

	public EvaluationReport(String location, String description, String expectedResult, String constraints,
			String risks, int estimatedPerfomanceDuration, int requestID) {
		this.location = location;
		this.description = description;
		this.expectedResult = expectedResult;
		this.constraints = constraints;
		this.risks = risks;
		this.estimatedPerfomanceDuration = estimatedPerfomanceDuration;
		this.requestID = requestID;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	public String getRisks() {
		return risks;
	}

	public void setRisks(String risks) {
		this.risks = risks;
	}

	public int getEstimatedPerfomanceDuration() {
		return estimatedPerfomanceDuration;
	}

	public void setEstimatedPerfomanceDuration(int estimatedPerfomanceDuration) {
		this.estimatedPerfomanceDuration = estimatedPerfomanceDuration;
	}

}
