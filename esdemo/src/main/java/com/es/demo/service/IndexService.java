package com.es.demo.service;

import java.io.IOException;

public interface IndexService {

    /**
     * 创建索引
     * @param index
     * @return
     * @throws IOException
     */
    boolean createIndex(String index) throws IOException;

    /**
     * 查询索引
     * @param index
     * @return
     * @throws IOException
     */
    String[] queryIndex(String index) throws IOException;

    /**
     * 删除索引
     * @param index
     * @return
     * @throws IOException
     */
    boolean deleteIndex(String index) throws IOException;
}
