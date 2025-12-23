FROM eclipse-temurin:8-jdk

WORKDIR /app

COPY OperationsBancaires.idl /app/
RUN idlj -fall -td generated OperationsBancaires.idl

COPY src /app/src

RUN mkdir -p /app/build/classes \
    && javac -d /app/build/classes $(find src generated -name "*.java")
