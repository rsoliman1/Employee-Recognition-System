<%@ page import="java.sql.*" %>
<%
    String ssn = request.getParameter("ssn");

    if (ssn != null && !ssn.trim().equals("")) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@//artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", "hlee236", "epsiboku");

            Statement s = conn.createStatement();

            String sql = "SELECT DISTINCT award_id FROM GRANTED WHERE ssn = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ssn);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                int awardId = rs.getInt("award_id");
                out.println(awardId + "#");
                found = true;
            }

            if (!found) {
                out.println("No award IDs found for SSN " + ssn);
            }
            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    } else {
        out.println("No SSN provided");
    }
%>

 