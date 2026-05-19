# Snip API

This is the backend for the Snip URL shortner project.

# How to use

Build a docker image

```bash
docker build -t snip-api:v1.0 .
```

Run the image

```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e MYSQL_HOST=host.docker.internal \
  -e MYSQL_PORT=3306 \
  -e MYSQL_DB=snip \
  -e MYSQL_USER=root \
  -e MYSQL_PASSWORD=root \
  -e APP_CORS_ALLOWED_ORIGINS=http://localhost:3000 \
  snip-api:v1.0
```