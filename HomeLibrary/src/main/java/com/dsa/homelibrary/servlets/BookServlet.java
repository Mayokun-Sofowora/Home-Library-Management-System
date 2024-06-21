//package com.dsa.homelibrary.servlets;
//
//import jakarta.persistence.*;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.*;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//@WebServlet("/books")
//public class BookServlet extends HttpServlet {
//
//    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.DSA_HomeLibrary_db_jar_PU");
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        EntityManager em = emf.createEntityManager();
//        List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
//        em.close();
//
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        out.println("[");
//        for (int i = 0; i < books.size(); i++) {
//            Book book = books.get(i);
//            out.println("{");
//            out.println("\"id\": \"" + book.getId() + "\",");
//            out.println("\"title\": \"" + book.getTitle() + "\",");
//            out.println("\"isbn\": \"" + book.getISBN() + "\",");
//            out.println("\"totalCopies\": \"" + book.getTotalCopies() + "\",");
//            out.println("\"availableCopies\": \"" + book.getAvailableCopies() + "\",");
//            out.println("\"reviews\": \"" + String.join(", ", book.getReviews()) + "\",");
//            out.println("\"authors\": \"" + String.join(", ", book.getAuthors().stream().map(Author::getName).toArray(String[]::new)) + "\",");
//            out.println("\"genres\": \"" + String.join(", ", book.getGenres().stream().map(Genre::getName).toArray(String[]::new)) + "\"");
//            out.println("}");
//            if (i < books.size() - 1) {
//                out.println(",");
//            }
//        }
//        out.println("]");
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String title = request.getParameter("title");
//        int isbn = Integer.parseInt(request.getParameter("isbn"));
//        int totalCopies = Integer.parseInt(request.getParameter("totalCopies"));
//        int availableCopies = Integer.parseInt(request.getParameter("availableCopies"));
//
//        Book book = new Book();
//        book.setTitle(title);
//        book.setISBN(isbn);
//        book.setTotalCopies(totalCopies);
//        book.setAvailableCopies(availableCopies);
//
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(book);
//        em.getTransaction().commit();
//        em.close();
//
//        response.sendRedirect(request.getContextPath() + "/books");
//    }
//}
