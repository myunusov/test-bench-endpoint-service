FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine as builder
ADD . /src
WORKDIR /src
RUN ["chmod", "777", "./mvnw"]
RUN ./mvnw package -DskipTests

FROM alpine:3.10.3 as packager
RUN apk --no-cache add openjdk11-jdk openjdk11-jmods
ENV JAVA_MINIMAL="/opt/java-minimal"
# build minimal JRE
RUN /usr/lib/jvm/java-11-openjdk/bin/jlink \
    --verbose \
    --add-modules \
        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
    --compress 2 --strip-debug --no-header-files --no-man-pages \
    --release-info="add:IMPLEMENTOR=radistao:IMPLEMENTOR_VERSION=radistao_JRE" \
    --output "$JAVA_MINIMAL"

FROM alpine:3.10.3
LABEL maintainer="Maksim Iunusov maksim@iunusov.ru"

# Add Tini (see https://github.com/krallin/tini)
RUN apk add --no-cache tini
ENTRYPOINT ["/sbin/tini", "--"]

ENV JAVA_HOME=/opt/java-minimal
ENV PATH="$PATH:$JAVA_HOME/bin"
COPY --from=packager "$JAVA_HOME" "$JAVA_HOME"
COPY --from=builder /src/target/endpoint-*.jar app.jar
EXPOSE 8080
CMD ["java","-jar","/app.jar"]