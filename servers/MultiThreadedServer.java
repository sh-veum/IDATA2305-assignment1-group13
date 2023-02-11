package servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import computation.AsyncSearchSimulator;

public class MultiThreadedServer implements Runnable {

    protected int serverPort = 8080;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;

    public MultiThreadedServer(int port) {
        this.serverPort = port;
    }

    public void run() {
        openServerSocket();

        while (!isStopped()) {
            // wait for a connection
            Socket clientSocket = null;
            try {
                // wait for a client to connect
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
            }

            // on receiving a request, execute the heavy computation in a new thread
            new Thread(
                    new AsyncSearchSimulator(
                            clientSocket,
                            "Multithreaded Server"))
                    .start();
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