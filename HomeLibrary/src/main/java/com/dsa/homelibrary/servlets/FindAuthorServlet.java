//package com.dsa.homelibrary.servlets;
//
//import jakarta.persistence.*;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.List;
//
//
//@WebServlet(name = "FindAuthorServlet", urlPatterns = {"/findAuthor"})
//public class FindAuthorServlet extends HttpServlet {
//
//    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("HomeLibraryPU");
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String authorName = request.getParameter("authorName");
//
//        EntityManager em = emf.createEntityManager();
//        try {
//            // Assuming you have an Author entity and it has a named query "Author.findByName"
//            List<Author> authors = em.createNamedQuery("Author.findByName", Author.class)
//                    .setParameter("name", authorName)
//                    .getResultList();
//
//            response.setContentType("application/json");
//            PrintWriter out = response.getWriter();
//            out.print(new Gson().toJson(authors));  // Using Gson for JSON conversion
//        } catch (Exception e) {
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "Find authors by name";
//    }
//}