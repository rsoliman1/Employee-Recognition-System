<%@ page import="java.sql.*" %>
<%
    String ssn = request.getParameter("ssn") != null ? request.getParameter("ssn").trim() : "";
    if (!ssn.isEmpty()) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@//artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu",
                "hlee236", "epsiboku"
            );
            String query = "SELECT name, NVL(total_sales, 0) AS total_sales FROM Employees e LEFT JOIN Emp_Sales s ON e.ssn = s.ssn WHERE e.ssn = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ssn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println(rs.getString("name") + "," + rs.getDouble("total_sales") + "#");
            } else {
                out.println("No employee found for SSN: " + ssn);
            }
            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    } else {
        out.println("No SSN provided");
    }
%>

