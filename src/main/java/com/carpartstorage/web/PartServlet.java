package com.carpartstorage.web;
import com.carpartstorage.dao.PartDAO;
import com.carpartstorage.bean.Part;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class PartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PartDAO partDAO;

    public void init() { partDAO = new PartDAO(); }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/filter":
                    filterPart(request, response);
                    break;

                case "/insert":
                    insertPart(request, response);
                    break;

                case "/new":
                    showNewForm(request, response);
                    break;

                case "/update":
                    updatePart(request, response);
                    break;

                case "/edit":
                    showEditForm(request, response);
                    break;

                case "/delete":
                    deletePart(request, response);
                    break;

                default:
                    listPart(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void filterPart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String filter = request.getParameter("brand-filter");
        if(filter == null) {filter = "";}
        List<Part> listPart = partDAO.filterBrand(filter);
        request.setAttribute("listPart", listPart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    private void listPart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int page;
        int tot_page = 5;
        String order = request.getParameter("order");
        if(order != null) {}
        else {order = "id";}

        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        } else {page = 1;}
        List<Part> listPart = page == 1 ? partDAO.selectAllParts(page, tot_page, order) :
                partDAO.selectAllParts((page - 1) * tot_page + 1, tot_page, order);
        request.setAttribute("listPart", listPart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void insertPart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String brand = request.getParameter("brand");
        Part newPart = new Part(name, type, brand);
        partDAO.insertPart(newPart);
        response.sendRedirect("list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        dispatcher.forward(request, response);
    }

    private void updatePart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String brand = request.getParameter("brand");
        Part book = new Part(id, name, type, brand);
        partDAO.updatePart(book);
        response.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Part existingPart = partDAO.selectPart(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        request.setAttribute("part", existingPart);
        dispatcher.forward(request, response);
    }

    private void deletePart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        partDAO.deletePart(id);
        response.sendRedirect("list");
    }
}