import React, { useEffect, useState } from 'react';
import './OrderPage.css'; // Import CSS file
import { NavLink, useNavigate } from 'react-router-dom';
import { meBeSrc } from '../../service/meBeSrc'; // Không cần destructuring vì meBeSrc không phải là named export
import { jwtDecode } from 'jwt-decode';

export default function OrderPage() {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        address: '',
        note: ''
    });

    const [errors, setErrors] = useState({});
    const [addresses, setAddresses] = useState([]);
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false); // New state to check if user is logged in
    const [userId, setUserId] = useState(null); // State to store userId
    const [cartItems, setCartItems] = useState([]);

    /**
     * Check if cart is emty, if so, redirect to cart page
     */
    useEffect(() => {
        const storedCartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
        setCartItems(storedCartItems);

        if (storedCartItems.length === 0) {
            navigate('/cart');
        }
    }, [navigate]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });

        if (errors[name]) {
            setErrors({
                ...errors,
                [name]: ''
            });
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const newErrors = {};

        if (!formData.firstName) newErrors.firstName = 'Vui lòng nhập họ.';
        if (!formData.lastName) newErrors.lastName = 'Vui lòng nhập tên.';
        if (!formData.email) newErrors.email = 'Vui lòng nhập email.';
        if (!formData.phone) newErrors.phone = 'Vui lòng nhập số điện thoại.';
        if (!formData.address) newErrors.address = 'Vui lòng nhập địa chỉ.';

        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
        } else {
            const orderData = {
                guest: !isLoggedIn ? {
                    firstName: formData.firstName,
                    lastName: formData.lastName,
                    username: "guestUser",
                    email: formData.email,
                    phoneNumber: formData.phone,
                    address: formData.address,
                    password: "guestPassword",
                    birthOfDate: "01/01/1970"
                } : null,
                userId: isLoggedIn ? userId : null,
                voucherId: null,
                status: "Pending",
                totalAmount: parseFloat(getTotalPrice().replace(/\./g, '')),
                orderType: "online",
                paymentStatus: "pending",
                note: formData.note,
                item: cartItems.map(item => ({
                    productId: item.productId,
                    quantity: item.quantity,
                    price: item.price,
                    salePrice: item.salePrice || item.price
                }))
            };

            meBeSrc.createOrder(orderData)
                .then(response => {
                    console.log('Order created successfully:', response.data);
                    // Clear cartItems from state and localStorage
                    setCartItems([]);
                    localStorage.removeItem('cartItems');
                    navigate('/order-success');
                })
                .catch(error => {
                    console.error('Error creating order:', error);
                    console.error('Error response data:', error.response.data);
                });
        }
    };

    const getTotalPrice = () => {
        return cartItems.reduce((total, item) => total + item.totalPrice, 0).toLocaleString('vi-VN');
    };

    const [user, setUser] = useState({
        username: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        email: ""
    });

    useEffect(() => {
        const token = localStorage.getItem('USER_INFO');
        if (token) {
            setIsLoggedIn(true); // Set user as logged in
            const decoded = jwtDecode(token);
            const username = decoded.sub;

            meBeSrc.getUserByUserName(username)
                .then((res) => {
                    const userData = res.data;
                    setUser(userData);
                    setUserId(userData.id); // Set userId
                    setAddresses(userData.listAddress);
                    setFormData({
                        firstName: userData.firstName,
                        lastName: userData.lastName,
                        email: userData.email,
                        phone: userData.phoneNumber,
                        address: userData.listAddress[0]?.address || '',
                        note: ''
                    });
                })
                .catch((err) => {
                    console.log("Error fetching user", err);
                });
        }
    }, []);

    return (
        <div className="order-container">
            <div className="order-left">
                <form onSubmit={handleSubmit}>
                    <div className="order-left_top">
                        <h3>Me&Be</h3>
                        <p>Giỏ hàng {'>'} Thông tin giao hàng {'>'} Phương thức thanh toán</p>
                    </div>
                    <div className='order-left_body'>
                        <h4>Thông tin giao hàng</h4>
                        <p>Bạn đã có tài khoản? <a href="#">Đăng nhập</a></p>
                        <div className='order-name'>
                            <div className="order-input-group">
                                <input
                                    type='text'
                                    name='firstName'
                                    value={formData.firstName}
                                    onChange={handleChange}
                                    className={`order-input firstname ${errors.firstName ? 'input-error' : ''}`}
                                    placeholder='Họ'
                                />
                                {errors.firstName && <p className="error-text">{errors.firstName}</p>}
                            </div>
                            <div className="order-input-group">
                                <input
                                    type='text'
                                    name='lastName'
                                    value={formData.lastName}
                                    onChange={handleChange}
                                    className={`order-input lastname ${errors.lastName ? 'input-error' : ''}`}
                                    placeholder='Tên'
                                />
                                {errors.lastName && <p className="error-text">{errors.lastName}</p>}
                            </div>
                        </div>

                        <div className='order-email_phone'>
                            <div className="order-input-group">
                                <input
                                    type='text'
                                    name='email'
                                    value={formData.email}
                                    onChange={handleChange}
                                    className={`order-input email ${errors.email ? 'input-error' : ''}`}
                                    placeholder='Email'
                                />
                                {errors.email && <p className="error-text">{errors.email}</p>}
                            </div>
                            <div className="order-input-group">
                                <input
                                    type='text'
                                    name='phone'
                                    value={formData.phone}
                                    onChange={handleChange}
                                    className={`order-input phone ${errors.phone ? 'input-error' : ''}`}
                                    placeholder='Số điện thoại'
                                />
                                {errors.phone && <p className="error-text">{errors.phone}</p>}
                            </div>
                        </div>

                        <div className="order-input-group">
                            <input
                                type='text'
                                name='note'
                                value={formData.note}
                                onChange={handleChange}
                                className={`order-input note`}
                                placeholder='Ghi chú'
                            />
                        </div>
                    </div>
                    <div className='order-left_address'>
                        <h4>Giao tận nơi</h4>
                        <div className="order-input-group">
                            <input
                                type='text'
                                name='address'
                                value={formData.address}
                                onChange={handleChange}
                                className={`order-input street ${errors.address ? 'input-error' : ''}`}
                                placeholder='Địa chỉ'
                            />
                            {errors.address && <p className="error-text">{errors.address}</p>}
                        </div>
                    </div>

                    <div className='order-navigate'>
                        <NavLink to="/cart">Giỏ hàng</NavLink>
                        <button type='submit'>Hoàn tất đơn hàng</button>
                    </div>
                </form>
            </div>
            <div className="order-right">
                <div className="order-summary">
                    <h4>Đơn hàng của bạn</h4>
                    {cartItems.map((item, index) => (
                        <div className="order-item" key={index}>
                            <div className='order-item_img'>
                                <img src={item.images} alt={item.name} />
                                <span>{item.quantity}</span>
                            </div>
                            <p>{item.name}</p>
                            <p>{item.totalPrice.toLocaleString('vi-VN')}₫</p>
                        </div>
                    ))}

                    <div className='voucher'>
                        <input type='text' className='order-input' placeholder='Mã giảm giá' />
                        <button className='order-button'>Sử dụng</button>
                    </div>
                    <div className="order-total">
                        <span>Tổng cộng: </span><span>{getTotalPrice()}₫</span>
                    </div>
                </div>
            </div>
        </div>
    );
}
