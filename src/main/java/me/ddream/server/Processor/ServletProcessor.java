package me.ddream.server.Processor;

import me.ddream.server.HttpServer;
import me.ddream.server.Request;
import me.ddream.server.Response;

import javax.servlet.Servlet;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/***
 * @description : Todo
 * @author : DDDreame
 * @date : 2023/6/22 19:03 
 */
public class ServletProcessor {
    private static final String OKMessage = "HTTP/1.1 ${StatusCode} ${StatusName}\r\n"+
            "Content-Type: ${ContentType}\r\n"+
            "Server: MiniTomcat\r\n"+
            "Date: ${ZonedDateTime}\r\n"+
            "\r\n";

    public void process(Request request, Response response) throws Exception {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;
        PrintWriter writer = null;

        // create a URLClassLoader,规定用户servlet放在web_root下。
        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        File classPath = new File(HttpServer.WEB_ROOT);
        String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
        urls[0] = new URL(null, repository, streamHandler);
        loader = new URLClassLoader(urls);

        //加载servlet
        Class<?> servletClass = null;
        servletClass = loader.loadClass(servletName);

        //写response  head
        writer = response.getWriter();
        String head = composeResponseHead();
        writer.println(head);

        //调用servlet service()
        Servlet servlet = null;
        servlet = (Servlet) servletClass.newInstance();
        servlet.service(request, response);

        writer.flush();
    }

    private String composeResponseHead() {

        return "HTTP/1.1 200 OK\r\n"+
                "Content-Type: text/html\r\n"+
                "Server: MiniTomcat\r\n"
                +"\r\n";
    }
}
