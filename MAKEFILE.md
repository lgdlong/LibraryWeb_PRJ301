# 🧱 Tài liệu lệnh Makefile cho dự án LibraryWeb_PRJ301_G1

Tài liệu này liệt kê và giải thích toàn bộ các lệnh `make` trong dự án.
📌 **Luôn chạy các lệnh trong Git Bash để đảm bảo hoạt động ổn định trên mọi máy.**

---

## 🔨 Build & Xuất WAR

| Lệnh          | Mô tả                                       |
|---------------|---------------------------------------------|
| `make build`  | Build file `.war` và Docker image           |
| `make export` | Trích xuất WAR từ Docker ra thư mục `dist/` |

---

## 🚀 Khởi động / Tắt hệ thống

| Lệnh           | Mô tả                                                |
|----------------|------------------------------------------------------|
| `make up`      | Chạy toàn bộ hệ thống (Tomcat + MSSQL)               |
| `make down`    | Dừng toàn bộ container                               |
| `make restart` | Xoá toàn bộ dữ liệu DB (volume), build lại, chạy lại |

---

## 🧱 Chạy riêng database MSSQL

| Lệnh           | Mô tả                     |
|----------------|---------------------------|
| `make db-up`   | Bật riêng container MSSQL |
| `make db-down` | Tắt riêng container MSSQL |

---

## 💾 Quản lý backup database

| Lệnh             | Mô tả                                                                               |
|------------------|-------------------------------------------------------------------------------------|
| `make db-backup` | Tạo bản sao `init.sql` → lưu thành `init-<timestamp>.sql` trong thư mục `/database` |

---

## 🧹 Dọn dẹp

| Lệnh         | Mô tả                                   |
|--------------|-----------------------------------------|
| `make clean` | Xoá WAR + toàn bộ Docker image đã build |

---

## ✅ Gợi ý quan trọng

> 🧠 `init.sql` là file được Docker chạy để khởi tạo database mỗi lần bạn dùng `make restart`.
> Nếu bạn thay đổi bảng hoặc dữ liệu, hãy xuất lại file này bằng SSMS, sau đó chạy `make db-backup` để lưu bản cũ.

> 🔁 Khi bạn pull code mới hoặc teammate vừa update `init.sql`, **hãy luôn chạy `make restart`** để đảm bảo hệ thống hoạt
> động đồng nhất.
