package net.supercraftalex.liquido.serverconector;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {
	public static void connect() throws UnknownHostException, IOException {
		Socket s=new Socket("localhost",6666);  
		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		
		String str="hi", str2="";
		
		str=br.readLine();
		dout.writeUTF(str);
		dout.flush();
		str2=din.readUTF();
		System.out.println("Server says: "+str2);
		
		dout.close();  
		s.close();  
	}
	
}
