package me.ddream.server;

import me.ddream.server.Processor.HttpProcessor;
import me.ddream.server.Processor.ServletProcessor;
import me.ddream.server.Processor.StaticResourceProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * @description : 简易Http Server
 * @author : DDDreame
 * @date : 2023/6/22 16:14 
 */
public class HttpServer {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer();
        server.await();
    }

    /**
     * 监听信息
     */
    public void await() throws IOException {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("0.0.0.0"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Server Start!");
        while (true) {
            Socket socket;
            socket = serverSocket.accept();
            HttpProcessor processor = new HttpProcessor();
            processor.process(socket);
            socket.close();

        }
    }


}
