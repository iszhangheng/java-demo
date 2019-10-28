package cn.iszh.estools.utils;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: EsUtils
 * @packageName: cn.iszh.estools.utils
 * @description: ES_CRUD工具类
 * @date: 2019-09-12 13:54
 **/

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

public class EsUtils {
    /**
     * 获取删除指定索引，类型，文档的请求
     *
     * @param index 索引
     * @param type  类型
     * @param docId 文档ID
     * @return 删除请求对象
     */
    public static DocWriteRequest initDeleteRequest(String index, String type, String docId) {
        return new DeleteRequest(index, type, docId);
    }

    /**
     * 获取插入指定数据的请求
     *
     * @param index 索引
     * @param type  类型
     * @param docId 文档ID
     * @param obj   插入信息
     * @return 插入请求对象
     */
    public static DocWriteRequest initIndexRequest(String index, String type, String docId, JSONObject obj) {
        return new IndexRequest(index, type, docId).source(obj, XContentType.JSON);
    }

    /**
     * 获取插入指定数据的请求
     *
     * @param index 索引
     * @param type  类型
     * @param obj   插入信息
     * @return 插入请求对象
     */
    public static DocWriteRequest initIndexRequest(String index, String type, JSONObject obj) {
        return new IndexRequest(index, type).source(obj, XContentType.JSON);
    }
}

