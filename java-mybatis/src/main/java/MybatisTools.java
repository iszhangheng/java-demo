import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: MybatisTools
 * @packageName: PACKAGE_NAME
 * @description: Mybatis工具类
 * @date: 2019-10-08 14:36
 **/
public class MybatisTools {
    private static SqlSessionFactory factory = null;

    static {
        InputStream is = null;
        String config = "mybatis-config.xml";
        try {
            is = Resources.getResourceAsStream(config);
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
            factory = sessionFactory;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * @param autoCommit 设置是否为自动提交
     * @return
     */
    public static SqlSession getSession(boolean autoCommit) {
        return factory.openSession(autoCommit);
    }
}
