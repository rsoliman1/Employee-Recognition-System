import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.sql.*;

@WebServlet("/login")
public class Login extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String result = "";

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@//artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu",
                "hlee236", "epsiboku"
            );

            String sql = "SELECT ssn FROM login WHERE username = ? AND passwd = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = "Yes:" + rs.getString("ssn");
            } else {
                result = "No";
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }

        out.print(result);
    }
}

 