package SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class readSQL {
//	public static void main(String[] args)
//	{
	public void getSqlresulte(){
		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop";
		String jdbcUser="student";
		String jdbcPassword="student";
		double[]ourAvg=new double[9];
		double[]Avg=new double[9];
		double[]example = {2128259830,1149748017,-683317070,1193961129,1577914705,-1315066918,-1377331871,306711633,919248096};
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

			Statement statement = connection.createStatement();
			//select data
			String allCustomersQuery = "SELECT *FROM logs WHERE SomeDouble=2128259830 AND SecondID=313383259;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);

			for(int i=0;i<example.length;i++) {
				allCustomersQuery = "SELECT *FROM logs WHERE SomeDouble="+example[i]+" AND SecondID NOT IN(313383259);";
				resultSet = statement.executeQuery(allCustomersQuery);
				double ourSum=0;
				int ourCounter=0;
				while(resultSet.next())
				{    ourSum+=resultSet.getDouble("Point");
				ourCounter++;

				}
				Avg[i]=ourSum/ourCounter;


			}
			for(int i=0;i<example.length;i++) {
				allCustomersQuery = "SELECT *FROM logs WHERE SomeDouble="+example[i]+" AND SecondID=313383259;";
				resultSet = statement.executeQuery(allCustomersQuery);
				double sum=0;
				int counter=0;

				while(resultSet.next())
				{    sum+=resultSet.getDouble("Point");
				counter++;

				}
				ourAvg[i]=sum/counter;


			}
			for(int i=0;i<Avg.length;i++) {
				System.out.println("for game number "+(i+1)+" \nour avg is:"+ourAvg[i]+" overall avg is: "+Avg[i]);
			}
			resultSet.close();		
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
