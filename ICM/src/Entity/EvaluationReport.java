package Entity;

import java.io.Serializable;
/**
 * The EvaluationReport: represents a data of EvaluationReport
 * @author Arkan Muhammad
 *
 */
public class EvaluationReport implements Serializable {
	private static final long serialVersionUID = 1L;
	private String location;
	private String description;
	private String expectedResult;
	private String constraints;
	private String risks;
	private int estimatedPerfomanceDuration;
	private int requestID;
	private int id;
/**
 * First Constructor
 * @param location
 * @param description
 * @param expectedResult
 * @param constraints
 * @param risks
 * @param estimatedPerfomanceDuration
 * @param requestID
 */
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
/**
 * getRequestID : method returns request Id
 * @return requestID
 */
	public int getRequestID() {
		return requestID;
	}
/**
 * setRequestID : method to set the requestID 
 * @param requestID
 */
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	/**
	 * getId : method returns id
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * setId : method to set the id 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * getLocation : method returns Location of EvaluationReport
	 * @return location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * setLocation : method sets Location of EvaluationReport
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * getDescription : method returns description of EvaluationReport
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * setDescription : method sets description of EvaluationReport
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * getExpectedResult : method returns expectedResult of EvaluationReport
	 * @return expectedResult
	 */
	public String getExpectedResult() {
		return expectedResult;
	}
	/**
	 * setExpectedResult : method sets expectedResult of EvaluationReport
	 * @param expectedResult
	 */
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	/**
	 * getConstraints : method returns constraints of EvaluationReport
	 * @return constraints
	 */
	public String getConstraints() {
		return constraints;
	}
	/**
	 * setConstraints : method sets constraints of EvaluationReport
	 * @param constraints
	 */
	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}
	/**
	 * getRisks : method returns risks of EvaluationReport
	 * @return risks
	 */
	public String getRisks() {
		return risks;
	}
	/**
	 * setRisks : method sets risks of EvaluationReport
	 * @param risks
	 */
	public void setRisks(String risks) {
		this.risks = risks;
	}
	/**
	 * getEstimatedPerfomanceDuration : method returns estimatedPerfomanceDuration of EvaluationReport
	 * @return estimatedPerfomanceDuration
	 */
	public int getEstimatedPerfomanceDuration() {
		return estimatedPerfomanceDuration;
	}
	/**
	 * setEstimatedPerfomanceDuration : method sets estimatedPerfomanceDuration of EvaluationReport
	 * @param estimatedPerfomanceDuration
	 */
	public void setEstimatedPerfomanceDuration(int estimatedPerfomanceDuration) {
		this.estimatedPerfomanceDuration = estimatedPerfomanceDuration;
	}

}
