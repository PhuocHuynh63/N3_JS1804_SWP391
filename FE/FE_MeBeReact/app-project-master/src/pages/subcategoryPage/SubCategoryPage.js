import React from 'react';
import './SubCategoryPage.css';
import { meBeSrc } from '../../service/meBeSrc';

export default function SubCategory() {
    return (
        <div className='subcategory'>
            <header>
                <div class="logo">
                    <img src="logo.png" alt="Nous Logo" />
                </div>
                <nav>
                    <ul>
                        <li><a href="#">GIỚI THIỆU NOUS</a></li>
                        <li><a href="#">THỜI TRANG SƠ SINH</a></li>
                        <li><a href="#">THỜI TRANG CHO BÉ 2-6Y</a></li>
                        <li><a href="#">BỘ SƯU TẬP</a></li>
                        <li><a href="#">HỆ THỐNG ĐẠI LÝ</a></li>
                        <li><a href="#">LỚN CÙNG NOUS</a></li>
                    </ul>
                </nav>
                <div class="search">
                    <input type="text" placeholder="Nhập tên sản phẩm" />
                </div>
                <div class="account">
                    <a href="#">Tài khoản</a>
                    <a href="#">Giỏ hàng</a>
                </div>
            </header>
            <main>
                <div class="breadcrumbs">
                    Trang chủ / Bé 3-24 tháng bộ dài tay (14)
                </div>
                <div class="filters">
                    <button>Bộ lọc</button>
                    <button>SIZE</button>
                    <button>DÒNG SẢN PHẨM</button>
                    <button>THƯƠNG HIỆU</button>
                    <button>MÀU SẮC</button>
                    <button>GIÁ BÁN</button>
                </div>
                <div class="products">
                    <div class="product-item">
                        <img src="product1.png" alt="Product 1" />
                        <p>Bộ dài tay chui đầu màu trắng in họa tiết</p>
                        <p>205,000đ</p>
                    </div>
                    <div class="product-item">
                        <img src="product2.png" alt="Product 2" />
                        <p>Bộ CTD Petit kẻ hồng in họa tiết mèo ở túi</p>
                        <p>195,000đ</p>
                    </div>
                    <div class="product-item">
                        <img src="product3.png" alt="Product 3" />
                        <p>Bộ CTD kẻ xám</p>
                        <p>215,000đ</p>
                    </div>
                    <div class="product-item">
                        <img src="product4.png" alt="Product 4" />
                        <p>Bộ cài thẳng dài hồng</p>
                        <p>195,000đ</p>
                    </div>
                    <div class="product-item">
                        <img src="product5.png" alt="Product 5" />
                        <p>Bộ CTD petit kẻ xanh in họa tiết chim non ở túi</p>
                        <p>195,000đ</p>
                    </div>
                    <div class="product-item">
                        <img src="product6.png" alt="Product 6" />
                        <p>Bộ cài thẳng dài xanh</p>
                        <p>195,000đ</p>
                    </div>
                </div>
            </main>
        </div>
    )

}