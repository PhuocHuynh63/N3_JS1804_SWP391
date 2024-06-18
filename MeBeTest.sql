﻿CREATE DATABASE me_be_test
GO

USE me_be_test
GO

-- Tạo bảng User
 CREATE TABLE [user] (
    [user_id] INT PRIMARY KEY IDENTITY(1,1),
    avatar VARCHAR(255),
    first_name NVARCHAR(50),
    last_name NVARCHAR(50),
	username NVARCHAR(50) UNIQUE,
    email VARCHAR(100) UNIQUE,
    [password] VARCHAR(255),
    birth_date DATE,
	[role] VARCHAR(20),
    phone VARCHAR(20),
    point INT,
	[status] NVARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL
);

INSERT INTO [user] (avatar, first_name, last_name, username, email, [password], birth_date, [role], phone, point, [status])
VALUES
	-- 123/ 1234 / 555 
    ('avatar1.jpg', N'Nguyễn', N'Văn A', N'user1', 'user1@example.com', '$2a$10$C0Fe7kS86ZiupWiZwKJRbeiY7IMeiY9MfZhH09ZcV46eyvAbDjyRW', '1990-01-01', 'admin', '0123456789', 100, N'active'),
    ('avatar2.jpg', N'Trần', N'Thị B', N'user2', 'user2@example.com', '$2a$10$GAB3II6hwaJ.dU9FQ6VSL.46Hl5Oi0V12u6fNjm0WbRVokLn7ERfO', '1995-05-10', 'staff', '0987654321', 50, N'active'),
    ('avatar3.jpg', N'Lê', N'Văn C', N'user3', 'user3@example.com', '$2a$10$MicDh3ueKAAtAoImX0fmA.nsStMOFIBKhbUpF7a0IploKJjo.tI1S', '1988-12-15', 'member', '0909090909', 75, N'inactive');


-- Tạo bảng Address
CREATE TABLE [address] (
    address_id INT PRIMARY KEY IDENTITY(1,1),
    [user_id] INT,
    is_default BIT,
    title NVARCHAR(100),
    [address] NVARCHAR(255),
    city NVARCHAR(100),
	district NVARCHAR(100),
	ward NVARCHAR(100),
    FOREIGN KEY ([user_id]) REFERENCES [user]([user_id])
);

INSERT INTO [address] ([user_id], is_default, title, [address], city, district, ward)
VALUES
    (1, 1, N'Nhà riêng', N'123 Đường ABC', N'Hà Nội', N'Quận Hoàn Kiếm', N'Phường Hàng Trống'),
    (1, 0, N'Văn phòng', N'456 Đường XYZ', N'Hà Nội', N'Quận Hai Bà Trưng', N'Phường Bạch Đằng'),
    (2, 1, N'Nhà riêng', N'789 Đường DEF', N'Hồ Chí Minh', N'Quận 1', N'Phường Bến Nghé'),
    (3, 1, N'Nhà riêng', N'147 Đường GHI', N'Đà Nẵng', N'Quận Hải Châu', N'Phường Thanh Bình')

-- Tạo bảng Category
CREATE TABLE category (
    category_id INT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(100),
    [slug] NVARCHAR(MAX)
);

INSERT INTO category ([name], [slug])
VALUES
    (N'Sữa & Bình Sữa', N'sua-binh-sua'),
    (N'Bỉm Tã - Vệ Sinh', N'bim-ta-ve-sinh'),
    (N'Đồ Cho Mẹ', N'do-cho-me')

-- Tạo bảng SubCategory
CREATE TABLE subcategory (
    subcategory_id INT PRIMARY KEY IDENTITY(1,1),
    parent_id INT,
    [name] NVARCHAR(100),
    [image] NVARCHAR(MAX),
    FOREIGN KEY (parent_id) REFERENCES category(category_id)
);

INSERT INTO subcategory (parent_id, [name], [image])
VALUES

	--Sữa & Bình sữa
	(1, N'Sữa bột', N'/images/sua_bot-modified.png'),
	(1, N'Sữa pha sẵn', N'sua-pha-san'),
	(1, N'Bình sữa', N'binh-sua'),
	(1, N'Phụ kiện bình sữa', N'phu-kien-binh-sua'),
	(1, N'Núm ti', N'num-ti'),


   -- Bỉm tả-vệ sinh
   (2, N'Bỉm tả', N'bim-ta'),
   (2, N'Bỉm người lớn', N'bim-nguoi-lon'),
   (2, N'Bô', N'bo'),
   (2, N'Chăm sóc răng miệng', N'cham-soc-rang-mieng'),
   (2, N'Sữa tắm/gội', N'sua-tam-goi'),


   --Đồ cho mẹ
   (3, N'Sữa bầu', N'sua-bau'),
   (3, N'Vitamin cho mẹ', N'vitamin-cho-me'),
   (3, N'Gối bầu', N'goi-bau'),
   (3, N'Phụ kiện cho mẹ', N'phu-kien-cho-me')



-- Tạo bảng ProductAttribute
CREATE TABLE product_attribute (
    product_attribute_id INT PRIMARY KEY IDENTITY(1,1),
    [type] NVARCHAR(50),
    [value] VARCHAR(100)
);

