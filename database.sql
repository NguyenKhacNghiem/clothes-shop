-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 30, 2023 at 04:12 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clothes_shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE DATABASE clothes_shop;
USE clothes_shop;

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `total_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id`, `total_price`) VALUES
(1, 0),
(2, 3157000),
(452, 0),
(552, 0);

-- --------------------------------------------------------

--
-- Table structure for table `cart_seq`
--

CREATE TABLE `cart_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cart_seq`
--

INSERT INTO `cart_seq` (`next_val`) VALUES
(651);

-- --------------------------------------------------------

--
-- Table structure for table `catalog`
--

CREATE TABLE `catalog` (
  `name` varchar(50) NOT NULL,
  `image` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `catalog`
--

INSERT INTO `catalog` (`name`, `image`) VALUES
('Áo Khoác', 'catalog-coat.png'),
('Áo Thun', 'catalog-t-shirt.png'),
('Quần Dài', 'catalog-pants.png');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `username` varchar(50) NOT NULL,
  `cart_id` int(11) NOT NULL,
  `password` varchar(50) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  `type` varchar(50) NOT NULL,
  `active` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`username`, `cart_id`, `password`, `fullname`, `phone`, `address`, `type`, `active`) VALUES
('admin', 1, 'benjo', 'Admin', '', '', 'admin', 0),
('nguyenvana', 452, 'ohvzfowbob', 'Nguyễn Văn A', '0909000000', '76/10 Lý Thái Tổ Phường 9 Quận 10 Thành phố Hồ Chí Minh', 'đồng', 0),
('nknghiem', 2, 'olohijfn', 'Nguyễn Khắc Nghiêm', '0703149783', 'Số 12 Lê Hồng Phong Phường 10 Quận 10 Thành phố Hồ Chí Minh', 'vàng', 0),
('tranthib', 552, 'usbouijc', 'Trần Thị B', '0909000111', '108 Phạm Ngũ Lão Phường 3 Quận Gò Vấp Thành Phố Hồ Chí Minh', 'bạc', 0);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `total_price` int(11) NOT NULL,
  `date` varchar(20) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `username`, `total_price`, `date`, `status`) VALUES
('nknghiem29042023230522', 'nknghiem', 2300000, '29/04/2023 23:05:22', 'Hoàn thành'),
('nknghiem29042023230527', 'nknghiem', 495000, '29/04/2023 23:05:27', 'Đang vận chuyển'),
('nknghiem29042023230533', 'nknghiem', 2446000, '29/04/2023 23:05:33', 'Chờ xác nhận'),
('nknghiem30042023015323', 'nknghiem', 3157000, '30/04/2023 01:53:23', 'Đang vận chuyển');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `color` varchar(20) NOT NULL,
  `description` varchar(400) NOT NULL,
  `price` int(11) NOT NULL,
  `image` varchar(100) NOT NULL,
  `brand` varchar(20) NOT NULL,
  `deleted` int(11) NOT NULL,
  `catalog` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `color`, `description`, `price`, `image`, `brand`, `deleted`, `catalog`) VALUES
