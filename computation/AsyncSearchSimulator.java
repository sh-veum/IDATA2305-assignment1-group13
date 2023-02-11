package computation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import utils.ResponseGenerator;

public class AsyncSearchSimulator implements Runnable {

  protected Socket clientSocket = null;
  protected String serverText = null;

  public AsyncSearchSimulator(Socket clientSocket, String serverText) {
    this.clientSocket = clientSocket;
    this.serverText = serverText;
  }

  public void run() {
    try {
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
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}