INSERT INTO product_attribute ([type], [value])
VALUES
    (N'Color', 'Red'),
    (N'Color', 'Blue'),
    (N'Color', 'Green'),
    (N'Color', 'Black'),
    (N'Color', 'White'),
    (N'Size', 'Small'),
    (N'Size', 'Medium'),
    (N'Size', 'Large'),
    (N'Size', 'X-Large'),
    (N'Material', 'Cotton'),
    (N'Material', 'Polyester'),
    (N'Material', 'Leather'),
    (N'Material', 'Denim'),
    (N'Style', 'Casual'),
    (N'Style', 'Formal'),
    (N'Style', 'Sports'),
    (N'Brand', 'Nike'),
    (N'Brand', 'Adidas'),
    (N'Brand', 'Levi''s'),
    (N'Brand', 'Gucci'),
    (N'Season', 'Summer'),
    (N'Season', 'Winter'),
    (N'Season', 'Spring'),
    (N'Season', 'Autumn'),
	(N'Tình trạng', N'Còn hàng'),
	(N'Thương hiệu', N'Nuti Food'),
	(N'Dạng sản phẩm', N'Sữa nước'),
	(N'Mã sản phẩm', N'124945'),
	(N'Dung tích', N'110ml'),
	(N'Hương vị', N'Vani'),
	(N'Size', N'S'),
	(N'Size', N'M'),
	(N'Size', N'L'),
	(N'Color', N'Red'),
	(N'Color', N'Blue'),
	(N'Size', N'XL'),
	(N'Size', N'XXL'),
	(N'Color', N'Green'),
	(N'Color', N'Black');

-- Tạo bảng Product
CREATE TABLE product (
    product_id INT PRIMARY KEY IDENTITY(1,1),
    subcategory_id INT,
	slug NVARCHAR(100),
	[name] NVARCHAR(255),
	images NVARCHAR(MAX),
	[description] NVARCHAR(MAX),
	price DECIMAL(10, 2),
	sale_price 	DECIMAL(10, 2),
	[status] NVARCHAR(50),
	total_sold INT,
	product_view INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subcategory_id) REFERENCES subcategory(subcategory_id)
);

INSERT INTO product (subcategory_id, slug, name, images, [description], price, sale_price, [status], total_sold, product_view)
VALUES
    (1, N'sbps-nuvi-grow-110ml-1y', N'SBPS Nuvi Grow 110ml 1Y+', N'nuvi_grow.jpg', N'DINH DƯỠNG TỐI ƯU CHIỀU CAO', 32000.00, 0, N'Còn hàng', 0, 0),
    (1, N'sua-nuvi-grow-iq-step-3-900g', N'Sữa Nuvi Grow IQ Step 3 900g', N'nuvi_grow_iq_step3.jpg', N'Sữa bột Nuvi Grow IQ Step 3 với công thức IQ Advance', 385000.00, 370000.00, N'Còn hàng', 0, 0),
    (1, N'sua-bot-dielac-grow-plus-2-400g', N'Sữa bột Dielac Grow Plus 2 400g', N'dielac_grow_plus_2.jpg', N'Sữa bột Dielac Grow Plus 2 400g dành cho trẻ từ 1-2 tuổi', 135000.00, 130000.00, N'Còn hàng', 0, 0),
    (1, N'sua-bot-dielac-alpha-gold-iq-step-3-900g', N'Sữa bột Dielac Alpha Gold IQ Step 3 900g', N'dielac_alpha_gold_iq_step3.jpg', N'Sữa bột Dielac Alpha Gold IQ Step 3 900g cho trẻ từ 1-2 tuổi', 405000.00, 390000.00, N'Còn hàng', 0, 0),
    (1, N'sua-pha-san-nestle-nan-optipro-so-4-lon-800ml', N'Sữa pha sẵn Nestle NAN Optipro Số 4 lon 800ml', N'nan_optipro_4.jpg', N'Sữa pha sẵn Nestle NAN Optipro Số 4 lon 800ml phù hợp với trẻ từ 2 tuổi trở lên', 85000.00, 0, N'Còn hàng', 0, 0),
    (1, N'sua-pha-san-nestle-nan-optipro-so-3-6x800ml', N'Sữa pha sẵn Nestle NAN Optipro Số 3 6x800ml', N'nan_optipro_3.jpg', N'Lốc 6 lon sữa pha sẵn Nestle NAN Optipro Số 3 800ml cho trẻ từ 1-2 tuổi', 465000.00, 450000.00, N'Còn hàng', 0, 0)
    
-- Tạo bảnh Inventory
CREATE TABLE inventory (
	inventory_id INT PRIMARY KEY IDENTITY(1,1),
	product_id INT,
	size_id	INT,
	color_id INT,
	quantity INT,
    FOREIGN KEY(product_id) REFERENCES product(product_id),
	FOREIGN KEY(size_id) REFERENCES product_attribute(product_attribute_id),
	FOREIGN KEY(color_id) REFERENCES product_attribute(product_attribute_id)
);

