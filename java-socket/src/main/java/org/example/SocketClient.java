package org.example;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) throws InterruptedException {
        try {
            // 和服务器创建连接
            Socket socket = new Socket("localhost", 8088);

            // 要发送给服务器的信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("客户端发送信息");
            pw.flush();

            socket.shutdownOutput();

            // 从服务器接收的信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器返回信息：" + info);
            }

            br.close();
            is.close();
            os.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SocketClientThread extends Thread {
        private Socket socket = null;

        public SocketClientThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            OutputStream os = null;
            PrintWriter pw = null;
            try {
                is = socket.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                String info = null;

                while ((info = br.readLine()) != null) {
                    System.out.println("我是服务器，客户端说：" + info);
                }
                socket.shutdownInput();

                os = socket.getOutputStream();
                pw = new PrintWriter(os);
                pw.write("服务器欢迎你");

                pw.flush();
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                //关闭资源
                try {
                    if (pw != null)
                        pw.close();
                    if (os != null)
                        os.close();
                    if (br != null)
                        br.close();
                    if (isr != null)
                        isr.close();
                    if (is != null)
                        is.close();
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
