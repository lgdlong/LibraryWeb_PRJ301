# ==================================
# ⚙️ CONFIGURATION
# ==================================

WAR_NAME = LibraryWeb_PRJ301_G1.war
WAR_PATH = dist/$(WAR_NAME)
DOCKER_IMAGE_NAME = servlet-library-app

# ==================================
# 🛠️ TASKS
# ==================================

.PHONY: all build-java build-docker up down db-up db-down clean prj-restart export-db

# 📦 Build WAR và Docker image
all: build-java build-docker

# 🔨 Build WAR file bằng Ant (NetBeans)
build-java:
	@echo "🛠️  [build-java] Building WAR with Ant..."
	ant clean dist

# 🐳 Build Docker image (Tomcat + WAR)
build-docker: $(WAR_PATH)
	@echo "🐳 [build-docker] Building Docker image..."
	docker build -t $(DOCKER_IMAGE_NAME) .

# 🚀 Khởi động toàn bộ stack: servlet + database
up:
	@echo "🚀 [up] Starting full stack..."
	docker compose up --build

# 🛑 Tắt tất cả container (servlet + mssql)
down:
	@echo "🛑 [down] Stopping all containers..."
	docker compose down

# 🧱 Chạy riêng dịch vụ MSSQL
db-up:
	@echo "🧱 [db-up] Starting only MSSQL service..."
	docker compose up -d mssql

# 📴 Tắt riêng dịch vụ MSSQL
db-down:
	@echo "📴 [db-down] Stopping only MSSQL service..."
	docker compose stop mssql

# 🧹 Xoá WAR và Docker image
clean:
	@echo "🧹 [clean] Removing build artifacts and image..."
	rm -rf dist/*.war
	docker rmi -f $(DOCKER_IMAGE_NAME) || true

# 🔁 Xóa sạch, build lại WAR + Docker + chạy lại init.sql
prj-restart:
	@echo "🔁 [prj-restart] Full reset: clean volume, rebuild, redeploy..."
	docker compose down -v
	make build-java
	make build-docker
	docker compose up --build

# 📤 (Optional) Export schema/data thành init.sql (placeholder)
export-db:
	@echo "📤 [export-db] Attempting to export DB schema (limited)..."
	docker exec -i mssql-dev /opt/mssql-tools/bin/sqlcmd \
		-S localhost -U sa -P YourStrong\!Passw0rd \
		-Q "SELECT name FROM sys.databases;" \
		-o /init.sql
	@echo "⚠️ Note: MSSQL không hỗ trợ xuất .sql đầy đủ qua sqlcmd."
	@echo "✅ Khuyên dùng DBeaver hoặc Azure Data Studio để export chuẩn init.sql."
