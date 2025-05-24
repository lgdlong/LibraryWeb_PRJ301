# ==================================
# ⚙️ CONFIGURATION
# ==================================

SHELL := bash

WAR_NAME = LibraryWeb_PRJ301_G1.war
WAR_PATH = dist/$(WAR_NAME)
DOCKER_IMAGE_NAME = servlet-library-app

# ==================================
# 💠 TASKS
# ==================================

.PHONY: all build-war build-docker export-war up down db-up db-down clean prj-restart export-db ensure-db-dir

print-shell-env:
	@echo "SHELL     = $(SHELL)"
	@echo "ComSpec   = $(shell echo $$ComSpec)"
	@echo "OSTYPE    = $(shell echo $$OSTYPE)"
	@echo "TERM      = $(shell echo $$TERM)"
	@echo "uname     = $(shell uname -a)"

# 📆 Build WAR + Docker image
all: build-war export-war build-docker



# 🔧 Build WAR inside Docker (single-stage)
build-war:
	@echo "🔧 [build-war] Building WAR inside Docker image..."
	docker build --target build-only -t $(DOCKER_IMAGE_NAME)-builder .

# 📂 Copy WAR out of builder container
export-war: ensure-dist-dir
	@echo "📂 [export-war] Extracting WAR from builder container..."
	docker create --name tmp-builder $(DOCKER_IMAGE_NAME)-builder
	docker cp tmp-builder:/usr/local/tomcat/webapps/$(WAR_NAME) $(WAR_PATH)
	docker rm tmp-builder

# 💣 Build final Tomcat image with WAR
build-docker: $(WAR_PATH)
	@echo "💣 [build-docker] Building Tomcat Docker image..."
	docker build -t $(DOCKER_IMAGE_NAME) .

# 🚀 Start full stack
up:
	@echo "🚀 [up] Starting full stack..."
	docker compose up --build

# 🚫 Stop all
down:
	@echo "🚫 [down] Stopping all services..."
	docker compose down

# 🧱 DB only up/down
db-up:
	docker compose up -d mssql

db-down:
	docker compose stop mssql

# 💩 Clean build artifacts
clean:
	rm -rf dist/*.war
	docker rmi -f $(DOCKER_IMAGE_NAME) $(DOCKER_IMAGE_NAME)-builder || true

# 🔄 Full restart
prj-restart:
	docker compose down -v
	make build-war
	make export-war
	make build-docker
	docker compose up --build

# 📤 Export DB schema
export-db: ensure-db-dir
	$(eval NOW := $(shell date +%Y%m%d-%H%M%S))
	$(eval FILE := database/init-$(NOW).sql)
	docker exec -i mssql-dev /opt/mssql-tools/bin/sqlcmd \
		-S localhost -U sa -P YourStrong\!Passw0rd \
		-Q "SELECT name FROM sys.databases;" \
		-o /docker-entrypoint-initdb.d/init.sql
	docker cp mssql-dev:/docker-entrypoint-initdb.d/init.sql $(FILE)
	cp $(FILE) database/init.sql

# 📁 Ensure directories exist
ensure-db-dir:
	@mkdir -p database

ensure-dist-dir:
	@mkdir -p dist