(1302, 'Áo Khoác Puma Everyday Hussle T7', 'Đỏ Tía', 'Được tạo ra cho cuộc sống hối hả hàng ngày, bộ sưu tập PUMA x TMC Everyday Hussle có mặt để nâng tầm cuộc sống hàng ngày của bạn.', 2300000, 'coat1.png', 'Puma', 0, 'Áo Khoác'),
(1303, 'Áo Khoác Puma Everyday Hussle T8', 'Xanh Rêu', 'Được tạo ra cho cuộc sống hối hả hàng ngày, bộ sưu tập PUMA x TMC Everyday Hussle có mặt để nâng tầm cuộc sống hàng ngày của bạn.', 1800000, 'coat2.png', 'Puma', 0, 'Áo Khoác'),
(1304, 'Áo Khoác Puma Blaster', 'Đen', 'Nổi bật với công nghệ thấm hút ẩm dryCELL, Áo khoác tập luyện Blaster PUMA kết hợp cổ tay đàn hồi với miếng lót lưới', 900000, 'coat3.png', 'Puma', 0, 'Áo Khoác'),
(1305, 'Áo Khoác Puma Chạy Bộ In Phản Quang', 'Xám Than', 'Chạy hết mình và ăn mừng chiến thắng trong màn trình diễn Áo khoác chạy bộ dệt kim in phản quang. siêu nhẹ này', 500000, 'coat4.png', 'Puma', 0, 'Áo Khoác'),
(1306, 'Áo Khoác Nike Paris Saint-Germain', 'Đen', 'Làm mới tủ quần áo của bạn vào mùa xuân này với một chiếc áo khoác nhẹ mang cả Jordan và Paris Saint-Germain.', 3600000, 'coat5.png', 'Nike', 0, 'Áo Khoác'),
(1307, 'Áo Khoác Nike Dri-Fit', 'Xanh Đen', 'Giữ cho nhóm của bạn được bảo vệ trong Áo khoác Nike Dri-FIT. Vải dệt mềm sử dụng công nghệ thấm mồ hôi để giúp bạn che chắn', 900000, 'coat6.png', 'Nike', 0, 'Áo Khoác'),
(1308, 'Áo Khoác Nike Acg Storm-Fit', 'Vàng', 'Dãy núi Cascade đảm bảo rằng phần phía tây của Tây Bắc Thái Bình Dương có nhiều mưa. Cơn mưa đó là thứ làm cho khí hậu của chúng ta trở nên đẹp đẽ và cũng khó để tận hưởng sự thoải mái.', 2100000, 'coat7.png', 'Nike', 0, 'Áo Khoác'),
(1309, 'Áo Khoác Adidas Frostguard ', 'Xanh Navy', 'Chiếc áo khoác adidas này sẽ bảo vệ bạn khi nhiệt độ xuống thấp. Lớp cách nhiệt bên dưới làm ấm lõi của bạn và vải dệt co giãn trên cánh tay đảm bảo toàn bộ phạm vi chuyển động.', 2880000, 'coat9.png', 'Adidas', 0, 'Áo Khoác'),
(1313, 'Áo Khoác Adidas City Escape', 'Đen', 'Phong cách từ ngoài trời đến thành thị, được gói gọn trong một chiếc áo gió adidas. Chiếc áo khoác nhẹ thời trang này được lót bằng lưới thoáng khí nên sẽ không làm bạn nặng nề khi di chuyển trên đường phố.', 1700000, 'coat8.png', 'Adidas', 0, 'Áo Khoác'),
(1314, 'Áo Khoác Có Mũ Trùm Đầu Scuderia Ferrari Race', 'Đỏ', 'Mùa giải này, các chuyên gia về trang phục thể thao đã hợp tác với các bậc thầy về đua xe thể thao để tạo ra một bộ trang phục sôi động, lấy cảm hứng từ đường đua.', 4420000, 'coat10.png', 'Puma', 0, 'Áo Khoác'),
(1352, 'Áo Thun Puma Có Logo', 'Đỏ', 'Nổi bật với phong cách cổ điển và thương hiệu quá khổ của chiếc áo thun in logo này.', 362000, 't-shirt1.png', 'Puma', 0, 'Áo Thun'),
(1353, 'Áo Thun Puma Có Logo Cổ Điển', 'Trắng', 'Hãy nói to và tự hào về lòng trung thành với thương hiệu của bạn trong chiếc áo thun cotton nguyên chất cổ điển này', 495000, 't-shirt2.png', 'Puma', 0, 'Áo Thun'),
(1354, 'Áo Thun Puma Tập Luyện', 'Xanh Rêu', 'Làm mới bộ đồ tập gym của bạn với chiếc áo thun đơn giản này, được thiết kế với khả năng thấm ẩm để giúp bạn luôn khô ráo khi tập luyện', 515000, 't-shirt3.png', 'Puma', 0, 'Áo Thun'),
(1355, 'Áo Thun Gucci In Hình', 'Trắng', 'Được thiết kế với phom dáng quá khổ, món đồ này có in hình Gucci Blade nhiều màu. Họa tiết xác định chiếc áo phông này được chế tác từ áo cotton trắng.', 4765000, 't-shirt4.png', 'Gucci', 0, 'Áo Thun'),
(1356, 'Áo Thun Gucci Web Jersey', 'Trắng', 'Một chiếc thun dài tay bằng vải jersey cotton có mạng lưới màu xanh lục và đỏ quanh tay áo. Ảnh hưởng preppy, một phong cách có thể bắt nguồn từ các trường Ivy League ở Đông Bắc Hoa Kỳ, tiếp tục là đặc trưng của câu chuyện kể về Gucci.', 3965000, 't-shirt5.png', 'Gucci', 0, 'Áo Thun'),
(1357, 'Áo Thun Lacoste Cổ Tròn', 'Xanh Da Trời', 'Đừng bỏ lỡ sự đơn giản và thoải mái của chiếc áo phông này, được thiết kế cho thời tiết ấm áp. Được chế tác bằng bông trứng cá muối nhẹ, tươi, nó có đường cắt vừa vặn hợp thời trang và cổ tròn có gân hai màu. Mặc nó riêng hoặc mặc nó với một chiếc áo sơ mi in ngắn tay.', 412000, 't-shirt7.png', 'Lacoste', 0, 'Áo Thun'),
(1358, 'Áo Thun Lacoste Thể Thao 3D Jersey', 'Đen', 'Công nghệ tiên tiến mang đến điều thực sự đặc biệt cho chiếc áo thun ngắn tay màu trơn này được làm từ vải jersey pha cotton: áo có đặc tính siêu khô giúp thấm mồ hôi ngay cả trong những buổi tập luyện cường độ cao nhất', 638000, 't-shirt6.png', 'Lacoste', 0, 'Áo Thun'),
(1359, 'Áo Thun Lacoste Dáng Rộng Cổ Thuyền', 'Trắng', 'Cập nhật tủ quần áo của bạn với chiếc áo phông Lacoste nổi bật này, có hình chú cá sấu khổng lồ theo phong cách cổ điển. Chọn nhỏ hơn 1 size so với size thông thường của bạn để có phong cách vừa vặn hơn.', 880000, 't-shirt8.png', 'Lacoste', 0, 'Áo Thun'),
(1360, 'Áo Thun Adidas Họa Tiết Real Madrid', 'Trắng', 'Ăn mừng Real Madrid theo phong cách nổi bật. Chiếc áo phông bóng đá này mang đến cho câu lạc bộ yêu thích của bạn cách xử lý ánh kim với huy hiệu lấp lánh và Huy hiệu thể thao adidas. Áo thun cotton mềm mại tạo cảm giác thoải mái cho dù bạn mặc đi chơi với bạn bè hay mặc hàng ngày.', 1450000, 't-shirt10.png', 'Adidas', 0, 'Áo Thun'),
(1361, 'Áo Thun Adidas Sleeve Camo', 'Đen', 'Những người hâm mộ Đội 3-Sọc, không cần tìm đâu xa. Với họa tiết camo tinh tế, chiếc áo phông adidas này kết hợp hoàn hảo với đôi giày thể thao yêu thích của bạn. Được làm từ vải jersey đơn cotton mềm mại, đây là món đồ bổ sung thoải mái và lâu dài cho tủ quần áo của bạn', 947000, 't-shirt9.png', 'Adidas', 0, 'Áo Thun'),
(1362, 'Quần Dài Puma Bóng Rổ Clyde', 'Xanh Dương', 'Trang bị cho mình những bộ đồ thể thao cổ điển được mô phỏng lại cho người hâm mộ hiện đại, lấy cảm hứng từ di sản đáng kinh ngạc của bóng rổ', 1265000, 'pants1.png', 'Puma', 0, 'Quần Dài'),
(1363, 'Quần Dài Puma Spongebob T7', 'Xám', 'Ai sống trong một quả dứa dưới biển? PUMA x SPONGEBOB! Chúng tôi mang đến một số nét duyên dáng hoạt hình kỳ quặc cho biểu tượng PUMA, quần thể thao T7, sẵn sàng cho mọi cuộc phiêu lưu trên sân chơi.', 741000, 'pants2.png', 'Puma', 0, 'Quần Dài'),
(1364, 'Quần Dài Puma Dệt Cổ Điển', 'Nâu Sữa', 'Đá lại theo phong cách PUMA cổ điển. Cho dù bạn đang làm việc ở nhà, ra phố hay ngồi trên ghế sofa, những chiếc quần chịu ảnh hưởng từ hàng hóa này sẽ giúp bạn thư giãn mà không ảnh hưởng đến sự thoải mái.', 2446000, 'pants3.png', 'Puma', 0, 'Quần Dài'),
(1365, 'Quần Dài Gucci Chạy Bộ Họa Tiết Kỹ Thuật', 'Đỏ', 'Sự pha trộn giữa các thiết kế và chất liệu bất ngờ đã tạo nên những món đồ may sẵn xuyên suốt bộ sưu tập Xuân Hè 2023. Trang phục thể thao được làm thủ công bằng vải ánh kim và dạ hội, tạo ra một loạt các món đồ năng động và độc đáo', 2980000, 'pants5.png', 'Gucci', 0, 'Quần Dài'),
(1366, 'Quần Dài Gucci Thêu', 'Nâu Nhạt', 'Khám phá thêm trang phục trang trọng, những kiểu dáng phù hợp xuất hiện xuyên suốt bộ sưu tập Pre-Fall. Chiếc quần này được làm từ cotton màu ngà, được trang trí bằng hình thêu \'Gucci Boutique\'.', 3600000, 'pants4.png', 'Gucci', 0, 'Quần Dài'),
(1367, 'Quần Dài Gucci Thêu Cổ Điển', 'Trắng', 'Một chủ đề chính trong câu chuyện của Gucci, tinh thần hài hước thấm nhuần các thiết kế đặc trưng, thêm cảm giác hay thay đổi cho bộ sưu tập Pre-Fall 2023. Ngôi nhà sử dụng những hình thêu nhỏ và hình in rực rỡ theo những cách sáng tạo, làm phong phú thêm bộ sưu tập trang phục nam', 4150000, 'pant6.png', 'Gucci', 0, 'Quần Dài'),
(1368, 'Quần Dài Adidas Cổ Điển Beckenbauer', 'Xanh Navy', 'Quay số theo phong cách OG nào đó cho những ngày thư giãn của bạn. Chiếc quần thể thao adidas này rất phù hợp để đi dạo nhưng cũng đủ cổ điển để mặc ra ngoài. 3 Sọc mang tính biểu tượng ở hai bên tạo thêm nét cổ điển cho phong cách thể thao.', 2800000, 'pants7.png', 'Adidas', 0, 'Quần Dài'),
(1369, 'Quần Dài Adidas Tiro Suit-Up', 'Tím', 'Quần Tiro bắt đầu trên sân bóng đá. Với diện mạo mới và những tính năng tiềm ẩn, những chiếc quần cổ điển này đã trở thành một biểu tượng hàng ngày. Sinh ra từ thể thao, mặc cho phong cách.', 3330000, 'pants8.png', 'Adidas', 0, 'Quần Dài'),
(1370, 'Quần Dài Nike Thể Thao Repel Tech', 'Nâu Đất', 'Bạn cần một chiếc quần đa năng mang lại nhiều tiện ích? Chúng kết hợp vải Ripstop chống thấm nước với lớp lót mịn, thấm mồ hôi. Các túi đựng đồ có khóa kéo cho phép bạn cất giữ những vật dụng cần thiết của mình một cách an toàn', 1150000, 'pants9.png', 'Nike', 0, 'Quần Dài'),
(1371, 'Quần Dài Nike Jordan Engineered', 'Xanh Ngọc', 'Tiện ích hàng ngày đã đến. Chiếc quần dệt nhẹ này có lỗ thông hơi ở đầu gối để thoáng khí. Bungee ở phần hở chân và dây rút ở eo giúp bạn có được thân hình lý tưởng.', 2350000, 'pants10.png', 'Nike', 0, 'Quần Dài');

