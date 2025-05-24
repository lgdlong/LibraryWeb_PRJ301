# ==================================
# ⚙️ CONFIGURATION
# ==================================

WAR_NAME = LibraryWeb_PRJ301_G1.war
WAR_PATH = dist/$(WAR_NAME)
DOCKER_IMAGE_NAME = servlet-library-app
ANT_IMAGE_NAME = ant-builder

# ==================================
# 🛠️ TASKS
# ==================================

.PHONY: all setup-ant build-java build-docker up down db-up db-down clean prj-restart export-db ensure-db-dir

# ⚙️ Chạy 1 lần duy nhất để build image có sẵn Ant
setup-ant:
	@echo "🔧 [setup-ant] Building Ant Docker image..."
	docker build -f Dockerfile.ant.dockerfile -t $(ANT_IMAGE_NAME) .

# 📦 Build WAR và Docker image
all: build-java build-docker

# 🔨 Build WAR bằng container có Ant
build-java:
	@echo "🛠️  [build-java] Building WAR using $(ANT_IMAGE_NAME)..."
	docker run --rm -v ${PWD}:/app -w /app $(ANT_IMAGE_NAME) ant clean dist

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

# 📤 Export schema/data thành init-YYYYMMDD-HHMMSS.sql + alias init.sql
export-db: ensure-db-dir
	@echo "📤 [export-db] Exporting MSSQL schema (placeholder)..."
	$(eval NOW := $(shell date +%Y%m%d-%H%M%S))
	$(eval FILE := database/init-$(NOW).sql)

	docker exec -i mssql-dev /opt/mssql-tools/bin/sqlcmd \
		-S localhost -U sa -P YourStrong\!Passw0rd \
		-Q "SELECT name FROM sys.databases;" \
		-o /docker-entrypoint-initdb.d/init.sql

	docker cp mssql-dev:/docker-entrypoint-initdb.d/init.sql $(FILE)
	cp $(FILE) database/init.sql

	@echo "✅ Exported to $(FILE)"
	@echo "📌 Alias updated: database/init.sql"

# 📁 Tạo thư mục database nếu chưa có
ensure-db-dir:
	@echo "📁 Ensuring database/ directory exists..."
	@mkdir -p database
