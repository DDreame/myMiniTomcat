package me.ddream.server.Processor;

import me.ddream.server.Request;
import me.ddream.server.Response;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/***
 * @description : Todo
 * @author : DDDreame
 * @date : 2023/6/23 16:57 
 */
public class HttpProcessor {
    public void process(Socket socket) {
        InputStream input;
        OutputStream output;
        try {
            // wait for a message
            input = socket.getInputStream();
            output = socket.getOutputStream();

            // create Request object and parse
            Request request = new Request(input);
            request.parse();

            // create Response object
            Response response = new Response(output);
            response.setRequest(request);
            if (request.getUri().startsWith("/servlet/")) {
                ServletProcessor processor = new ServletProcessor();
                processor.process(request, response);
                System.out.println("servlet processor process finished.");
            }
            else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);
                System.out.println("static resource processor process finished.");
            }

            // Close the socket
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