-- --------------------------------------------------------

--
-- Table structure for table `productcart`
--

CREATE TABLE `productcart` (
  `product_id` int(11) NOT NULL,
  `cart_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `size` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `productorder`
--

CREATE TABLE `productorder` (
  `product_id` int(11) NOT NULL,
  `order_id` varchar(50) NOT NULL,
  `quantity` int(11) NOT NULL,
  `size` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `productorder`
--

INSERT INTO `productorder` (`product_id`, `order_id`, `quantity`, `size`) VALUES
(1302, 'nknghiem29042023230522', 1, 'XXL'),
(1302, 'nknghiem30042023015323', 1, 'XXL'),
(1352, 'nknghiem30042023015323', 1, 'XXL'),
(1353, 'nknghiem29042023230527', 1, 'XXL'),
(1353, 'nknghiem30042023015323', 1, 'XXL'),
(1364, 'nknghiem29042023230533', 1, 'M');

-- --------------------------------------------------------

--
-- Table structure for table `product_seq`
--

CREATE TABLE `product_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product_seq`
--

INSERT INTO `product_seq` (`next_val`) VALUES
(1451);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `catalog`
--
ALTER TABLE `catalog`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`username`),
  ADD KEY `cartId` (`cart_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `catalog` (`catalog`);

--
-- Indexes for table `productcart`
--
ALTER TABLE `productcart`
  ADD PRIMARY KEY (`product_id`,`cart_id`,`size`) USING BTREE,
  ADD KEY `cartId` (`cart_id`);

--
-- Indexes for table `productorder`
--
ALTER TABLE `productorder`
  ADD PRIMARY KEY (`product_id`,`order_id`,`size`),
  ADD KEY `order_id` (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=553;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1373;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`username`) REFERENCES `customer` (`username`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`catalog`) REFERENCES `catalog` (`name`);

--
-- Constraints for table `productcart`
--
ALTER TABLE `productcart`
  ADD CONSTRAINT `productcart_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  ADD CONSTRAINT `productcart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Constraints for table `productorder`
--
ALTER TABLE `productorder`
  ADD CONSTRAINT `productorder_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `productorder_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
