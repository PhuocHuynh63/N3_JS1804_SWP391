import React, { useEffect, useState } from 'react';
import './OrderPage.css'; // Import CSS file
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { meBeSrc } from '../../service/meBeSrc'; // Không cần destructuring vì meBeSrc không phải là named export
import { jwtDecode } from 'jwt-decode';
import Loading from '../../components/loading/Loading';

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
    const [selectedPaymentMethod, setSelectedPaymentMethod] = useState('COD');
    const [loading, setLoading] = useState(false); //LoadingIcon...

    useEffect(() => {
        const storedCartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
        setCartItems(storedCartItems);

        if (storedCartItems.length === 0) {
            navigate('/cart');
        }
    }, [navigate]);


    /**
     * handleChange function to handle input change
     * @param {*} e 
     */
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
    //-----End-----//


    /**
     * handle submit function to handle form submit
     * @param {*} e 
     */
    const handleSubmit = (e) => {
        setLoading(true);
        e.preventDefault();
        const newErrors = {};
        const isOnlyLetters = (name) => name.trim().length > 0 && /^[a-zA-ZàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlKmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ\s]+$/.test(name);
        const isValidEmail = (email) => email.trim().length > 0 && /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email);
        const isValidPhone = (phone) => phone.trim().length > 0 && /^\d{10,11}$/.test(phone);
        if (!formData.firstName) {
            newErrors.firstName = 'Vui lòng nhập họ.';
        } else if (!isOnlyLetters(formData.firstName)) {
            newErrors.firstName = 'Họ chỉ được phép chứa chữ cái.';
        }

        if (!formData.lastName) {
            newErrors.lastName = 'Vui lòng nhập tên.';
        } else if (!isOnlyLetters(formData.lastName)) {
            newErrors.lastName = 'Tên chỉ được phép chứa chữ cái.';
        }
        if (!formData.email) {
            newErrors.email = 'Vui lòng nhập email.';
        } else if (!isValidEmail(formData.email)) {
            newErrors.email = 'Email không hợp lệ.';
        }
        if (!formData.phone) {
            newErrors.phone = 'Vui lòng nhập số điện thoại.';
        } else if (!isValidPhone(formData.phone)) {
            newErrors.phone = 'Số điện thoại phải có 10-11 số.';
        }
        if (!formData.address) newErrors.address = 'Vui lòng nhập địa chỉ.';

        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
            setLoading(false);
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
                    birthOfDate: ""
                } : null,
                userId: isLoggedIn ? userId : null,
                voucherId: null,
                shipAddress: formData.address,
                status: selectedPaymentMethod === 'COD' ? "Chờ xác nhận" : "Đang được xử lý",
                totalAmount: parseFloat(getTotalPrice().replace(/\./g, '')),
                orderType: selectedPaymentMethod,
                paymentStatus: selectedPaymentMethod === 'COD' ? "pending" : "unpaid",
                note: formData.note,
                item: cartItems.map(item => ({
                    productId: item.productId,
                    quantity: item.quantity,
                    price: item.price,
                    salePrice: item.salePrice || item.price
                }))
            };

            if (selectedPaymentMethod === 'COD') {
                // Place order directly
                console.log(orderData);
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
            } else if (selectedPaymentMethod === 'VNPay') {

                console.log('Order data:', orderData);
                meBeSrc.createVNPay(orderData)
                    .then(response => {
                        // Clear cartItems from state and localStorage
                        setCartItems([]);
                        localStorage.removeItem('cartItems');
                        // Redirect to VNPay sandbox for payment
                        window.location.href = response.data.url;
                    })
                    .catch(error => {
                        console.error('Error creating order:', error);
                        console.error('Error response data:', error.response.data);
                    });
            }
        }
    };
    //-----End-----//


    const getTotalPrice = () => {
        return cartItems.reduce((total, item) => total + item.totalPrice, 0).toLocaleString('vi-VN');
    };


    /**
     * Fetch user data from API and set it to state
     */
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


    const [user, setUser] = useState({
        username: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        email: ""
    });
    //-----End-----//


    return loading ? (<Loading />) : (
        <div className="order-container">
            <div className="order-left">
                <form onSubmit={handleSubmit}>
                    <div className="order-left_top">
                        <h3>Me&Be</h3>
                        <p><NavLink to="/cart">Giỏ hàng</NavLink> {'>'} <NavLink>Thông tin giao hàng</NavLink></p>
                    </div>
                    <div className='order-left_body'>
                        <h4>Thông tin giao hàng</h4>
                        {isLoggedIn ? true : (
                            <p>Bạn đã có tài khoản? <Link to="/signin">Đăng nhập</Link></p>
                        )}
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
                            {errors.address && <p className="error-text" style={{ marginLeft: "20px" }}>{errors.address}</p>}
                        </div>
                    </div>

                    <h4 className='payment'>Phương thức thanh toán</h4>
                    <div className={`payment-method ${selectedPaymentMethod === 'COD' ? 'selected' : ''}`} onClick={() => setSelectedPaymentMethod('COD')}>
                        <input type="radio" name="paymentMethod" id="cod" checked={selectedPaymentMethod === 'COD'} readOnly />
                        <label htmlFor="cod">
                            <img src="https://hstatic.net/0/0/global/design/seller/image/payment/cod.svg?v=6" alt="COD" />
                            Thanh toán khi giao hàng (COD)
                        </label>
                    </div>
                    <div className={`payment-method ${selectedPaymentMethod === 'VNPay' ? 'selected' : ''}`} onClick={() => setSelectedPaymentMethod('VNPay')}>
                        <input type="radio" name="paymentMethod" id="vnpay" checked={selectedPaymentMethod === 'VNPay'} readOnly />
                        <label htmlFor="vnpay">
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTp1v7T287-ikP1m7dEUbs2n1SbbLEqkMd1ZA&s" alt="VNPay" />
                            Thanh toán qua VNPay
                        </label>
                    </div>

                    <div className='order-navigate'>
                        <NavLink to="/cart">Giỏ hàng</NavLink>
                        <button type='submit'>Hoàn tất thanh toán</button>
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

                    <div className="order-total">
                        <span>Tổng cộng: </span><span>{getTotalPrice()}₫</span>
                    </div>
                </div>
            </div>
        </div>
    );
}
