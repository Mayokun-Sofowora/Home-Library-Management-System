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
//@WebServlet("/journals")
//public class JournalServlet extends HttpServlet {
//
//    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.DSA_HomeLibrary_db_jar_PU");
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        EntityManager em = emf.createEntityManager();
//        List<Journal> journals = em.createQuery("SELECT j FROM Journal j", Journal.class).getResultList();
//        em.close();
//
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        out.println("[");
//        for (int i = 0; i < journals.size(); i++) {
//            Journal journal = journals.get(i);
//            out.println("{");
//            out.println("\"id\": \"" + journal.getId() + "\",");
//            out.println("\"title\": \"" + journal.getTitle() + "\",");
//            out.println("\"issn\": \"" + journal.getISSN() + "\",");
//            out.println("\"volume\": \"" + journal.getVolume() + "\",");
//            out.println("\"issue\": \"" + journal.getIssue() + "\",");
//            out.println("\"availableCopies\": \"" + journal.getAvailableCopies() + "\"");
//            out.println("}");
//            if (i < journals.size() - 1) {
//                out.println(",");
//            }
//        }
//        out.println("]");
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String title = request.getParameter("title");
//        String issn = request.getParameter("issn");
//        int volume = Integer.parseInt(request.getParameter("volume"));
//        int issue = Integer.parseInt(request.getParameter("issue"));
//        int availableCopies = Integer.parseInt(request.getParameter("availableCopies"));
//
//        Journal journal = new Journal();
//        journal.setTitle(title);
//        journal.setISSN(issn);
//        journal.setVolume(volume);
//        journal.setIssue(issue);
//        journal.setAvailableCopies(availableCopies);
//
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(journal);
//        em.getTransaction().commit();
//        em.close();
//
//        response.sendRedirect(request.getContextPath() + "/journals");
//    }
//}
