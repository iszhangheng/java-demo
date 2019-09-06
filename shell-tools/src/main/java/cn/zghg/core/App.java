package cn.zghg.core;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String username = "root";
        String password = "sunline";
        String serverIPs = "10.25.1.203";
        String shPath = "/data/test.sh";
        String[] params = {"1", "2"};
        //1. 调用远程Shell脚本，对生产库中数据进行导出和导入备份。
        System.out.println("测试开始。。。。。");
        // RemoteShellExecutor executor = new RemoteShellExecutor(serverIPs, username, password);
        // // 执行myTest.sh 参数为java Know dummy
        // try {
        //     System.out.println(executor.exec(shPath + " " + params[0]));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        try {
            RemoteShellExecutor.exec(serverIPs, username, password, shPath, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
