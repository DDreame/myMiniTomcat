package test;

import server.Request;
import server.Response;

import javax.servlet.*;
import java.io.IOException;

/***
 * @description : Todo
 * @author : DDDreame
 * @date : 2023/6/22 20:54 
 */
public class HelloServlet implements Servlet {


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setCharacterEncoding("UTF-8");
        String doc = "<!DOCTYPE html> \n" +
                "<html>\n" +
                "<head><meta charset=\"utf-8\"><title>Test</title></head>\n"+
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + "Hello World 你好" + "</h1>\n";

        res.getWriter().println(doc);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
