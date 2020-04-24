# redis操作工具类

作者：掌少

## 1. 概述

Spring 自带的 RedisTemplate 已经是非常不错的Redis操作工具，但是存在以下几个问题：

1. 不支持命名空间；使用命名空间，可以隔离应用之间的数据，避免冲突；方便后期迁移数据；方便通过key反向找到应用及其负责人；
2. 增加时间监控；
3. 对Lua支持更好；
4. 内嵌Hessian序列化工具；

## 2. 使用方法

## 2.1 导入依赖包

    compile "com.easysoft:easysoft-starter-redis:xxx"

## 2.2 redis配置
按org.springframework.boot.autoconfigure.data.redis.RedisProperties 进行配置
 
 1. 单机版Redis配置
 
        spring: 
          redis: 
            host: 172.16.135.249
            port: 6379
            pool: 
              maxWait: 1500
              maxActive: 2048
              minIdle: 20
              maxIdle: 200
        
 2. 集群配置
 
         spring: 
          redis: 
            cluster:
              nodes:
              - 10.7.100.95:7000
              - 10.7.100.95:7001
              - 10.7.100.95:7002
              - 10.7.100.95:7003
              - 10.7.100.95:7004
              - 10.7.100.95:7005
              - 10.7.100.95:7006
            pool: 
              maxWait: 1500
              maxActive: 2048
              minIdle: 20
              maxIdle: 200
              
  配置时，把集群的所有节点都加到配置文件中来。当master节点不可用时，slave节点自动切为master节点时，应用还能正常使用Redis。

以上Redis的两种配置，只要选择一种即可；

## 2.3 redis工具类配置

	easysoft:
      redis:
        namespace-enable: true
        slow-log-slower-than: 15
        namespace: redis

### 2.4 使用方法

    ......
    @Autowired
    private IRedisOperater redisOperater;
    ......
