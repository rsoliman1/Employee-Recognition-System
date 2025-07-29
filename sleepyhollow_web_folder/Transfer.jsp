<%@ page import="java.sql.*" %>
<%
    String ssn1 = request.getParameter("ssn1");
    String ssn2 = request.getParameter("ssn2");
    String amountStr = request.getParameter("amount");

    if (ssn1 != null && ssn2 != null && amountStr != null) {
        Connection conn = null;
        try {
            double amount = Double.parseDouble(amountStr);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", "hlee236", "epsiboku");

            conn.setAutoCommit(false);

            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM Employees WHERE ssn IN (?, ?)");
            checkStmt.setString(1, ssn1);
            checkStmt.setString(2, ssn2);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            checkStmt.close();

            if (count == 2) {
                PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO Transfer (from_ssn, to_ssn, transfer_date, amount) VALUES (?, ?, SYSDATE, ?)");
                insertStmt.setString(1, ssn1);
                insertStmt.setString(2, ssn2);
                insertStmt.setDouble(3, amount);
                insertStmt.executeUpdate();
                insertStmt.close();

                conn.commit();
                out.println("Transfer completed successfully.");
            } else {
                conn.rollback();
                out.println("Error: One or both SSNs do not exist.");
            }

        } catch (Exception e) {
            if (conn != null) conn.rollback();
            out.println("Error: " + e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }
    } else {
        out.println("Missing transfer data");
    }
%>