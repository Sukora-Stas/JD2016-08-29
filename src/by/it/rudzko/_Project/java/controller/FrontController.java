package by.it.rudzko._Project.java.controller;

import by.it.rudzko._Project.java.DataBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private RequestDispatcher disp(Action action) {
        ServletContext context = getServletContext();
        String landingJsp = action.getJsp();
        return context.getRequestDispatcher(landingJsp);
    }

    @Override
    public void init() throws ServletException {
        DataBase db=new DataBase();
        //db.buildDefaultStructure();
        //раскомментировать для того, чтобы заполнить таблицу (с пустой программа тоже будет работать корректно).
        db.reset();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = Actions.defineFrom(req);
        Action redirectAction = action.execute(req);
        if (redirectAction != null) {
            resp.sendRedirect("do?command=" + redirectAction);
        } else {
            disp(action).forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = Actions.defineFrom(req);
        action.execute(req);
        disp(action).forward(req, resp);
    }

    @Override
    public void destroy() {
        new DataBase().deleteTables();
    }

}
