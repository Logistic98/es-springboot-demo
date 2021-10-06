# es-springboot-demo

Elasticsearch与Springboot整合及基本增删查改操作示例。

详见：[Elasticsearch整合及基本操作示例](https://www.eula.club/Elasticsearch%E6%95%B4%E5%90%88%E5%8F%8A%E5%9F%BA%E6%9C%AC%E6%93%8D%E4%BD%9C%E7%A4%BA%E4%BE%8B.html)

文档说明：

- 尚硅谷项目课程系列之Elasticsearch.pdf —— Elasticsearch视频教程的配套资料。
- ES基本操作.postman_collection.json —— 通过HTTP请求直接操作Elasticsearch。
- ES-Springboot整合增删查改示例.postman_collection.json —— 里通过Java操作Elasticsearch （本项目要用的）

使用方法：

- Step1：使用Maven拉取项目依赖，安装Elasticsearch开发环境（版本7.14.1，开启了xpack安全验证）。
- Step2：把application.properties.example去掉.example，然后配置自己的Elasticsearch环境信息，启动项目。
- Step3：将ES-Springboot整合增删查改示例.postman_collection.json导入Postman工具，进行测试即可。

