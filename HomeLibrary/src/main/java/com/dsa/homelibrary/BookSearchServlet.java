package com.dsa.homelibrary;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="BookSearchServlet", urlPatterns = {"/bookSearch"})
public class BookSearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchTerm = request.getParameter("q");
        String genre = request.getParameter("genre");
        String language = request.getParameter("language");
        String yearFromStr = request.getParameter("yearFrom");
        String yearToStr = request.getParameter("yearTo");
        boolean includeCensored = request.getParameter("includeCensored") != null;

        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (searchTerm != null && !searchTerm.isEmpty()) {
            conditions.add("titulo LIKE ?");
            parameters.add("%" + searchTerm + "%");
        }

        if (genre != null && !genre.isEmpty()) {
            conditions.add("genero = ?");
            parameters.add(genre);
        }

        if (language != null && !language.isEmpty()) {
            conditions.add("idioma = ?");
            parameters.add(language);
        }

        if (yearFromStr != null && !yearFromStr.isEmpty()) {
            try {
                int yearFrom = Integer.parseInt(yearFromStr);
                conditions.add("anio >= ?");
                parameters.add(yearFrom);
            } catch (NumberFormatException e) {
                // Handle invalid yearFrom input
            }
        }

        if (yearToStr != null && !yearToStr.isEmpty()) {
            try {
                int yearTo = Integer.parseInt(yearToStr);
                conditions.add("anio <= ?");
                parameters.add(yearTo);
            } catch (NumberFormatException e) {
                // Handle invalid yearTo input
            }
        }

        if (!includeCensored) {
            conditions.add("censurado = ?");
            parameters.add(false);
        }

        // Build the SQL query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM libros");

        if (!conditions.isEmpty()) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(String.join(" AND ", conditions));
        }

        String sql = queryBuilder.toString();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set parameters in the prepared statement
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = statement.executeQuery();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Resultado de la búsqueda</title></head><body>");
            out.println("<h2>Resultado de la búsqueda:</h2>");
            out.println("<ul>");
            while (resultSet.next()) {
                String titulo = resultSet.getString("titulo");
                String autor = resultSet.getString("autor");
                out.println("<li>" + titulo + " - " + autor + "</li>");
            }
            out.println("</ul>");
            out.println("</body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
