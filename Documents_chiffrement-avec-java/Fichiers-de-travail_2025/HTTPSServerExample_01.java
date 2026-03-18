import java.io.*;
import java.net.*;
import javax.net.ssl.*;
//
// Example of an HTTPS server to illustrate SSL certificate and socket
public class HTTPSServerExample_01 {
   
    public static void main(String[] args) throws IOException {

	final int MYPORT = 12345;
	
	SSLServerSocketFactory sslsf =
	    (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
	ServerSocket ss = sslsf.createServerSocket(MYPORT);

	// loop forever
	while (true) {
	    try {

		// block waiting for client connection
		Socket s = ss.accept();
		System.out.println( "Client connection made" );

		// get client request
		BufferedReader in = new BufferedReader(
		    new InputStreamReader(s.getInputStream()));
		System.out.println(in.readLine());

		// make an HTML response
		PrintWriter out = new PrintWriter( s.getOutputStream() );
		out.println("<HTML><HEAD><TITLE>HTTPS Server Example</TITLE>" +
			    "</HEAD><BODY><H1>Hello Students</H1></BODY></HTML>\n");
		// Close the stream and socket
		out.close();
		s.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }
}
