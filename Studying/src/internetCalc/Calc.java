package internetCalc;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import mathematics.MathUtils;

public class Calc {
	public static void main(String[] args){
		try {
			ServerSocket s=new ServerSocket(61329);
			System.out.println(s.getInetAddress());
			System.out.println(s.getLocalPort());
			Socket client=s.accept();
			System.out.println("Connected!");
			BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out= new PrintWriter(client.getOutputStream(),true);
			while(true){
				while(!in.ready()){}
				String input="";
				while(in.ready()){
					input=in.readLine();
				}
				System.out.println(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
