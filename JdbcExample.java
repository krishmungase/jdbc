import java.sql.*;
import java.util.Scanner;

public class JdbcExample {

    public static void main(String[] args) {

        String url = "jdbc:oracle:thin:@localhost:1521:XE"; 
        String username = "hr";  
        String password = "hr";  

        Scanner sc = new Scanner(System.in);

        try {
           
            Class.forName("oracle.jdbc.driver.OracleDriver");

            System.out.println("Driver Loaded");
            System.out.println("Connecting to Oracle Database...");
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Oracle Database");

            while(true) {
                System.out.println("\n1.Insert  2.Update  3.Delete  4.Display  5.Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch(choice) {
                    case 1:
                        System.out.print("Enter Loan Number: ");
                        String lno = sc.next();
                        System.out.print("Enter Branch Name: ");
                        String bname = sc.next();
                        System.out.print("Enter Amount: ");
                        double amt = sc.nextDouble();

                        PreparedStatement ps1 = con.prepareStatement("INSERT INTO Loan_table VALUES (?, ?, ?)");
                        ps1.setString(1, lno);
                        ps1.setString(2, bname);
                        ps1.setDouble(3, amt);
                        ps1.executeUpdate();
                        System.out.println("Record Inserted");
                        break;

                    case 2:
                        System.out.print("Enter Loan Number to Update: ");
                        String lnoU = sc.next();
                        System.out.print("Enter New Amount: ");
                        double amtU = sc.nextDouble();

                        PreparedStatement ps2 = con.prepareStatement("UPDATE Loan_table SET amount=? WHERE loan_number=?");
                        ps2.setDouble(1, amtU);
                        ps2.setString(2, lnoU);
                        int updated = ps2.executeUpdate();
                        if(updated > 0)
                            System.out.println("Record Updated");
                        else
                            System.out.println("Loan Number Not Found");
                        break;

                    case 3:
                        System.out.print("Enter Loan Number to Delete: ");
                        String lnoD = sc.next();

                        PreparedStatement ps3 = con.prepareStatement("DELETE FROM Loan_table WHERE loan_number=?");
                        ps3.setString(1, lnoD);
                        int deleted = ps3.executeUpdate();
                        if(deleted > 0)
                            System.out.println("Record Deleted");
                        else
                            System.out.println("Loan Number Not Found");
                        break;

                    case 4:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM Loan_table");

                        System.out.println("Loan_Number | Branch_Name | Amount");
                        while(rs.next()) {
                            System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getDouble(3));
                        }
                        break;

                    case 5:
                        con.close();
                        System.out.println("Connection Closed");
                        System.exit(0);
                }
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}

