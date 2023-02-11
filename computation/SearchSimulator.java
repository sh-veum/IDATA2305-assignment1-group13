package computation;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import utils.ResponseGenerator;

public class SearchSimulator {
  public static void processClientRequest(Socket clientSocket) throws Exception {
    InputStream inputStream = clientSocket.getInputStream();
    OutputStream outputStream = clientSocket.getOutputStream();

    long time1 = System.currentTimeMillis();
    System.out.println("Request processing started at: " + time1);
    Thread.sleep(10 * 1000);
    long time2 = System.currentTimeMillis();
    System.out.println("Request processing ended at: " + time2);

    byte[] responseHTML = ResponseGenerator.generatorResponseHTML(time1, time2).getBytes();
    byte[] responseHeader = ResponseGenerator.generatorResponseHeader(responseHTML.length).getBytes();

    outputStream.write(responseHeader);
    outputStream.write(responseHTML);
    outputStream.close();
    inputStream.close();
  }
}
