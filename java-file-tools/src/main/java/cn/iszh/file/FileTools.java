package cn.iszh.file;

import java.util.List;

public interface FileTools {
    /**
     * 获取当前系统文件路径分隔符
     *
     * @return
     */
    String currentSystemSeparator();

    /**
     * 获取当前项目根目录
     *
     * @return
     */
    String currentPath();

    /**
     * 写入数据
     *
     * @param list
     * @return
     */
    boolean writeList(String path, String fileName, List list);

    /**
     * 关闭占用资源
     */
    void close();
}
