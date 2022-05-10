package com.eptitsyn.webapp.web;

import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.storage.SqlStorage;
import com.eptitsyn.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    Storage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");

    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    response.getWriter().write("<html><head></head><body><table><tr><th>UUID</th><th>Full Name</th></tr>");

    for (Resume r : storage.getAllSorted()) {
      response.getWriter().write("<tr><td>" + r.getUuid() + "</td><td>" + r.getFullName() + "</td><tr>");
    }
    response.getWriter().write("</table></body></html>");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

  }
}
