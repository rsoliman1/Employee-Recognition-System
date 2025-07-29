<%@ page import="java.sql.*" %>
<%
    String ssn = request.getParameter("ssn");
    String awardid = request.getParameter("award_id");
    if (ssn != null && awardid != null) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", "hlee236", "epsiboku");
            Statement s = conn.createStatement();
            String sql = "SELECT award_date, center_name FROM Granted g JOIN Award_Centers ac ON g.center_id = ac.center_id WHERE ssn = '" + ssn + "' AND award_id = " + awardid;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                out.print(rs.getDate("award_date") + "," + rs.getString("center_name"));
            }
            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    } else {
        out.println("Missing parameters");
    }
%>