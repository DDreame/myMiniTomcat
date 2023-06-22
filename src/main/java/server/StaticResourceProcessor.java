package server;

import org.apache.commons.lang3.text.StrSubstitutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/***
 * @description : Todo
 * @author : DDDreame
 * @date : 2023/6/22 19:03 
 */
public class StaticResourceProcessor {
    private static final int BUFFER_SIZE = 1024;
    private static final String fileNotFoundMessage = "HTTP/1.1 404 File Not Found\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: 23\r\n" +
            "\r\n" +
            "<h1>File Not Found</h1>";
    private static final String OKMessage = "HTTP/1.1 ${StatusCode} ${StatusName}\r\n"+
            "Content-Type: ${ContentType}\r\n"+
            "Content-Length: ${ContentLength}\r\n"+
            "Server: MiniTomcat\r\n"+
            "Date: ${ZonedDateTime}\r\n"+
            "\r\n";
    public void process(Request request, Response response) throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        OutputStream output = null;
        output = response.getOutput();
        File file = new File(HttpServer.WEB_ROOT, request.getUri());
        if (file.exists()) {
            String head = composeResponseHead(file);
            output.write(head.getBytes("utf-8"));

            fis = new FileInputStream(file);
            int ch = fis.read(bytes, 0, BUFFER_SIZE);
            while (ch!=-1) {
                output.write(bytes, 0, ch);
                ch = fis.read(bytes, 0, BUFFER_SIZE);
            }
            output.flush();
        }
        else {
            output.write(fileNotFoundMessage.getBytes());
        }
    }
    private String composeResponseHead(File file) {
		  /*
		   * "HTTP/1.1 ${StatusCode} ${StatusName}\r\n"+
			  "Content-Type: ${ContentType}\r\n"+
			  "Content-Length: ${ContentLength}\r\n"+
			  "Server: minit\r\n"+
			  "Date: ${ZonedDateTime}\r\n"+
		   */
        long fileLength = file.length();

        Map<String,Object> valuesMap = new HashMap<>();
        valuesMap.put("StatusCode","200");
        valuesMap.put("StatusName","OK");
        valuesMap.put("ContentType","image/JPEG;charset=uft-8");
        valuesMap.put("ContentLength",fileLength);
        valuesMap.put("ZonedDateTime", DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now()));

        StrSubstitutor sub = new StrSubstitutor(valuesMap);

        return sub.replace(OKMessage);
    }
}
