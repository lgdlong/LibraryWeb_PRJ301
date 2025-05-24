#!/bin/bash
set -e
echo "🛠️  Building WAR using Docker container ant-builder..."

docker run --rm \
  -v "$(pwd -W)":/app \
  -w /app \
  ant-builder \
  ant clean dist
