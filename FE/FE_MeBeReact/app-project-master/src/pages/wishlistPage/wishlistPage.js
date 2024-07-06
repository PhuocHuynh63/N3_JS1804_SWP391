import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import "./wishlistPage.css";
import { useNavigate } from "react-router-dom";

const WishlistPage = ({ show, handleClose }) => {
    const [wishlistItems, setWishlistItems] = useState([]);
    const [modalMessage, setModalMessage] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const storedWishlistItems = JSON.parse(localStorage.getItem('wishlistItems')) || [];
        setWishlistItems(storedWishlistItems);
    }, [show]);

    const updateQuantity = (productId, change) => {
        const updatedWishlistItems = wishlistItems.map(item => {
            if (item.productId === productId) {
                const newQuantity = item.quantity + change;
                if (newQuantity > 0) {
                    item.quantity = newQuantity;
                    item.totalPrice = (item.salePrice || item.price) * newQuantity;
                }
            }
            return item;
        });
        setWishlistItems(updatedWishlistItems);
        localStorage.setItem('wishlistItems', JSON.stringify(updatedWishlistItems));
    }

    const getTotalQuantity = () => {
        return wishlistItems.reduce((total, item) => total + item.quantity, 0);
    };

    const getTotalPrice = () => {
        return wishlistItems.reduce((total, item) => total + item.totalPrice, 0).toLocaleString('vi-VN');
    };

    const handleRemoveItem = (productId) => {
        const updatedWishlistItems = wishlistItems.filter(item => item.productId !== productId);
        setWishlistItems(updatedWishlistItems);
        localStorage.setItem('wishlistItems', JSON.stringify(updatedWishlistItems));
    }

    const showModalnotify = (message) => {
        setModalMessage(message);
        setIsModalVisible(true);
    };

    const handleNavigate = (url) => {
        handleClose();
        navigate(url);
    }

    return (
        <Modal show={show} onHide={handleClose} size="lg" centered>
            <div className="wishlist-container">
                <div className="wishlist-header">
                    <h2>Danh Sách Đặt Trước</h2>
                    <button className="close-btn" onClick={handleClose}>×</button>
                </div>
                <table className="wishlist-table">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Giá tiền</th>
                            <th>Số lượng</th>
                            <th>Thành tiền</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody className="tbody-wishlist">
                        {wishlistItems.length === 0 ? (
                            <p className="empty-wishlist">Danh sách đặt trước của bạn đang trống, nhấn vào
                                <a href="/"> đây </a>
                                để tiếp tục mua hàng
                            </p>
                        ) : (
                            wishlistItems.map((item, index) => (
                                <tr className="wishlist-tr" key={index}>
                                    <td>
                                        <div className="product-info">
                                            <img src={item.images} alt={item.name} />
                                            <div>
                                                <p>{item.name}</p>
                                                <p>{item.size}</p>
                                            </div>
                                        </div>
                                    </td>
                                    <td className="price">{(item.salePrice || item.price).toLocaleString('vi-VN')}₫</td>
                                    <td>
                                        <div className="quantity-control">
                                            <button className="minus-btn" onClick={() => updateQuantity(item.productId, -1)}>-</button>
                                            <input type="text" className="wishlist-quantity" value={item.quantity} readOnly />
                                            <button className="plus-btn" onClick={() => updateQuantity(item.productId, 1)}>+</button>
                                        </div>
                                    </td>
                                    <td className="subtotal">{item.totalPrice.toLocaleString('vi-VN')}₫</td>
                                    <td><button className="remove-btn" onClick={() => handleRemoveItem(item.productId)}>×</button></td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
                <div className="wishlist-summary">
                    <p>Số lượng: <span id="total-quantity">{getTotalQuantity()}</span> sản phẩm</p>
                    <p>Tổng cộng: <span id="total-price">{getTotalPrice()}₫</span></p>
                </div>
                <div className="wishlist-actions">
                    <button className="view-cart-btn" onClick={() => handleNavigate("/wishlist")}>Xem wishlist</button>
                </div>
            </div>
            <Modal
                title="Thông báo"
                visible={isModalVisible}
                footer={null}
                onCancel={() => setIsModalVisible(false)}
                className="custom-modal"
            >
                <div>{modalMessage}</div>
            </Modal>
        </Modal>
    );
}

export default WishlistPage;
