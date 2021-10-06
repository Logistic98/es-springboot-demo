package com.es.demo.controller;

import com.es.demo.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 创建索引
     * @param index
     * @return
     * @throws Exception
     */
    @PostMapping("/createIndex")
    public ResponseEntity<Boolean> createIndex(@RequestParam(value = "index") String index) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.createIndex(index));
    }

    /**
     * 查询索引
     * @param index
     * @return
     * @throws Exception
     */
    @PostMapping("/queryIndex")
    public String[] queryIndex(@RequestParam(value = "index") String index) throws Exception {
        return indexService.queryIndex(index);
    }


    /**
     * 删除索引
     * @param index
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteIndex")
    public ResponseEntity<Boolean> deleteIndex(@RequestParam(value = "index") String index) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.deleteIndex(index));
    }
}
