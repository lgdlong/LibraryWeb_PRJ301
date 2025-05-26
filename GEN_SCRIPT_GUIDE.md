# Hướng dẫn Generate Scripts (.sql) trong SSMS

> Mục tiêu: Xuất toàn bộ **database schema + dữ liệu** ra file `.sql` để dùng trong Docker (init lại database cho toàn
> team).

## Khi nào cần làm?

- Sau khi bạn **chỉnh sửa table**, thêm dữ liệu mẫu, thay đổi cấu trúc…
- Trước khi chạy `make restart`
- Khi muốn chia sẻ phiên bản database hiện tại cho cả nhóm

## Step 1: Xuất database thành file `.sql`

<details>
<summary>Xem chi tiết</summary>

### 1. Mở Generate Scripts

- Chuột phải vào database `library_system` → chọn Tasks → Generate Scripts...

<img src="instruction/genscript_1.png" alt="Step 1" height="300"/>

### 2. Bỏ qua Introduction

- Nhấn **Next**.

<img src="instruction/genscript_2.png" alt="Step 2" height="300"/>

### 3. Chọn đối tượng để export

- Nhấn **Next**.

<img src="instruction/genscript_3.png" alt="Step 3" height="300"/>

### 4. Cấu hình nâng cao

- Tại bước "Set Scripting Options" → chọn **Advanced** (góc phải).
- Chọn xong click **Ok**.

<img src="instruction/genscript_4.png" alt="Step 4" height="300"/>

### 5. Lưu file `.sql`

- Chọn đường dẫn: `./database/init.sql` (**ghi đè file cũ**)
- Nhấn **Next → Finish**

<img src="instruction/genscript_5.png" alt="Step 5" height="500"/>

### 6. Còn lại

<img src="instruction/genscript_6.png" alt="Step 5" height="400"/>
<img src="instruction/genscript_7.png" alt="Step 5" height="400"/>
</details>

## Step 2: Tạo bản sao lưu database

```bash
make db-backup

```

→ Tạo bản sao như: `database/init-20250526-185211.sql`
