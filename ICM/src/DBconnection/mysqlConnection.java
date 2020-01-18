package DBconnection;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Boundary.RequestTreatmentAction;
import Entity.Employee;
import Entity.EvaluationReport;
import Entity.ExtensionDuration;
import Entity.MyFile;
import Entity.Notification;
import Entity.Phase;
import Entity.Request;
import Entity.RequestPhase;
import Entity.State;
import Entity.Student;
import Entity.User;
import Server.MainForServer;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

public class mysqlConnection {
	private static int count = 0;
	private static int numOfReport = 0;
	private static String DB_PASSWORD;
	private static String DB_USERNAME;

	// this method creates and returns a connection to the relevant schema in the
	// database that we would like to work with
	public static Connection makeAndReturnConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}
		try {
			DB_USERNAME = Server.ServerController.get_DB_UserName();
			DB_PASSWORD = Server.ServerController.get_DB_Password();
			MainForServer.set_Connection(DriverManager.getConnection("jdbc:mysql://localhost/icm?serverTimezone=IST",
					DB_USERNAME, DB_PASSWORD));
			System.out.println("SQL connection succeed");
			return MainForServer.get_Connection();
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;

	}

	public static User isInDB(Connection con, String username, String password) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement(
					"SELECT user.username, user.password, user.loggedIn FROM user WHERE username=? AND password=?;");
			stm.setString(1, username);
			stm.setString(2, password);
			ResultSet rs = stm.executeQuery();
			// if the user is not an ICM-User
			if (!rs.next())
				return null;

			stm = con.prepareStatement("SELECT employee.* FROM employee WHERE username=?;");
			stm.setString(1, username);
			rs = stm.executeQuery();
			if (rs.next()) {
				Employee employee1 = new Employee(rs.getString(1), password, rs.getString(2), rs.getString(3),
						rs.getString(8));
				return employee1;
			}
			stm = con.prepareStatement("SELECT student.* FROM student WHERE username=?;");
			stm.setString(1, username);
			rs = stm.executeQuery();
			if (rs.next()) {
				Student student1 = new Student(rs.getString(1), password, rs.getString(2), rs.getString(3));
				return student1;
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void updateUSerLoggedInToYes(Connection con, String username, String password) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("UPDATE user SET loggedIn='yes' WHERE username=? AND password=?;");
			stm.setString(1, username);
			stm.setString(2, password);
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String IsConnectedByAnotherClient(Connection con, String username, String password) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("SELECT user.loggedIn FROM user WHERE username=? AND password=?;");
			stm.setString(1, username);
			stm.setString(2, password);
			ResultSet rs = stm.executeQuery();

			// if the user is not an ICM-User
			if (!rs.next())
				return "false";
			// checking if the userAccount is Already logged-in
			if (rs.getString(1).equals("yes"))
				return "true";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "false";
	}

	public static void SetAllUsersLoginToNo(Connection con) {

		String username, password;
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("SELECT user.username, user.password FROM user;");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				stm = con.prepareStatement("UPDATE user SET loggedIn='no' WHERE username=? AND password=?;");
				username = rs.getString(1);
				password = rs.getString(2);
				stm.setString(1, username);
				stm.setString(2, password);
				stm.executeUpdate();
			} // while
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String logOutUser(Connection con, String username, String password, String endProgram) {
		PreparedStatement stm = null;
		String res = "true";
		try {
			// Logged-In ='yes'
			stm = con.prepareStatement("UPDATE user SET loggedIn='no' WHERE username=? AND password=?;");
			stm.setString(1, username);
			stm.setString(2, password);
			stm.executeUpdate();
			if (endProgram.equals("End"))// Exit program => true1
				res = "true1";
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = "false";
			return res;
		}
	}

	public static ArrayList<String> getUserData(Connection con, String username, String userJob) {
		PreparedStatement stm = null;
		ResultSet rs;
		String id = null, USERname = null, fullname = null, email = null, faculty = null, role = null;
		ArrayList<String> res = new ArrayList<String>();
		try {
			if (userJob.equals("Student")) {
				stm = con.prepareStatement("SELECT student.* FROM student WHERE username=?;");
				stm.setString(1, username);
				rs = stm.executeQuery();
				if (rs.next()) {// data
					USERname = rs.getString(1);
					fullname = rs.getString(2) + " " + rs.getString(3);
					id = rs.getString(4);
					email = rs.getString(5);
					faculty = rs.getString(6);// faculty
				}
				// res={id,USERname,fullname,email,faculty};
				res.add(id);
				res.add(USERname);
				res.add(fullname);
				res.add(email);
				res.add(faculty);
				res.add("Student");
			} else// User is an employee
			{
				stm = con.prepareStatement("SELECT employee.* FROM employee WHERE username=?;");
				stm.setString(1, username);
				rs = stm.executeQuery();
				if (rs.next()) {
					USERname = rs.getString(1);
					fullname = rs.getString(2) + " " + rs.getString(3);
					email = rs.getString(4);
					faculty = rs.getString(6);// faculty
					id = rs.getString(7);
					role = rs.getString(8);

					// res= {id,username,fullname,email,faculty};
					// res={id,USERname,fullname,email,faculty};
					res.add(id);
					res.add(USERname);
					res.add(fullname);
					res.add(email);
					res.add(faculty);
					res.add(role);

				}
			} // else
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static String getinitiatorname(Connection con, String username) {
		PreparedStatement stmt2 = null;
		String Initiatorname = null;
		try {
			stmt2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
			stmt2.setString(1, username);
			ResultSet rs2 = stmt2.executeQuery();
			if (rs2.next())
				Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
			if (Initiatorname == null) {
				stmt2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
				stmt2.setString(1, username);
				rs2 = stmt2.executeQuery();
				if (rs2.next())
					Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
			}
			if (Initiatorname != null)
				rs2.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Initiatorname;
	}

	public static ArrayList<RequestPhase> getDataFromDB(Connection con) {
		String[] phases = { "closing", "testing", "performance", "decision", "evaluation" };
		String Initiatorname = null;
		ArrayList<RequestPhase> arr = new ArrayList<RequestPhase>();
		Statement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		Request s = null;
		RequestPhase result = null;
		int i = 0;
		try {
			stmt1 = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ResultSet rs = stmt1.executeQuery("SELECT R.* FROM icm.request R ORDER BY id DESC;");
			while (rs.next()) {
				Initiatorname = getinitiatorname(con, rs.getString(9));
				stmt4 = con.prepareStatement(
						"SELECT E.phase,E.state,E.repetion FROM icm.requestinphase E WHERE request_id=? AND phase=? ORDER BY request_id DESC;");
				stmt4.setInt(1, rs.getInt(7));
				stmt4.setString(2, phases[i]);
				ResultSet rs4 = stmt4.executeQuery();
				if (rs4.next()) {
					i = -1;
					s = new Request(rs.getInt(7), Initiatorname, rs.getString(8), rs.getString(1), rs.getDate(6));
					result = new RequestPhase(null, null, s, Phase.valueOf(rs4.getString(1)),
							State.valueOf(rs4.getString(2)));
					Employee inspecotor = getInspector(con);
					result.setEmployee(inspecotor.getUsername());
					result.setRepetion(rs4.getInt(3));
					rs4.close();
				} else {
					do {
						i++;
						stmt3 = con.prepareStatement(
								"SELECT E.phase,E.state,E.repetion,E.phase_administrator,E.start_date,E.due_date FROM icm.requestinphase E WHERE request_id=? AND phase=? AND state!='over' ORDER BY request_id DESC;");
						stmt3.setInt(1, rs.getInt(7));
						stmt3.setString(2, phases[i]);
						ResultSet rs3 = stmt3.executeQuery();
						if (rs3.next()) {
							i = -1;
							s = new Request(rs.getInt(7), Initiatorname, rs.getString(8), rs.getString(1),
									rs.getDate(6));
							result = new RequestPhase(null, null, s, Phase.valueOf(rs3.getString(1)),
									State.valueOf(rs3.getString(2)));
							result.setEmployee(rs3.getString(4));
							result.setRepetion(rs3.getInt(3));
							result.setStartDate(rs3.getDate(5));
							result.setDueDate(rs3.getDate(6));
							rs3.close();
						}
					} while (i != -1 && i != 4);
				}
				if (result != null) {
					arr.add(result);
				}
				stmt2 = null;
				stmt3 = null;
				stmt4 = null;
				s = null;
				result = null;
				i = 0;
				Initiatorname = null;
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	public static ArrayList<RequestPhase> getRequestsWorkOn(Connection con, String username, String phase) {
		String Initiatorname = null;
		ArrayList<RequestPhase> arr = new ArrayList<RequestPhase>();
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		Request s = null;
		RequestPhase result = null;
		int count = 0;
		try {
			stmt1 = con.prepareStatement(
					"SELECT DISTINCT R.request_id FROM icm.requestinphase R WHERE phase_administrator=? AND phase=?;");
			stmt1.setString(1, username);
			stmt1.setString(2, phase);
			ResultSet rs = stmt1.executeQuery();
			while (rs.next()) {
				count++;
				stmt2 = con.prepareStatement(
						"SELECT R.* FROM icm.requestinphase R WHERE phase_administrator=? AND phase=? AND request_id=?;");
				stmt2.setString(1, username);
				stmt2.setString(2, phase);
				stmt2.setInt(3, rs.getInt(1));
				ResultSet rs2 = stmt2.executeQuery();
				int max = 0;
				while (rs2.next()) {
					if (rs2.getInt(3) >= max)
						max = rs2.getInt(3);
				}
				rs2.close();
				stmt3 = con.prepareStatement(
						"SELECT R.* FROM icm.requestinphase R WHERE phase_administrator=? AND phase=? AND request_id=? AND repetion=?;");
				stmt3.setString(1, username);
				stmt3.setString(2, phase);
				stmt3.setInt(3, rs.getInt(1));
				stmt3.setInt(4, max);
				ResultSet rs3 = stmt3.executeQuery();
				if (rs3.next()) {
					try {
						stmt4 = con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=? ORDER BY id DESC;");
						stmt4.setInt(1, rs.getInt(1));
						ResultSet rs4 = stmt4.executeQuery();
						if (rs4.next()) {
							Initiatorname = getinitiatorname(con, rs4.getString(9));
							if (Initiatorname != null) {
								s = new Request(rs4.getInt(7), Initiatorname, rs4.getString(8), rs4.getString(1),
										rs4.getDate(6));
								result = new RequestPhase(null, null, s, Phase.valueOf(rs3.getString(2)),
										State.valueOf(rs3.getString(7)));
								result.setRepetion(max);
								arr.add(result);
							}
						}
						stmt3 = null;
						stmt4 = null;
						rs4.close();
						rs3.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (count > 0)
			return arr;
		else
			return null;
	}

	public static ArrayList<Request> getmyRequestFromDB(Connection con, String username) {
		String Initiatorname = null;
		ArrayList<Request> arr = new ArrayList<Request>();
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		Request s = null;
		try {
			stmt1 = con.prepareStatement("SELECT R.* FROM icm.request R WHERE initiator_username=? ORDER BY id DESC;");
			stmt1.setString(1, username);
			ResultSet rs = stmt1.executeQuery();
			while (rs.next()) {
				stmt2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
				stmt2.setString(1, username);
				ResultSet rs2 = stmt2.executeQuery();
				if (rs2.next())
					Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
				if (Initiatorname == null) {
					stmt2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
					stmt2.setString(1, rs.getString(9));
					rs2 = stmt2.executeQuery();
					if (rs2.next())
						Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
				}
				if (Initiatorname != null) {
					s = new Request(rs.getInt(7), Initiatorname, rs.getString(8), rs.getString(1), rs.getDate(6));
					arr.add(s);
				}
				stmt2 = null;
				s = null;
				rs2.close();
			}
			rs.close();
		} catch (SQLException e) {
//TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arr;
	}

//this function make the update done by the client in the database
	public static void UpdateUserInDB(Request r, Connection con) {
		if (con != null) {
			try {
				// String id=r.getId();
				PreparedStatement stm = con.prepareStatement("UPDATE requirement SET status=? WHERE ID=?;");
				stm.setString(1, r.getStatus());
				// stm.setString(2, id);
				stm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Request insertRequestToDB(Connection con, Request request) {
		PreparedStatement stm = null;
		PreparedStatement stm1 = null;
		Statement st = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(request.id) FROM request;");
			if (rs.next()) {
				count = rs.getInt(1) + 1;
			} else
				count = 0;
			/*
			 * long s=request.getDate().getTime()+(int) (1000 * 60 * 60 * 24 );
			 * request.setDate(new java.sql.Date(s));
			 */
			stm = con.prepareStatement("INSERT INTO request VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);");
			stm.setString(1, request.getPrivilegedInfoSys());
			stm.setString(2, request.getExistingSituation());
			stm.setString(3, request.getExplainRequest());
			stm.setString(4, request.getReason());
			stm.setString(5, request.getComment());
			stm.setDate(6, request.getDate());
			stm.setInt(7, count);
			request.setId(count);
			stm.setString(8, "active");
			stm.setString(9, request.getInitiator().getUsername());
			stm.setString(12, "evaluation");
			stm.setDate(13, null);
			if (request.getMyFile() == null) {
				stm.setBytes(10, null);
				stm.setString(11, null);
			} else {
				stm.setBytes(10, request.getMyFile().getMybyterray());
				stm.setString(11, request.getFilename());
			}
			stm.executeUpdate();
			mysqlConnection.updateCurrentPhase(con, request.getId(), Phase.evaluation);
			stm1 = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
			stm1.setInt(1, request.getId());
			stm1.setString(2, "evaluation");
			stm1.setInt(3, 0);
			stm1.setDate(4, null);
			stm1.setDate(5, null);
			stm1.setString(6, null);
			stm1.setString(7, "wait");
			stm1.executeUpdate();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return request;
	}

	public static void insertRecruitNotificationToDB(Connection con, Request newRequest) {
		PreparedStatement stm = null;
		Statement st = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(notification.id) FROM notification;");
			if (rs.next()) {
				count = rs.getInt(1) + 1;
			} else
				count = 0;
			stm = con.prepareStatement("INSERT INTO notification VALUES(?,?,?,?);");
			stm.setInt(1, count);
			stm.setString(2, "You've been recruited to evaluate request#" + newRequest.getId());
			stm.setDate(3, newRequest.getDate());
			stm.setString(4, "automatic recruit");
			stm.executeUpdate();
			stm = con.prepareStatement("SELECT employee.username FROM employee WHERE support_system=?;");
			stm.setString(1, newRequest.getPrivilegedInfoSys());
			rs = stm.executeQuery();
			if (rs.next()) {
				String username = rs.getString(1);
				stm = con.prepareStatement("INSERT INTO notificationforuser VALUES(?,?);");
				stm.setInt(1, count);
				stm.setString(2, username);
				stm.executeUpdate();
			}

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<Notification> getNotificationsForUser(Connection con, String username) {
		PreparedStatement stm = null;
		ArrayList<Notification> Nlist = new ArrayList<>();
		try {
			stm = con.prepareStatement(
					"SELECT notification.* FROM notification,notificationforuser WHERE notification.id=notificationforuser.notification_id AND notificationforuser.username=? ORDER BY id DESC;");
			stm.setString(1, username);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Notification n = new Notification(rs.getString(2), rs.getDate(3), rs.getString(4));
				n.setId(rs.getInt(1));
				Nlist.add(n);
			}
			return Nlist;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Employee getAutomaticRecruit(Connection con, String privilegedInfoSys) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("SELECT employee.* FROM employee WHERE job='evaluator' AND support_system=?;");
			stm.setString(1, privilegedInfoSys);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Notification insertNotificationToDB(Connection con, Notification n1) {
		PreparedStatement stm = null;
		Statement st = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(notification.id) FROM notification;");
			if (rs.next()) {
				count = rs.getInt(1) + 1;
			} else
				count = 0;
			stm = con.prepareStatement("INSERT INTO notification VALUES(?,?,?,?);");
			stm.setInt(1, count);
			stm.setString(2, n1.getContent());
			stm.setDate(3, n1.getDate());
			stm.setString(4, n1.getType());
			stm.executeUpdate();
			n1.setId(count);
			return n1;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void insertRecruitNotificationForInspectorToDB(Connection con, Notification n1) {
		Statement st = null;
		PreparedStatement stm = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT employee.username FROM employee WHERE job='inspector';");
			if (rs.next()) {
				String username = rs.getString(1);
				stm = con.prepareStatement("INSERT INTO notificationforuser VALUES(?,?);");
				stm.setInt(1, n1.getId());
				stm.setString(2, username);
				stm.executeUpdate();
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Employee recruitAutomatically(Connection con, int id) {
		Statement st = null;
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("SELECT request.Privileged_information_system FROM request WHERE id=?;");
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				String privilegedSystem = rs.getString(1);
				Employee evaluator = mysqlConnection.getAutomaticRecruit(con, privilegedSystem);
				mysqlConnection.newassignEmployee(con, id, evaluator, "evaluation");
				return evaluator;
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void newassignEmployee(Connection con, int id, Employee employee, String phase) {
		PreparedStatement stm = null;
		PreparedStatement stm1 = null;
		PreparedStatement stm4 = null;
		int maxRepetion = 0;
		try {
			stm1 = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
			stm1.setInt(1, id);
			stm1.setString(2, phase);
			ResultSet rs = stm1.executeQuery();
			if (rs.next())
				maxRepetion = rs.getInt(1);
			stm = con.prepareStatement(
					"SELECT * FROM icm.requestinphase WHERE request_id=? AND phase=? AND repetion=? AND phase_administrator IS NULL");
			stm.setInt(1, id);
			stm.setString(2, phase);
			stm.setInt(3, maxRepetion);
			ResultSet rs3 = stm.executeQuery();
			if (rs3.next()) {
				stm4 = con.prepareStatement(
						"UPDATE requestinphase SET phase_administrator=? WHERE request_id=? AND phase=? AND repetion=?;");
				stm4.setInt(2, id);
				stm4.setString(3, phase);
				stm4.setInt(4, 0);
				stm4.setString(1, employee.getUsername());
				stm4.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean assignEmployeeToPhaseRequest(Connection con, Employee employee, int id, String phase) {
		PreparedStatement stm = null;
		PreparedStatement stm2 = null;
		PreparedStatement stm3 = null;
		try {
			int Max = 0;
			stm2 = con.prepareStatement("SELECT R.repetion FROM icm.requestinphase R WHERE request_id=? AND phase=?;");
			stm2.setInt(1, id);
			stm2.setString(2, phase);
			ResultSet rs = stm2.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) >= Max)
					Max = rs.getInt(1);
			}
			stm3 = con.prepareStatement(
					"SELECT R.phase_administrator FROM icm.requestinphase R WHERE request_id=? AND phase=? AND repetion=?;");
			stm3.setInt(1, id);
			stm3.setString(2, phase);
			stm3.setInt(3, Max);
			ResultSet rs3 = stm3.executeQuery();
			rs3.next();
			if (rs3.getString(1) != null) {
				return false;
			} else {
				stm = con.prepareStatement(
						"UPDATE requestinphase SET phase_administrator=? WHERE request_id=? AND phase=? AND repetion=?;");
				stm.setString(1, employee.getUsername());
				stm.setInt(2, id);
				stm.setString(3, phase);
				stm.setInt(4, Max);
				stm.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean assignPerformerToRequest(Connection con, Employee Performer, int id) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
			stm.setInt(1, id);
			stm.setString(2, "performance");
			Statement st = null;
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(request.id) FROM request;");
			if (rs.next()) {
				count = rs.getInt(1) + 1;
			} else
				count = 0;
			stm.setInt(3, count);
			stm.setDate(4, null);
			stm.setDate(5, null);
			stm.setString(6, Performer.getUsername());
			stm.setString(7, "wait");
			stm.executeUpdate();
			return true;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public static ArrayList<Employee> getEmployees(Connection con, String job) {
		Employee employee;
		ArrayList<Employee> list = new ArrayList<>();
		PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT employee.* FROM employee WHERE job=?;");
			st.setString(1, job);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				employee = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
				list.add(employee);
			}
			return list;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Employee getSpecificEmployee(Connection con, String fullname) {
		PreparedStatement stm = null;
		String[] name = new String[2];
		name = fullname.split(" ");
		try {
			stm = con.prepareStatement("SELECT employee.* FROM employee WHERE first_name=? AND last_name=?;");
			stm.setString(1, name[0]);
			stm.setString(2, name[1]);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void insertNotificationForUserToDB(Connection con, Notification n1, User employee) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("INSERT INTO notificationforuser VALUES(?,?);");
			stm.setInt(1, n1.getId());
			stm.setString(2, employee.getUsername());
			stm.executeUpdate();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Request getRequestInfo(Connection con, int id) {
		PreparedStatement stm1 = null;
		PreparedStatement stm2 = null;
		String Initiatorname = null;
		String role = null;
		Request r = null;
		try {
			stm1 = con.prepareStatement("SELECT R.* FROM icm.request R WHERE id=?;");
			stm1.setInt(1, id);
			ResultSet rs = stm1.executeQuery();

			while (rs.next()) {
				String username = rs.getString(9);
				stm2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
				stm2.setString(1, username);
				ResultSet rs2 = stm2.executeQuery();
				if (rs2.next())
					Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
				role = rs2.getString(4);
				if (Initiatorname == null) {
					stm2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
					stm2.setString(1, rs.getString(9));
					rs2 = stm2.executeQuery();
					if (rs2.next())
						Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
					role = "student";
				}
				if (Initiatorname != null) {
					if (rs.getBytes(10) == null) {
						r = new Request(rs.getInt(7), Initiatorname, role, rs2.getString(8), rs.getString(8),
								rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5),
								rs.getDate(6), new MyFile(), null);
					} else {
						MyFile myfile = new MyFile();
						myfile.setMybytearray(rs.getBytes(10));
						r = new Request(rs.getInt(7), Initiatorname, role, rs2.getString(8), rs.getString(8),
								rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5),
								rs.getDate(6), myfile, rs.getString(11));
					}

				}
				rs2.close();
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;

	}

	public static RequestPhase getRequestTrack(Connection con, int id) {
		PreparedStatement stmRP = null;
		PreparedStatement stmR = null;
		PreparedStatement stm = null;
		int maxRepetion = 0;
		String currentPhase = null;
		RequestPhase rp = null;
		Request r = null;
		try {
			stmR = con.prepareStatement("SELECT R.* FROM icm.request R WHERE id=?;");
			stmR.setInt(1, id);
			ResultSet rs1 = stmR.executeQuery();
			if (rs1.next()) {
				currentPhase = rs1.getString(12);
				r = new Request(rs1.getInt(7), rs1.getString(9), rs1.getString(8), rs1.getString(1), rs1.getDate(6),
						Enum.valueOf(Phase.class, rs1.getString(12)));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
			stm.setInt(1, id);
			stm.setString(2, currentPhase);
			ResultSet rs = stm.executeQuery();
			if (rs.next())
				maxRepetion = rs.getInt(1);
			stmRP = con.prepareStatement(
					" SELECT RP.* FROM icm.requestinphase RP where request_id=? AND phase=? AND repetion=?;");
			stmRP.setInt(1, id);
			stmRP.setString(2, currentPhase);
			stmRP.setInt(3, maxRepetion);
			ResultSet rs2 = stmRP.executeQuery();
			if (rs2.next())
				rp = new RequestPhase(rs2.getDate(4), rs2.getDate(5), r, Enum.valueOf(Phase.class, rs2.getString(2)),
						Enum.valueOf(State.class, rs2.getString(7)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rp;
	}

	public static boolean insertDate(Connection con, int id, LocalDate[] d, Phase p) {

		/*
		 * d[0] = d[0].replaceAll("(\\r|\\n)", ""); d[1] = d[1].replaceAll("(\\r|\\n)",
		 * "");
		 */
		PreparedStatement stm = null;
		Statement st = null;
		int maxRepetion = 0;
		Date start = Date.valueOf(d[0]);
		Date due = Date.valueOf(d[1]);
		if (getStatus(con, id).equals("frozen")) {
			return false;

		}
		try {
			long s = start.getTime() + (int) (1000 * 60 * 60 * 24);
			start = new java.sql.Date(s);
			long dd = due.getTime() + (int) (1000 * 60 * 60 * 24);
			due = new java.sql.Date(dd);
			stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
			stm.setInt(1, id);

			stm.setString(2, p.toString());
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				maxRepetion = rs.getInt(1);
				PreparedStatement stm1 = con.prepareStatement(
						"UPDATE icm.requestinphase" + " SET start_date = ?, due_date = ?,state='waitingForApprove' "
								+ "WHERE (request_id = ? and phase=? and repetion=?);");
				stm1.setDate(1, start);
				stm1.setDate(2, due);
				stm1.setInt(3, id);
				stm1.setString(4, p.toString());
				stm1.setInt(5, maxRepetion);
				stm1.executeUpdate();
				// mysqlConnection.updateCurrentPhase(con, id, Phase.evaluation);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static Employee FindEmployee(Connection con, int id, String phase) {
		PreparedStatement stmR = null;
		PreparedStatement stmt = null;
		RequestPhase rp = null;
		Request r = null;
		try {
			try {
				PreparedStatement stm = con.prepareStatement("UPDATE requestinphase SET state=? WHERE request_id=?;");
				stm.setString(1, "over");
				stm.setInt(2, id);
				stm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmR = con.prepareStatement(
					"SELECT R.phase_administrator FROM icm.requestinphase R WHERE request_id=? AND phase=?;");
			stmR.setInt(1, id);
			stmR.setString(2, phase);
			ResultSet rs = stmR.executeQuery();
			if (rs.next()) {
				stmt = con.prepareStatement("SELECT R.* FROM icm.employee R WHERE username=?;");
				stmt.setString(1, rs.getString(1));
				ResultSet rs2 = stmt.executeQuery();
				if (rs2.next()) {
					return new Employee(rs2.getString(1), rs2.getString(2), rs2.getString(3), rs2.getString(8));

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Employee getChairman(Connection con) {
		Statement st = null;
		Employee Chairman = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT employee.* FROM employee WHERE job='chairman';");
			if (rs.next())
				Chairman = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
			return Chairman;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void updatePerfomanceFinishedInDB(Connection con, int id) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("UPDATE requestinphase SET state='over' WHERE request_id=?;");
			stm.setInt(1, id);
			stm.executeUpdate();
			long millis = System.currentTimeMillis();
			Date s = new java.sql.Date(millis);
			long week = s.getTime() + (int) (1000 * 60 * 60 * 24 * 7);
			Date d = new java.sql.Date(week);
			stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
			stm.setInt(1, id);
			stm.setString(2, "testing");
			stm.setInt(3, 0);
			stm.setDate(4, s);
			stm.setDate(5, d);
			stm.setString(6, null);
			stm.setString(7, "wait");
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateDBdueToFailTest(Connection con, int requestId) {
		PreparedStatement stm = null;
		int maxRepetion;
		try {
			stm = con
					.prepareStatement("UPDATE requestinphase SET state='over' WHERE phase='testing' AND request_id=?;");
			stm.setInt(1, requestId);
			stm.executeUpdate();
			stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase WHERE request_id=? AND phase='performance';");
			stm.setInt(1, requestId);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				maxRepetion = rs.getInt(1) + 1;
			} else {
				maxRepetion = 0;
			}
			stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
			stm.setInt(1, requestId);
			stm.setString(2, "performance");
			stm.setInt(3, maxRepetion);
			stm.setDate(4, null);
			stm.setDate(5, null);
			stm.setString(6, null);
			stm.setString(7, "wait");
			stm.executeUpdate();
			mysqlConnection.updateCurrentPhase(con, requestId, Phase.performance);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sendFailDetailsToInspector(Connection con, Notification n) {
		Statement st = null;
		Employee inspector = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT username,first_name,last_name FROM employee WHERE job='inspector';");
			if (rs.next()) {
				inspector = new Employee(rs.getString(1), rs.getString(2), rs.getString(3));
			}
			mysqlConnection.insertNotificationForUserToDB(con, n, inspector);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sendExtensionConfiramtionToInspector(Connection con, Notification n) {
		Statement st = null;
		Employee inspector = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT username,first_name,last_name FROM employee WHERE job='inspector';");
			if (rs.next()) {
				inspector = new Employee(rs.getString(1), rs.getString(2), rs.getString(3));
			}
			mysqlConnection.insertNotificationForUserToDB(con, n, inspector);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sendExtensionConfiramtionToAdmin(Connection con, Notification n) {
		Statement st = null;
		Employee Admin = null;
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT username,first_name,last_name FROM employee WHERE job='administrator';");
			if (rs.next()) {
				Admin = new Employee(rs.getString(1), rs.getString(2), rs.getString(3));
			}
			mysqlConnection.insertNotificationForUserToDB(con, n, Admin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void updateDBdueToSuccessTest(Connection con, int requestId) {

		PreparedStatement stm = null;
		int maxRepetion = 0;
		try {

			stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase WHERE request_id=? AND phase='testing';");
			stm.setInt(1, requestId);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				maxRepetion = rs.getInt(1);
			}
			stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
			stm.setInt(1, requestId);
			stm.setString(2, "closing");
			stm.setInt(3, 0);
			stm.setDate(4, null);
			stm.setDate(5, null);
			stm.setString(6, null);
			stm.setString(7, "wait");
			stm.executeUpdate();
			stm = con.prepareStatement(
					"UPDATE requestinphase SET state='over' WHERE phase='testing' AND request_id=? AND repetion=?;");
			stm.setInt(1, requestId);
			stm.setInt(2, maxRepetion);
			stm.executeUpdate();
			mysqlConnection.updateCurrentPhase(con, requestId, Phase.closing);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addRequestToDB(Connection con, int id, String dec) {
		PreparedStatement stm = null;
		PreparedStatement stm2 = null;
		PreparedStatement stm3 = null;
		try {
			if (dec.equals("approve")) {
				int Max = 0;
				stm2 = con.prepareStatement(
						"SELECT R.repetion FROM icm.requestinphase R WHERE request_id=? AND phase=?;");
				stm2.setInt(1, id);
				stm2.setString(2, "performance");
				ResultSet rs = stm2.executeQuery();
				while (rs.next()) {
					if (rs.getInt(1) >= Max)
						Max = rs.getInt(1) + 1;
				}
				stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
				stm.setInt(1, id);
				stm.setString(2, "performance");
				stm.setInt(3, Max);
				stm.setString(4, null);
				stm.setString(5, null);
				stm.setString(6, null);
				stm.setString(7, "wait");
				stm.executeUpdate();
				mysqlConnection.updateCurrentPhase(con, id, Phase.performance);

				PreparedStatement stm5 = con.prepareStatement(
						"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
				stm5.setInt(1, id);
				stm5.setString(2, "decision");
				ResultSet rs2 = stm5.executeQuery();
				if (rs2.next()) {
					stm3 = con.prepareStatement(
							"UPDATE icm.requestinphase SET state='over' WHERE request_id = ? and phase='decision' and repetion=?;");
					stm3.setInt(1, id);
					stm3.setInt(2, rs2.getInt(1));
					stm3.executeUpdate();
				}
			} else if (dec.equals("reject")) {
			
					stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
					stm.setInt(1, id);
					stm.setString(2, "closing");
					stm.setInt(3, 0);
					stm.setString(4, null);
					stm.setString(5, null);
					stm.setString(6, null);
					stm.setString(7, "wait");
					stm.executeUpdate();
					PreparedStatement stm6 = con.prepareStatement(
							"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
					stm6.setInt(1, id);
					stm6.setString(2, "decision");
					ResultSet rs3 = stm6.executeQuery();
					System.out.println("llll");
					System.out.println(id);
					if (rs3.next()) {
						System.out.println("ggg");
						stm3 = con.prepareStatement(
								"UPDATE icm.requestinphase SET state='over' WHERE request_id = ? and phase='decision' and repetion=?;");
						stm3.setInt(1, id);
						stm3.setInt(2, rs3.getInt(1));
						stm3.executeUpdate();
					
					mysqlConnection.updateCurrentPhase(con, id, Phase.closing);
				}
			} else {
				int Max1 = 0;
				stm2 = con.prepareStatement(
						"SELECT R.repetion FROM icm.requestinphase R WHERE request_id=? AND phase=?;");
				stm2.setInt(1, id);
				stm2.setString(2, "evaluation");
				ResultSet rs5 = stm2.executeQuery();
				while (rs5.next()) {
					if (rs5.getInt(1) >= Max1)
						Max1 = rs5.getInt(1);
				}
				stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
				stm.setInt(1, id);
				stm.setString(2, "evaluation");
				stm.setInt(3, Max1 + 1);
				stm.setString(4, null);
				stm.setString(5, null);
				stm.setString(6, null);
				stm.setString(7, "wait");
				stm.executeUpdate();
				PreparedStatement stm7 = con.prepareStatement(
						"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
				stm7.setInt(1, id);
				stm7.setString(2, "decision");
				ResultSet rs9 = stm7.executeQuery();
				if (rs9.next()) {
					stm3 = con.prepareStatement(
							"UPDATE icm.requestinphase SET state='over' WHERE request_id = ? and phase='decision' and repetion=?;");
					stm3.setInt(1, id);
					stm3.setInt(2, rs9.getInt(1));
					stm3.executeUpdate();
				}
				mysqlConnection.updateCurrentPhase(con, id, Phase.evaluation);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insertNotificationDetailsToDB(Connection con, Notification n1, String details) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("INSERT INTO notificationdetails VALUES(?,?);");
			stm.setInt(1, n1.getId());
			stm.setString(2, details);
			stm.executeUpdate();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getnotificationdetails(Connection con, int id) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("SELECT R.Details FROM icm.notificationdetails R WHERE notification_id=?;");
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next())
				return rs.getString(1);
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void insertReport(Connection con, EvaluationReport er) {
		PreparedStatement stm1 = null;
		Statement st = null;
		int maxRepetion = 0;
		try {
			PreparedStatement stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=?;");
			stm.setInt(1, er.getRequestID());
			ResultSet rs1 = stm.executeQuery();
			if (rs1.next()) {
				maxRepetion = rs1.getInt(1);
			}
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(evaluationreport.number) FROM evaluationreport;");
			if (rs.next()) {
				numOfReport = rs.getInt(1) + 1;
			} else
				numOfReport = 0;
			stm1 = con.prepareStatement("INSERT INTO icm.evaluationreport VALUES(?,?,?,?,?,?,?,?);");
			stm1.setInt(1, numOfReport);
			er.setId(numOfReport);
			stm1.setString(2, er.getLocation());
			stm1.setString(3, er.getDescription());
			stm1.setString(4, er.getExpectedResult());
			stm1.setString(5, er.getConstraints());
			stm1.setString(6, er.getRisks());
			stm1.setInt(7, er.getEstimatedPerfomanceDuration());
			stm1.setInt(8, er.getRequestID());
			stm1.executeUpdate();
			PreparedStatement stm2 = con.prepareStatement(
					"UPDATE icm.requestinphase SET state='over' WHERE request_id = ? and phase='evaluation' and repetion=?;");
			stm2.setInt(1, er.getRequestID());
			stm2.setInt(2, maxRepetion);
			stm2.executeUpdate();
			long millis = System.currentTimeMillis();
			Date Startdate = new java.sql.Date(millis);
			long week = Startdate.getTime() + (int) (1000 * 60 * 60 * 24 * 7);
			Date dueDate = new java.sql.Date(week);
			PreparedStatement stm3 = con.prepareStatement("INSERT INTO icm.requestinphase  VALUES(?,?,?,?,?,?,?) ");
			stm3.setInt(1, er.getRequestID());
			stm3.setString(2, "decision");
			stm3.setInt(3, maxRepetion);
			stm3.setDate(4, Startdate);
			stm3.setDate(5, dueDate);
			Employee chairman = mysqlConnection.getChairman(con);
			stm3.setString(6, chairman.getUsername());
			stm3.setString(7, "work");
			stm3.executeUpdate();
			mysqlConnection.updateCurrentPhase(con, er.getRequestID(), Phase.decision);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static RequestPhase getRequestPhase(Connection con, int id, String phase) {
		RequestPhase rp = null;
		PreparedStatement stm1 = null;
		PreparedStatement stm = null;
		int maxRepetion = 0;
		try {
			stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? and phase=?;");
			stm.setInt(1, id);
			stm.setString(2, phase);
			ResultSet rs1 = stm.executeQuery();
			if (rs1.next()) {
				maxRepetion = rs1.getInt(1);
			}
			stm1 = con.prepareStatement(
					"SELECT  requestinphase.* FROM icm.requestinphase where request_id=? and phase=? and repetion=?;");
			stm1.setInt(1, id);
			stm1.setString(2, phase);
			stm1.setInt(3, maxRepetion);
			ResultSet rs2 = stm1.executeQuery();
			if (rs2.next()) {

				rp = new RequestPhase(id, rs2.getDate(4), rs2.getDate(5), Enum.valueOf(Phase.class, phase),
						Enum.valueOf(State.class, rs2.getString(7)), rs2.getString(6));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return rp;
	}

	public static void getToWork(Connection con, Date today) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("UPDATE requestinphase SET state='work' WHERE state='wait' AND start_date=?;");
			stm.setDate(1, Date.valueOf(today.toLocalDate().plusDays(1)));
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<RequestPhase> detectExceptions(Connection con, Date today) {
		PreparedStatement stm = null;
		PreparedStatement stm1 = null;
		ArrayList<RequestPhase> list = new ArrayList<>();
		try {
			stm = con.prepareStatement(
					"SELECT request_id,phase,repetion FROM requestinphase WHERE state='work' AND due_date<?;");
			stm.setDate(1, today);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				stm1 = con.prepareStatement(
						"SELECT request_id FROM exception WHERE request_id=? AND phase=? AND repetion=?");
				stm1.setInt(1, rs.getInt(1));
				stm1.setString(2, rs.getString(2));
				stm1.setInt(3, rs.getInt(3));
				ResultSet rs1 = stm1.executeQuery();
				if (!rs1.next()) {
					stm = con.prepareStatement(
							"INSERT INTO exception (date,phase,request_id,repetion) VALUES(?,?,?,?);");
					stm.setDate(1, today);
					stm.setString(2, rs.getString(2));
					stm.setInt(3, rs.getInt(1));
					stm.setInt(4, rs.getInt(3));
					stm.executeUpdate();
					RequestPhase rp = new RequestPhase(rs.getInt(1), Enum.valueOf(Phase.class, rs.getString(2)),
							rs.getInt(3));
					list.add(rp);
				}

			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static Employee getAdmin(Connection con) {
		Statement st = null;
		Employee admin = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT employee.* FROM employee WHERE job='administrator';");
			if (rs.next())
				admin = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
			return admin;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static Employee getPhaseAdministrator(Connection con, int id, Phase phase, int repetion) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement(
					"SELECT E.* FROM employee E,requestinphase P WHERE E.username=P.phase_administrator AND P.request_id=? AND P.phase=? AND P.repetion=?;");
			stm.setInt(1, id);
			stm.setString(2, phase.toString());
			stm.setInt(3, repetion);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				Employee em = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
				return em;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<RequestPhase> findWhatNeedsDocument(Connection con, Date today) {
		PreparedStatement stm = null;
		PreparedStatement stm1 = null;
		ArrayList<RequestPhase> list = new ArrayList<>();
		try {
			stm = con.prepareStatement(
					"SELECT X.request_id,X.phase,X.repetion,X.date FROM exception X,requestinphase P WHERE X.request_id=P.request_id AND X.phase=P.phase AND X.repetion=P.repetion AND X.overdue='-1' AND P.state='over';");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				RequestPhase rp = new RequestPhase(rs.getInt(1), Enum.valueOf(Phase.class, rs.getString(2)),
						rs.getInt(3));
				list.add(rp);
				stm1 = con.prepareStatement(
						"UPDATE exception SET overdue=? WHERE request_id=? AND phase=? AND repetion=?;");
				int overdue = (int) ((today.getTime() - rs.getDate(4).getTime()) / (24 * 60 * 60 * 1000));
				stm1.setInt(1, overdue);
				stm1.setInt(2, rs.getInt(1));
				stm1.setString(3, rs.getString(2));
				stm1.setInt(4, rs.getInt(3));
				stm1.executeUpdate();
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Employee getInspector(Connection con) {
		Statement st = null;
		Employee admin = null;
		try {
			st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT employee.* FROM employee WHERE job='inspector';");

			if (rs.next())
				admin = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
			return admin;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void addDocumentToException(Connection con, int id, String phase, int repetion, String document) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement(
					"UPDATE exception SET documentation=? WHERE request_id=? AND phase=? AND repetion=?;");
			stm.setString(1, document);
			stm.setInt(2, id);
			stm.setString(3, phase);
			stm.setInt(4, repetion);
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<RequestPhase> whoNeedsReminding(Connection con, Date today) {
		PreparedStatement stm = null;
		PreparedStatement stm1 = null;
		ArrayList<RequestPhase> list = new ArrayList<>();
		try {
			stm = con.prepareStatement(
					"SELECT request_id,phase,repetion,phase_administrator FROM requestinphase WHERE due_date=?;");
			LocalDate tommorrow1 = today.toLocalDate().plusDays(2);
			Date tommorrow = Date.valueOf(tommorrow1);
			stm.setDate(1, tommorrow);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				stm1 = con.prepareStatement(
						"SELECT reminder.* FROM reminder WHERE request_id=? AND phase=? AND repetion=?;");
				stm1.setInt(1, rs.getInt(1));
				stm1.setString(2, rs.getString(2));
				stm1.setInt(3, rs.getInt(3));
				ResultSet rs1 = stm1.executeQuery();
				if (!rs1.next()) {
					stm1 = con.prepareStatement("INSERT INTO reminder VALUES(?,?,?);");
					stm1.setInt(1, rs.getInt(1));
					stm1.setString(2, rs.getString(2));
					stm1.setInt(3, rs.getInt(3));
					stm1.executeUpdate();
					RequestPhase rp = new RequestPhase(rs.getInt(1), Enum.valueOf(Phase.class, rs.getString(2)),
							rs.getInt(3));
					list.add(rp);
				}
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String getStatus(Connection con, int id) {
		PreparedStatement st = null;
		String s = null;
		try {
			st = con.prepareStatement("SELECT * FROM icm.request Where id=?;");
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next())
				s = rs.getString(8);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public static Boolean changestatus(Connection con, int id, String newstatus) {
		PreparedStatement stm = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		try {
			stm = con.prepareStatement("SELECT E.* FROM icm.request E WHERE status='active' AND id=?;");
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				stmt1 = con.prepareStatement("UPDATE request SET status=? WHERE id=?;");
				stmt1.setString(1, newstatus);
				stmt1.setInt(2, id);
				stmt1.executeUpdate();
				if (newstatus.equals("rejected") || newstatus.equals("closed")) {
					stmt3 = con.prepareStatement("UPDATE request SET close_date=? WHERE id=?;");
					long millis1 = System.currentTimeMillis();
					stmt3.setDate(1, new java.sql.Date(millis1));
					stmt3.setInt(2, id);
					stmt3.executeUpdate();
				}
				stmt2 = con.prepareStatement(
						"UPDATE requestinphase SET state='over' WHERE phase='closing' AND request_id=?;");
				stmt2.setInt(1, id);
				stmt2.executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean FreazeRequest(Connection con, int id) {
		PreparedStatement stm = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		try {
			stmt2 = con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=?;");
			stmt2.setInt(1, id);
			ResultSet rs2 = stmt2.executeQuery();
			rs2.next();
			if (!rs2.getString(8).equals("frozen")) {
				stm = con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=?;");
				stm.setInt(1, id);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					stmt1 = con.prepareStatement("UPDATE request SET status=? WHERE id=?;");
					stmt1.setString(1, "frozen");
					stmt1.setInt(2, id);
					stmt1.executeUpdate();
					return true;
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean ActiveRequest(Connection con, int id) {
		PreparedStatement stm = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		try {
			stmt2 = con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=?;");
			stmt2.setInt(1, id);
			ResultSet rs2 = stmt2.executeQuery();
			rs2.next();
			if (rs2.getString(8).equals("frozen")) {
				stm = con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=?;");
				stm.setInt(1, id);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					stmt1 = con.prepareStatement("UPDATE request SET status=? WHERE id=?;");
					stmt1.setString(1, "active");
					stmt1.setInt(2, id);
					stmt1.executeUpdate();
					return true;
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static void EnterFreazeToDBUpdateTable(Connection con, Employee Inspector, int requestid, String explain) {
		PreparedStatement stm = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt1 = null;
		long millis = System.currentTimeMillis();
		try {
			stmt1 = con.prepareStatement(
					"SELECT E.* FROM icm.update E WHERE updater_name=?AND essence=? AND date=? AND request_id=?;");
			stmt1.setString(1, Inspector.getUsername());
			stmt1.setString(2, explain);
			stmt1.setDate(3, new java.sql.Date(millis));
			stmt1.setInt(4, requestid);
			ResultSet rs = stmt1.executeQuery();
			if (!rs.next()) {
				stm = con.prepareStatement("INSERT INTO icm.update VALUES(?,?,?,?,?);");
				stm.setString(1, Inspector.getUsername());
				stm.setString(2, Inspector.getFirstName() + Inspector.getLastName());
				stm.setString(3, explain);
				stm.setDate(4, new java.sql.Date(millis));
				stm.setInt(5, requestid);
				stm.executeUpdate();
				stmt2 = con.prepareStatement("INSERT INTO icm.frozen VALUES(?,?,?);");
				stmt2.setInt(1, requestid);
				long millis1 = System.currentTimeMillis();
				stmt2.setDate(2, new java.sql.Date(millis1));
				stmt2.setDate(3, null);
				stmt2.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void EnterUpdateToDBUpdateTable(Connection con, Employee Inspector, int requestid, String explain) {
		PreparedStatement stm = null;
		PreparedStatement stmt1 = null;
		long millis = System.currentTimeMillis();
		try {
			stmt1 = con.prepareStatement("SELECT MAX(E.NO) FROM icm.update E;");
			ResultSet rs = stmt1.executeQuery();
			int max = 0;
			if (rs.next()) {
				max = rs.getInt(1) + 1;
			}
			stm = con.prepareStatement("INSERT INTO icm.update VALUES(?,?,?,?,?,?);");
			stm.setString(1, Inspector.getUsername());
			stm.setString(2, Inspector.getFirstName() + Inspector.getLastName());
			stm.setString(3, explain);
			stm.setDate(4, new java.sql.Date(millis));
			stm.setInt(5, requestid);
			stm.setInt(6, max);
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void EnterActiveToDBUpdateTable(Connection con, Employee Inspector, int requestid, String explain) {
		PreparedStatement stm = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		try {

			stm = con.prepareStatement("INSERT INTO icm.update VALUES(?,?,?,?,?);");
			stm.setString(1, Inspector.getUsername());
			stm.setString(2, Inspector.getFirstName() + Inspector.getLastName());
			stm.setString(3, explain);
			long millis = System.currentTimeMillis();
			stm.setDate(4, new java.sql.Date(millis));
			stm.setInt(5, requestid);
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<Integer, String> getMap(Connection con) {
		PreparedStatement stm = null;
		Map<Integer, String> map = new HashMap<>();
		try {
			stm = con.prepareStatement("SELECT id,status FROM request;");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				map.put(rs.getInt(1), rs.getString(2));
			}
			return map;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static User getInitiatorUser(Connection con, int id) {
		PreparedStatement stm = null;
		PreparedStatement stmt2 = null;
		String Initiatorname = null;
		User initiator = null;
		try {
			stm = con.prepareStatement("SELECT R.initiator_username FROM icm.request R WHERE id=?;");
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				stmt2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
				stmt2.setString(1, rs.getString(1));
				ResultSet rs2 = stmt2.executeQuery();
				if (rs2.next()) {
					Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
					initiator = new User(rs.getString(1), null, null);
					initiator.setEmail(rs2.getString(4));
				}
				if (Initiatorname == null) {
					stmt2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
					stmt2.setString(1, rs.getString(1));
					rs2 = stmt2.executeQuery();
					if (rs2.next()) {
						Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
						initiator = new User(rs.getString(1), null, null);
						initiator.setEmail(rs2.getString(5));
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return initiator;
	}

	public static void changeState(Connection con, int id, Phase phase, State state) {
		RequestPhase rp = null;
		PreparedStatement stm1 = null;
		PreparedStatement stm = null;
		int maxRepetion = 0;
		try {
			stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? and phase=?;");
			stm.setInt(1, id);
			stm.setString(2, phase.toString());
			ResultSet rs1 = stm.executeQuery();
			if (rs1.next()) {
				maxRepetion = rs1.getInt(1);
			}
			stm1 = con.prepareStatement(
					"Update  icm.requestinphase SET state=? where request_id=? and phase=? and repetion=?;");
			stm1.setString(1, state.toString());
			stm1.setInt(2, id);
			stm1.setString(3, phase.toString());
			stm1.setInt(4, maxRepetion);
			stm1.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateCurrentPhase(Connection con, int id, Phase newPhase) {
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("UPDATE icm.request SET current_phase=? where id=?");
			stm.setString(1, newPhase.toString());
			stm.setInt(2, id);
			stm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static EvaluationReport getevaluationreport(Connection con, int request_id) {
		PreparedStatement stm = null;
		EvaluationReport report = null;
		try {
			stm = con.prepareStatement("SELECT E.* FROM evaluationreport E WHERE request_id=?;");
			stm.setInt(1, request_id);
			ResultSet rs = stm.executeQuery();
			int max = 0;
			while (rs.next()) {
				if (rs.getInt(1) >= max) {
					max = rs.getInt(1);
					report = new EvaluationReport(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getInt(7), request_id);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return report;
	}

	public static boolean changePermission(Connection con, Employee oldE, Employee newE) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("UPDATE employee SET job=? WHERE id=?;");
			stm.setString(1, oldE.getJob());
			stm.setInt(2, newE.getId());
			stm.executeUpdate();
			stm = con.prepareStatement("UPDATE employee SET job=? WHERE id=?;");
			stm.setString(1, "engineer");
			stm.setInt(2, oldE.getId());
			stm.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void changeEvaluator(Connection con, Employee oldEv, Employee newEv) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("UPDATE employee SET job=?,support_system=? WHERE id=?;");
			stm.setString(1, "evaluator");
			stm.setString(2, oldEv.getSupportSystem());
			stm.setInt(3, newEv.getId());
			stm.executeUpdate();
			stm = con.prepareStatement("UPDATE employee SET job=?,support_system=? WHERE id=?;");
			stm.setString(1, "engineer");
			stm.setString(2, null);
			stm.setInt(3, oldEv.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setNewJob(Connection con, Employee newEv, String job) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("UPDATE employee SET job=?,support_system=? WHERE id=?;");
			stm.setString(1, job);
			stm.setString(2, newEv.getSupportSystem());
			stm.setInt(3, newEv.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<Employee> getAllengineers(Connection con) {
		Statement st = null;
		ArrayList<Employee> list = new ArrayList<>();

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT first_name,last_name,id,job,support_system FROM employee WHERE belonging<>'lecturer' AND job<>'administrator';");
			while (rs.next()) {
				list.add(new Employee(rs.getString(1) + " " + rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5)));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Employee[] getComittee(Connection con) {
		Statement st = null;
		Employee[] Comember = new Employee[2];
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT first_name,last_name,id,job FROM employee WHERE job='comittee member';");
			rs.next();
			Comember[0] = new Employee(rs.getString(1) + " " + rs.getString(2), rs.getInt(3), rs.getString(4));
			rs.next();
			Comember[1] = new Employee(rs.getString(1) + " " + rs.getString(2), rs.getInt(3), rs.getString(4));
			return Comember;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<RequestPhase> getrequestEngineerworkon(Connection con, String username) {
		String Initiatorname = null;
		ArrayList<RequestPhase> arr = new ArrayList<RequestPhase>();
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		Request s = null;
		RequestPhase result = null;
		int count = 0;
		try {
			stmt1 = con.prepareStatement("SELECT DISTINCT R.request_id FROM icm.enginnerequest R WHERE username=?;");
			stmt1.setString(1, username);
			ResultSet rs = stmt1.executeQuery();
			while (rs.next()) {
				count++;
				stmt2 = con.prepareStatement("SELECT R.* FROM icm.enginnerequest R WHERE request_id=? AND username=?;");
				stmt2.setInt(1, rs.getInt(1));
				stmt2.setString(2, username);
				ResultSet rs2 = stmt2.executeQuery();
				int max = 0;
				while (rs2.next()) {
					if (rs2.getInt(2) >= max)
						max = rs2.getInt(2);
				}
				rs2.close();
				stmt3 = con.prepareStatement(
						"SELECT R.* FROM icm.requestinphase R WHERE request_id=? AND phase=? AND repetion=?;");
				stmt3.setInt(1, rs.getInt(1));
				stmt3.setString(2, "performance");
				stmt3.setInt(3, max);
				ResultSet rs3 = stmt3.executeQuery();
				rs3.next();
				try {
					stmt4 = con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=?;");
					stmt4.setInt(1, rs.getInt(1));
					ResultSet rs4 = stmt4.executeQuery();
					if (rs4.next()) {
						Initiatorname = getinitiatorname(con, rs4.getString(9));
						if (Initiatorname != null) {
							s = new Request(rs4.getInt(7), Initiatorname, rs4.getString(8), rs4.getString(1),
									rs4.getDate(6));
							result = new RequestPhase(null, null, s, Phase.valueOf("performance"),
									State.valueOf(rs3.getString(7)));
							arr.add(result);
						}
					}
					stmt2 = null;
					stmt3 = null;
					stmt4 = null;
					rs4.close();
					rs3.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (count > 0)
			return arr;
		else
			return null;
	}

	public static ArrayList<Long> getPeriodricReportData(Connection con, String keymessage, Date from, Date to,
			String Rtype) {
		Statement stm = null;
		ResultSet rs;
		Date date = from;
		ArrayList<Long> arr = new ArrayList<>();
		ArrayList<Integer> seen = new ArrayList<>();
		int i = 0;
		try {
			stm = con.createStatement();
			if (keymessage.equals("Pdays")) {
				while (!date.equals(Date.valueOf(to.toLocalDate().plusDays(1)))) {
					arr.add((long) 0);
					rs = stm.executeQuery("SELECT * FROM request;");
					while (rs.next()) {
						switch (Rtype) {
						case "No. Active Requests":
							if (isActive(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13))) {
								arr.set(i, arr.get(i) + 1);
							}
							break;
						case "No. Frozen Requests":
							if (isFrozen(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13)))
								arr.set(i, arr.get(i) + 1);
							break;
						case "No. Closed Requests":
							if (isClosed(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13)))
								arr.set(i, arr.get(i) + 1);
							break;
						case "No. Rejected Requests":
							if (isRejected(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13)))
								arr.set(i, arr.get(i) + 1);
							break;
						}
					}
					date = Date.valueOf(date.toLocalDate().plusDays(1));
					i++;
				}
				return arr;
			}
			if (keymessage.equals("Pmonths")) {
				arr.add((long) 0);
				int m = date.getMonth();
				while (!date.equals(Date.valueOf(to.toLocalDate().plusDays(1)))) {
					if (m != date.getMonth()) {
						m = date.getMonth();
						i++;
						arr.add((long) 0);
					}
					rs = stm.executeQuery("SELECT * FROM request;");
					while (rs.next()) {
						switch (Rtype) {
						case "No. Active Requests":
							if (isActive(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13))) {
								int id = rs.getInt(7);
								if (!seen.contains(id)) {
									arr.set(i, arr.get(i) + 1);
									seen.add(id);
								}
							}
							break;
						case "No. Frozen Requests":
							if (isFrozen(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13))) {
								int id = rs.getInt(7);
								if (!seen.contains(id)) {
									arr.set(i, arr.get(i) + 1);
									seen.add(id);
								}
							}
							break;
						case "No. Closed Requests":
							if (isClosed(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13))) {
								int id = rs.getInt(7);
								if (!seen.contains(id)) {
									arr.set(i, arr.get(i) + 1);
									seen.add(id);
								}
							}
							break;
						case "No. Rejected Requests":
							if (isRejected(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13))) {
								int id = rs.getInt(7);
								if (!seen.contains(id)) {
									arr.set(i, arr.get(i) + 1);
									seen.add(id);
								}
							}
							break;
						}
					}
					date = Date.valueOf(date.toLocalDate().plusDays(1));
				}
				return arr;
			}
			if (keymessage.equals("Pyears")) {
				arr.add((long) 0);
				int y = date.getYear();
				while (!date.equals(Date.valueOf(to.toLocalDate().plusDays(1)))) {
					if (y != date.getYear()) {
						y = date.getYear();
						i++;
						arr.add((long) 0);
					}
					rs = stm.executeQuery("SELECT * FROM request;");
					int count = 0;
					while (rs.next()) {
						switch (Rtype) {
						case "No. Active Requests":
							if (isActive(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13))) {
								int id = rs.getInt(7);
								if (!seen.contains(id)) {
									arr.set(i, arr.get(i) + 1);
									seen.add(id);
								}
							}
							break;
						case "No. Frozen Requests":
							if (isFrozen(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13))) {
								int id = rs.getInt(7);
								if (!seen.contains(id)) {
									arr.set(i, arr.get(i) + 1);
									seen.add(id);
								}
							}
							break;
						case "No. Closed Requests":
							if (isClosed(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13))) {
								int id = rs.getInt(7);
								if (!seen.contains(id)) {
									arr.set(i, arr.get(i) + 1);
									seen.add(id);
								}
							}
							break;
						case "No. Rejected Requests":
							if (isRejected(con, date, rs.getInt(7), rs.getDate(6), rs.getDate(13))) {
								int id = rs.getInt(7);
								if (!seen.contains(id)) {
									arr.set(i, arr.get(i) + 1);
									seen.add(id);
								}
							}
							break;
						}
					}
					date = Date.valueOf(date.toLocalDate().plusDays(1));
				}
				return arr;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static int getMaxTreatment(Connection con, Date from, Date to) {
		Statement stm = null;
		int max = 0, len;
		try {
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery("SELECT date,close_date FROM request;");
			while (rs.next()) {
				Date sd = rs.getDate(1);
				Date cd = rs.getDate(2);

				if (cd == null) {
					cd = to;
				} else if (cd.after(to)) {
					cd = to;
				}
				if (sd.before(from)) {
					sd = from;
				}
				len = (int) (cd.toLocalDate().toEpochDay() - sd.toLocalDate().toEpochDay());
				if (len > max)
					max = len;
			}
			return max;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	private static boolean isDayActive(Connection con, Date date) {
		Statement stm = null;
		try {
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery("SELECT id,date,close_date FROM requests;");
			while (rs.next()) {
				if (isActive(con, date, rs.getInt(1), rs.getDate(2), rs.getDate(3)))
					return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	private static boolean isRejected(Connection con, Date date, int int1, Date sd, Date cd) {
		PreparedStatement stm = null;
		if (cd == null)
			return false;
		if (cd.after(date))
			return false;
		try {
			stm = con.prepareStatement("SELECT status FROM request WHERE id=?;");
			stm.setInt(1, int1);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals("rejected"))
					return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private static boolean isClosed(Connection con, Date date, int int1, Date sd, Date cd) {
		PreparedStatement stm = null;
		if (cd == null)
			return false;
		if (cd.after(date))
			return false;
		try {
			stm = con.prepareStatement("SELECT status FROM request WHERE id=?;");
			stm.setInt(1, int1);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals("closed"))
					return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private static boolean isFrozen(Connection con, Date date, int int1, Date sd, Date cd) {
		PreparedStatement stm = null;
		if (date.before(sd))
			return false;
		if (cd != null) {
			if (date.after(cd))
				return false;
		}
		try {
			stm = con.prepareStatement("SELECT start,end FROM frozen WHERE id=?;");
			stm.setInt(1, int1);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				if (rs.getDate(2) == null)
					return true;
				if (date.after(rs.getDate(1)) && date.before(rs.getDate(2)))
					return true;
				if (date.equals(rs.getDate(1)) || date.equals(rs.getDate(2)))
					return true;
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private static boolean isActive(Connection con, Date date, int int1, Date sd, Date cd) {
		PreparedStatement stm = null;
		if (date.before(sd))
			return false;
		if (cd != null) {
			if (date.after(cd))
				return false;
		}
		try {
			stm = con.prepareStatement("SELECT start,end FROM frozen WHERE id=?;");
			stm.setInt(1, int1);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				if (rs.getDate(2) == null)
					return false;
				if (date.after(rs.getDate(1)) && date.before(rs.getDate(2)))
					return false;
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<Long> getFourthReportData(Connection con, String keymessage, Date from, Date to) {
		Statement stm = null;
		ArrayList<Long> arr = new ArrayList<>();
		int max = 0, len;
		int N, period, k;
		try {
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery("SELECT date,close_date FROM request;");
			while (rs.next()) {
				Date sd = rs.getDate(1);
				Date cd = rs.getDate(2);

				if (cd == null) {
					cd = to;
				} else if (cd.after(to)) {
					cd = to;
				}
				if (sd.before(from)) {
					sd = from;
				}
				len = (int) (cd.toLocalDate().toEpochDay() - sd.toLocalDate().toEpochDay());
				if (len > max)
					max = len;
			}
			if (max > 10) {
				if (max % 10 == 0) {
					N = max / 10;
				} else
					N = max / 10 + 1;
			} else
				N = 1;
			for (int j = 0; j < Integer.min(max, 10); j++) {
				arr.add((long) 0);
			}
			rs = stm.executeQuery("SELECT date,close_date FROM request;");
			while (rs.next()) {
				Date sd = rs.getDate(1);
				Date cd = rs.getDate(2);
				if (cd == null)
					cd = to;
				else if (cd.after(to))
					cd = to;
				if (sd.before(from))
					sd = from;
				period = (int) (cd.toLocalDate().toEpochDay() - sd.toLocalDate().toEpochDay());
				k = period / N;
				if (period % N == 0 || period < N)
					arr.set(k - 1, arr.get(k - 1) + 1);
				else
					arr.set(k, arr.get(k) + 1);
			}
			return arr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<ExtensionDuration> getPerformanceReport(Connection con, String keymessage) {
		Statement stm = null;
		ArrayList<ExtensionDuration> arr = new ArrayList<>();
		ResultSet rs = null;
		try {
			stm = con.createStatement();
			if (keymessage.equals("Extension durations"))
				rs = stm.executeQuery("SELECT old_due_date,new_due_date,request_id FROM extension;");
			else
				rs = stm.executeQuery("SELECT start_date,due_date,request_id FROM requestinphase WHERE repetion>'0';");
			while (rs.next()) {
				if (rs.getDate(2) != null && rs.getDate(1) != null)
					arr.add(new ExtensionDuration(rs.getInt(3),
							rs.getDate(2).toLocalDate().toEpochDay() - rs.getDate(1).toLocalDate().toEpochDay()));
			}
			return arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Long> getDelaysReportData(Connection con, String keymessage) {
		PreparedStatement stm = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<>();
		list.add("Moodle");
		list.add("Student information system");
		list.add("Lecturer information system");
		list.add("Employee information system");
		list.add("Library system");
		list.add("Computers in the classroom");
		list.add("Labs and computer farms");
		list.add("College official site");
		ArrayList<Long> arr = new ArrayList<>();
		int i = 0;
		for (String str : list) {
			if (keymessage.equals("No.Delays")) {
				try {
					stm = con.prepareStatement(
							"SELECT COUNT(exception.request_id) FROM exception,request WHERE exception.request_id=request.id AND request.Privileged_information_system=?;");
					stm.setString(1, str);
					rs = stm.executeQuery();
					if (rs.next()) {
						arr.add((long) rs.getInt(1));
					} else
						arr.add((long) 0);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (keymessage.equals("Delays Durations")) {
				try {
					stm = con.prepareStatement(
							"SELECT SUM(exception.overdue) FROM exception,request WHERE exception.request_id=request.id AND request.Privileged_information_system=?;");
					stm.setString(1, str);
					rs = stm.executeQuery();
					if (rs.next())
						arr.add((long) rs.getInt(1));
					else
						arr.add((long) 0);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return arr;

	}

	public static void assignorChangeEmployee(Connection con, String username, int repetion, int id, String phase,
			LocalDate start, LocalDate due) {
		PreparedStatement stm = null;
		PreparedStatement stm2 = null;
		PreparedStatement stm3 = null;
		try {
			if (username != null) {
				stm = con.prepareStatement(
						"UPDATE requestinphase SET phase_administrator=? WHERE request_id=? AND phase=? AND repetion=?;");
				stm.setString(1, username);
				stm.setInt(2, id);
				stm.setString(3, phase);
				stm.setInt(4, repetion);
				stm.executeUpdate();
			}
			if (start != null) {
				stm2 = con.prepareStatement(
						"UPDATE requestinphase SET start_date=? WHERE request_id=? AND phase=? AND repetion=?;");
				Date st = Date.valueOf(start);
				long s = st.getTime() + (int) (1000 * 60 * 60 * 24);
				st = new java.sql.Date(s);
				stm2.setDate(1, st);
				stm2.setInt(2, id);
				stm2.setString(3, phase);
				stm2.setInt(4, repetion);
				stm2.executeUpdate();
			} else if (due != null) {
				stm2 = con.prepareStatement(
						"UPDATE requestinphase SET due_date=? WHERE request_id=? AND phase=? AND repetion=?;");
				Date du = Date.valueOf(due);
				long dd = du.getTime() + (int) (1000 * 60 * 60 * 24);
				du = new java.sql.Date(dd);
				stm2.setDate(1, du);
				stm2.setInt(2, id);
				stm2.setString(3, phase);
				stm2.setInt(4, repetion);
				stm2.executeUpdate();
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void EnterrequestonengineerrequestDB(Connection con, ArrayList<String> fullname, int repetion,
			int id) {
		PreparedStatement stmt4 = null;
		PreparedStatement stm = null;
		for (int i = 0; i < fullname.size(); i++) {
			Employee engineer = getSpecificEmployee(con, fullname.get(i));
			try {
				stmt4 = con.prepareStatement(
						"SELECT E.* FROM icm.enginnerequest E WHERE request_id=? AND repetion=? AND username=?;");
				stmt4.setInt(1, id);
				stmt4.setInt(2, repetion);
				stmt4.setString(3, engineer.getUsername());
				ResultSet rs4 = stmt4.executeQuery();
				if (!rs4.next()) {
					stm = con.prepareStatement("INSERT INTO icm.enginnerequest VALUES(?,?,?);");
					stm.setInt(1, id);
					stm.setInt(2, repetion);
					stm.setString(3, engineer.getUsername());
					stm.executeUpdate();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static boolean extendTime(Connection con, RequestPhase rp, LocalDate newDue) {
		PreparedStatement stm1 = null;
		PreparedStatement stm2 = null;
		PreparedStatement stm3 = null;

		int maxRepetion = 0;
		try {
			stm1 = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
			stm1.setInt(1, rp.getId());
			stm1.setString(2, rp.getPhase().toString());
			ResultSet rs = stm1.executeQuery();
			if (rs.next())
				maxRepetion = rs.getInt(1);

			stm2 = con.prepareStatement("SELECT * FROM icm.extension WHERE request_id=? AND phase=? AND repetion=?");
			stm2.setInt(1, rp.getId());
			stm2.setString(2, rp.getPhase().toString());
			stm2.setInt(3, maxRepetion);
			ResultSet rs1 = stm2.executeQuery();
			if (rs1.next()) {
				return false;
			}
			stm3 = con.prepareStatement("INSERT INTO extension VALUES(?,?,?,?,?);");
			stm3.setDate(1, rp.getDueDate());
			stm3.setDate(2, Date.valueOf(newDue));
			stm3.setInt(3, maxRepetion);
			stm3.setString(4, rp.getPhase().toString());
			stm3.setInt(5, rp.getId());
			stm3.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static String[] getExtension(Connection con, int id, String phase) {
		PreparedStatement stm1 = null;
		PreparedStatement stm2 = null;
		PreparedStatement stm3 = null;
		String data[] = new String[3];
		int maxRepetion = 0;
		try {
			stm1 = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
			stm1.setInt(1, id);
			stm1.setString(2, phase);
			ResultSet rs = stm1.executeQuery();
			if (rs.next())
				maxRepetion = rs.getInt(1);
			stm2 = con.prepareStatement("SELECT * FROM icm.extension WHERE request_id=? AND phase=? AND repetion=?");
			stm2.setInt(1, id);
			stm2.setString(2, phase);
			stm2.setInt(3, maxRepetion);
			ResultSet rs1 = stm2.executeQuery();
			if (rs1.next()) {
				data[0] = rs1.getDate(1).toString();
				data[1] = rs1.getDate(2).toString();
				RequestPhase rp = mysqlConnection.getRequestPhase(con, id, phase);
				data[2] = rp.getEmployee();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	public static void updateDuedate(Connection con, int id, String phase, LocalDate dueDate) {
		PreparedStatement stm1 = null;
		PreparedStatement stm2 = null;
		int maxRepetion = 0;
		try {
			stm1 = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
			stm1.setInt(1, id);
			stm1.setString(2, phase);
			ResultSet rs = stm1.executeQuery();
			if (rs.next())
				maxRepetion = rs.getInt(1);
			Date newDue = Date.valueOf(dueDate);
			long s = newDue.getTime() + (int) (1000 * 60 * 60 * 24);
			newDue = new java.sql.Date(s);
			stm2 = con.prepareStatement(
					"UPDATE icm.requestinphase SET due_date=? WHERE request_id=? AND phase=? AND repetion=?;");
			stm2.setDate(1, newDue);
			stm2.setInt(2, id);
			stm2.setString(3, phase);
			stm2.setInt(4, maxRepetion);
			stm2.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Employee getMyEmployee(Connection con, String username) {
		Employee employee = null;
		PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT employee.* FROM employee WHERE username=?;");
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				employee = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}
     public static void changestatewaitforapproveDecision(Connection con,int id,int repetion) {
    		PreparedStatement st = null;
			System.out.println("xxc");
			System.out.println(id);
			System.out.println(repetion);
    		try {
    			st=con.prepareStatement("UPDATE icm.requestinphase SET state='waitingForApprove' WHERE request_id=? AND phase='decision' AND repetion=?;");
    			st.setInt(1,id);
                st.setInt(2, repetion);
            	st.executeUpdate();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     }
}
