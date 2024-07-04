package com.dsa.homelibrary;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/searchArtifact")
public class SearchServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("username");
        String genre = request.getParameter("genre");
        String language = request.getParameter("language");
        String yearFrom = request.getParameter("yearFrom");
        String yearTo = request.getParameter("yearTo");
        boolean includeCensored = request.getParameter("isCensoredIncluded") != null;

        // Perform search logic (e.g., query database)
        //List<Book> searchResults = bookDAO.searchBooks(name, genre, language, yearFrom, yearTo, includeCensored);
//        List<Journal> searchResults2 = journalDao.searchJournals(title, volume, issues, includeAvailableOnly);

        // Set attributes for JSP rendering
       // request.setAttribute("searchResults", searchResults);
//        request.setAttribute("searchResults", searchResults2);

        // Forward to JSP for rendering the results
       // request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
//        request.getRequestDispatcher("/journals.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
