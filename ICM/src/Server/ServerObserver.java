package Server;

import java.util.Observable;
import java.util.Observer;

public class ServerObserver implements Observer {
	public ServerObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg1 instanceof String) {
			String str=(String)arg1;
			System.out.println(str);
		}
	}

}
