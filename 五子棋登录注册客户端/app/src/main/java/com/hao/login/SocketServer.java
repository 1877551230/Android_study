package com.hao.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	//监听端口号
	private static final int SERVER_PORT = 12345;
 
	public static void main(String[] args) {
		try {
			System.out.println("Server: Connecting...");
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			while (true) {
				//循环监听客户端请求
				Socket clientSocket = serverSocket.accept();
				System.out.println("Server: Receiving...");
				try {
					//获取输入流
					BufferedReader in = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					//获取从客户端发来的信息
					String str = in.readLine();
					System.out.println("Server: Received: '" + str + "'");
				} catch (Exception e) {
					System.out.println("Server: Error");
					e.printStackTrace();
				} finally {
					clientSocket.close();
					System.out.println("Server: Close.");
				}
			}
 
		} catch (Exception e) {
			System.out.println("Server: Error");
			e.printStackTrace();
		}
	}
}
