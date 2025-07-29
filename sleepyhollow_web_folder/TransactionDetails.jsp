<%@ page import="java.sql.*" %>
<%
    String txnid = request.getParameter("txnid");

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        conn = DriverManager.getConnection(
            "jdbc:oracle:thin:@//artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu",
            "hlee236", "epsiboku"
        );
        String transQuery = "SELECT t_date, amount FROM transactions WHERE trans_id = ?";
        ps = conn.prepareStatement(transQuery);
        ps.setString(1, txnid);
        rs = ps.executeQuery();

        if (rs.next()) {
            out.print(rs.getDate("t_date") + "," + rs.getDouble("amount"));
        }
        rs.close();
        ps.close();
        
        String productQuery = 
            "SELECT p.prod_name, p.p_price, tp.quantity " +
            "FROM txns_prods tp " +
            "JOIN products p ON tp.prod_id = p.prod_id " +
            "WHERE tp.trans_id = ?";
        ps = conn.prepareStatement(productQuery);
        ps.setString(1, txnid);
        rs = ps.executeQuery();

        while (rs.next()) {
            out.print("#" + rs.getString("prod_name") + "," + rs.getDouble("p_price") + "," + rs.getInt("quantity"));
        }

    } catch (Exception e) {
        out.print("Error:" + e.getMessage());
    } 
    conn.close();
%>