-- Tạo bảng Review
CREATE TABLE review (
    review_id INT PRIMARY KEY IDENTITY(1,1),
    [user_id] INT,
    product_id INT,
    rate INT,
    comment NVARCHAR(MAX),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	update_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ([user_id]) REFERENCES [user]([user_id]),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- Tạo bảng Voucher
CREATE TABLE voucher (
    voucher_id INT PRIMARY KEY IDENTITY(1,1),
	voucher_code NVARCHAR(50),
	discount_type NVARCHAR(50),
	discount_value DECIMAL(10, 2),
	[name] NVARCHAR(255),
	cost DECIMAL(10, 2),
	quantity INT,
	minimum_apply DECIMAL(10, 2),
	max_discount DECIMAL(10, 2),
	is_active BIT,
	is_public BIT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	update_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng Order
CREATE TABLE [order] (
    order_id INT PRIMARY KEY IDENTITY(1,1),
    [user_id] INT,
    voucher_id INT,
	[status] NVARCHAR(50),
	delivery_fee DECIMAL(10, 2),
	total_amount DECIMAL(10, 2),
    deposite_amount DECIMAL(10, 2),
	order_type NVARCHAR(50),
	payment_status NVARCHAR(50),
	note NVARCHAR(200),
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ([user_id]) REFERENCES [user]([user_id]),
    FOREIGN KEY (voucher_id) REFERENCES voucher(voucher_id)
);

-- Tạo bảng OrderDetail
CREATE TABLE order_detail (
    order_detail_id  INT PRIMARY KEY IDENTITY(1,1),
    order_id INT,
    inventory_id  INT,
    quantity INT,
	price DECIMAL(10, 2),
    sale_price  DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES [order](order_id),
	FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id)
);


-- Tạo bảng Payment
CREATE TABLE payment (
    payment_id INT PRIMARY KEY IDENTITY(1,1),
	order_id INT,
	amount DECIMAL(10, 2),
	payment_type NVARCHAR(50),
	payment_method NVARCHAR(50),
	payment_status NVARCHAR(50),
	transaction_reference NVARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
	FOREIGN KEY (order_id) REFERENCES [order](order_id),
);


-- Thêm dữ liệu vào bảng inventory
INSERT INTO inventory (product_id, size_id, color_id, quantity)
VALUES
(1, 1, 4, 50),
(1, 2, 4, 30),
(1, 3, 5, 20),
(2, 6, 8, 40),
(2, 7, 9, 60);

-- Thêm dữ liệu vào bảng review
INSERT INTO review ([user_id], product_id, rate, comment)
VALUES
(1, 1, 4, N'Sản phẩm tốt, chất lượng đáng tin cậy.'),
(2, 1, 5, N'Rất hài lòng với sản phẩm này.'),
(3, 2, 3, N'Sản phẩm khá ổn, nhưng giá hơi cao.');

-- Thêm dữ liệu vào bảng voucher
INSERT INTO voucher (voucher_code, discount_type, discount_value, [name], cost, quantity, minimum_apply, max_discount, is_active, is_public)
VALUES
(N'SUMMER10', N'Percentage', 10.00, N'Giảm giá 10% cho đơn hàng từ 500,000đ', 0.00, 100, 500000.00, 100000.00, 1, 1),
(N'FREESHIP', N'Fixed Amount', 30000.00, N'Miễn phí vận chuyển cho đơn hàng từ 1,000,000đ', 0.00, 50, 1000000.00, 30000.00, 1, 1),
(N'NEWUSER', N'Percentage', 15.00, N'Giảm giá 15% cho khách hàng mới', 0.00, 200, 0.00, 200000.00, 1, 0);

-- Thêm dữ liệu vào bảng order
INSERT INTO [order] ([user_id], voucher_id, [status], delivery_fee, total_amount, deposite_amount, order_type, payment_status, note)
VALUES
(1, 1, N'Processing', 20000.00, 500000.00, 0.00, N'Online', N'Unpaid', N'Giao hàng trong giờ hành chính'),
(2, NULL, N'Completed', 15000.00, 750000.00, 750000.00, N'COD', N'Paid', NULL),
(3, 2, N'Shipping', 0.00, 1200000.00, 1200000.00, N'Online', N'Paid', N'Giao hàng nhanh');

-- Thêm dữ liệu vào bảng order_detail
INSERT INTO order_detail (order_id, inventory_id, quantity, price, sale_price)
VALUES
(1, 1, 2, 32000.00, 0),
(1, 2, 1, 32000.00, 0),
(2, 4, 3, 135000.00, 130000.00),
(2, 5, 1, 135000.00, 130000.00),
(3, 3, 5, 32000.00, 0),
(3, 4, 1, 135000.00, 130000.00);


-- Thêm dữ liệu vào bảng payment
INSERT INTO payment (order_id, amount, payment_type, payment_method, payment_status, transaction_reference)
VALUES
(1, 500000.00, N'Online', N'Credit Card', N'Pending', N'TRX001'),
(2, 750000.00, N'COD', N'Cash', N'Completed', NULL),
(3, 1200000.00, N'Online', N'Bank Transfer', N'Completed', N'TRX002');


