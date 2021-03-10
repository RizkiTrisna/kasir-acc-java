-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 23 Feb 2021 pada 15.07
-- Versi server: 10.4.8-MariaDB
-- Versi PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `acc_kasir`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL,
  `username` varchar(150) NOT NULL,
  `password` varchar(80) NOT NULL,
  `nama_admin` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `password`, `nama_admin`) VALUES
(1, 'rizkitrisna', 'pass123', 'Rizki Trisna'),
(2, 'wahyuirawan', 'password123', 'Wahyu Irawan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `id_barang` int(11) NOT NULL,
  `id_jenis_barang` int(11) NOT NULL,
  `nama_barang` varchar(175) NOT NULL,
  `harga_jual` int(11) NOT NULL,
  `harga_pokok` int(11) NOT NULL,
  `stok_barang` int(11) NOT NULL,
  `input_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `update_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`id_barang`, `id_jenis_barang`, `nama_barang`, `harga_jual`, `harga_pokok`, `stok_barang`, `input_date`, `update_date`) VALUES
(1, 2, 'Plastik Mika a3', 20000, 23000, 25, '2020-12-17 10:52:04', '2020-12-17 10:52:04'),
(2, 2, 'Kantong platik 1kg', 12000, 11500, 10, '2020-12-17 10:52:04', '2020-12-17 10:52:04'),
(4, 1, 'Kertas a4 34 rim', 43000, 41500, 1, '2020-12-17 10:54:20', '2020-12-17 10:54:20'),
(5, 2, 'Kertas figura', 1112, 1111, 3, '2020-12-22 10:26:50', '2020-12-22 10:26:50'),
(6, 1, 'Kertas baliho', 1300, 1000, 14, '2020-12-22 10:28:19', '2020-12-22 10:28:19'),
(7, 2, 'Plastik 12kg', 1200, 1000, 6, '2020-12-22 10:30:25', '2020-12-22 10:30:25'),
(9, 2, 'Silica', 1500, 1222, 23, '2020-12-24 14:03:01', '2020-12-24 14:03:01'),
(10, 1, 'Kertas warna', 200, 125, 89, '2021-01-15 13:48:30', '2021-01-15 13:48:30'),
(11, 1, 'Lakmushh', 33, 11, 33, '2021-01-15 13:51:52', '2021-01-15 13:51:52'),
(17, 1, 'tambah baru', 0, 100, 9, '2021-01-17 05:18:26', '2021-01-17 05:18:26'),
(18, 1, '22', 22, 22, 22, '2021-01-17 05:27:36', '2021-01-17 05:27:36'),
(19, 1, 'safga', 10000, 9000, 21, '2021-01-17 06:51:42', '2021-01-17 06:51:42'),
(20, 1, '1111', 110, 11, 1, '2021-01-17 06:52:49', '2021-01-17 06:52:49'),
(25, 1, 'barang Azz', 2500, 2000, 122, '2021-02-02 15:40:40', '2021-02-02 15:40:40'),
(26, 1, 'Barang B', 250, 200, 14, '2021-02-03 04:12:52', '2021-02-03 04:12:52');

-- --------------------------------------------------------

--
-- Stand-in struktur untuk tampilan `checkout`
-- (Lihat di bawah untuk tampilan aktual)
--
CREATE TABLE `checkout` (
`id_temp` int(11)
,`id_admin` int(11)
,`nama_admin` varchar(100)
,`id_barang` int(11)
,`nama_barang` varchar(175)
,`qty` int(11)
,`harga_jual` int(11)
,`subtotal` bigint(21)
,`tanggal_pembelian` timestamp
);

-- --------------------------------------------------------

--
-- Struktur dari tabel `jenis_barang`
--

