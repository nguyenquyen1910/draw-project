# Draw Project - Ứng dụng vẽ trực tuyến

Đây là một ứng dụng vẽ trực tuyến sử dụng Spring Boot, WebSocket và MySQL.

## Tính năng

- Đăng ký/Đăng nhập người dùng
- Tạo và tham gia phòng vẽ
- Vẽ trực tuyến real-time với WebSocket
- Lưu trữ lịch sử các hành động vẽ
- Quản lý phiên người dùng

## Cấu trúc Database

### Bảng Users
- `id`: UUID (Primary Key)
- `email`: Email người dùng
- `username`: Tên đăng nhập (unique)
- `password`: Mật khẩu (đã mã hóa)
- `avatar`: URL avatar
- `created_at`, `updated_at`: Timestamps

### Bảng Rooms
- `id`: UUID (Primary Key)
- `room_code`: Mã phòng (unique)
- `room_name`: Tên phòng
- `description`: Mô tả phòng
- `created_by`: Người tạo phòng
- `max_users`: Số người tối đa
- `is_private`: Phòng riêng tư hay không
- `created_at`, `updated_at`: Timestamps

### Bảng User Sessions
- `id`: UUID (Primary Key)
- `user_id`: ID người dùng (Foreign Key)
- `room_id`: ID phòng (Foreign Key)
- `is_connected`: Trạng thái kết nối
- `joined_at`: Thời gian tham gia
- `left_at`: Thời gian rời khỏi
- `created_at`, `updated_at`: Timestamps

### Bảng Draw Actions
- `id`: UUID (Primary Key)
- `room_id`: ID phòng (Foreign Key)
- `user_id`: ID người dùng (Foreign Key)
- `action_type`: Loại hành động (draw, erase, etc.)
- `start_x`, `start_y`: Tọa độ bắt đầu
- `end_x`, `end_y`: Tọa độ kết thúc
- `line_width`: Độ dày nét vẽ
- `color`: Màu sắc
- `sequence_number`: Số thứ tự hành động
- `created_at`, `updated_at`: Timestamps

## Cài đặt và chạy với Docker

### Yêu cầu
- Docker
- Docker Compose

### Cách chạy

1. **Clone repository và di chuyển vào thư mục:**
   ```bash
   cd draw-project
   ```

2. **Chạy ứng dụng với Docker Compose:**
   ```bash
   docker-compose up -d
   ```

3. **Kiểm tra trạng thái:**
   ```bash
   docker-compose ps
   ```

4. **Xem logs:**
   ```bash
   # Xem logs của ứng dụng
   docker-compose logs app
   
   # Xem logs của database
   docker-compose logs mysql
   ```

### Truy cập ứng dụng

- **Ứng dụng:** http://localhost:8080
- **Database:** localhost:3306
  - Username: `draw_user`
  - Password: `draw_password`
  - Database: `draw_project`

### Dừng ứng dụng

```bash
docker-compose down
```

### Xóa dữ liệu (nếu cần)

```bash
# Dừng và xóa containers, networks
docker-compose down

# Xóa volumes (dữ liệu database)
docker-compose down -v
```

## Chạy local development

### Yêu cầu
- Java 21
- Maven
- MySQL 8.0

### Cách chạy

1. **Cài đặt MySQL và tạo database:**
   ```sql
   CREATE DATABASE draw_project;
   CREATE USER 'draw_user'@'localhost' IDENTIFIED BY 'draw_password';
   GRANT ALL PRIVILEGES ON draw_project.* TO 'draw_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

2. **Chạy ứng dụng:**
   ```bash
   ./mvnw spring-boot:run
   ```

## API Endpoints

### Authentication
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/register` - Đăng ký

### Rooms
- `GET /api/rooms` - Lấy danh sách phòng
- `POST /api/rooms` - Tạo phòng mới
- `GET /api/rooms/{id}` - Lấy thông tin phòng
- `PUT /api/rooms/{id}` - Cập nhật phòng
- `DELETE /api/rooms/{id}` - Xóa phòng

### WebSocket
- `ws://localhost:8080/ws` - Kết nối WebSocket

## Cấu trúc thư mục

```
draw-project/
├── src/
│   ├── main/
│   │   ├── java/com/project/draw/
│   │   │   ├── controller/     # REST Controllers
│   │   │   ├── entity/         # JPA Entities
│   │   │   ├── repository/     # Data Repositories
│   │   │   ├── service/        # Business Logic
│   │   │   └── websocket/      # WebSocket Handlers
│   │   └── resources/
│   │       └── application.yml # Configuration
│   └── test/
├── init-scripts/               # Database initialization scripts
├── docker-compose.yml         # Docker Compose configuration
├── Dockerfile                 # Docker image configuration
└── README.md                  # This file
```

## Troubleshooting

### Lỗi kết nối database
- Kiểm tra MySQL container đã chạy: `docker-compose ps`
- Kiểm tra logs: `docker-compose logs mysql`
- Đảm bảo port 3306 không bị chiếm dụng

### Lỗi ứng dụng không start
- Kiểm tra logs: `docker-compose logs app`
- Đảm bảo database đã sẵn sàng trước khi app start
- Kiểm tra cấu hình trong `application.yml`

### Reset database
```bash
docker-compose down -v
docker-compose up -d
```

## Đóng góp

1. Fork repository
2. Tạo feature branch
3. Commit changes
4. Push to branch
5. Tạo Pull Request
