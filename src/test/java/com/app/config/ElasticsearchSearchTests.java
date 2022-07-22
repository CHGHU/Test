package com.app.config;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
public class ElasticsearchSearchTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //获取简单文档数据
    @Test
    public void testGetDoc() throws IOException {
        GetRequest getRequest = new GetRequest("nan_index");
        getRequest.id("1");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse);
    }

    // 测试文档列表的查询（带条件）
    // 这是ElasticSearch最重要的地方
    @Test
    public void testGetListDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("nan_index");
        // 构建搜索builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建查询条件(查询所有)
        //  MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        // 构建查询条件(精确匹配)
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("name", "xiaomei");

        // 把查询条件设置给搜索builder
        searchSourceBuilder.query(termsQueryBuilder);

        // 设置分页查询(跟sql语句的limit一样)
        searchSourceBuilder.from(0); // 开始下标(当前页码-1)*每页显示条数
        searchSourceBuilder.size(3); // 要查多少个

        // 设置排序规则
        searchSourceBuilder.sort("age", SortOrder.DESC);

        // 把所有条件设置给查询请求
        searchRequest.source(searchSourceBuilder);

        // 开始查询
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 查看结果(结果很多，所以循环)
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    // 测试过滤查询出来的字段（也就是不把表中所有的字段查出来）
    @Test
    public void testFilterDoc() throws IOException {
        // 构建搜索查询请求
        SearchRequest searchRequest = new SearchRequest().indices("nan_index");

        // 构建查询条件builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建真正的查询条件（这里是全部查询）
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        // 把查询条件设置给builder
        searchSourceBuilder.query(matchAllQueryBuilder);

        // 设置过滤字段
        String[] excludes = {};
        String[] includes = {"name"};
        searchSourceBuilder.fetchSource(includes, excludes);

        // 把所有的查询条件builder设置给查询请求
        searchRequest.source(searchSourceBuilder);

        //执行请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //打印结果
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    // 多条件查询，也叫组合查询
    @Test
    public void testBoolqueryDoc() throws IOException {
        // 构建查询请求
        SearchRequest searchRequest = new SearchRequest("nan_index");

        // 构建搜索条件builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建多条件builder
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 在多条件builder中设置满足条件
        // must 就是必须满足这个条件(相当于mysql中column = 值)
        // mustnot 就是必须不满足这个条件（相当于mysql中column！=值）
        // should 就是或者的意思（相当于mysql中的or）
        boolQueryBuilder.must(QueryBuilders.termsQuery("name", "xiaofei"));
//        boolQueryBuilder.must(QueryBuilders.termsQuery("age","24"));
        boolQueryBuilder.should(QueryBuilders.termsQuery("age", "24"));
        boolQueryBuilder.should(QueryBuilders.termsQuery("age", "28"));
        // 把多条件查询条件放到builder中
        searchSourceBuilder.query(boolQueryBuilder);

        // 把所有搜索条件设置到查询请求中
        searchRequest.source(searchSourceBuilder);
        // 执行请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 打印结果
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    // 范围查询
    @Test
    public void testRangeDoc() throws IOException {
        // 构建查询请求
        SearchRequest searchRequest = new SearchRequest("nan_index");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置到范围的字段
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("age");
        // 设置范围（）gte就是大于等于
        rangeQueryBuilder.gte(24);
        rangeQueryBuilder.lte(30);
        // 把范围查询设置到条件中
        searchSourceBuilder.query(rangeQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    // 模糊查询
    @Test
    public void testLikeDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("nan_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "xiao").fuzziness(Fuzziness.TWO);
        searchSourceBuilder.query(fuzzyQueryBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    //高亮查询
    @Test
    public void testHighLightDoc() throws IOException {
        // 构建搜索请求
        SearchRequest searchRequest = new SearchRequest("nan_index");

        // 构建搜索条件构造器(也就是总的搜索条件)
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建单独的一个高亮构建器
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 设置高亮字段
        highlightBuilder.preTags("<font color='red'>"); //前缀
        highlightBuilder.postTags("</font>");   // 后缀
        highlightBuilder.field("name");
        // 把单独的高亮构建器设置给总构建器
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // 把总的搜索条件给到搜索请求中
        searchRequest.source(searchSourceBuilder);
        // 执行请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 打印结果
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
            // 获取对应的高亮域
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields);
            // 获取对应的高亮字段
            HighlightField highlightField = highlightFields.get("name");
            if (highlightField != null) {
                // 拿到高亮字段的文本域
                Text[] texts = highlightField.getFragments();
                String name = "";
                for (Text text : texts) {
                    name += text;
                    // 打印高亮字段
                    System.out.println(name);
                }
            }
        }
    }

    // 最大值、平均值、最小值
    @Test
    public void testAggraDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("nan_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建一个最大值builder
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("MAXAGE").field("age");
        // 把最大值builder设置给总查询条件
        searchSourceBuilder.aggregation(maxAggregationBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    // 分组查询
    @Test
    public void testAggraGroupDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("nan_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建一个分组builder
        // terms里面的参数是给分组取的名字、后面field是要分组的字段
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("AGEGROUP").field("age");
        // 把分组builder设置给总查询条件
        searchSourceBuilder.aggregation(termsAggregationBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

}