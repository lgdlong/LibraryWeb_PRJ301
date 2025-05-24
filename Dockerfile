# Base image: Tomcat 10 + JDK 8 (JDK 1.8.0_172)
FROM tomcat:10.0.27-jdk8

# Xóa ứng dụng mặc định để nhẹ và sạch hơn
RUN rm -rf /usr/local/tomcat/webapps/* \
           /usr/local/tomcat/webapps.dist \
           /usr/local/tomcat/logs/*

# Copy WAR file vào webapps với context path gốc
COPY dist/LibraryWeb_PRJ301_G1.war /usr/local/tomcat/webapps/LibraryWeb_PRJ301_G1.war

# Copy tomcat-users.xml từ project vào container
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

# Mở port 8080
EXPOSE 8080
