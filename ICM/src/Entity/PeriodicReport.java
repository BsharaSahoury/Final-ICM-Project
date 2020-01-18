package Entity;

public class PeriodicReport {
private float median;
private float Std;
private float frequencyDistribution;
private int numActive;
private int numFrozen;
private int numClosed;
private int numRejected;
private int numTreatment;
public PeriodicReport(float median, float std, float frequencyDistribution, int numActive, int numFrozen, int numClosed,
		int numRejected, int numTreatment) {
	this.median = median;
	Std = std;
	this.frequencyDistribution = frequencyDistribution;
	this.numActive = numActive;
	this.numFrozen = numFrozen;
	this.numClosed = numClosed;
	this.numRejected = numRejected;
	this.numTreatment = numTreatment;
}
public float getMedian() {
	return median;
}
public void setMedian(float median) {
	this.median = median;
}
public float getStd() {
	return Std;
}
public void setStd(float std) {
	Std = std;
}
public float getFrequencyDistribution() {
	return frequencyDistribution;
}
public void setFrequencyDistribution(float frequencyDistribution) {
	this.frequencyDistribution = frequencyDistribution;
}
public int getNumActive() {
	return numActive;
}
public void setNumActive(int numActive) {
	this.numActive = numActive;
}
public int getNumFrozen() {
	return numFrozen;
}
public void setNumFrozen(int numFrozen) {
	this.numFrozen = numFrozen;
}
public int getNumClosed() {
	return numClosed;
}
public void setNumClosed(int numClosed) {
	this.numClosed = numClosed;
}
public int getNumRejected() {
	return numRejected;
}
public void setNumRejected(int numRejected) {
	this.numRejected = numRejected;
}
public int getNumTreatment() {
	return numTreatment;
}
public void setNumTreatment(int numTreatment) {
	this.numTreatment = numTreatment;
}


}