CREATE TABLE `jenis_barang` (
  `id_jenis` int(11) NOT NULL,
  `nama_jenis` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `jenis_barang`
--

INSERT INTO `jenis_barang` (`id_jenis`, `nama_jenis`) VALUES
(1, 'Kertas'),
(2, 'Plastik');

-- --------------------------------------------------------

--
-- Struktur dari tabel `session`
--

CREATE TABLE `session` (
  `id_session` int(11) NOT NULL,
  `id_admin` int(11) NOT NULL,
  `waktu_login` timestamp NOT NULL DEFAULT current_timestamp(),
  `waktu_logout` timestamp NOT NULL DEFAULT current_timestamp(),
  `status_login` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `session`
--

INSERT INTO `session` (`id_session`, `id_admin`, `waktu_login`, `waktu_logout`, `status_login`) VALUES
(1, 1, '2021-01-21 02:27:54', '2021-01-21 02:28:43', 0),
(2, 1, '2021-01-22 09:06:38', '2021-01-22 09:06:38', 1),
(3, 1, '2021-01-22 09:07:01', '2021-01-22 09:07:01', 1),
(4, 1, '2021-01-22 09:10:00', '2021-01-22 09:10:00', 1),
(5, 1, '2021-01-22 09:10:01', '2021-01-22 09:10:01', 1),
(6, 1, '2021-01-22 09:11:08', '2021-01-22 09:11:08', 1),
(7, 1, '2021-01-22 09:11:08', '2021-01-22 09:11:08', 1),
(8, 1, '2021-01-22 09:11:46', '2021-01-22 09:11:46', 1),
(9, 1, '2021-01-22 09:11:46', '2021-01-22 09:25:02', 0),
(10, 2, '2021-01-22 09:26:25', '2021-01-22 09:26:25', 1),
(11, 2, '2021-01-22 09:26:25', '2021-01-22 09:28:21', 0),
(12, 2, '2021-01-22 09:28:44', '2021-02-20 07:30:06', 0),
(13, 1, '2021-02-20 07:38:32', '2021-02-20 07:39:12', 0),
(14, 1, '2021-02-20 07:42:27', '2021-02-20 07:42:30', 0),
(15, 1, '2021-02-20 07:43:11', '2021-02-20 07:43:18', 0),
(16, 1, '2021-02-20 07:45:59', '2021-02-21 14:57:49', 0),
(17, 1, '2021-02-21 14:58:05', '2021-02-21 15:32:18', 0),
(18, 1, '2021-02-21 15:37:24', '2021-02-23 10:41:42', 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `temp_transaksi`
--

CREATE TABLE `temp_transaksi` (
  `id_temp` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `id_admin` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `tanggal_pembelian` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `id_admin` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `tanggal_pembelian` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_barang`, `id_admin`, `qty`, `tanggal_pembelian`) VALUES
(2, 19, 2, 1, '2021-01-01 04:08:55'),
(3, 26, 2, 1, '2021-01-31 04:08:55'),
(4, 2, 2, 2, '2021-02-01 04:12:22'),
(5, 4, 2, 1, '2021-02-28 04:12:22'),
(6, 10, 2, 1, '2021-02-04 04:25:23'),
(7, 25, 1, 21, '2021-02-18 14:06:59'),
(8, 10, 1, 3, '2021-02-18 14:07:17'),
(9, 2, 1, 2, '2021-02-21 15:21:21'),
(10, 5, 1, 1, '2021-02-21 15:21:21'),
(11, 20, 1, 11, '2021-02-21 15:21:21'),
(12, 1, 1, 1, '2021-02-23 10:35:18'),
(13, 2, 1, 1, '2021-02-23 10:35:18'),
(14, 7, 1, 1, '2021-02-23 10:35:18'),
(15, 11, 1, 1, '2021-02-23 10:35:18'),
(16, 4, 1, 1, '2021-02-23 10:39:57');

-- --------------------------------------------------------

--
-- Struktur untuk view `checkout`
--
DROP TABLE IF EXISTS `checkout`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `checkout`  AS  select `temp_transaksi`.`id_temp` AS `id_temp`,`temp_transaksi`.`id_admin` AS `id_admin`,`admin`.`nama_admin` AS `nama_admin`,`temp_transaksi`.`id_barang` AS `id_barang`,`barang`.`nama_barang` AS `nama_barang`,`temp_transaksi`.`qty` AS `qty`,`barang`.`harga_jual` AS `harga_jual`,`barang`.`harga_jual` * `temp_transaksi`.`qty` AS `subtotal`,`temp_transaksi`.`tanggal_pembelian` AS `tanggal_pembelian` from ((`temp_transaksi` join `barang`) join `admin`) where `barang`.`id_barang` = `temp_transaksi`.`id_barang` and `admin`.`id_admin` = `temp_transaksi`.`id_admin` ;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_barang`),
  ADD KEY `id_jenis_barang` (`id_jenis_barang`);

--
-- Indeks untuk tabel `jenis_barang`
--
ALTER TABLE `jenis_barang`
  ADD PRIMARY KEY (`id_jenis`);

--
-- Indeks untuk tabel `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id_session`),
  ADD KEY `id_admin` (`id_admin`);

--
-- Indeks untuk tabel `temp_transaksi`
--
ALTER TABLE `temp_transaksi`
  ADD PRIMARY KEY (`id_temp`),
  ADD KEY `id_barang` (`id_barang`),
  ADD KEY `id_admin` (`id_admin`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_barang` (`id_barang`),
  ADD KEY `id_admin` (`id_admin`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `barang`
--
ALTER TABLE `barang`
  MODIFY `id_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT untuk tabel `jenis_barang`
--
ALTER TABLE `jenis_barang`
  MODIFY `id_jenis` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `session`
--
ALTER TABLE `session`
  MODIFY `id_session` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT untuk tabel `temp_transaksi`
--
ALTER TABLE `temp_transaksi`
  MODIFY `id_temp` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `barang_ibfk_1` FOREIGN KEY (`id_jenis_barang`) REFERENCES `jenis_barang` (`id_jenis`);

--
-- Ketidakleluasaan untuk tabel `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`);

--
-- Ketidakleluasaan untuk tabel `temp_transaksi`
--
ALTER TABLE `temp_transaksi`
  ADD CONSTRAINT `temp_transaksi_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`),
  ADD CONSTRAINT `temp_transaksi_ibfk_2` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`);

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
