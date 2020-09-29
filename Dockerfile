FROM oracle/graalvm-ce:20.1.0-java8 as graalvm
RUN gu install native-image

COPY . /home/app/micronaut_led
WORKDIR /home/app/micronaut_led

RUN native-image --no-server -cp build/libs/micronaut_led-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut_led/micronaut_led /app/micronaut_led
ENTRYPOINT ["/app/micronaut_led"]
