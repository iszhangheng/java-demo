package cn.zghg.core;

import ch.ethz.ssh2.Session;

import java.util.Date;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: CommandWaitForThread
 * @packageName: cn.zghg.core
 * @description: 防止线程阻塞
 * @date: 2019-09-05 14:34
 **/
public class CommandWaitForThread extends Thread {

    private Session session;
    private boolean finish = false;
    private int exitValue = -1;
    private long timeOut = 0;
    private long startDateTime;

    CommandWaitForThread(Session session, long timeOut) {
        this.session = session;
        this.startDateTime = new Date().getTime();
        this.timeOut = timeOut;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + this.session);
        try {
            while (this.session.getExitStatus() == null) {
                Thread.sleep(100);
                if (new Date().getTime() - this.startDateTime > timeOut) {
                    break;
                }
            }
            if (this.session.getExitStatus() == null) {
                this.exitValue = 2;
                System.out.println("等待执行状态超时！");
            } else {
                this.exitValue = this.session.getExitStatus();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.finish = true;
        }
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getExitValue() {
        return exitValue;
    }

}