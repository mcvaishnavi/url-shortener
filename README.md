# 🚀 URL Shortener System (Spring Boot + Redis + MySQL + Docker)

A scalable URL Shortener service built using Spring Boot, integrated with Redis for caching & real-time analytics, MySQL for persistence, and Docker for containerized deployment.

# 📌 Features
🔗 Shorten long URLs into compact links

⚡ Fast redirection using Redis cache

📊 Click tracking system (Redis + MySQL sync)

🚦 Rate limiting using Redis

🐳 Fully containerized using Docker & Docker Compose

🔄 Background scheduler for syncing Redis → MySQL

📦 REST APIs for URL creation & analytics

# 🏗️ System Architecture

<img width="1536" height="1024" alt="UrlShortener-system-architecture" src="https://github.com/user-attachments/assets/398b4498-5e3a-4d5f-8cf4-2a143d9727c9" />


# ⚙️ Tech Stack
Java 17+

Spring Boot

Spring Data JPA

MySQL

Redis

Docker

Docker Compose

Maven

# 📁 Project Structure

com.project.urlshortener

│

├── controller → REST APIs

├── service → Business logic

├── repository → Database layer

├── entity → JPA entities

├── dto → Request & Response models

├── config → Redis & application config

├── exception → Exception handling

├── scheduler → Background sync jobs

# 🚀 API Endpoints

1. Create Short URL

``POST /api/shorten``

```json
Request
{
  "originalUrl": "https://www.google.com"
}

Response
{
  "originalUrl": "https://www.google.com",
  "shortUrl": "http://localhost:8080/abcd1234"
}
```

2. Redirect URL

``GET /{shortCode}``

Redirects to original URL and increments click count.


3. Get Stats

``GET /api/stats/{shortCode}``

```json
Response
{
  "originalUrl": "https://www.google.com",
  "shortCode": "abcd1234",
  "clickCount": 10
}
```

# ⚡ Redis Usage

Redis is used for:

🔥 URL caching (fast lookup)

📊 Click counter (real-time updates)

🚦 Rate limiting per IP

Example Keys
```
url:abcd1234 → https://google.com

clicks:abcd1234 → 15

rate:127.0.0.1 → request count
```

# 🐳 Docker Setup

📦 Dockerfile (Spring Boot)
```
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
```

# 📦 docker-compose.yml

```yaml
version: "3.8"

services:

  mysql:
    image: mysql:8
    container_name: url-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: url_shortener
      MYSQL_USER: user
      MYSQL_PASSWORD: password
    ports:
      - "3306:3306"

  redis:
    image: redis:7
    container_name: url-redis
    ports:
      - "6379:6379"

  app:
    build: .
    container_name: url-shortener-app
    ports:
      - "8080:8080"
    depends_on:
      - mysql
      - redis
```
      
⚙️ application.properties (Docker Ready)
```yaml
spring.datasource.url=jdbc:mysql://mysql:3306/url_shortener
spring.datasource.username=user
spring.datasource.password=password

spring.data.redis.host=redis
spring.data.redis.port=6379

spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

# 🧪 Commands Used

1. Build project
```bash
mvn clean package -DskipTests
```
3. Start full system (Docker Compose)
```bash
docker-compose up --build
```
5. Stop system
```bash
docker-compose down
```
7. Check running containers
```bash
docker ps
```
9. Access MySQL container
```bash
docker exec -it url-mysql mysql -u user -p
```
11. Access Redis container
```bash
docker exec -it url-redis redis-cli
```
13. Redis commands
```bash
ping
keys *
get clicks:abcd1234
```

# 🗄️ Database Schema

| Column Name | Type | Description |
|-------------|------|-------------|
| id | BIGINT | Primary Key |
| original_url | VARCHAR(2048) | Original URL |
| short_code | VARCHAR(20) | Generated Short Code |
| click_count | BIGINT | Number of Clicks |

# 🔐 Rate Limiting

Implemented using Redis
Limits requests per IP per time window
Prevents abuse of API

# 🧠 Key Learnings

Microservice-style architecture using Spring Boot

In-memory caching using Redis

Containerization using Docker

Background job scheduling

System design principles (scalability, caching, decoupling)

# 👩‍💻 Author

M C Vaishnavi

Built as a portfolio project for backend + system design learning
