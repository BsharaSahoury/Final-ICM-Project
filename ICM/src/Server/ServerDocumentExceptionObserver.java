package Server;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;

public class ServerDocumentExceptionObserver implements Observer {
	public ServerDocumentExceptionObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			ConnectionToClient client=(ConnectionToClient)arg2[0];
			if(arg2[1] instanceof Object[]) {
				Object[] arg3=(Object[])arg2[1];
				if(arg3[0] instanceof String) {
					String keymessage=(String)arg3[0];
					if(keymessage.equals("document")) {
						int id=(int)arg3[1];
						String phase=(String)arg3[2];
						int repetion=(int)arg3[3];
						String document=(String)arg3[4];
						mysqlConnection.addDocumentToException(MainForServer.con,id,phase,repetion,document);
						Object[] msg= {"document"};
						try {
							client.sendToClient(msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}

}
