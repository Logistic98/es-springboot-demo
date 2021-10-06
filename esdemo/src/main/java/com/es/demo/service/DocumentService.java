package com.es.demo.service;

import com.es.demo.pojo.UserDocument;
import java.io.IOException;
import java.util.List;

public interface DocumentService {

    /**
     * 新增文档
     * @param document
     * @return
     * @throws Exception
     */
    Boolean createDocument(UserDocument document) throws Exception;

    /**
     * 查询文档
     * @param id
     * @return
     * @throws IOException
     */
    UserDocument queryDocument(String id) throws IOException;

    /**
     * 修改文档
     * @param document
     * @return
     * @throws Exception
     */
    Boolean updateDocument(UserDocument document) throws Exception;

    /**
     * 删除文档
     * @param id
     * @return
     * @throws Exception
     */
    String deleteDocument(String id) throws Exception;

    /**
     * 批量新增文档
     * @param documents
     * @return
     * @throws IOException
     */
    Boolean bulkCreateDocument(List<UserDocument> documents) throws IOException;

    /**
     * 批量删除文档
     * @param documents
     * @return
     * @throws Exception
     */
    Boolean bulkDeleteDocument(List<UserDocument> documents) throws Exception;

    /**
     * 全量查询文档
     * @return
     * @throws IOException
     */
    List<UserDocument> queryAllDocument() throws IOException;

    /**
     * 全量查询文档结果处理（字段排序、过滤字段）
     * @return
     * @throws IOException
     */
    List<UserDocument> queryFilterDocument() throws IOException;

    /**
     * 分页查询文档
     * @return
     * @throws IOException
     */
    List<UserDocument> queryPageDocument(int from, int size) throws IOException;

    /**
     * 单条件查询文档
     * @param name
     * @return
     * @throws IOException
     */
    List<UserDocument> querySingleConditionDocument(String name) throws IOException;

    /**
     * 组合条件查询
     * @param name,city
     * @return
     * @throws IOException
     */
    List<UserDocument> queryCombinationConditionDocument(String name,String city) throws IOException;

    /**
     * 范围查询文档
     * @param minAge,maxAge
     * @return
     * @throws IOException
     */
    List<UserDocument> queryRangeDocument(int minAge, int maxAge) throws IOException;

    /**
     * 模糊查询文档
     * @param name
     * @return
     * @throws IOException
     */
    List<UserDocument> queryFuzzyDocument(String name) throws IOException;


}
