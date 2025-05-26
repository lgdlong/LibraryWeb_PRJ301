# ==================================
# ⚙️ CONFIGURATION
# ==================================

SHELL := bash

WAR_NAME = LibraryWeb_PRJ301_G1.war
WAR_PATH = dist/$(WAR_NAME)
DOCKER_IMAGE_NAME = servlet-library-app

# ==================================
# 🔧 UTILITIES
# ==================================

.PHONY: all build export up down restart clean db-up db-down db-backup

ensure-dirs:
	@mkdir -p dist database

# ==================================
# 🔨 BUILD TASKS
# ==================================

all: build export

build: build-war build-image

build-war:
	@echo "🔧 Building WAR..."
	docker build --target build-only -t $(DOCKER_IMAGE_NAME)-builder .

export:
	@echo "📂 Exporting WAR from builder container..."
	docker create --name tmp-builder $(DOCKER_IMAGE_NAME)-builder
	docker cp tmp-builder:/usr/local/tomcat/webapps/$(WAR_NAME) $(WAR_PATH)
	docker rm tmp-builder

build-image: $(WAR_PATH)
	@echo "💣 Building final Docker image..."
	docker build -t $(DOCKER_IMAGE_NAME) .

# ==================================
# 🚀 RUNTIME TASKS
# ==================================

up:
	@echo "🚀 Starting app + DB..."
	docker compose up --build

down:
	@echo "🛑 Stopping all services..."
	docker compose down

restart:
	@echo "🔄 Full restart (delete volumes)..."
	docker compose down -v
	make build
	make up

# ==================================
# 🗃️ DATABASE TASKS
# ==================================

db-up:
	docker compose up -d mssql

db-down:
	docker compose stop mssql

db-backup: ensure-dirs
	$(eval NOW := $(shell date +%Y%m%d-%H%M%S))
	cp database/init.sql database/init-$(NOW).sql
	@echo "✅ Backup saved as init-$(NOW).sql"

# ==================================
# 🧹 CLEAN TASK
# ==================================

clean:
	@echo "🧹 Cleaning build artifacts..."
	rm -rf dist/*.war
	docker rmi -f $(DOCKER_IMAGE_NAME) $(DOCKER_IMAGE_NAME)-builder || true
