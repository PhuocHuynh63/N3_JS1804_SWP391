CREATE DATABASE me_be_test
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

    FOREIGN KEY ([user_id]) REFERENCES [user]([user_id])
);

INSERT INTO [address] ([user_id], is_default, title, [address])
VALUES
    (1, 1, N'Nhà riêng', N'123 Đường ABC'),
    (1, 0, N'Văn phòng', N'456 Đường XYZ'),
    (2, 1, N'Nhà riêng', N'789 Đường DEF'),
    (3, 1, N'Nhà riêng', N'147 Đường GHI')

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
	[slug] NVARCHAR(MAX),
    [name] NVARCHAR(100),
    [image] NVARCHAR(MAX),
	[image2] NVARCHAR(MAX),
    FOREIGN KEY (parent_id) REFERENCES category(category_id)
);

INSERT INTO subcategory (parent_id, slug, [name], [image], [image2])
VALUES

	--Sữa & Bình sữa
	(1, 'sua-bot', N'Sữa bột', N'https://i.pinimg.com/564x/04/67/41/046741880cb159d847b238ef9bec6b10.jpg', 'https://i.pinimg.com/564x/0b/3a/d2/0b3ad277ac077fd6cb73ad819392acb9.jpg'),
	(1, 'sua-pha-san', N'Sữa pha sẵn', N'https://i.pinimg.com/564x/69/6e/0c/696e0c73fa7978a14dee8713cb693123.jpg', 'https://i.pinimg.com/736x/4c/7e/22/4c7e22ad99ab10035ffa370bff1978a1.jpg'),
	(1, 'binh-sua', N'Bình sữa', N'https://babi1.com/wp-content/uploads/2022/11/2402.jpg', 'https://maunhi.com/wp-content/uploads/2021/10/240825257_916385525641235_7817464745646276515_n.jpg'),
	(1, 'phu-kien-binh-sua', N'Phụ kiện bình sữa', N'https://hegen.com.vn/wp-content/uploads/2022/01/1600x1600_adaptation-27-1.jpg', 'https://bizweb.dktcdn.net/100/443/377/products/z3592549188508-316ac8a34779b42571c014cb184145c2-1658729354355.jpg?v=1693585236580'),
	(1, 'num-ti', N'Núm ti', N'https://www.spectrababy.vn/images/products/2021/06/15/original/num-ty-moi_1623725125.jpg', 'https://bizweb.dktcdn.net/100/358/258/files/num-vu-gia-singlemum-vn-1.jpg?v=1610293301938'),


   -- Bỉm tả-vệ sinh
   (2, 'bim-ta', N'Bỉm tả', N'https://ss-images.saostar.vn/wwebp700/pc/1640784062534/saostar-y33ekt3p69b4rbxl.png', 'https://www.moby.com.vn/data/bt9/bim-ta-dan-moony-newborn-90-mieng-duoi-5kg-1634871497.jpg'),
   (2, 'bim-nguoi-lon', N'Bỉm người lớn', N'https://img.lazcdn.com/g/p/4c1043a068797e91d5f6940349c6af5b.jpg_720x720q80.jpg', 'https://cdn-v2.kidsplaza.vn/media/catalog/product/b/i/bim-dan-nguoi-lon-sunmate-a2-m10-1.jpg'),
   (2, 'bo', N'Bô', N'https://danhchobeyeu.com/media/data/product/royalcare/RC-8828-bo-tre-em-hinh-thu-co-banh-xe-2-trong-1-Royalcare%20-8828-mau-xanh-duong.jpg', 'https://file.hstatic.net/1000279312/file/bo_ve_sinh_co_lung_tua_cho_be_viet_nhat__3._.jpg'),
   (2, 'cham-soc-rang-mieng', N'Chăm sóc răng miệng', N'https://oralmart.vn/wp-content/uploads/1.6-8-1659344783283-247x296.jpg', 'https://img.kwcdn.com/product/Fancyalgo/VirtualModelMatting/49137a9a2cff05c3dd2852620a1bb2e8.jpg?imageView2/2/w/800/q/70/format/webp'),
   (2, 'sua-tam-goi', N'Sữa tắm/gội', N'https://bizweb.dktcdn.net/100/421/012/products/sua-tam-goi-toan-than-cho-be-cetaphil-400ml-hoa-cuc-1-1678728068614.png?v=1678728072270', 'https://www.shopeggy.com/cdn/shop/products/B2B7056_540x.jpg?v=1616188731'),


   --Đồ cho mẹ
   (3, 'sua-bau', N'Sữa bầu', N'https://www.acfc.com.vn/wp/wp-content/uploads/2022/06/top-10-loai-sua-cho-ba-bau-tot-nhat-hien-nay-10-1.jpg', 'https://i1-suckhoe.vnecdn.net/2019/08/06/three-week-old-baby-died-after-4738-9850-1565084585.png?w=1020&h=0&q=100&dpr=1&fit=crop&s=qtEB9ILQZD6Lh3TKhpDNnA'),
   (3, 'vitamin-cho-me', N'Vitamin cho mẹ', N'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9TnBsVBaqpMT_xX8XVqB484Tsz1cyVDTKJg&s', 'https://i.pinimg.com/564x/5a/d6/cc/5ad6cc0165266256a8e95fae2786fe22.jpg'),
   (3, 'phu-kien-cho-me', N'Phụ kiện cho mẹ', N'https://down-my.img.susercontent.com/file/95337ec902bf1522abbd3c424e1a0cb3', 'https://media.licdn.com/dms/image/D5622AQES46cuduYRLw/feedshare-shrink_1280/0/1700718448613?e=1721865600&v=beta&t=wdwjLSksF3S3XZa0sG8gf7MgCLIDGYVGBcpz2cx5B_Q')



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
	quantity INT,
	product_view INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subcategory_id) REFERENCES subcategory(subcategory_id)
);


