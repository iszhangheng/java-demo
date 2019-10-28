package cn.iszh.estools.service;

import cn.iszh.estools.config.EsIndexEnum;
import cn.iszh.estools.domain.Student;
import cn.iszh.estools.utils.EsBulkCache;
import cn.iszh.estools.utils.EsUtils;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCRUDTest {
    @Resource
    private TestCRUD testCRUD;

    @Test
    public void test() throws Exception {
        Student student1 = new Student("stu1","boy",24,"2019-09-04");
        Student student2 = new Student("stu2","boy",23,"2019-09-04");
        Student student3 = new Student("stu3","boy",25,"2019-09-04");
        Student student4 = new Student("stu4","boy",14,"2019-09-04");
        Student student5 = new Student("stu5","boy",22,"2019-09-04");
        Student student6 = new Student("stu6","boy",21,"2019-09-04");
        EsBulkCache.addDocWriteRequest(EsUtils.initIndexRequest(EsIndexEnum.INDEX_STUDENT, "_doc", (JSONObject) JSONObject.toJSON(student1)));
        EsBulkCache.addDocWriteRequest(EsUtils.initIndexRequest(EsIndexEnum.INDEX_STUDENT, "_doc", (JSONObject) JSONObject.toJSON(student2)));
        EsBulkCache.addDocWriteRequest(EsUtils.initIndexRequest(EsIndexEnum.INDEX_STUDENT, "_doc", (JSONObject) JSONObject.toJSON(student3)));
        EsBulkCache.addDocWriteRequest(EsUtils.initIndexRequest(EsIndexEnum.INDEX_STUDENT, "_doc", (JSONObject) JSONObject.toJSON(student4)));
        EsBulkCache.addDocWriteRequest(EsUtils.initIndexRequest(EsIndexEnum.INDEX_STUDENT, "_doc", (JSONObject) JSONObject.toJSON(student5)));
        EsBulkCache.addDocWriteRequest(EsUtils.initIndexRequest(EsIndexEnum.INDEX_STUDENT, "_doc", (JSONObject) JSONObject.toJSON(student6)));
        EsBulkCache.executeBulk();
    }

    @Test
    public void crud() throws IOException {
        testCRUD.searchData();
    }
}