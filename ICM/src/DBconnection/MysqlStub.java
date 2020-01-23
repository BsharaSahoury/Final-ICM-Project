package DBconnection;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

public class MysqlStub implements IExtensionMangaer{

	@Override
	public ArrayList<Long> getPeriodricReportData(Connection con, String keymessage, Date from, Date to, String Rtype) {

        
		return null;
	}
	

}
