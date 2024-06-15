import React from "react";
import { Modal } from "react-bootstrap";
import "./Cart.css";

const CartPage = ({ show, handleClose }) => {
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
                    <tbody>
                        <tr>
                            <td>
                                <div className="product-info">
                                    <img src="https://via.placeholder.com/50" alt="Product Image" />
                                    <div>
                                        <p>Gạc Răng Miệng Chippi Baby</p>
                                        <p>Default Title</p>
                                    </div>
                                </div>
                            </td>
                            <td className="price">110000</td>
                            <td>
                                <div className="quantity-control">
                                    <button className="minus-btn">-</button>
                                    <input type="text" className="quantity" value="2" readOnly />
                                    <button className="plus-btn">+</button>
                                </div>
                            </td>
                            <td className="subtotal">220000</td>
                            <td><button className="remove-btn">×</button></td>
                        </tr>
                        <tr>
                            <td>
                                <div className="product-info">
                                    <img src="https://via.placeholder.com/50" alt="Product Image" />
                                    <div>
                                        <p>Bộ quần áo dài tay Nous petit (trắng/hồng)</p>
                                        <p>0-3M - Trắng</p>
                                    </div>
                                </div>
                            </td>
                            <td className="price">195000</td>
                            <td>
                                <div className="quantity-control">
                                    <button className="minus-btn">-</button>
                                    <input type="text" className="quantity" value="4" readOnly />
                                    <button className="plus-btn">+</button>
                                </div>
                            </td>
                            <td className="subtotal">780000</td>
                            <td><button className="remove-btn">×</button></td>
                        </tr>
                        <tr>
                            <td>
                                <div className="product-info">
                                    <img src="https://via.placeholder.com/50" alt="Product Image" />
                                    <div>
                                        <p>Bánh AD Little Spoon vị cam quýt 30g</p>
                                        <p>Default Title</p>
                                    </div>
                                </div>
                            </td>
                            <td className="price">75000</td>
                            <td>
                                <div className="quantity-control">
                                    <button className="minus-btn">-</button>
                                    <input type="text" className="quantity" value="1" readOnly />
                                    <button className="plus-btn">+</button>
                                </div>
                            </td>
                            <td className="subtotal">75000</td>
                            <td><button className="remove-btn">×</button></td>
                        </tr>
                    </tbody>
                </table>
                <div className="cart-summary">
                    <p>Số lượng: <span id="total-quantity">7</span> sản phẩm</p>
                    <p>Tổng cộng: <span id="total-price">1075000₫</span></p>
                </div>
                <div className="cart-actions">
                    <a href="/cart" className="view-cart-btn">Xem giỏ hàng</a>
                    <button className="checkout-btn">Thanh toán</button>
                </div>
            </div>
        </Modal>
    )
}
export default CartPage;
