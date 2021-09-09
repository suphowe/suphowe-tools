package com.soft.method.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket线程
 * @author suphowe
 */
public class SocketJob implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SocketJob.class);

    @Override
    public void run() {
        while (true) {
            oneServer();
        }
    }

    public  void oneServer(){
        try{
            ServerSocket server=null;
            try{
                server=new ServerSocket(5209);
                //b)指定绑定的端口，并监听此端口。
                logger.info("服务器启动成功");
                //创建一个ServerSocket在端口5209监听客户请求
            }catch(Exception e) {
                logger.error("没有启动监听：", e);
            }
            Socket socket=null;
            try{
                //2、调用accept()方法开始监听，等待客户端的连接,使用accept()阻塞等待客户请求，有客户请求到来则产生一个Socket对象，并继续执行
                socket=server.accept();
            }catch(Exception e) {
                logger.info("Error:", e);
            }
            //3、获取输入流，并读取客户端信息
            String line;
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //由Socket对象得到输入流，并构造相应的BufferedReader对象
            PrintWriter writer=new PrintWriter(socket.getOutputStream());
            //由Socket对象得到输出流，并构造PrintWriter对象
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            //由系统标准输入设备构造BufferedReader对象
            logger.info("Client:{}", in.readLine());
            //在标准输出上打印从客户端读入的字符串
            line=br.readLine();
            //从标准输入读入一字符串
            //4、获取输出流，响应客户端的请求
            while(!"end".equals(line)){
                //如果该字符串为 "bye"，则停止循环
                writer.println(line);
                //向客户端输出该字符串
                writer.flush();
                //刷新输出流，使Client马上收到该字符串
                logger.info("Server:{}", line);
                //在系统标准输出上打印读入的字符串
                logger.info("Client:{}", in.readLine());
                //从Client读入一字符串，并打印到标准输出上
                line=br.readLine();
                //从系统标准输入读入一字符串
            } //继续循环

            //5、关闭资源
            //关闭Socket输出流
            writer.close();
            //关闭Socket输入流
            in.close();
            //关闭Socket
            socket.close();
            //关闭ServerSocket
            server.close();
        }catch(Exception e) {
            logger.info("Error:", e);
        }
    }
}
