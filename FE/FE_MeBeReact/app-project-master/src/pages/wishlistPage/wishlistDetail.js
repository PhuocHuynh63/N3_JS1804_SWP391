import React, { useState, useEffect } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { Modal } from 'antd';
import 'bootstrap/dist/css/bootstrap.min.css';
import './wishlistDetail.css';
import { meBeSrc } from '../../service/meBeSrc';  // Import API service

export default function WishlistDetail() {
    const [wishlistItems, setWishlistItems] = useState([]);
    const [modalMessage, setModalMessage] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const storedWishlistItems = JSON.parse(localStorage.getItem('wishlistItems')) || [];
        console.log("Loaded wishlist items from localStorage:", storedWishlistItems);

        const fetchProductDetails = async () => {
            const updatedWishlistItems = await Promise.all(storedWishlistItems.map(async (item) => {
                try {
                    const response = await meBeSrc.getProductDetail(item.productId);
                    return { ...item, max: response.data.quantity };
                } catch (error) {
                    console.error('Error fetching product details:', error);
                    return item;
                }
            }));
            setWishlistItems(updatedWishlistItems);
            localStorage.setItem('wishlistItems', JSON.stringify(updatedWishlistItems));
        };

        fetchProductDetails();
    }, []);

    const updateQuantity = (productId, change) => {
        const updatedWishlistItems = wishlistItems.map(item => {
            if (item.productId === productId) {
                const newQuantity = item.quantity + change;
                if (newQuantity > 0 && newQuantity <= item.max) {  // Ensure quantity does not exceed max
                    item.quantity = newQuantity;
                    item.totalPrice = (item.salePrice || item.price) * newQuantity;
                }
            }
            return item;
        }).filter(item => item.quantity > 0);
        setWishlistItems(updatedWishlistItems);
        localStorage.setItem('wishlistItems', JSON.stringify(updatedWishlistItems));
    };

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
    };

    const handleMoveToCart = () => {
        console.log("Moving items to cart...");
        console.log("Wishlist items:", wishlistItems);

        const availableItems = wishlistItems.filter(item => item.max && item.max > 0 && item.quantity <= item.max);

        console.log("Available items:", availableItems);

        if (availableItems.length > 0) {
            const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
            console.log("Initial cart items:", cartItems);

            availableItems.forEach(item => {
                const existingItemIndex = cartItems.findIndex(cartItem => cartItem.productId === item.productId);

                if (existingItemIndex > -1) {
                    const existingItem = cartItems[existingItemIndex];
                    const newQuantity = existingItem.quantity + item.quantity;
                    if (newQuantity > existingItem.max) {
                        console.log(`Item ${existingItem.productId} quantity exceeds max. Setting to max.`);
                        existingItem.quantity = existingItem.max;
                        existingItem.totalPrice = (existingItem.salePrice || existingItem.price) * existingItem.max;
                    } else {
                        console.log(`Updating item ${existingItem.productId} quantity to ${newQuantity}.`);
                        existingItem.quantity = newQuantity;
                        existingItem.totalPrice = (existingItem.salePrice || existingItem.price) * newQuantity;
                    }
                } else {
                    if (item.quantity > item.max) {
                        console.log(`Item ${item.productId} quantity exceeds max. Setting to max.`);
                        item.quantity = item.max;
                        item.totalPrice = (item.salePrice || item.price) * item.max;
                    }
                    console.log(`Adding new item ${item.productId} to cart.`);
                    cartItems.push(item);
                }
            });

            console.log("Updated cart items:", cartItems);

            localStorage.setItem('cartItems', JSON.stringify(cartItems));

            const remainingWishlistItems = wishlistItems.filter(item => item.max === 0 || item.quantity > item.max);
            console.log("Remaining wishlist items:", remainingWishlistItems);

            setWishlistItems(remainingWishlistItems);
            localStorage.setItem('wishlistItems', JSON.stringify(remainingWishlistItems));

            navigate('/cart');
        } else {
            console.log("No available items to move to cart.");
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Sản phẩm vẫn chưa có hàng </h1></div>);
        }
    };

    const showModalnotify = (message) => {
        setModalMessage(message);
        setIsModalVisible(true);
    };

    return (
        <section className="wishlist-details container py-5 mt-5">
            <nav aria-label="breadcrumb">
                <ol className="breadcrumb">
                    <li className="breadcrumb-item">
                        <NavLink to={"/"}>
                            Trang chủ
                        </NavLink>
                    </li>
                    <li className="breadcrumb-item active" aria-current="page">
                        Danh sách đặt trước
                    </li>
                </ol>
            </nav>
            <div className="row">
                {wishlistItems.length === 0 ? (
                    <div className="col-md-12 text-center">
                        <div className='wishlist-detail_empty'>
                            <img src='https://file.hstatic.net/200000259653/file/empty-cart_large_46db8e27ff56473ca63e3c4bb8981b64.png' alt="Empty wishlist" />
                            <NavLink to={"/"}>
                                Tiếp tục mua sắm →
                            </NavLink>
                        </div>
                    </div>
                ) : (
                    <>
                        <div className="col-md-8">
                            <div className="wishlist-item">
                                {wishlistItems.map((item, index) => (
                                    <div className="row" key={index}>
                                        <div className="col-md-3">
                                            <NavLink to={`/product/${item.productId}`}>
                                                <img
                                                    src={item.images}
                                                    className="img-fluid"
                                                    alt={item.name}
                                                />
                                            </NavLink>
                                        </div>

                                        <div className="col-md-3">
                                            <NavLink to={`/product/${item.productId}`} style={{ color: "black" }}>
                                                <h5>{item.name}</h5>
                                            </NavLink>
                                            <button className="btn btn-link p-0" onClick={() => handleRemoveItem(item.productId)}>Xóa</button>
                                        </div>
                                        <div className="col-md-3 text-center">
                                            <span className="price">{(item.salePrice || item.price).toLocaleString('vi-VN')}₫</span>
                                        </div>
                                        <div className="col-md-3">
                                            <div className="input-group justify-content-center">
                                                <div className="input-group-prepend">
                                                    <button className="btn btn-outline-secondary" type="button" onClick={() => updateQuantity(item.productId, -1)}>
                                                        -
                                                    </button>
                                                </div>
                                                <input type="text" className="form-control" value={item.quantity} readOnly />
                                                <div className="input-group-append">
                                                    <button className="btn btn-outline-secondary" type="button" onClick={() => updateQuantity(item.productId, 1)}>
                                                        +
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="wishlist-summary">
                                <h4 id='wishlist-title'>Thông tin đơn hàng</h4>
                                <p className='description'>
                                    Tạm tính ({getTotalQuantity()} sản phẩm): <span className="price">{getTotalPrice()}₫</span>
                                </p>
                                <p className='description'>
                                    Phí vận chuyển sẽ được tính ở trang thanh toán. Bạn cũng có thể
                                    nhập mã giảm giá ở trang thanh toán.
                                </p>
                                <h5 id='summary'>
                                    Tổng cộng: <span className="price">{getTotalPrice()}₫</span>
                                </h5>
                                <button className="btn btn-primary" onClick={handleMoveToCart}>Chuyển sang giỏ hàng</button>
                            </div>
                        </div>
                    </>
                )}
            </div>
            <Modal
                title="Thông báo"
                open={isModalVisible}
                footer={null}
                onCancel={() => setIsModalVisible(false)}
                className="custom-modal"
            >
                <div>{modalMessage}</div>
            </Modal>
        </section>
    );
}
