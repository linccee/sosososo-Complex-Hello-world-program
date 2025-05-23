# 极其复杂的 Hello World 系统

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Code Quality](https://img.shields.io/badge/code%20quality-A%2B-brightgreen.svg)]()
[![Coverage](https://img.shields.io/badge/coverage-98%25-brightgreen.svg)]()

## 项目简介

这是一个**极其复杂**且**过度设计**的 Hello World 应用程序。正常情况下，打印 "Hello World" 只需要一行代码，然而这个项目使用了最复杂的企业级架构、大量设计模式和最佳实践，以构建一个分布式微服务系统来实现这个简单的功能。

项目设计理念：以幽默的方式展示软件开发中的过度工程现象，同时作为一个学习工具，展示现代企业级应用的各种架构模式和技术。

## 系统架构

![系统架构图](documentation/architecture.png)

系统由以下四个主要组件构成：

1. **Hello 服务**：负责生成 "Hello" 部分的文本，支持多语言和多种策略。
2. **World 服务**：负责生成 "World" 部分的文本，支持多种行星类型和地理范围。
3. **Hello World 聚合器**：聚合 Hello 和 World 服务的结果，并提供统一的 API。
4. **Hello World 客户端**：命令行客户端，允许用户与系统交互。

## 技术栈

- **Java 17** - 编程语言
- **Spring Boot** - 应用框架
- **Spring Cloud** - 微服务工具集
- **PostgreSQL** - Hello 服务的关系型数据库
- **MongoDB** - World 服务的文档型数据库
- **Redis** - 缓存
- **Kafka** - 消息队列
- **Eureka** - 服务发现
- **Feign** - 声明式 REST 客户端
- **Resilience4j** - 容错库
- **Micrometer** - 指标收集
- **Zipkin** - 分布式追踪
- **Prometheus & Grafana** - 监控和可视化
- **Picocli** - 命令行界面
- **Docker & Docker Compose** - 容器化和编排

## 项目结构

```
hello-world-super-complex/
├── pom.xml                     # 父 POM 文件
├── docker-compose.yml          # Docker Compose 配置
├── prometheus.yml              # Prometheus 配置
├── hello-service/              # Hello 服务模块
├── world-service/              # World 服务模块
├── hello-world-aggregator/     # 聚合器服务模块
└── hello-world-client/         # 命令行客户端模块
```

## 设计模式

该项目实现了多种设计模式：

1. **策略模式** - 用于 Hello 和 World 文本生成
2. **工厂模式** - 用于创建不同的生成策略
3. **观察者模式** - 用于事件通知和处理
4. **适配器模式** - 用于数据转换和兼容性
5. **代理模式** - 用于缓存和远程调用
6. **命令模式** - 用于客户端命令处理
7. **装饰器模式** - 用于增强核心功能
8. **单例模式** - 用于共享资源管理
9. **模板方法模式** - 用于定义算法骨架
10. **组合模式** - 用于消息组装

## 功能特性

- **多语言支持**：支持多种语言的 Hello 文本
- **多行星类型**：支持不同行星的 World 文本
- **不同地理范围**：全球、大陆、区域等不同范围的文本
- **不同正式程度**：从非常随意到非常正式的多种语调
- **自定义分隔符**：可自定义 Hello 和 World 之间的分隔符
- **大小写转换**：将输出转换为大写
- **反转功能**：反转输出
- **加密功能**：加密输出
- **异步生成**：支持异步消息生成
- **彩色输出**：命令行客户端中的彩色显示
- **缓存**：用 Redis 缓存常用结果
- **熔断**：内置服务熔断机制
- **指标收集**：收集和暴露性能指标

## 运行指南

### 前提条件

- Java 17+
- Maven 3.6+
- Docker & Docker Compose

### 本地运行

1. 克隆仓库：

```bash
git clone https://github.com/your-username/hello-world-super-complex.git
cd hello-world-super-complex
```

2. 构建项目：

```bash
mvn clean package
```

3. 启动所有服务 (Docker方式)：

```bash
docker-compose up -d
```

4. 使用客户端：

```bash
cd hello-world-client
mvn spring-boot:run -Dspring-boot.run.arguments="--language=en --formality=3 --planet=EARTH --scope=GLOBAL"
```

### 客户端使用

可用选项：

```
--language, -l           语言代码 (例如: en, fr, es)
--formality, -f          正式程度 (1-5, 其中1是随意, 5是非常正式)
--planet, -p             行星类型 (EARTH, MARS, VENUS 等)
--scope, -s              地理范围 (GLOBAL, CONTINENTAL, REGIONAL, NATIONAL, LOCAL)
--delimiter, -d          Hello 和 World 之间的分隔符
--uppercase, -u          将输出转换为大写
--reversed, -r           反转输出
--encrypted, -e          加密输出
--async, -a              异步生成消息
--interval, -i           异步请求的轮询间隔(毫秒)
--color, -c              使用彩色输出
--help, -h               显示帮助信息
```

示例：

```bash
# 基本使用
java -jar target/hello-world-client-1.0.0-SNAPSHOT.jar

# 自定义参数
java -jar target/hello-world-client-1.0.0-SNAPSHOT.jar --language=fr --formality=5 --planet=MARS --scope=LOCAL --delimiter=" ~ " --uppercase
```

## API 文档

所有服务都提供了 Swagger UI，可通过以下 URL 访问：

- Hello 服务: http://localhost:8081/api/v1/hello/swagger-ui.html
- World 服务: http://localhost:8082/api/v1/world/swagger-ui.html
- 聚合器服务: http://localhost:8080/api/v1/hello-world/swagger-ui.html

## 监控

- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (默认用户名/密码: admin/admin)
- **Zipkin**: http://localhost:9411
- **Eureka**: http://localhost:8761

## 生产环境配置

虽然这个项目主要是一个演示，但它完全可以在生产环境中运行。对于生产环境，建议进行以下配置：

1. 启用 HTTPS 和正确的认证机制
2. 使用外部配置服务器管理敏感配置
3. 设置适当的资源限制
4. 实施日志聚合
5. 设置适当的备份策略
6. 实施合适的水平扩展策略

## 贡献指南

1. Fork 仓库
2. 创建功能分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证 - 详情见 [LICENSE](LICENSE) 文件

## 致谢

感谢所有对过度工程和复杂性科学做出贡献的开发者。正是因为你们，我们才能用 10000 行代码做到一行代码就能完成的事情。
