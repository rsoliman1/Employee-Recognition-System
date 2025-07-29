<%@ page import="java.sql.*" %>
<%
    String ssn = request.getParameter("ssn");
    if (ssn != null && !ssn.trim().equals("")) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", "hlee236", "epsiboku");
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT trans_id, t_date, amount FROM Transactions WHERE ssn = '" + ssn + "'");
            while (rs.next()) {
                out.print(rs.getInt("trans_id") + "," + rs.getDate("t_date") + "," + rs.getDouble("amount") + "#");
            }
            conn.close();
        } catch (Exception e) {
            out.print("Error:" + e.getMessage());
        }
    } else {
        out.print("No SSN provided");
    }
%>