package DBconnection;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

public interface IExtensionMangaer {

	ArrayList<Long> getPeriodricReportData(Connection con, String keymessage, Date from, Date to, String Rtype);

}
