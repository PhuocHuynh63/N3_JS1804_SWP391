import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import "./Cart.css";
import { NavLink } from "react-router-dom";

const CartPage = ({ show, handleClose }) => {

    /**
     * Handle quantity change
     */
    const [quantity, setQuantity] = useState('');

    const handleQuantityChange = (e, productId) => {
        const value = parseInt(e.target.value);
        if (value < 1) {
            setQuantity(0);
        } else {
            setQuantity(value);
        }

        setCartItems(prevCartItems => {
            const updatedCartItems = prevCartItems.map(item => {
                if (item.productId === productId) {
                    const newQuantity = value;
                    if (newQuantity > 0) {
                        if (newQuantity >= item.max) {
                            item.quantity = item.max
                        } else {
                            item.quantity = newQuantity;
                            item.totalPrice = (item.salePrice || item.price) * newQuantity;
                        }
                    }
                }
                return item;
            }).filter(item => item.quantity > 0); // Remove items with 0 quantity

            localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
            return updatedCartItems;
        });
    };
    //-----End-----//


    /**
     * Cart
     */
    const [cartItems, setCartItems] = useState([]);

    useEffect(() => {
        const storedCartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
        setCartItems(storedCartItems);
    }, [show]);

    // Update quantity of an item in the cart
    const updateQuantity = (productId, change) => {
        setCartItems(prevCartItems => {
            const updatedCartItems = prevCartItems.map(item => {
                if (item.productId === productId) {
                    const newQuantity = item.quantity + change;
                    if (newQuantity > 0 && newQuantity <= item.max) {
                        item.quantity = newQuantity;
                        item.totalPrice = (item.salePrice || item.price) * newQuantity;
                    }
                }
                return item;
            }).filter(item => item.quantity > 0); // Remove items with 0 quantity

            localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
            return updatedCartItems;
        });
    };

    // Get total quantity of items in the cart
    const getTotalQuantity = () => {
        return cartItems.reduce((total, item) => total + item.quantity, 0);
    };

    // Get total price of items in the cart
    const getTotalPrice = () => {
        return cartItems.reduce((total, item) => total + item.totalPrice, 0).toLocaleString('vi-VN');
    };

    // Remove item from cart
    const handleRemoveItem = (productId) => {
        setCartItems(prevCartItems => {
            const updatedCartItems = prevCartItems.filter(item => item.productId !== productId);
            localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
            return updatedCartItems;
        });
    };

    return (
        <Modal show={show} onHide={handleClose} size="lg" centered>
            <div className="cart-container">
                <div className="cart-header">
                    <h2>Giỏ Hàng Của Bạn</h2>
                    <button className="close-btn" onClick={handleClose}>×</button>
                </div>
                <table className="cart-table">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Giá tiền</th>
                            <th>Số lượng</th>
                            <th>Thành tiền</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody className="tbody-cart">
                        {cartItems.length === 0 ? (
                            <p className="empty-cart">Giỏ hàng của bạn đang trống, nhấn vào
                                <a href="/"> đây </a>
                                để tiếp tục mua hàng
                            </p>
                        ) : (
                            cartItems.map((item, index) => (
                                <tr className="cart-tr" key={index}>
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
                                            <input type="text" className="cart-quantity" value={item.quantity} onChange={(e) => handleQuantityChange(e, item.productId)} />
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
                <div className="cart-summary">
                    <p>Số lượng: <span id="total-quantity">{getTotalQuantity()}</span> sản phẩm</p>
                    <p>Tổng cộng: <span id="total-price">{getTotalPrice()}₫</span></p>
                </div>
                <div className="cart-actions">
                    <a href="/cart" className="view-cart-btn">Xem giỏ hàng</a>
                    <NavLink to={"/checkout"}>
                        <button className="checkout-btn1">Thanh toán</button>
                    </NavLink>
                </div>
            </div>
        </Modal>
    );
};

export default CartPage;
