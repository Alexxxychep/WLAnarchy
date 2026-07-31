FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S minecraft && adduser -S -G minecraft minecraft

WORKDIR /data
RUN mkdir -p /data/plugins

COPY docker/paper-1.21.11.jar /data/
COPY build/libs/WLAnarchy-1.0-reobf.jar /data/plugins/WLAnarchy-1.0.jar
RUN chown -R minecraft:minecraft /data

USER minecraft

EXPOSE 25565

ENV MEMORY=2G

CMD echo "eula=true" > /data/eula.txt && \
    java -Xmx${MEMORY} -Xms${MEMORY} -jar /data/paper-1.21.11.jar nogui