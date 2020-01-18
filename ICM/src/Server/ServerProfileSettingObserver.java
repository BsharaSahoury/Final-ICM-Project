package Server;

import java.io.IOException;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;

public class ServerProfileSettingObserver implements Observer {
	public ServerProfileSettingObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Object[] args = null;
		if(arg instanceof Object[]) {
		args=(Object[])arg;
		ConnectionToClient client=(ConnectionToClient)args[0];
		if(args[1] instanceof String[]) {
			String[] Message=(String[])args[1];
			String keyMessage=Message[0];
			if(Message.length==3&&keyMessage.equals("ProfileSetting")) {
				System.out.println("ServerProfileSettingObserver **********\n\n***************");
				Connection con=mysqlConnection.makeAndReturnConnection();
				ArrayList<String> arr=mysqlConnection.getUserData(con,Message[1],Message[2]);
				Object[] send=new Object[3];
				send[0]="ProfileSetting";
				send[1]=arr;//profile setting of user
				send[2]=Message[2];//Job of user
			try {
				client.sendToClient(send);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			}
		}
		
	}
}


