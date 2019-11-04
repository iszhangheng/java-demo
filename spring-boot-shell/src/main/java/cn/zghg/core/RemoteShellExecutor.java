package cn.zghg.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: RemoteShellExecutor
 * @packageName: cn.zghg.core
 * @description: 远程调用Shell脚本工具类
 * @date: 2019-09-05 9:52
 **/
class RemoteShellExecutor {
    private String charset = Charset.defaultCharset().toString();
    private static final int TIME_OUT = 1000 * 5 * 60;


    /**
     * 测试远程登录linux服务器，获取连接对象
     *
     * @param ip       远程服务器IP
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    private static Connection login(String ip, String username, String password) {
        Connection connection = new Connection(ip);
        try {
            connection.connect();
            connection.authenticateWithPassword(username, password);
        } catch (IOException e) {
            throw new RuntimeException("远程调用Shell脚本登录操作失败，请检查IP地址及用户信息是否正确没网络是否连接");
        }
        return connection;
    }

    public static void exec(String ip, String username, String password, String command, String[] params) {
        long timeout = 10 * 1000;
        Connection conn = login(ip, username, password);
        Session session = null;
        try {
            session = conn.openSession();
            session.execCommand(command);

            CommandStreamGobbler errorGobbler = new CommandStreamGobbler(session.getStderr(), command);
            CommandStreamGobbler outputGobbler = new CommandStreamGobbler(session.getStdout(), command);

            errorGobbler.start();
            // 必须先等待错误输出ready再建立标准输出
            while (!errorGobbler.isReady()) {
                Thread.sleep(10);
            }
            outputGobbler.start();
            while (!outputGobbler.isReady()) {
                Thread.sleep(10);
            }

            CommandWaitForThread commandThread = new CommandWaitForThread(session, timeout);
            commandThread.start();

            long commandTime = new Date().getTime();
            long nowTime = new Date().getTime();
            boolean timeoutFlag = false;
            while (!commandIsFinish(commandThread, errorGobbler, outputGobbler)) {
                if (nowTime - commandTime > timeout) {
                    timeoutFlag = true;
                    break;
                } else {
                    Thread.sleep(100);
                    nowTime = new Date().getTime();
                }
            }
            if (timeoutFlag) {
                // 命令超时
                errorGobbler.setTimeout(CommandStreamGobbler.TIME_OUT);
                outputGobbler.setTimeout(CommandStreamGobbler.TIME_OUT);
                System.out.println("正式执行命令：" + command + "超时");
            } else {
                // 命令执行完成
                errorGobbler.setTimeout(CommandStreamGobbler.EXECUTED);
                outputGobbler.setTimeout(CommandStreamGobbler.EXECUTED);
            }
            while (!errorGobbler.isReadFinish() && !outputGobbler.isReadFinish()) {
                Thread.sleep(10);
            }
            System.out.println(errorGobbler.getInfoList());
            System.out.println(outputGobbler.getInfoList());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static boolean commandIsFinish(CommandWaitForThread commandThread, CommandStreamGobbler errorGobbler, CommandStreamGobbler outputGobbler) {
        if (commandThread != null) {
            return commandThread.isFinish();
        } else {
            return (errorGobbler.isReadFinish() && outputGobbler.isReadFinish());
        }
    }
}