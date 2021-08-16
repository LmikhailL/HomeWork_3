package com.db.databaseapp.servlets;

import com.db.databaseapp.pojo.User;
import com.db.databaseapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/insert")
public class DataInsertionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/insert.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int age = Integer.parseInt(request.getParameter("user_age"));
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            UserService.addUser(new User(age, firstName, lastName));
            response.sendRedirect(request.getContextPath() + "/index");
            System.out.println(request.getContextPath());
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/insert.jsp").forward(request, response);
        }
    }
}