INSERT INTO product (subcategory_id, slug, name, images, [description], price, sale_price, [status], total_sold, quantity, product_view)
VALUES
    (1, N'san-pham-dinh-duong-cong-thuc-chuan-so-3-withmom-cesar', N'Sản phẩm dinh dưỡng công thức Withmom Cesar số 3', N'https://product.hstatic.net/200000178477/product/z5370813280050_ac580a92aecd1a21f63875f962ee9e17_35c853c65002483ab1732f61e229c01f_master.jpg', N'Sữa tươi loại 1-A không kháng sinh 17,54% (Hàn Quốc, theo tiêu chuẩn số lượng vi khuẩn), lactose, dextrin, dầu ăn hỗn hợp [dầu đậu nành, dầu dừa, dầu chế biến 6,02% (dầu cọ, dầu hướng dương)', 799000.00, 700000.000, N'Còn hàng', 0, 10, 0),
    (1, N'sua-aptamil-duc-so-3', N'Sữa Aptamil Đức số 3', N'https://product.hstatic.net/200000178477/product/image_2023-02-13_120841960_c12f93fbd6b7433988d8a8320ac37283_master.png', N'Lấy đầy muỗng sữa và gạt ngang miệng (1 muỗng Aptamil 3 gạt ngang tương đương 5g sữa) đong 4 muỗng sữa pha với 120ml nước được dung dịch 130ml sữa.', 595000.00, 0, N'Còn hàng', 0, 10, 0),
    (1, N'sua-bot-aptamil-essensis-organic-so-1', N'Sữa bột Aptamil Essensis Organic số 3', N'https://product.hstatic.net/200000178477/product/image_2022-06-15_105147544_6991b8fd5794438aad389b803b29fcdf_master.png', N'Sữa Aptamil Essensis Organic số 1 - dòng sữa công thức cao cấp dành cho trẻ', 1295000.00, 0, N'Còn hàng', 0, 10, 0),
    (1, N'sua-blackmores-jnr', N'Sữa Blackmores JNR Balance+ 850g', N'https://product.hstatic.net/200000178477/product/sua_blackmores_jnr_balance__5_22d53dbe69854f54b34f307cc99d6439_master.png', N'Blackmores JNR Balance+ thuộc tập đoàn BLACKMORES – Đơn vị hàng đầu thế giới về chăm sóc sức khoẻ con người. Công thức độc đáo của sữa Blackmores JNR Balance+ được nghiên cứu bởi những chuyên gia hàng đầu của Blackmores. Sản phẩm bổ sung dinh dưỡng trong giai đoạn từ 1 -10 tuổi, giúp bé phát triển toàn diện.', 690000.00, 0, N'Còn hàng', 0, 30, 0),

	(2, N'sua-bot-pha-san-nan-115ml', N'Sữa bột pha sẵn Nan 115ml (vỉ 9 hộp)', N'https://product.hstatic.net/200000178477/product/image_2022-11-24_100829730_e523c0eb11d14e9482112e8853e62010_master.png', N'Cung cấp hệ dưỡng chất thiết yếu cho sự phát triển toàn diện của trẻ 2 hộp 180ml cung cấp gần 40% Nhu cầu Đạm mỗi ngày', 83000.00, 0, N'Không có sẵn', 0, 10, 0),
    (2, N'sua-phat-trien-chieu-cao-150ml', N'Sữa phát triển chiều cao', N'https://product.hstatic.net/200000178477/product/image_2022-08-17_171549139_a0f68ce742c841c08ddbd811fb933852_master.png', N'Sữa nước Vegemil Greenbia HiKids Hàn tăng chiều cao & tăng cân, bổ sung sữa non tăng đề kháng cho bé từ 1 tuổi (1 lốc 3 hộp - 150ml/hộp)', 126000.00, 120000.00, N'Còn hàng', 0, 10, 0),
    
	(3, N'sbps-nuvi-grow-110ml-1y', N'SBPS Nuvi Grow 110ml 1Y+','https://product.hstatic.net/200000178477/product/z5468436611242_a5d915da97c27b1e7519149817f91fe1_8599714d304b4759b5f4703804c650bb_master.jpg', N'DINH DƯỠNG TỐI ƯU CHIỀU CAO', 32000.00, 0, N'Còn hàng', 0, 10, 0),
    (3, N'sua-dem-fruto-vi-chuoi', N'Sữa đêm Fruto vị chuối dâu 200ml', N'https://product.hstatic.net/200000178477/product/sua-fruto-dau-chuoi-3_221f51a6270a4310b99fee93331f4691_master.jpg', N'Sữa bột Nuvi Grow IQ Step 3 với công thức IQ Advance', 35000.00, 29900.00, N'Còn hàng', 0, 10, 0),

	(6, N'bim-quan-moony-natural', N'Bỉm quần Moony Natural', N'https://product.hstatic.net/200000178477/product/ta_quan_moony_natural_m46_99bb19a279ff46bcadb2e7dd747a96d5_master.jpg', N'Moony Natural an toàn cho làn da sơ sinh! Lần đầu tiên xuất hiện tại Nhật Bản, Moony Natural là dòng tã siêu cao cấp, với sự kết hợp của mặt bông hữu cơ và 5 thiết kế nổi bật khiến sản phẩm êm mềm lành tính cho làn da sơ sinh của bé.', 470000.00, 420000.00,N'Còn hàng', 0, 10, 0),
    (6, N'bim-quan-bobby', N'Bỉm quần Bobby', N'https://product.hstatic.net/200000178477/product/adba_9fcba30c246349b6a77638ff819a3d3a_714e58730ede4f0ba9379e2c44efae26_dad7b4e21a694a7d8c382087483b8dbd_master.jpg', N'Tã quần Bobby đến từ thương hiệu Unicharm Nhật Bản với những đặc điểm nổi trội như bề mặt tã 3D thông minh, mặt đáy siêu thấm và siêu thoát ẩm, lưng chun co giãn linh hoạt chống tràn hiệu quả,... sẽ mang đến cho bé một sản phẩm chăm sóc sức khỏe tuyệt vời trong những năm tháng đầu đời. Mẹ yên tâm rằng bé sẽ vô cùng thoải mái, dễ chịu khi mặc tã. Bé nhà bạn sẽ tự do vận động, vui chơi khám phá thế giới xung quanh mà không bị làm phiền. Sản phẩm với bịch lớn 48 miếng mang đến sự tiện lợi khi sử dụng.', 199000.00, 0 ,N'Còn hàng', 0, 10, 0),
	(6, N'bim-quan-huggies', N'Bỉm quần Huggies', N'https://product.hstatic.net/200000178477/product/9466_dd64d3317f4f4d8e8066bc7b8e7bcb9e_7f6990f49e1747b18e54390a0e9fb0a2_45ef09bbfbaf4d0ba689fc934580ec26_master.jpg', N'Đệm mây 4 chiều là cải tiến mới của tã quần Huggies Dry, mang lại cho bé sự dễ chịu, tạm biệt vết hằn đỏ trên da do mặc tã. Mẹ có thể thoải mái cho bé mặc tã quần cả ngày không còn lo bé bị khó chịu, hằn đỏ lên da.', 192000.00, 190000.00, N'Còn hàng', 0, 10, 0),

	(7, N'caryn-ta-giay-nguoi-lon-size-xl10-mieng', N'CARYN-TÃ GIẤY NGƯỜI LỚN SIZE XL10 miếng', N'https://product.hstatic.net/200000178477/product/d26dd000c5bb7ca65b5c31e99e81009e_b6db6f5e6f6a418382720fdcb11c12d7_master.jpg', N'Dành cho người hạn chế khả năng đi lại', 125000.00, 0, N'Còn hàng', 0, 10, 0),
	(7, N'ta-dan-caryn-m-l40', N'Tã dán Caryn M-L40', N'https://product.hstatic.net/200000178477/product/4ddb9471eb514bd2891f8c93bddef4c5_6053043a70694dd1bc0bb86faa82a8af_master.jpg', N'	Dành cho người hạn chế khả năng đi lại',395000.00, 0, N'Còn hàng', 0, 10, 0),
	(7, N'ta-giay-caryn-m-l-3-mieng', N'Tã giấy Caryn M/L 3 miếng', N'https://product.hstatic.net/200000178477/product/anh_web_2f50c3f79b294bfab48a224d1483be74_master.jpg', N'Màng đáy Air-Active Tã dán Caryn có màng đáy Air-Active với tối đa diện tích biết thở dạng vải, mềm mại và tránh hằm bí, giúp da hô hấp với không khí tự nhiên.',34000.00, 30000.00, N'Còn hàng', 0, 10, 0),

	(8, N'be-bon-cau-hong-chicco', N'Bệ bồn cầu Chicco màu hồng', N'https://product.hstatic.net/200000178477/product/image_2022-06-27_105841812_bd28f277e7ab4c94b776d581749a90a0_master.png', N'Đang cập nhật nội dung',159000.000 , 0, N'Còn hàng', 0, 10, 0),
	(8, N'bo-phi-thuyen-holla-hong', N'Bô phi thuyền Holla hồng', N'https://product.hstatic.net/200000178477/product/artboard_1_b48252c78e8d4012b1c180e6dde49e52_master.png', N'Thiết kế thoải mái, phù hợp với chiều cao của trẻ. Phần bô đựng bên trong có thể tháo rời và dễ dàng vệ sinh.', 350000.00, 300000.00, N'Còn hàng', 0, 10, 0),
	(8, N'bo-hinh-khi-mau-xanh-royalcare', N'Bô hình khỉ màu xanh Royalcare', N'https://product.hstatic.net/200000178477/product/image_2022-06-08_102202292_91cd03db8a7e400e9ad856d8f8bec2bf_master.png', N'Đang cập nhật nội dung', 239000.00, 234000.00, N'Còn hàng', 0, 10, 0),

	(9, N'ban-chai-chu-u-hoat-hinh-3d-ht622-cho-be', N'Bàn chải chữ U hoạt hình 3D HT622 cho bé', N'https://product.hstatic.net/200000178477/product/thiet_ke_chua_co_ten__85__b9662318ac4443f3b325c31d90ebce91_master.png', N'Đang cập nhật thông tin', 39000.00, 0, N'Còn hàng', 0, 10, 0),
	(9, N'gac-ro-luoi-dr-papie-30pcs', N'Gạc răng miệng Dr.PaPie rơ lưỡi cho bé từ sơ sinh', N'https://product.hstatic.net/200000178477/product/anh_web_7f20189fdafb4d3bbc337b658a120faf_master.jpg', N'Đang cập nhật thông tin', 11000.00, 0, N'Còn hàng', 0, 10, 0),
	(9, N'gel-danh-rang-tre-em-eq-tech-junior-75g', N'Gel đánh răng trẻ em EQ Tech Junior 75g', N'https://product.hstatic.net/200000178477/product/image_2023-01-18_101401846_7b85956419074d3baf60f6b01123c068_master.png', N'Đang cập nhật thông tin', 29000.00, 0, N'Còn hàng', 0, 10, 0),

	(10, N'dau-goi-ngan-rung-toc-dersante-150ml', N'Dầu gội ngăn rụng tóc Dersante 150ml', N'https://product.hstatic.net/200000178477/product/dau_goi_keiko_356cb1a931d4497ca4c433071652129b_master.png', N'Dầu gội ngăn rụng tóc Dersante giúp: làm sạch da đầu và tóc, làm sạch gàu, ngăn ngừa và cải thiện tình trạng tóc: xơ, rối, chẻ ngọn, gãy, rụng,… cho mái tóc trông dày, bóng mượt và chắc khỏe hơn. Góp phần thúc đẩy nhanh quá trình phục hồi tóc bị hư tổn và kích thích sự phát triển của nang tóc.', 145000.00, 143000.00, N'Còn hàng', 0, 10, 0),
	(10, N'sua-tam-goi-toan-bebble-shampoo-body-wash-400ml', N'Sữa tắm gội toàn Bebble Shampoo & Body Wash 400ml', N'https://product.hstatic.net/200000178477/product/me___be__39__71d3baf2d3e4470f8afc94fcfc157e0b_master.png', N':Sữa tắm gội nhẹ nhàng chăm sóc, làm sạch, bảo vệ da khỏi tác động từ môi trường bên ngoài, cung cấp độ ẩm cho da mềm mại, hỗ trợ điều trị khô da, viêm da cơ địa, vảy nến.', 392000.00, 389000.00, N'Còn hàng', 0, 10, 0),
	(10, N'dau-goi-bac-ha-mat-ruoi-h-s-1200', N'Dầu Gội Bạc Hà Mát Rượi H&S 1200', N'https://product.hstatic.net/200000178477/product/dau-goi-headshoulders-bac-ha-1200ml-1_9fed44abe3bc4d2eb52da632bd137e96_master.jpg', N'Đang cập nhật nội dung', 238000.00, 230000.00, N'Còn hàng', 0, 10, 0),

	(11, N'sua-ensure-gold-strengthpro', N'Sữa Ensure gold StrengthPro YBG hương vani 400g', N'https://product.hstatic.net/200000178477/product/screenshot_4_45ad991acad741bb89eecb5846441245_master.png', N'Thiếu hụt vitamin và khoáng chất là một trong những nguyên nhân chính dẫn đến tình trạng sức khỏe bị suy giảm nghiêm trọng và mắc bệnh. Vì vậy, việc bổ sung đầy đủ các chất dinh dưỡng vi lượng và đa lượng cho cơ thể hàng ngày là vô cùng cần thiết', 410000.00, 399000.00, N'Còn hàng', 0, 10, 0),
	(11, N'sua-bau-vi-tra-sua', N'Sữa bầu Morinaga vị trà sữa (12 gói)', N'https://product.hstatic.net/200000178477/product/anh_web_f8b638836cb949a5987ac1a9788444b0_master.jpg', N'Trong thời gian mang bầu, các mẹ cần cung cấp đủ dưỡng chất cho thai nhi, để thai nhi phát triển khỏe mạnh. Sữa bầu Morinaga chứa đầy đủ các chất dinh dững cho cả mẹ và bé nên được rất nhiều mẹ tin tưởng. Mẹ có thể sử dụng sữa trong suốt thời gian mang thai và cho con bú mà không lo lắng vấn đề tăng cân mất kiểm soát.', 219000.00, 20000.0, N'Còn hàng', 0, 10, 0),
	(11, N'sua-bau-danmilko-mamature-800g', N'Sữa bầu Danmilko Mamature 800g', N'https://product.hstatic.net/200000178477/product/sua_bau_danmilko_miniature_7aa39ce6ceb14792a3945163142dba67_master.png', N'Với hương vị thanh nhẹ từ Châu Âu, sữa bầu Danmilko Mamature cung cấp các dưỡng chất thiết yếu như DHA, Choline, Axit Folic giúp hỗ trợ thai kỳ và bổ sung và bù đắp dinh dưỡng cho mẹ trong suốt thai kỳ.', 495000.00, 490000.00, N'Còn hàng', 0, 10, 0),

	(13, N'lot-chong-tham-chong-trao-nguoc-hybaby', N'Lót chống thấm chống trào ngược Hybaby', N'https://product.hstatic.net/200000178477/product/image_2022-06-04_211725522_79c01c860e364b1ea8271d4102b97f6a_master.png', N'Lót thay tã, lót ngủ Hybaby cho bé với mẫu mã xinh xắn, thấm hút mồ hôi, chất lỏng ngăn thấm ngược sẽ là lựa chọn số 1 cùng mẹ chăm sóc bé yêu.', 169000.00, 0, N'Còn hàng', 0, 10, 0),
	(13, N'mieng-lot-tham-sua-ku-ku-36-mieng', N'Miếng lót thấm sữa Ku-Ku 36 miếng', N'https://product.hstatic.net/200000178477/product/anh_web_08fe2ba8d7b44144b71a123a9696dc40_master.jpg', N'Miếng Lót Thấm Sữa Kuku là loại sử dụng một lần, tiện mang theo để sử dụng hằng ngày. Sản phẩm giúp bạn chăm sóc vệ sinh khuôn ngực, tránh cho bầu vú không bị hăm vì ướt sữa trong giai đoạn cho con bú.', 115000.00, 100000.00, N'Còn hàng', 0, 10, 0),
	(13, N'lot-tham-sua-moby-60-mieng', N'Lót thấm sữa Moby ( 60 miếng )', N'https://product.hstatic.net/200000178477/product/anh_web_6fbe35f2ed0246a8ba638435c2198179_master.jpg', N'Thiết kế đường viền khóa chống tràn 4 bên ngăn rò gỉ hiệu quả, Miếng lót thấm sữa Baby Moby 60 miếng với lớp gel siêu thấm, nhanh chóng thấm hút lượng sữa thừa, dàn đều và khóa chặt chúng bên trong, không gây tràn ngược trở lại. Bề mặt đường lưới kim cương tăng cường độ mềm mại và sự thoải mái bởi không khí sẽ dễ dàng lưu thông bên trong tạo sự thông thoáng, giữ cho ngực mẹ luôn khô ráo và thoáng mát suốt ngày dài.', 220000.00, 0, N'Còn hàng', 0, 10, 0)

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
	total_amount DECIMAL(10, 2),
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
    product_id  INT,
    quantity INT,
	price DECIMAL(10, 2),
    sale_price  DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES [order](order_id),
	FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- Tạo bảng wishlist
