import computation.SearchSimulator;
import servers.MultiThreadedServer;
import servers.SingleThreadedServer;

class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting server");
        // SingleThreadedServer server = new SingleThreadedServer(1234);
        MultiThreadedServer server = new MultiThreadedServer(1234);
        new Thread(server).start();
    }
}