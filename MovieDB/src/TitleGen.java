import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TitleGen {
	public static void main(String[] args) throws Exception {
		String url = "jdbc:oracle:thin:testuser/password@localhost";
		String sql = "";
		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");

		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);
		PreparedStatement preStatement;
		ResultSet result;
		Scanner in = new Scanner(System.in);
		String choice = "y";
		String description;
		String adj = null, noun = null, mName;

		while (choice.equalsIgnoreCase("y")) {
			sql = "SELECT * FROM (SELECT * FROM adj ORDER BY DBMS_RANDOM.RANDOM) WHERE rownum =1";
			preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();
			if (result.next()) {
				adj = result.getString("adjacent");
			}
			sql = "SELECT * FROM (SELECT * FROM nouns ORDER BY DBMS_RANDOM.RANDOM) WHERE rownum =1";

			preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();
			if (result.next()) {
				noun = result.getString("nouns");
			}
			mName = adj + " " + noun;
			System.out.println("Your movie title is: " + mName);
			System.out.println("Description: ");
			description = in.nextLine();
			sql = "insert into movie(id, title, description) values(null, '"
					+ mName + "', '" + description + "')";
			preStatement = conn.prepareStatement(sql);
			preStatement.executeQuery();

			System.out.println("One more?(y/n)");
			choice = in.next();
		}
	}
}