CREATE TABLE wishlist (
	wishlist_id INT PRIMARY KEY IDENTITY(1,1),
	[user_id] INT,
	product_id INT,
	status NVARCHAR(50),
	quantity INT,
	total_amount DECIMAL(10, 2),
	estimated_date DATETIME,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY ([user_id]) REFERENCES [user]([user_id]),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
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
(N'FREESHIP', N'Fixed Amount', 30.00, N'Miễn phí vận chuyển cho đơn hàng từ 1,000,000đ', 0.00, 50, 1000000.00, 30000.00, 1, 1),
(N'NEWUSER', N'Percentage', 15.00, N'Giảm giá 15% cho khách hàng mới', 0.00, 200, 0.00, 200000.00, 1, 0);

-- Thêm dữ liệu vào bảng order
INSERT INTO [order] ([user_id], voucher_id, [status], total_amount, order_type, payment_status, note)
VALUES
(1, 1, N'Đang được xử lý', 500000.00, N'Online', N'Chưa thanh toán', N'Giao hàng trong giờ hành chính'),
(1, NULL, N'Đã hủy', 1200000.00, N'Online', N'Chưa thanh toán', N'Giao hàng nhanh'),
(2, NULL, N'Hoàn thành',750000.00, N'COD', N'Đã thanh toán', NULL),
(3, 2, N'Đang giao', 1200000.00, N'Online', N'Đã thanh toán', N'Giao hàng nhanh');

-- Thêm dữ liệu vào bảng order_detail
INSERT INTO order_detail (order_id, product_id, quantity, price, sale_price)
VALUES
(1, 1, 2, 32000.00, 0),
(1, 2, 1, 32000.00, 0),
(2, 3, 2, 30000.00, 0),
(3, 4, 3, 135000.00, 130000.00),
(3, 5, 1, 135000.00, 130000.00),
(4, 3, 5, 32000.00, 0),
(4, 4, 1, 135000.00, 130000.00);


-- Thêm dữ liệu vào bảng payment
INSERT INTO payment (order_id, amount, payment_type, payment_method, payment_status, transaction_reference)
VALUES
(1, 500000.00, N'Online', N'Credit Card', N'Chờ xử lý', N'TRX001'),
(2, 750000.00, N'COD', N'Cash', N'Hoàn thành', NULL),
(3, 1200000.00, N'Online', N'Bank Transfer', N'Chờ xử lý', N'TRX002');


