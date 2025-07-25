USE [master]
GO
/****** Object:  Database [library_system]    Script Date: 7/8/2025 17:44:10 ******/
CREATE DATABASE [library_system]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'library_system', FILENAME = N'/var/opt/mssql/data/library_system.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'library_system_log', FILENAME = N'/var/opt/mssql/data/library_system_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [library_system] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [library_system].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [library_system] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [library_system] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [library_system] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [library_system] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [library_system] SET ARITHABORT OFF 
GO
ALTER DATABASE [library_system] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [library_system] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [library_system] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [library_system] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [library_system] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [library_system] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [library_system] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [library_system] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [library_system] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [library_system] SET  ENABLE_BROKER 
GO
ALTER DATABASE [library_system] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [library_system] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [library_system] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [library_system] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [library_system] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [library_system] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [library_system] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [library_system] SET RECOVERY FULL 
GO
ALTER DATABASE [library_system] SET  MULTI_USER 
GO
ALTER DATABASE [library_system] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [library_system] SET DB_CHAINING OFF 
GO
ALTER DATABASE [library_system] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [library_system] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [library_system] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [library_system] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'library_system', N'ON'
GO
ALTER DATABASE [library_system] SET QUERY_STORE = OFF
GO
USE [library_system]
GO
/****** Object:  Table [dbo].[book_requests]    Script Date: 7/8/2025 17:44:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[book_requests](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[book_id] [int] NOT NULL,
	[request_date] [date] NULL,
	[status] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[books]    Script Date: 7/8/2025 17:44:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[books](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](255) NULL,
	[author] [nvarchar](255) NULL,
	[isbn] [varchar](20) NOT NULL,
	[category] [varchar](50) NULL,
	[published_year] [int] NULL,
	[total_copies] [int] NULL,
	[available_copies] [int] NULL,
	[status] [varchar](20) NULL,
	[cover_url] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[borrow_records]    Script Date: 7/8/2025 17:44:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[borrow_records](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[book_id] [int] NOT NULL,
	[borrow_date] [date] NULL,
	[due_date] [date] NOT NULL,
	[return_date] [date] NULL,
	[status] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[fines]    Script Date: 7/8/2025 17:44:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[fines](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[borrow_id] [int] NOT NULL,
	[fine_amount] [decimal](12, 2) NULL,
	[paid_status] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[system_config]    Script Date: 7/8/2025 17:44:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[system_config](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[config_key] [varchar](50) NOT NULL,
	[config_value] [varchar](100) NOT NULL,
	[description] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 7/8/2025 17:44:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
	[email] [varchar](100) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[role] [varchar](20) NULL,
	[status] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[book_requests] ON 

INSERT [dbo].[book_requests] ([id], [user_id], [book_id], [request_date], [status]) VALUES (1, 3, 1, CAST(N'2025-05-01' AS Date), N'pending')
INSERT [dbo].[book_requests] ([id], [user_id], [book_id], [request_date], [status]) VALUES (2, 4, 2, CAST(N'2025-04-30' AS Date), N'approved')
INSERT [dbo].[book_requests] ([id], [user_id], [book_id], [request_date], [status]) VALUES (3, 5, 3, CAST(N'2025-04-28' AS Date), N'rejected')
INSERT [dbo].[book_requests] ([id], [user_id], [book_id], [request_date], [status]) VALUES (4, 3, 5, CAST(N'2025-05-05' AS Date), N'pending')
INSERT [dbo].[book_requests] ([id], [user_id], [book_id], [request_date], [status]) VALUES (5, 4, 4, CAST(N'2025-04-27' AS Date), N'approved')
SET IDENTITY_INSERT [dbo].[book_requests] OFF
GO
SET IDENTITY_INSERT [dbo].[books] ON 

INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (1, N'Effective Java', N'Joshua Bloch', N'9780134685991', N'Programming', 2018, 5, 3, N'active', N'https://m.media-amazon.com/images/I/7167aaVxs3L._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (2, N'Clean Code', N'Robert C. Martin', N'9780132350884', N'Software Engineering', 2008, 4, 4, N'active', N'https://m.media-amazon.com/images/I/51E2055ZGUL._SL1000_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (3, N'Java: The Complete Reference', N'Herbert Schildt', N'9781260440232', N'Programming', 2020, 6, 5, N'active', N'https://m.media-amazon.com/images/I/81UDSuaFAeL._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (4, N'Design Patterns', N'Erich Gamma', N'9780201633610', N'Architecture', 1994, 3, 2, N'active', N'https://m.media-amazon.com/images/I/81IGFC6oFmL._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (5, N'Head First Java', N'Kathy Sierra', N'9780596009205', N'Beginner', 2005, 2, 1, N'active', N'https://m.media-amazon.com/images/I/819DmGB3vhL._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (6, N'The Richest Man In Babylon', N'George S Clason', N'9781939438638', N'Business Ethics', 2021, 10, 8, N'active', N'https://m.media-amazon.com/images/I/71V-2pnfL3L._SL1360_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (7, N'The Psychology of Money: Timeless lessons on wealth, greed, and happiness', N'Morgan Housel', N'9780857197689', N'Budgeting & Money Management', 2020, 10, 5, N'active', N'https://m.media-amazon.com/images/I/81wZXiu4OiL._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (8, N'Suối Nguồn', N'Ayn Rand', N'8934974158479', N'Classic Literature & Fiction', 2018, 5, 4, N'active', N'https://cdn1.fahasa.com/media/catalog/product/i/m/image_180164_1_43_1_57_1_4_1_2_1_210_1_29_1_98_1_25_1_21_1_5_1_3_1_18_1_18_1_45_1_26_1_32_1_14_1_2215.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (9, N'The Book of Five Rings: Deluxe Slipcase Edition', N'Miyamoto Musashi', N'9781788883214', N'Martial Arts', 2018, 10, 5, N'active', N'https://m.media-amazon.com/images/I/813l+kuJiqL._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (10, N'The Prince', N'Niccolò Machiavelli', N'9798712157877', N'Political Leadership', 2021, 10, 3, N'active', N'https://m.media-amazon.com/images/I/51KV9QHeUBL._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (11, N'The Art Of War', N'Sun Tzu', N'9781599869773', N'Military Strategy History', 2007, 3, 3, N'active', N'https://m.media-amazon.com/images/I/51HwNMz3EuL._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (12, N'Marcus Aurelius - Meditations: Adapted for the Contemporary Reader', N'Marcus Aurelius', N'9781539952299', N'Individual Philosophers', 2016, 2, 2, N'active', N'https://m.media-amazon.com/images/I/61ZY+gIU5dL._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (13, N'Kafka Bên Bờ Biển', N'Haruki Murakami', N'8935235225817', N'Magical Realism', 2020, 10, 2, N'active', N'https://cdn1.fahasa.com/media/catalog/product/i/m/image_195509_1_32831.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (14, N'Tôi Nói Gì Khi Nói Về Chạy Bộ', N'Haruki Murakami', N'8935235231610', N'Running & Jogging', 2021, 10, 5, N'active', N'https://cdn1.fahasa.com/media/catalog/product/i/m/image_244296.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (15, N'Nhà Giả Kim', N'Paulo Coelho', N'8935235226272', N'Business & Investing', 2020, 10, 5, N'active', N'https://cdn1.fahasa.com/media/catalog/product/i/m/image_195509_1_36793.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (16, N'The Power of a Humble Life', N'Richard E. Simmons III', N'9781939358158', N'Christian Spiritual Growth', 2017, 10, 1, N'active', N'https://m.media-amazon.com/images/I/91tw9xgttJL._SL1500_.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (17, N'Tiếng Gọi Nơi Hoang Dã', N'Jack London', N'8935352619780', N'Action & Adventure', 2024, 10, 9, N'active', N'https://cdn1.fahasa.com/media/catalog/product/t/i/tieng-goi-noi-hoang-da_bia.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (18, N'Hai Số Phận', N'Jeffrey Archer', N'8935095633104', N'Family Saga Fiction', 2023, 10, 2, N'active', N'https://cdn1.fahasa.com/media/catalog/product/h/s/hsp-bia-cung---xuat-in-goc-b1_1.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (19, N'Không Gia Đình', N'Hector Malot', N'8935095631520', N'Fiction', 2021, 10, 1, N'active', N'https://cdn1.fahasa.com/media/catalog/product/i/m/image_240282.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (20, N'Lời Hiệu Triệu Của Cthulhu', N'H.P. Lovecraft', N'8935244868883', N'Horror', 2022, 10, 3, N'active', N'https://cdn1.fahasa.com/media/catalog/product/l/o/loi-hieu-trieu-cua-cthulhu_bia.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (21, N'Khởi Nghiệp Du Kích', N'Trần Thanh Phong', N'9786048451233', N'Startup', 2020, 5, 2, N'active', N'https://cdn1.fahasa.com/media/catalog/product/9/7/9786048451233.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (22, N'Minh Triết Trong Ăn Uống Của Phương Đông', N'Ngô Đức Vượng', N'8935086857328', N'Food', 2023, 3, 2, N'active', N'https://cdn1.fahasa.com/media/catalog/product/8/9/8935086857328_2.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (23, N'Ghi Chép Pháp Y - Những Thi Thể Không Hoàn Chỉnh', N'Lưu Bát Bách', N'8935325017797', N'Novel', 2023, 2, 1, N'active', N'https://cdn1.fahasa.com/media/catalog/product/b/_/b_a_tr_c_ph_p_y_3.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (24, N'Sự Im Lặng Của Bầy Cừu', N'Thomas Harris', N'8935235220508', N'Novel', 2019, 2, 2, N'active', N'https://cdn1.fahasa.com/media/flashmagazine/images/page_images/su_im_lang_cua_bay_cuu_tai_ban_2019/2021_05_11_15_57_19_1-390x510.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (25, N'Rồng Đỏ', N'Thomas Harris', N'8935235234130', N'Novel', 2022, 2, 2, N'active', N'https://toplist.vn/images/800px/rong-do-thomas-harris-72873.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (26, N'Người Ếch - Kẻ Giết Người Hàng Loạt', N'Shichiri Nakayama', N'8935325023309', N'Novel', 2024, 2, 2, N'active', N'https://cdn1.fahasa.com/media/catalog/product/b/i/bia1-nguoiech.png')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (27, N'Còn Chút Gì Để Nhớ', N'Nguyễn Nhật Ánh', N'8934974178590', N'Novel', 2022, 5, 2, N'active', N'https://cdn1.fahasa.com/media/catalog/product/8/9/8934974178590.jpg')
INSERT [dbo].[books] ([id], [title], [author], [isbn], [category], [published_year], [total_copies], [available_copies], [status], [cover_url]) VALUES (28, N'Cảm Ơn Người Lớn', N'Nguyễn Nhật Ánh', N'8934974158486', N'Novel', 2024, 5, 4, N'active', N'https://cdn1.fahasa.com/media/catalog/product/c/a/cam_on_nguoi_lon_bia_mem_xp.jpg')
SET IDENTITY_INSERT [dbo].[books] OFF
GO
SET IDENTITY_INSERT [dbo].[borrow_records] ON 

INSERT [dbo].[borrow_records] ([id], [user_id], [book_id], [borrow_date], [due_date], [return_date], [status]) VALUES (1, 3, 1, CAST(N'2025-04-25' AS Date), CAST(N'2025-05-09' AS Date), NULL, N'overdue')
INSERT [dbo].[borrow_records] ([id], [user_id], [book_id], [borrow_date], [due_date], [return_date], [status]) VALUES (2, 4, 2, CAST(N'2025-04-20' AS Date), CAST(N'2025-05-04' AS Date), CAST(N'2025-05-03' AS Date), N'returned')
INSERT [dbo].[borrow_records] ([id], [user_id], [book_id], [borrow_date], [due_date], [return_date], [status]) VALUES (3, 5, 3, CAST(N'2025-04-10' AS Date), CAST(N'2025-04-24' AS Date), NULL, N'overdue')
INSERT [dbo].[borrow_records] ([id], [user_id], [book_id], [borrow_date], [due_date], [return_date], [status]) VALUES (4, 3, 4, CAST(N'2025-04-15' AS Date), CAST(N'2025-04-29' AS Date), CAST(N'2025-05-01' AS Date), N'returned')
INSERT [dbo].[borrow_records] ([id], [user_id], [book_id], [borrow_date], [due_date], [return_date], [status]) VALUES (5, 4, 5, CAST(N'2025-05-01' AS Date), CAST(N'2025-05-15' AS Date), NULL, N'overdue')
SET IDENTITY_INSERT [dbo].[borrow_records] OFF
GO
SET IDENTITY_INSERT [dbo].[fines] ON 

INSERT [dbo].[fines] ([id], [borrow_id], [fine_amount], [paid_status]) VALUES (1, 3, CAST(30.50 AS Decimal(12, 2)), N'unpaid')
INSERT [dbo].[fines] ([id], [borrow_id], [fine_amount], [paid_status]) VALUES (2, 4, CAST(1.00 AS Decimal(12, 2)), N'paid')
INSERT [dbo].[fines] ([id], [borrow_id], [fine_amount], [paid_status]) VALUES (3, 1, CAST(23.00 AS Decimal(12, 2)), N'paid')
INSERT [dbo].[fines] ([id], [borrow_id], [fine_amount], [paid_status]) VALUES (4, 5, CAST(20.00 AS Decimal(12, 2)), N'unpaid')
INSERT [dbo].[fines] ([id], [borrow_id], [fine_amount], [paid_status]) VALUES (5, 2, CAST(0.00 AS Decimal(12, 2)), N'paid')
SET IDENTITY_INSERT [dbo].[fines] OFF
GO
SET IDENTITY_INSERT [dbo].[system_config] ON 

INSERT [dbo].[system_config] ([id], [config_key], [config_value], [description]) VALUES (1, N'overdue_fine_per_day', N'0.50', N'Fine amount per day for overdue books (in dollars)')
INSERT [dbo].[system_config] ([id], [config_key], [config_value], [description]) VALUES (2, N'default_borrow_duration_days', N'14', N'Default number of days a book can be borrowed')
INSERT [dbo].[system_config] ([id], [config_key], [config_value], [description]) VALUES (3, N'unit_price_per_book', N'10.00', N'Default unit price used for book replacement or reference')
INSERT [dbo].[system_config] ([id], [config_key], [config_value], [description]) VALUES (6, N'book_new_years', N'6.0', N'The number of year from published_year to now that tell a book is new or not')
SET IDENTITY_INSERT [dbo].[system_config] OFF
GO
SET IDENTITY_INSERT [dbo].[users] ON 

INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (1, N'Alice Admin', N'alice.admin@example.com', N'$2a$12$EAmCkK36IT/mArrL9Hzx9.NtZN01Vl6mk4Y8ze./38c5sJpJxsml.', N'admin', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (2, N'Thi No', N'No@example.com', N'$2a$12$NiCaQIyEtPDAU8HFHErZi.ACToXJGhRXu6vEvhtd.SkjAtPpKzYZa', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (3, N'Emma Reader', N'emma.user@example.com', N'$2a$12$0LwvgixAGiFiDgTG/cQ7luJ631ax5Cmk6041xS2aP.yP3wXxWd/eq', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (4, N'Noah Reader', N'noah.user@example.com', N'$2a$12$de9v66cVUD7J2RZs1TyBc.2buLJO7xEJZfHcIqKdxS4lErQ3hnj7.', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (5, N'Olivia Reader', N'olivia.user@example.com', N'$2a$12$PXZBfir9M8TZoIaWsb7SOebRzFJ4pvXySIO5K2xqEEIFU41sXZyTq', N'user', N'blocked')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (6, N'Hoang Long', N'long@gmail.com', N'$2a$12$0MUGZfSQTZsHPX4DFzLSO.dPQGJZnFeM.xC7Hsr8xq2Da9nfBRfde', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (8, N'Phung Luu Hoang Long', N'phungluuhoanglong@gmail.com', N'$2a$12$yNrcf4WjNpqiypv.7xQyxurvt9Aae/5nLT/uvvv.3WuspGamTBabS', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (9, N'Super Admin', N'admin@admin.com', N'$2a$12$en7VO2hdRneYZtUnmY2etu.jMnRvcBjYu4nneF3wOIEsJUYXHmgtC', N'admin', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (10, N'Blocked Man', N'blockedman@gmail.com', N'$2a$12$fI1XvuWnYMXEZM4Tacf2ce2ZSFAsY0jlC6nczLWoqJqMKSF9ctyQG', N'user', N'blocked')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (11, N'abc', N'abc@gmail.com', N'$2a$12$ejSaHnSvK2lZDFgcj83ooe/Vr7tgFANVkYGNEPNOYfv7NxWapdv0u', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (12, N'Nguyen Van A', N'vana@gmail.com', N'$2a$12$uBlPhFHQ7KIPa2DQ4fH3CuxQmf3JmvY5ztJN7urP/1J6QK5D3o/qa', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (13, N'John Doe', N'john.doe@example.com', N'$2a$12$jPa253fR..K.XWEtU3qDWe6eXwWvkzKFxOfj1N5Ex3PcsAXB.kgGe', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (14, N'Jane Smith', N'jane.smith@example.com', N'$2a$12$PIsoVb0JkVYKdg03BXrkD.8kyVgGay8DZqKDu4ruWA7FcdXXBH2lC', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (15, N'Phan Thi B', N'phanb@gmail.com', N'$2a$12$IH.x0avN/DDf2apDXf3veOh13y0howhtJY0e3hAx6JUPtVDKJzDaS', N'user', N'blocked')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (16, N'Le Quang C', N'lequangc@gmail.com', N'$2a$12$8z6roGzRTdgkB7F6V6lmiOanBQpamhUnMTz/RtZEGpnyETq.NePHS', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (17, N'Emily Reader', N'emily.reader@example.com', N'$2a$12$xz8Iza/NAExTEibdejw1oOZqn7KXbH7ggyPxQ2qkkq3L8YvpxpEvi', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (18, N'Mai Lan', N'mailan@gmail.com', N'$2a$12$/a7M888cUmcPYKp5Rwz4BeQD4R8/Z26GFYfS2driOqabBqPjrMeGK', N'user', N'blocked')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (19, N'Anna Nguyen', N'anna.nguyen@example.com', N'$2a$12$DHprc.hS7vltaIo4o1M2y.75jZPHH8j5GPrewcp7d5VSUdDBnqySq', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (20, N'James Bond', N'james.bond@example.com', N'$2a$12$xh/jrNH6TwsOzaes12THAekOZv/LqWgge9m2bGYpGBTy6hU5xilcC', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (21, N'Dao Minh D', N'daominhd@gmail.com', N'$2a$12$ZFPDyv8jAAUefUp/3hGUseLPdEoSAQvZ6Ju58Dne68Og0pCNcAcRi', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (22, N'Lisa Le', N'lisa.le@example.com', N'$2a$12$vBBfXdWqvdJq0bQeXgQ3AOThbxBd.q91CLLigA6wVTETufN2AqJky', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (23, N'Pham Tuan', N'pham.tuan@gmail.com', N'$2a$12$uZYhWKuiiRvEtyL5wjzjJOF/n1FbuuHoqxzacQq.b9ZIl8iQZzegu', N'user', N'blocked')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (24, N'Linda Tran', N'linda.tran@example.com', N'$2a$12$gmGpRll5lkkFEexZO2YMnO5ivnviEE29stO6jf7zqudL0668LfL9S', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (25, N'Vu Hoang', N'vu.hoang@gmail.com', N'$2a$12$grX2rzF5NjAi1XyYeJ50xOYOnZzNC0Ky8u6ftI.xnECCoZMIQNoWe', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (26, N'Peter Pan', N'peter.pan@example.com', N'$2a$12$S4XwGnIBnHNMU0KbCGywI.6/Bgw1W85vq3l22ybwdpeGBY2x0lwh.', N'user', N'blocked')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (27, N'Susan Lee', N'susan.lee@example.com', N'$2a$12$rFTDSF8xuvVevHJYXRwRwesaATV1iq9hGSADgH2rPtvkj9plKVPNi', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (28, N'Nguyen Thi Hoa', N'nguyenthihoa@gmail.com', N'$2a$12$wS6fBE62nH41E9agv6thguTUqWTSgvVnzXvKaTGthogrGa2J/Unpu', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (29, N'Chris Evans', N'chris.evans@example.com', N'$2a$12$0IgF146lkwhhaKfV.MtDve8zElw0oqbhPkcgA9fz/6vVvJ9EWoABC', N'user', N'active')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (30, N'Lan Huong', N'lanhuong@gmail.com', N'$2a$12$kDU.vJcM09Gcr77HGoK7r.wkio/.ndlg16q1nuv3v1oUTJMTd3W4W', N'user', N'blocked')
INSERT [dbo].[users] ([id], [name], [email], [password], [role], [status]) VALUES (31, N'Ben White', N'ben.white@example.com', N'$2a$12$JffJFd4ne1xKP8HA4QXXFetgxpWr5pN1ax71x6GPsUhoPBTJoFu8W', N'user', N'active')
SET IDENTITY_INSERT [dbo].[users] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__books__99F9D0A44DF3E6E7]    Script Date: 7/8/2025 17:44:16 ******/
ALTER TABLE [dbo].[books] ADD UNIQUE NONCLUSTERED 
(
	[isbn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__system_c__BDF6033DCD9350AF]    Script Date: 7/8/2025 17:44:16 ******/
ALTER TABLE [dbo].[system_config] ADD UNIQUE NONCLUSTERED 
(
	[config_key] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__AB6E6164ABC26FB0]    Script Date: 7/8/2025 17:44:16 ******/
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[book_requests] ADD  DEFAULT ('pending') FOR [status]
GO
ALTER TABLE [dbo].[books] ADD  DEFAULT ((1)) FOR [total_copies]
GO
ALTER TABLE [dbo].[books] ADD  DEFAULT ((1)) FOR [available_copies]
GO
ALTER TABLE [dbo].[books] ADD  DEFAULT ('active') FOR [status]
GO
ALTER TABLE [dbo].[borrow_records] ADD  DEFAULT (NULL) FOR [return_date]
GO
ALTER TABLE [dbo].[borrow_records] ADD  DEFAULT ('borrowed') FOR [status]
GO
ALTER TABLE [dbo].[fines] ADD  DEFAULT ((0.00)) FOR [fine_amount]
GO
ALTER TABLE [dbo].[fines] ADD  DEFAULT ('unpaid') FOR [paid_status]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ('active') FOR [status]
GO
ALTER TABLE [dbo].[book_requests]  WITH CHECK ADD FOREIGN KEY([book_id])
REFERENCES [dbo].[books] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[book_requests]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[borrow_records]  WITH CHECK ADD FOREIGN KEY([book_id])
REFERENCES [dbo].[books] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[borrow_records]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[fines]  WITH CHECK ADD FOREIGN KEY([borrow_id])
REFERENCES [dbo].[borrow_records] ([id])
ON DELETE CASCADE
GO
USE [master]
GO
ALTER DATABASE [library_system] SET  READ_WRITE 
GO
