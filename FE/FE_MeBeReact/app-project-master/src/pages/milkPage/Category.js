import React from 'react';
import './Category.css';

export default function Category() {
    return (
        <div className="category-container">
            <div className="category-header">
                <h2>Bạn cần tìm</h2>
                <div className="category-icons">
                    <div className="category-icon">
                        <img src='../images/sua_bot-modified.png' />
                        <p>Sữa bột</p>
                    </div>
                    <div className="category-icon">BỘ DÀI TAY</div>
                    <div className="category-icon">ÁO KHOÁC</div>
                    <div className="category-icon">BỘ CỘC TAY</div>
                    <div className="category-icon">BỘ YẾM</div>
                    <div className="category-icon">QUẦN</div>
                    <div className="category-icon">VÁY</div>
                </div>
            </div>
            <div className="category-section">
                <h3>Bộ tay dài</h3>
                <div className="products">
                    <div className="product">
                        <img src="link-to-image" alt="Product 1" />
                        <p>Bộ dài chui đầu màu vàng</p>
                        <p>195,000đ</p>
                    </div>
                    <div className="product">
                        <img src="link-to-image" alt="Product 2" />
                        <p>Bộ dài chui đầu cổ bèo màu hồng nhạt</p>
                        <p>195,000đ</p>
                    </div>
                    <div className="product">
                        <img src="link-to-image" alt="Product 3" />
                        <p>Bộ dài chui đầu màu xanh nhạt</p>
                        <p>195,000đ</p>
                    </div>
                </div>
                <a href="#" className="view-more">Xem thêm</a>
            </div>
            <div className="category-section">
                <h3>Bộ bodysuite tay dài</h3>
                <div className="products">
                    <div className="product">
                        <img src="link-to-image" alt="Product 4" />
                        <p>Bộ bodysuite tay dài</p>
                        <p>195,000đ</p>
                    </div>
                    <div className="product">
                        <img src="link-to-image" alt="Product 5" />
                        <p>Bộ bodysuite tay dài</p>
                        <p>195,000đ</p>
                    </div>
                    <div className="product">
                        <img src="link-to-image" alt="Product 6" />
                        <p>Bộ bodysuite tay dài</p>
                        <p>195,000đ</p>
                    </div>
                </div>
                <a href="#" className="view-more">Xem thêm</a>
            </div>
        </div>
    );
}
