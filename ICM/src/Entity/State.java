package Entity;

import java.io.Serializable;
/**
  * enum State that describes the State of a specific request in phase
  * @author Arkan Muhammad
  */
public enum State implements Serializable {
wait,
waitingForApprove,
work,
over;
}
