package com.es.demo.controller;

import com.es.demo.pojo.UserDocument;
import com.es.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    /**
     * 新增文档
     * @param document
     * @return
     * @throws Exception
     */
    @PostMapping("/createDocument")
    public ResponseEntity<Boolean> createDocument(@RequestBody UserDocument document) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(document));
    }

    /**
     * 查询文档
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/queryDocument")
    public UserDocument queryDocument(@RequestParam String id) throws Exception {
        return documentService.queryDocument(id);
    }

    /**
     * 修改文档
     * @param document
     * @return
     * @throws Exception
     */
    @PostMapping("/updateDocument")
    public ResponseEntity<Boolean> updateDocument(@RequestBody UserDocument document) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.updateDocument(document));
    }

    /**
     * 删除文档
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteDocument")
    public String deleteDocument(@RequestParam String id) throws Exception {
        return documentService.deleteDocument(id);
    }

    /**
     * 批量新增文档
     * @param document
     * @return
     * @throws Exception
     */
    @PostMapping("/bulkCreateDocument")
    public ResponseEntity<Boolean> bulkCreateDocument(@RequestBody List<UserDocument> document) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.bulkCreateDocument(document));
    }

    /**
     * 批量删除文档
     * @param document
     * @return
     * @throws Exception
     */
    @PostMapping("/bulkDeleteDocument")
    public Boolean bulkDeleteDocument(@RequestBody List<UserDocument> document) throws Exception {
        return documentService.bulkDeleteDocument(document);
    }

    /**
     * 全量查询文档
     * @return
     * @throws Exception
     */
     @GetMapping("/queryAllDocument")
     public List<UserDocument> queryAllDocument() throws Exception {
        return documentService.queryAllDocument();
     }

    /**
     * 全量查询文档结果处理（字段排序、过滤字段）
     * @return
     * @throws Exception
     */
    @GetMapping("/queryFilterDocument")
    public List<UserDocument> queryFilterDocument() throws Exception {
        return documentService.queryFilterDocument();
    }

    /**
     * 分页查询文档
     * @return
     * @throws Exception
     */
    @GetMapping("/queryPageDocument")
    public List<UserDocument> queryPageDocument(@RequestParam int from, int size) throws Exception {
        return documentService.queryPageDocument(from,size);
    }

    /**
     * 单条件查询文档
     * @param name
     * @return
     * @throws Exception
     */
    @GetMapping("/querySingleConditionDocument")
    public List<UserDocument> querySingleConditionDocument(@RequestParam String name) throws Exception {
        return documentService.querySingleConditionDocument(name);
    }

    /**
     * 组合条件查询文档
     * @param name,city
     * @return
     * @throws Exception
     */
    @GetMapping("/queryCombinationConditionDocument")
    public List<UserDocument> queryCombinationConditionDocument(@RequestParam String name, String city) throws Exception {
        return documentService.queryCombinationConditionDocument(name,city);
    }

    /**
     * 范围查询文档
     * @param minAge,maxAge
     * @return
     * @throws Exception
     */
    @GetMapping("/queryRangeDocument")
    public List<UserDocument> queryRangeDocument(@RequestParam int minAge, int maxAge) throws Exception {
        return documentService.queryRangeDocument(minAge,maxAge);
    }

    /**
     * 模糊查询文档
     * @param name
     * @return
     * @throws Exception
     */
    @GetMapping("/queryFuzzyDocument")
    public List<UserDocument> queryFuzzyDocument(@RequestParam String name) throws Exception {
        return documentService.queryFuzzyDocument(name);
    }

}
