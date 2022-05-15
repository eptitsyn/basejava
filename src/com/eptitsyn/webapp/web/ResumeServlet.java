package com.eptitsyn.webapp.web;

import com.eptitsyn.webapp.Config;
import com.eptitsyn.webapp.model.*;
import com.eptitsyn.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
  private Storage storage;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    String uuid = request.getParameter("uuid");
    String action = request.getParameter("action");
    if (action == null) {
      request.setAttribute("resumes", storage.getAllSorted());
      request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
      return;
    }
    response.setCharacterEncoding("UTF-8");

    switch (action) {
      case "get":
        if (uuid != null) {
          request.setAttribute("resume", storage.get(uuid));
          request.getRequestDispatcher("/WEB-INF/jsp/item.jsp").forward(request, response);
          return;
        }
        break;
      case "edit":
      case "add":
        if (uuid != null) {
          Resume r = storage.get(uuid);
          request.setAttribute("resume", r);
        } else {
          request.setAttribute("resume", new Resume());
        }
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
        return;
      case "clear":
        storage.clear();
        break;
      case "delete":
        if (uuid != null) {
          storage.delete(uuid);
        }
        break;
    }
    response.setContentType("text/html; charset=UTF-8");
    response.getWriter().write("<html><head><script>" +
            "setTimeout(function() {location.href = 'resume';}, 1500)" +
            "</script></head><body></body></html>");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String action = request.getParameter("action");
    if (action == null) {
      out.write("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html\" charset=\"UTF-8\"></head><body>");
      for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
        out.write(e.getKey() + " : " + String.join("<br>",
                String.join("", Arrays.asList(e.getValue())).split("\n")) + "<br/>");
      }
      out.write("asd</body></html>");
      return;
    }
    String uuid = request.getParameter("uuid");
    String fullName = request.getParameter("fullName");
    Resume r;
    if (uuid.isEmpty()) {
      r = new Resume(fullName);
    } else {
      r = new Resume(uuid, fullName);
    }
    for (ContactType contactType : ContactType.values()) {
      String value = request.getParameter(contactType.name());
      if (value != null && !value.equals("")) {
        r.putContacts(contactType, value);
      }
    }
    for (SectionType sectionType : SectionType.values()) {
      switch (sectionType) {
        case OBJECTIVE:
        case PERSONAL:
          String value = request.getParameter(sectionType.name());
          if (!value.isEmpty()) {
            r.putSection(sectionType, new StringSection(value));
          }
          break;
        case QUALIFICATIONS:
        case ACHIEVEMENTS:
          String valueMultiline = request.getParameter(sectionType.name());
          if (!valueMultiline.isEmpty()) {
            r.putSection(sectionType, new StringListSection(
                    Arrays.stream(valueMultiline.split("\n"))
                            .filter(s -> !s.replaceAll("^\\s+", "").replaceAll("\\s+$", "").isEmpty())
                            .collect(Collectors.toList())
            ));
          }
          break;
        case EDUCATION:
        case EXPERIENCE:
          List<Organisation> organisations = new ArrayList<>();
          String orgCount = request.getParameter(sectionType.name() + "_orgCount");
          for (int i = 0; i < (orgCount.isEmpty() ? 0 : Integer.parseInt(orgCount)); i++) {
            String name = request.getParameter(sectionType.name() + "_" + i + "_name");
            String urlStr = request.getParameter(sectionType.name() + "_" + i + "_url");
            URL url = urlStr.isEmpty() ? null : new URL(urlStr);
            String posCount = request.getParameter(sectionType.name() + "_" + i + "_posCount");
            List<Organisation.Position> positions = new ArrayList<>();
            for (int j = 0; j < (posCount.isEmpty() ? 0 : Integer.parseInt(posCount)); j++) {
              String startDate = request.getParameter(sectionType.name() + "_" + i + "_" + j + "_startDate");
              String endDate = request.getParameter(sectionType.name() + "_" + i + "_" + j + "_endDate");
              String title = request.getParameter(sectionType.name() + "_" + i + "_" + j + "_title");
              String description = request.getParameter(sectionType.name() + "_" + i + "_" + j + "_description");
              positions.add(new Organisation.Position(startDate, endDate, title, description));
            }
            organisations.add(new Organisation(name, url, positions));
          }
          r.putSection(sectionType, new Experience(organisations));
      }
    }
    if (!uuid.isEmpty()) {
      storage.delete(r.getUuid());
    }
    storage.save(r);
    response.getWriter().write("<html><head><script>" +
            "setTimeout(function() {location.href = 'resume';}, 1500)" +
            "</script></head><body></body></html>");
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    storage = Config.get().getSqlStorage();
  }
}
