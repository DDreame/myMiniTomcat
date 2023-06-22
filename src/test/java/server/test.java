package server;

import java.io.File;

/***
 * @description : Todo
 * @author : DDDreame
 * @date : 2023/6/22 16:12 
 */
public class test {
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator + "webroot";

    public static void main(String[] args) {
        System.out.println(WEB_ROOT);
    }
}
