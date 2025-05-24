# Base image: Tomcat 10 + JDK 8
FROM tomcat:10.0.27-jdk8 AS build-only

# ============ Cài Apache Ant =====================
# Chỉ cài 1 lần và tận dụng cache layer Docker
ENV ANT_VERSION=1.10.14
ENV ANT_HOME=/opt/apache-ant-${ANT_VERSION}
ENV PATH="${ANT_HOME}/bin:${PATH}"

RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://downloads.apache.org/ant/binaries/apache-ant-${ANT_VERSION}-bin.zip && \
    unzip apache-ant-${ANT_VERSION}-bin.zip -d /opt && \
    rm apache-ant-${ANT_VERSION}-bin.zip && \
    apt-get clean

# ============ Xoá ứng dụng mặc định trong Tomcat ============
RUN rm -rf /usr/local/tomcat/webapps/* \
    /usr/local/tomcat/webapps.dist \
    /usr/local/tomcat/logs/*

# ============ Copy source code trước để cache tốt ============
WORKDIR /app
# Ant script
COPY build.xml .
# Java source code
COPY src/ ./src
# JSP, web.xml
COPY web/ ./web
# Các .jar (jstl, sqljdbc4, v.v...)
COPY lib/ ./lib
COPY nbproject/ ./nbproject

# ============ Build WAR trong container ============
RUN ant clean dist

# ============ Copy WAR vào Tomcat ==================
RUN cp dist/*.war /usr/local/tomcat/webapps/LibraryWeb_PRJ301_G1.war

# ============ Copy user config =====================
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

# ============ Expose port ==========================
EXPOSE 8080
