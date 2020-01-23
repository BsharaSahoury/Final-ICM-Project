package Entity;

import java.io.Serializable;
/**
 * enum Phase that describes the Phase of a specific request
 * @author Arkan Muhammad
 *
 */
public enum Phase implements Serializable {
	evaluation,
	decision,
	performance,
	testing,
	closing;
}
