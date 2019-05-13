# elasticsearch-client-spring-boot-starter
## 使用说明

### 1. 依赖

~~~xml
<dependency>
     <groupId>com.github.baiczsy</groupId>
     <artifactId>elasticsearch-client-spring-boot-starter</artifactId>
     <version>1.0.0</version>
</dependency>
~~~

注意，由于依赖的是elasticsearch-rest-high-level-client 7.0.0版本，需要替换spring boot自带的低版本，避免依赖冲突。

~~~xml
<properties>
      <!-- 替换spring boot的低版本 -->
      <elasticsearch.version>7.0.0</elasticsearch.version>
</properties>
~~~

### 2. application.yml配置

~~~yml
spring:
  elasticsearch:
    client: 
      headers: {header1: value1, header2: value2}
      pool:
        min-idle: 5
        max-idle: 8
        max-active: 20
        max-wait: 1000ms 
      nodes:
        - 192.168.0.100:9200
        - 192.168.0.101:9200
        - 192.168.0.102:9200  
~~~

### 3. 注入RestClientTemplate

~~~java
@Autowired
private RestClientTemplate template;
~~~

关于RestClientTemplate的使用请参阅https://github.com/baiczsy/spring-elasticsearch-client
