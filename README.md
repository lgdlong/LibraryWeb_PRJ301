# LibraryWeb_PRJ301

# 📚 Library Management System (Java Servlet)

A simple web-based application built using **Java Servlet** to manage book borrowing operations. It connects to a **SQL Server** database and runs on **Apache Tomcat**.

---

## 🚀 Technologies Used

- **Java Servlet** (JDK/SDK: `1.8.0_172`)
- **Apache Tomcat** `10.x`
- **Microsoft SQL Server** as the relational database
- **JSP + JSTL** for view layer

---

## 🔧 Libraries & Dependencies

| Library             | Version     | Description                          |
|---------------------|-------------|--------------------------------------|
| `sqljdbc4.jar`      | (latest)    | Microsoft JDBC Driver for SQL Server |
| `jstl.jar`          | `2.0.0`     | JSTL core tag library                 |
| `jstl-api.jar`      | `2.0.0`     | JSTL API interfaces                   |
| JDK / Java SDK      | `1.8.0_172` | Java development platform             |

> All JAR files should be added to your project's `Libraries` folder in NetBeans or IntelliJ.

---

## ⚙️ How to Run

1. Import project into **NetBeans** or **IntelliJ**.
2. Make sure the following are configured:
    - Tomcat 10 installed and set up as Application Server
    - Microsoft SQL Server running and database is created
    - JDBC connection string updated in DAO/config
3. Build the project.
4. Run or deploy to Tomcat.
5. Access the app at [http://localhost:8080/LibraryWeb_PRJ301_G1](http://localhost:8080/LibraryWeb_PRJ301_G1).

---

## 🗃 Sample DB Configuration (in Java)

```
String url = "jdbc:sqlserver://localhost:1433;databaseName=library_system;encrypt=true;trustServerCertificate=true;";
String username = "sa";
String password = "12345";
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
Connection conn = DriverManager.getConnection(url, username, password);
```

## 👨‍💻 Group 1

-   SE190377 Phùng Lưu Hoàng Long
-   SE192850 Trần Sanh Điền
-   SE192515 Nguyễn Lê Phúc Nguyên
