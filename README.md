# elasticsearch-client-spring-boot-starter
## 在spring boot中使用spring-elasticsearch-client

### 1. 依赖

~~~xml
<dependency>
     <groupId>com.github.baiczsy</groupId>
     <artifactId>elasticsearch-client-spring-boot-starter</artifactId>
     <version>1.0.0</version>
</dependency>
~~~

注意，由于依赖的是elasticsearch-rest-high-level-client 7.0.0版本，需要替换spring boot集成的低版本，避免依赖冲突。

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
    nodes:
      - 192.168.0.100:9200
      - 192.168.0.101:9200
      - 192.168.0.102:9200
    client:
      connect-timeout: 1000
      connection-request-timeout: 500
      socket-timeout: 20000
      max-conn-total: 100
      max-conn-per-route: 100 
      headers: {header1: value1, header2: value2}
    pool:
      min-idle: 5
      max-idle: 8
      max-active: 20
      max-wait: 1000ms
~~~

### 3. 注入RestClientTemplate

~~~java
@Autowired
private RestClientTemplate template;
~~~

关于RestClientTemplate的使用请参阅https://github.com/baiczsy/spring-elasticsearch-client
