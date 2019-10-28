package cn.iszh.estools.utils;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: BulkCache
 * @packageName: cn.iszh.estools.utils
 * @description: ES批量执行请求缓存工具类
 * @date: 2019-09-12 14:01
 **/
@Component
public class EsBulkCache {
    private final static Logger LOGGER = LoggerFactory.getLogger(EsBulkCache.class);

    private final static List<DocWriteRequest> lists = new ArrayList<>();
    private final static Integer FLASH_SIZE = 1000;
    private static RestHighLevelClient highLevelClient;

    /**
     * 添加索引
     *
     * @param indexRequest
     */
    public synchronized static void addIndexRequest(IndexRequest indexRequest) throws Exception {
        addDocWriteRequest(indexRequest);
    }

    /**
     * 添加请求
     *
     * @param docWriteRequest
     * @throws Exception
     */
    public synchronized static void addDocWriteRequest(DocWriteRequest docWriteRequest) throws Exception {
        lists.add(docWriteRequest);
        if (lists.size() == FLASH_SIZE) {
            executeBulk();
        }
    }

    /**
     * 获取列表大小
     */
    public synchronized static Integer getSize() throws Exception {
        return lists.size();
    }

    /**
     * 执行插入操作
     *
     * @throws Exception
     */
    public synchronized static void executeBulk() throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        LOGGER.info("缓存日志：" + lists.size() + "条，开始插入。");
        for (DocWriteRequest item : lists) {
            bulkRequest.add(item);
        }
        executeBulk(bulkRequest);
        lists.clear();
    }

    /**
     * 执行插入操作
     *
     * @throws Exception
     */
    public synchronized static void executeBulk(BulkRequest bulkRequest) throws Exception {
        // 执行插入
        BulkResponse bulkResponse = highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        // 检测插入结果
        int errorNum = 0, successNum = 0;
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            if (bulkItemResponse.isFailed()) {
                errorNum++;
                LOGGER.error("批量执行异常：" + bulkItemResponse.getFailure());
                DocWriteResponse itemResponse = bulkItemResponse.getResponse();
                if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
                    LOGGER.error("插入异常：" + (IndexResponse) itemResponse);
                } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
                    LOGGER.error("更新异常：" + (UpdateResponse) itemResponse);
                } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
                    LOGGER.error("删除异常：" + (DeleteResponse) itemResponse);
                }
            } else {
                successNum++;
            }
        }
        LOGGER.info("批量执行结果：成功插入" + successNum + "条，插入失败" + errorNum + "条。");
    }

    public static RestHighLevelClient getHighLevelClient() {
        return highLevelClient;
    }

    @Autowired
    public void setHighLevelClient(RestHighLevelClient highLevelClient) {
        EsBulkCache.highLevelClient = highLevelClient;
    }
}

