package tracking;
public class ServerThread extends Thread
{
	RFIDServer server;
public ServerThread(RFIDServer server){
	this.server=server;
	start();
}
public void run(){
	server.start();
}
}