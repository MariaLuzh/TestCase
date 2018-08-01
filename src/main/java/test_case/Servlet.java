package test_case;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Servlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String connectionURL = "jdbc:postgresql://localhost:5432/postgres";
        Connection connection = null;
        ResultSet rs;
        Statement st;
        String s;
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(connectionURL, "postgres", "mashenika17122009M");
            String query1 =  "select ssoid, formid from test where ts<3600000000 and formid in ( (SELECT formid FROM test GROUP BY formid HAVING count(*)>1)) limit 20";

            st = connection.createStatement();
            rs = st.executeQuery(query1);
            System.out.println("1. Cписок пользователей и используемых ими форм за последний час :");

            while (rs.next()) {
                s = rs.getString(1);
                System.out.println(s);

                out.println("<table>");
                out.println("<c:forEach>");
                out.println("<br>");
                out.println("<td>" +
                        rs.getString("ssoid") + "</td><td>" +
                        rs.getString("formid") + "</td><td>");
                out.println("</c:forEach>");
                out.println("</table>");
            }
            out.println("</br>");
            st.close();
            rs.close();
            try {
                String query2 = "SELECT ssoid , subtype FROM test WHERE subtype not like all  ('{\"start%\", \"send%\"}') limit 20";
                st=connection.createStatement();
                rs=st.executeQuery(query2);
                while (rs.next()) {
                    System.out.println();
                    String s2 = rs.getString(1);
                    System.out.println(s2);

                    out.println("<table>");
                    out.println("<c:forEach>");
                    out.println("<br>");
                    out.println("<td>" +
                            rs.getString("ssoid")  +"</td><td>"+
                            rs.getString("subtype") + "</td><td>");
                    out.println("</c:forEach>");
                    out.println("</table>");
                }
                st.close();
                rs.close();

            }catch (SQLException e){
                e.getMessage();
                System.out.println("SQLException");
            }

         try {
             System.out.println();
             String query3 = "select formid, count(*) as formid_count  from test group by formid order by formid_count desc limit 10";

                st=connection.createStatement();
                rs=st.executeQuery(query3);
                while (rs.next()) {
                    String s3 = rs.getString(1);
                    System.out.println(s3);
                    out.println("<table>");
                    out.println("<c:forEach>");
                    out.println("<br>");
                    out.println("<td>" +
                    rs.getString("formid")  +"</td><td>"+
                            rs.getString("formid_count") + "</td><td>");
                    out.println("</c:forEach>");
                    out.println("</table>");
                }
                st.close();
                rs.close();

            }catch (SQLException e){
                e.getMessage();
                System.out.println("SQLException");
            }




        } catch (ClassNotFoundException e) {
            out.println("Couldn't load database driver: " + e.getMessage());
        } catch (SQLException e) {
            out.println("SQLException " + e.getMessage());
        } catch (Exception e) {
            out.println(e);
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException ig) {
                out.println(ig);
            }
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }
}