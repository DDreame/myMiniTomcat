package server;

import java.io.IOException;

/***
 * @description : Todo
 * @author : DDDreame
 * @date : 2023/6/22 20:38 
 */
public interface Servlet {

    public void service(Request req, Response res) throws IOException;
}
