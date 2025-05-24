# 📚 LibraryWeb_PRJ301_G1

A web-based **Library Management System** built with **Java Servlet**, **JSP**, and **SQL Server**, running on **Tomcat
10**. This project uses **Docker + Makefile** to help every team member build and run the app quickly and consistently.

---

## 🚀 Technologies Used

- **Java Servlet** (JDK 1.8.0_172)
- **Apache Tomcat** 10.0.27
- **Microsoft SQL Server** 2019 (inside Docker)
- **JSP + JSTL** (2.0.0)
- **Docker & Docker Compose**
- **Makefile** for build/run automation

---

## 📦 Libraries

| Library        | Version   | Description                          |
|----------------|-----------|--------------------------------------|
| `sqljdbc4.jar` | latest    | Microsoft JDBC Driver for SQL Server |
| `jstl.jar`     | 2.0.0     | JSTL core tag library                |
| `jstl-api.jar` | 2.0.0     | JSTL API interfaces                  |
| `jdk`          | 1.8.0_172 | Java development kit                 |

---

## ⚙️ Hướng Dẫn Dành Cho Nhóm

### ✅ Bước 1: Cài đặt yêu cầu

- [x] Java JDK 1.8.0_172
- [x] Docker Desktop (bật WSL nếu dùng Windows)
- [x] Có NetBeans hoặc IntelliJ để viết code (nếu muốn)

---

### 🛠️ Các lệnh `make` dành cho nhóm

| Lệnh                          | Mô tả                                                                  |
|-------------------------------|------------------------------------------------------------------------|
| `make` hoặc `make all`        | Build WAR bằng Ant và Docker image (chưa chạy app)                     |
| `make up`                     | Build và chạy cả app + MSSQL, dữ liệu được giữ lại từ lần trước        |
| `make prj-restart` 🔥         | XÓA toàn bộ dữ liệu DB, build lại WAR + Docker, chạy lại từ `init.sql` |
| `make db-up` / `make db-down` | Chạy hoặc tắt riêng dịch vụ MSSQL                                      |
| `make clean`                  | Xoá WAR + Docker image                                                 |
| `make export-db`              | (Thử nghiệm) Xuất schema từ DB ra `init.sql`                           |

---

### 🔁 Quy trình làm việc chuẩn cho mỗi thành viên

1. 🔄 **Khởi động lại từ đầu (nếu muốn sạch DB):**
   ```bash
   make prj-restart

	```

2. 📦 **Thay đổi code → build lại (không cần reset DB):**

   ```bash
   make build-java
   make build-docker
   make up

   ```

3. 🧪 **Truy cập hệ thống:**

- Ứng dụng: [http://localhost:8080/LibraryWeb_PRJ301_G1](http://localhost:8080/LibraryWeb_PRJ301_G1)

- Tomcat Manager: [http://localhost:8080/manager/html](http://localhost:8080/manager/html)
  Username: `admin` | Password: `admin`

4. 🧬 **Kết nối SQL Server bằng DBeaver/Azure Data Studio:**

- Host: `localhost`

- Port: `1433`

- User: `sa`

- Pass: `12345`

- DB name: theo `init.sql` của bạn

----------

## 🗃 Sample DB Connection Code (Java)

```java
String url = "jdbc:sqlserver://mssql:1433;databaseName=library_system;encrypt=true;trustServerCertificate=true;";
String username = "sa";
String password = "12345";
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
Connection conn = DriverManager.getConnection(url, username, password);

```

> Lưu ý: `"mssql"` là tên service trong Docker, không phải `localhost`.

----------

## 👨‍👨‍👦 Nhóm thực hiện – Group 1

- SE190377 – Phùng Lưu Hoàng Long

- SE192850 – Trần Sanh Điền

- SE192515 – Nguyễn Lê Phúc Nguyên

----------

## 📌 Ghi chú cuối
```text
- Không commit `*.class`, `*.iml`, hoặc `dist/*.war`
- Nhớ cập nhật lại `db/init.sql` nếu thay đổi dữ liệu

```
