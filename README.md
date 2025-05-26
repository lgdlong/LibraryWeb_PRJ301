# 📚 LibraryWeb_PRJ301_G1

A full-stack **Library Management System** built with **Java Servlet**, **JSP**, and **SQL Server**, using **Docker
Compose** and a clean, simple **Makefile-based workflow** for easy development and collaboration.

> ✅ Mọi thành viên cần sử dụng **Git Bash** khi chạy lệnh, và làm việc trên **nhánh phụ** – không commit trực tiếp lên
`main`.

---

## 🔁 Flow làm việc chuẩn

### 1️⃣ Tạo nhánh riêng để làm việc

```bash
git checkout -b <ten-nhanh-cua-ban>

```

Khi xong, push và tạo **Pull Request (PR)** về `main`.

----------

### 2️⃣ Mỗi khi khởi động dự án (bắt buộc cho từng thành viên)

✅ DÙNG `Git Bash` để chạy lệnh

```bash
make restart

```

👉 Lệnh này sẽ: Xoá sạch database cũ + Dùng lại file `database/init.sql` + Build WAR + chạy lại toàn bộ hệ thống


> ❗ Mục tiêu: giúp mọi thành viên **luôn dùng cùng 1 phiên bản database**, tránh mâu thuẫn hoặc lỗi dữ liệu cũ.

----------

### 🛠 Khi bạn chỉnh sửa database (schema hoặc dữ liệu)

1. Xem hướng dẫn xuất database thành file .sql tại đây:

> 📘 [GEN_SCRIPT_GUIDE.md](GEN_SCRIPT_GUIDE.md)

2. (Tuỳ chọn) **Lưu lại bản cũ trước khi ghi đè:**

```bash
make db-backup

```

→ Lưu bản sao `init.sql` thành `init-YYYYMMDD-HHMMSS.sql`



----------

### 3️⃣ Truy cập ứng dụng

- Web app: [http://localhost:8080/LibraryWeb_PRJ301_G1](http://localhost:8080/LibraryWeb_PRJ301_G1)

- Tomcat Manager: [http://localhost:8080/manager/html](http://localhost:8080/manager/html)
  Tài khoản: `admin` / Mật khẩu: `admin`

----------

### 4️⃣ Kết nối CSDL

<img src="instruction/connect_db.png" alt="Step 3" height="300"/>

Dùng DBeaver, Azure Data Studio hoặc SSMS:

| Thông tin     | Giá trị                           |
|---------------|-----------------------------------|
| Host          | `localhost`                       |
| Port          | `1433`                            |
| User          | `sa`                              |
| Password      | `YourStrong!Passw0rd`             |
| Database name | `library_system` (xem `init.sql`) |

----------

## 🧱 Cấu trúc Makefile hỗ trợ

| Lệnh             | Mô tả                                         |
|------------------|-----------------------------------------------|
| `make build`     | Build WAR và Docker image                     |
| `make export`    | Export WAR ra thư mục `dist/`                 |
| `make up`        | Chạy toàn bộ hệ thống                         |
| `make down`      | Dừng toàn bộ container                        |
| `make restart`   | Reset toàn bộ: xóa DB + build lại + chạy      |
| `make up`        | Chạy toàn bộ hệ thống                         |
| `make clean`     | Xóa WAR và Docker images                      |
| `make db-up`     | Chỉ bật MSSQL                                 |
| `make db-down`   | Chỉ tắt MSSQL                                 |
| `make db-backup` | Backup file `init.sql` thành bản có timestamp |

> 🧠 File `init.sql` được dùng để khởi tạo lại DB mỗi lần `restart`. Nếu bạn sửa dữ liệu hoặc schema, hãy update file này
> và backup lại.

----------

## 🔌 Ví dụ kết nối JDBC

```java
String url = "jdbc:sqlserver://mssql:1433;databaseName=library_system;encrypt=true;trustServerCertificate=true;";
String username = "sa";
String password = "YourStrong!Passw0rd";
Class.

forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

Connection conn = DriverManager.getConnection(url, username, password);

```

> ⚠️ `mssql` là tên service nội bộ Docker (không phải `localhost` khi chạy trong container).

----------

## 📦 Công nghệ sử dụng

- Java Servlet (JDK 1.8.0_172)

- Apache Tomcat 10.0.27

- Microsoft SQL Server 2019 (Docker)

- JSP + JSTL (2.0.0)

- Docker & Docker Compose

- Makefile (build automation)

----------

## 👥 Thành viên nhóm – Group 1

- SE190377 – Phùng Lưu Hoàng Long

- SE192850 – Trần Sanh Điền

- SE192515 – Nguyễn Lê Phúc Nguyên

----------

## 📌 Lưu ý

```text
- Luôn dùng Git Bash để chạy lệnh make
- Không commit các file: *.class, *.iml, dist/*.war
- Nếu chỉnh sửa database/init.sql → chạy make db-backup trước

```
