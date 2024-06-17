package com.dsa.homelibrary.servlets;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class BookSearchServlet extends HttpServlet {

    // Configura tus credenciales de la base de datos
    private static final String DB_URL = "jdbc:mysql://localhost:8080/HomeLibrary";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtiene el término de búsqueda del parámetro "q"
        String searchTerm = request.getParameter("q");

        // Verifica si el término de búsqueda no es nulo ni vacío
        if (searchTerm != null && !searchTerm.isEmpty()) {
            // Realiza la búsqueda en la base de datos
            try {
                // Establece la conexión con la base de datos
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                // Prepara la consulta SQL para buscar libros que coincidan con el término de búsqueda
                String sql = "SELECT * FROM libros WHERE titulo LIKE ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, "%" + searchTerm + "%");

                // Ejecuta la consulta
                ResultSet resultSet = statement.executeQuery();

                // Crea una respuesta HTML con los resultados de la búsqueda
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Resultado de la búsqueda</title></head><body>");
                out.println("<h2>Resultado de la búsqueda:</h2>");
                out.println("<ul>");
                while (resultSet.next()) {
                    // Muestra los resultados de la búsqueda
                    String titulo = resultSet.getString("titulo");
                    String autor = resultSet.getString("autor");
                    out.println("<li>" + titulo + " - " + autor + "</li>");
                }
                out.println("</ul>");
                out.println("</body></html>");

                // Cierra la conexión y libera recursos
                resultSet.close();
                statement.close();
                conn.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            // Si no se proporciona un término de búsqueda, muestra un mensaje de error
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h2>Error: Debes proporcionar un término de búsqueda</h2>");
            out.println("</body></html>");
        }
    }
}
