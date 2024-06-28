package com.dsa.homelibrary;

import java.io.*;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


@WebServlet("/locations")
public class LocationsServlet extends HttpServlet {
//
//    // Assuming you have a method in your DAO or service layer to fetch locations
//    // Replace 'LocationDAO' with your actual DAO class for locations
//    private LocationDAO locationDAO;
//
//    @Override
//    public void init() throws ServletException {
//        // Initialize your DAO or service layer
//        locationDAO = new LocationDAO(); // Initialize your DAO or service class
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // Fetch locations from your DAO or service layer
//        List<Location> locations = locationDAO.getAllLocations(); // Replace with your method to fetch locations
//
//        // Set locations in request attribute to be accessed in JSP
//        request.setAttribute("locations", locations);
//
//        // Forward the request to the locations.jsp page for rendering
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/locations.jsp");
//        dispatcher.forward(request, response);
//    }
}
