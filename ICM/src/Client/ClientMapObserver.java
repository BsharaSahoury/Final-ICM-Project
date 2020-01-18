package Client;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class ClientMapObserver implements Observer {
	public ClientMapObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			if(arg2[0] instanceof String) {
				String keymessage=(String)arg2[0];
				if(keymessage.equals("requests map")) {
					Map<Integer,String> map=(HashMap<Integer,String>)arg2[1];
					ClientConsole.map.putAll(map);
				}
			}
		}
		
	}

}
