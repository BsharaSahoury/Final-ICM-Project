package Entity;
/**
 *  The <code>Member</code> entity, represents a Member in the database.
 *  User Class Extends from Serializable Class
 *  User is an ICM-User in DataBase .
 *  The User has private variables:
 * @param median
 * @param Std
 * @param frequencyDistribution
 * @param numActive
 * @param numFrozen
 * @param numClosed
 * @param numRejected 
 * @param numTreatment
 * @author Arkan Muhammad
 */
public class PeriodicReport {
	private float median;
	private float Std;
	private float frequencyDistribution;
	private int numActive;
	private int numFrozen;
	private int numClosed;
	private int numRejected;
	private int numTreatment;
/**
 * First Constructor:
 * @param median
 * @param std
 * @param frequencyDistribution
 * @param numActive
 * @param numFrozen
 * @param numClosed
 * @param numRejected
 * @param numTreatment
 */
	public PeriodicReport(float median, float std, float frequencyDistribution, int numActive, int numFrozen,
			int numClosed, int numRejected, int numTreatment) {
		this.median = median;
		Std = std;
		this.frequencyDistribution = frequencyDistribution;
		this.numActive = numActive;
		this.numFrozen = numFrozen;
		this.numClosed = numClosed;
		this.numRejected = numRejected;
		this.numTreatment = numTreatment;
	}
/**
 * getMedian() : a method that returns variable of Median
 * @return : median of PeriodicReport
 */
	public float getMedian() {
		return median;
	}
	/**
	 * setMedian(float median) method to Set median of PeriodicReport
	 * @param median : the method received a parameter with float type that
	 * describes the median of the PeriodicReport
	 */
	public void setMedian(float median) {
		this.median = median;
	}
	/**
	 * getStd() : a method that returns a Std
	 * @return : Std of PeriodicReport
	 */
	public float getStd() {
		return Std;
	}
	/**
	 * setStd(float std) method to Set std of PeriodicReport
	 * @param std : the method received a parameter with float type that
	 * describes the std of the PeriodicReport
	 */
	public void setStd(float std) {
		Std = std;
	}
	/**
	 * getFrequencyDistribution() : a method that returns a frequencyDistribution
	 * @return : frequencyDistribution of PeriodicReport
	 */
	public float getFrequencyDistribution() {
		return frequencyDistribution;
	}
	/**
	 * setFrequencyDistribution(float frequencyDistribution), method to Set frequencyDistribution of PeriodicReport
	 * @param frequencyDistribution : the method received a parameter with float type that
	 * describes the frequencyDistribution of the PeriodicReport
	 */
	public void setFrequencyDistribution(float frequencyDistribution) {
		this.frequencyDistribution = frequencyDistribution;
	}
	/**
	 * getNumActive() : a method that returns a numActive
	 * @return : numActive of PeriodicReport
	 */
	public int getNumActive() {
		return numActive;
	}
	/**
	 * setNumActive(setNumActive), method to Set numActive of PeriodicReport
	 * @param numActive : the method received a parameter with int type that
	 * describes the numActive of the PeriodicReport
	 */
	public void setNumActive(int numActive) {
		this.numActive = numActive;
	}
	/**
	 * getNumFrozen() : a method that returns a numFrozen
	 * @return : numFrozen of PeriodicReport
	 */
	public int getNumFrozen() {
		return numFrozen;
	}
	/**
	 * setNumFrozen(setNumActive), method to Set numFrozen of PeriodicReport
	 * @param numActive : the method received a parameter with int type that
	 * describes the numFrozen of the PeriodicReport
	 */
	public void setNumFrozen(int numFrozen) {
		this.numFrozen = numFrozen;
	}
	/**
	 * getNumClosed() : a method that returns a numClosed
	 * @return : numClosed of PeriodicReport
	 */
	public int getNumClosed() {
		return numClosed;
	}
	/**
	 * setNumClosed(int numClosed), method to Set numClosed of PeriodicReport
	 * @param numClosed : the method received a parameter with int type that
	 * describes the numClosed of the PeriodicReport
	 */
	public void setNumClosed(int numClosed) {
		this.numClosed = numClosed;
	}
	/**
	 * getnumRejected() : a method that returns a numClosed
	 * @return : numRejected of PeriodicReport
	 */
	public int getNumRejected() {
		return numRejected;
	}
	/**
	 * setNumRejected(int numRejected), method to Set numRejected of PeriodicReport
	 * @param numRejected : the method received a parameter with int type that
	 * describes the numRejected of the PeriodicReport
	 */
	public void setNumRejected(int numRejected) {
		this.numRejected = numRejected;
	}
	/**
	 * getNumTreatment() : a method that returns a numTreatment
	 * @return : numRejected of PeriodicReport
	 */
	public int getNumTreatment() {
		return numTreatment;
	}
	/**
	 * numRejected(int numTreatment), method to Set numTreatment of PeriodicReport
	 * @param numTreatment : the method received a parameter with int type that
	 * describes the numTreatment of the PeriodicReport
	 */
	public void setNumTreatment(int numTreatment) {
		this.numTreatment = numTreatment;
	}

}
