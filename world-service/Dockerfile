FROM openjdk:17-slim as build

WORKDIR /app

# 复制maven配置和依赖
COPY ./pom.xml ./pom.xml
COPY ./mvnw ./mvnw
COPY ./.mvn ./.mvn

# 提前缓存依赖 - 避免每次构建都重新下载
RUN ./mvnw dependency:go-offline -B

# 复制源代码
COPY ./src ./src

# 构建应用
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# 设置运行阶段
FROM openjdk:17-slim

VOLUME /tmp

ARG DEPENDENCY=/app/target/dependency

# 复制项目依赖
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# 设置入口点
ENTRYPOINT ["java","-cp","app:app/lib/*","com.overengineered.world.WorldServiceApplication"]

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8082/actuator/health || exit 1

# 暴露端口
EXPOSE 8082
