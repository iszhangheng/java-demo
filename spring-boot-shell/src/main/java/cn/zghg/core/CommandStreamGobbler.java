package cn.zghg.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: CommandStreamCobbler
 * @packageName: cn.zghg.core
 * @description: 开启新线程读取流数据
 * @date: 2019-09-05 11:08
 **/
class CommandStreamGobbler extends Thread {
    // 流数据
    private InputStream is;
    // 脚本地址
    private String command;
    // 读取状态
    private boolean readFinish = false;
    // 准备状态
    private boolean ready = false;
    // 命令执行结果
    private int commandResult = EXECUTING;
    // 命令执行结果:执行中
    public final static int EXECUTING = 0;
    // 命令执行结果:超时
    public final static int TIME_OUT = 1;
    // 命令执行结果:执行完成
    public final static int EXECUTED = 2;
    private StringBuffer infoList = new StringBuffer();

    CommandStreamGobbler(InputStream is, String command) {
        this.is = is;
        this.command = command;
    }

    public void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            ready = true;
            while (commandResult != TIME_OUT) {
                if (br.ready() || commandResult == EXECUTED) {
                    if ((line = br.readLine()) != null) {
                        infoList.append(line);
                    } else {
                        break;
                    }
                } else {
                    Thread.sleep(100);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("读取流数据出现异常：", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ioe) {
                System.out.println("关闭字符流异常：" + command);
            }
            readFinish = true;
        }
    }

    public InputStream getIs() {
        return is;
    }

    public String getCommand() {
        return command;
    }

    public boolean isReadFinish() {
        return readFinish;
    }

    public boolean isReady() {
        return ready;
    }

    public String getInfoList() {
        return infoList.toString();
    }

    public void setTimeout(int timeout) {
        this.commandResult = timeout;
    }
}
