package com.app.config;

import com.app.util.JsonUtil;
import com.app.vo.User;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ElasticsearchModifyTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 测试文档的添加
    @Test
    public void testCreateDoc() throws IOException {
        // 准备好数据
        User user = new User("小杰", 23, "你要是这么想我也没办法", new String[]{"md", "渣男"});
        // 创建好index请求
        IndexRequest indexRequest = new IndexRequest("nan_index");
        // 设置索引
        indexRequest.id("1");
        // 设置超时时间（默认）
        indexRequest.timeout(TimeValue.timeValueSeconds(5));
        // 往请求中添加数据
        indexRequest.source(JsonUtil.object2Json(user), XContentType.JSON);
        //执行添加请求
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    // 测试文档批量添加（批量删除、更新、修改是一样的）
    @Test
    public void testBulkAdd() throws IOException {
        // 准备要添加的数据
        List<User> users = new ArrayList<>();
        users.add(new User("xiaofei", 25, "每天都是正能量", new String[]{"加油", "早起晚睡"}));
        users.add(new User("xiaohua", 22, "xiaohua", new String[]{"html", "早起晚睡"}));
        users.add(new User("xiaoer", 23, "我是菜鸟", new String[]{"宅男", "早起晚睡"}));
        users.add(new User("xiaoge", 22, "一看工资2500", new String[]{"加油", "渣男"}));
        users.add(new User("xiaomei", 23, "给我钱就行", new String[]{"hh", "早起晚睡"}));
        // 创建批量请求
        BulkRequest bulkRequest = new BulkRequest();
        // 利用循环将每一个add请求添加到bulkRequest请求中
        for (int i = 0; i < users.size(); i++) {
            IndexRequest indexRequest = new IndexRequest("nan_index").id("" + i);
            indexRequest.source(JsonUtil.object2Json(users.get(i)), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        // 执行批量请求
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse);
    }

    // 测试文档的更新(id存在就是更新,id不存在就是添加)
    // 用下面这个方法更新时，是全局更新，就是说里面的字段全部被覆盖，新对象里没有的字段就没有了
    @Test
    public void testUpdateDoc() throws IOException {
        User user = new User("xiaoer", 18);
        IndexRequest indexRequest = new IndexRequest("nan_index");
        indexRequest.id("2");
        indexRequest.source(JsonUtil.object2Json(user), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    //文档数据的更新（局部更新）（推荐）
    // 测试文档的更新(id存在就是更新,id不存在就是添加)
    // 用下面这个方法更新时，是局部更新，就是说只会覆盖其中有的字段
    @Test
    public void testUpdateBetterDoc() throws IOException {
        //准备好修改的数据
        User user = new User("xiaoer", 18);
        // 创建更新请求
        UpdateRequest updateRequest = new UpdateRequest("nan_index", "1");
        // 把要更新的数据装进去
        updateRequest.doc(JsonUtil.object2Json(user), XContentType.JSON);
        // 执行更新语句
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse);
    }

    // 测试文档删除
    @Test
    public void testDelDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("nan_index", "2");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse);
    }

}