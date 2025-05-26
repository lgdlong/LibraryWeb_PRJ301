# 🛠️ Hướng dẫn Generate Scripts (.sql) trong SSMS

> 🎯 Mục tiêu: Xuất toàn bộ **database schema + dữ liệu** ra file `.sql` để dùng trong Docker (init lại database cho toàn
> team).

---

## ✅ Khi nào cần làm?

- Sau khi bạn **chỉnh sửa table**, thêm dữ liệu mẫu, thay đổi cấu trúc…
- Trước khi chạy `make restart`
- Khi muốn chia sẻ phiên bản database hiện tại cho cả nhóm

---

## 🪜 Các bước thực hiện trong SSMS

<img src="instruction/genscript_1.png" alt="Step 1" height="300"/>

Chuột phải vào database `library_system` → chọn Tasks → Generate Scripts...

---

### Bỏ qua Introduction

<img src="instruction/genscript_2.png" alt="Step 2" height="300"/>

Nhấn **Next**.

---

### 2️⃣ Chọn đối tượng để export

<img src="instruction/genscript_3.png" alt="Step 3" height="300"/>

Nhấn **Next**.

---

### 3️⃣ Cấu hình nâng cao

<img src="instruction/genscript_4.png" alt="Step 4" height="300"/>

- Tại bước "Set Scripting Options" → chọn **Advanced** (góc phải).
- Chọn xong click **Ok**.

---

### 4️⃣ Lưu file `.sql`

<img src="instruction/genscript_5.png" alt="Step 5" height="500"/>

- Chọn đường dẫn: `./database/init.sql` (**ghi đè file cũ**)
- Nhấn **Next → Finish**

---

### Còn lại

<img src="instruction/genscript_6.png" alt="Step 5" height="300"/>

<img src="instruction/genscript_6.png" alt="Step 5" height="300"/>

## 💾 Sau đó, tạo bản sao lưu thủ công

Chạy lệnh trong **Git Bash**:

```bash
make db-backup

```

→ Tạo bản sao như: `database/init-20250526-185211.sql`

----------

## 🧠 Tóm tắt quy trình

```text
1. Chỉnh sửa DB → SSMS → Generate Scripts (Schema + Data) → Ghi đè init.sql
2. (Tuỳ chọn) make db-backup
3. Commit file database/init.sql nếu cần chia sẻ cho team
4. Thành viên khác chỉ cần make restart để đồng bộ DB

```

----------
