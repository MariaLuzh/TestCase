/*
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;

public class GetRows {
    public static void main(String args[]) {
        Connection con;
        ResultSet rs;
        String s;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        String query1 = "\n" +
                "select ssoid, formid from test where ts<3600000000 and formid in ( (SELECT formid FROM test GROUP BY formid HAVING count(*)>1)) limit 10";
        String query2 = "\n" +
                "SELECT ssoid, subtype FROM test WHERE subtype not like all  ('{\"start%\", \"send%\"}') limit 10";
        String query3 = "\n" +
                "select formid, count(*) as formid_count from test group by formid order by formid_count desc limit 10";

        try {
            con = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "mashenika17122009M");
            Statement st = con.createStatement();

            rs = st.executeQuery(query1);
            System.out.println("список пользователей и используемых ими форм за последний час :");
            while (rs.next()) {
                s = rs.getString(1);
                System.out.println(s);
            }

            rs = st.executeQuery(query2);
            System.out.println("Вывести список пользователей, которые начали активность на форме и не дошли до конца:");

            while (rs.next()) {
                s = rs.getString(2);
                System.out.println(s);
            }
ResultSet resultSet = st.executeQuery(query3);
            System.out.println("Составить ТОП – 5 самых используемых форм. ");
                  while (resultSet.next()){
                        s = resultSet.getString(1);
                        System.out.println();
                    }

            System.out.println("ok");
            rs.close();
            resultSet.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }
}
*/
