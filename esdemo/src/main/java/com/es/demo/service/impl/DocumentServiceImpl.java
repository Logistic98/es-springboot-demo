package com.es.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.es.demo.constant.Constant;
import com.es.demo.pojo.UserDocument;
import com.es.demo.service.DocumentService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    /**
     * 新增文档
     * @param document
     * @return
     * @throws Exception
     */
    @Override
    public Boolean createDocument(UserDocument document) throws Exception {
        String id = document.getId();
        IndexRequest indexRequest = new IndexRequest(Constant.INDEX)
                .id(id)
                .source(JSON.toJSONString(document), XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.status().equals(RestStatus.CREATED);
    }

    /**
     * 查询文档
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public UserDocument queryDocument(String id) throws IOException {
        GetRequest getRequest = new GetRequest(Constant.INDEX, id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        UserDocument result = new UserDocument();
        if (getResponse.isExists()) {
            String sourceAsString = getResponse.getSourceAsString();
            result = JSON.parseObject(sourceAsString, UserDocument.class);
        } else {
            logger.error("没有找到该 id 的文档");
        }
        return result;
    }

    /**
     * 修改文档
     * @param document
     * @return
     * @throws Exception
     */
    @Override
    public Boolean updateDocument(UserDocument document) throws Exception {
        String id = document.getId();
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(Constant.INDEX).id(id);
        updateRequest.doc(JSON.toJSONString(document), XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse.status().equals(RestStatus.OK);
    }

    /**
     * 删除文档
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteDocument(String id) throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest(Constant.INDEX, id);
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        return response.getResult().name();
    }

    /**
     * 批量新增文档
     * @param documents
     * @return
     * @throws IOException
     */
    @Override
    public Boolean bulkCreateDocument(List<UserDocument> documents) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (UserDocument document : documents) {
            String id = document.getId();
            IndexRequest indexRequest = new IndexRequest(Constant.INDEX)
                    .id(id)
                    .source(JSON.toJSONString(document), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.status().equals(RestStatus.OK);
    }

    /**
     * 批量删除文档
     * @param documents
     * @return
     * @throws Exception
     */
    @Override
    public Boolean bulkDeleteDocument(List<UserDocument> documents) throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        for (UserDocument document : documents) {
            String id = document.getId();
            bulkRequest.add(new DeleteRequest().index(Constant.INDEX).id(id));
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.status().equals(RestStatus.OK);
    }

    /**
     * 全量查询文档
     * @return
     * @throws IOException
     */
    @Override
    public List<UserDocument> queryAllDocument() throws IOException {
        SearchRequest getAllRequest = new SearchRequest();
        getAllRequest.indices(Constant.INDEX);
        getAllRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse getAllResponse = client.search(getAllRequest, RequestOptions.DEFAULT);
        SearchHits hits = getAllResponse.getHits();
        List<UserDocument> result = new ArrayList<>();
        for ( SearchHit hit : hits ) {
            result.add(JSON.parseObject(hit.getSourceAsString(), UserDocument.class));
        }
        return result;
    }

    /**
     * 全量查询文档结果处理（字段排序、过滤字段）
     * @return
     * @throws IOException
     */
    @Override
    public List<UserDocument> queryFilterDocument() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices(Constant.INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.sort("age", SortOrder.DESC);
        String[] excludes = {"id","city"};
        String[] includes = {};
        builder.fetchSource(includes, excludes);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<UserDocument> result = new ArrayList<>();
        for ( SearchHit hit : hits ) {
            result.add(JSON.parseObject(hit.getSourceAsString(), UserDocument.class));
        }
        return result;
    }

    /**
     * 分页查询文档
     * @return
     * @throws IOException
     */
    @Override
    public List<UserDocument> queryPageDocument(int from, int size) throws IOException {
        SearchRequest getPartRequest = new SearchRequest();
        getPartRequest.indices(Constant.INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.from(from); // 分页起始位置,(当前页码-1)*每页显示数据条数
        builder.size(size); // 每页展示条数
        getPartRequest.source(builder);
        SearchResponse response = client.search(getPartRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<UserDocument> result = new ArrayList<>();
        for ( SearchHit hit : hits ) {
            result.add(JSON.parseObject(hit.getSourceAsString(), UserDocument.class));
        }
        return result;
    }

    /**
     * 单条件查询文档
     * @param name
     * @return
     * @throws IOException
     */
    @Override
    public List<UserDocument> querySingleConditionDocument(String name) throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices(Constant.INDEX);
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("name", name)));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<UserDocument> result = new ArrayList<>();
        for ( SearchHit hit : hits ) {
            result.add(JSON.parseObject(hit.getSourceAsString(), UserDocument.class));
        }
        return result;
    }

    /**
     * 组合条件查询文档
     * @param name,city
     * @return
     * @throws IOException
     */
    @Override
    public List<UserDocument> queryCombinationConditionDocument(String name,String city) throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices(Constant.INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", name));
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("city", city));
        builder.query(boolQueryBuilder);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<UserDocument> result = new ArrayList<>();
        for ( SearchHit hit : hits ) {
            result.add(JSON.parseObject(hit.getSourceAsString(), UserDocument.class));
        }
        return result;
    }

    /**
     * 范围查询文档
     * @param minAge,maxAge
     * @return
     * @throws IOException
     */
    @Override
    public List<UserDocument> queryRangeDocument(int minAge, int maxAge) throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices(Constant.INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        // gt大于，gte大于等于，lt小于，lte小于等于
        rangeQuery.gte(minAge);
        rangeQuery.lt(maxAge);
        builder.query(rangeQuery);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<UserDocument> result = new ArrayList<>();
        for ( SearchHit hit : hits ) {
            result.add(JSON.parseObject(hit.getSourceAsString(), UserDocument.class));
        }
        return result;
    }

    /**
     * 模糊查询文档
     * @param name
     * @return
     * @throws IOException
     */
    @Override
    public List<UserDocument> queryFuzzyDocument(String name) throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices(Constant.INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.fuzzyQuery("name", name).fuzziness(Fuzziness.ONE)); // 模糊字段偏移量
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<UserDocument> result = new ArrayList<>();
        for ( SearchHit hit : hits ) {
            result.add(JSON.parseObject(hit.getSourceAsString(), UserDocument.class));
        }
        return result;
    }
}
