package servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import computation.SearchSimulator;

public class SingleThreadedServer implements Runnable {

    protected int serverPort = 8080;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;

    public SingleThreadedServer(int port) {
        this.serverPort = port;
    }

    public void run() {
        openServerSocket();

        while (!isStopped()) {
            Socket clienSocket = null;

            // wait for a connection
            // on receiving a request, execute the heavy computation
            try {
                clienSocket = this.serverSocket.accept();
            } catch (Exception e) {
                throw new RuntimeException("Failed to connect to server", e);
            }

            try {
                SearchSimulator.processClientRequest(clienSocket);
            } catch (Exception e) {
                //
            }
        }

        System.out.println("Server Stopped.");
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close server", e);
        }
    }

    private void openServerSocket() {
        this.isStopped = false;
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Failed to start server", e);
        }
    }
}