import React, { useState, useEffect } from 'react';
import { NavLink, useParams, useNavigate } from 'react-router-dom';
import { meBeSrc } from '../../service/meBeSrc';
import { Modal } from 'antd';
import {jwtDecode} from 'jwt-decode';
import "bootstrap/dist/css/bootstrap.min.css";
import "./ProductDetail.css";
import ImageMagnifier from '../../components/imageMagnifier/imageMagnifier';
import OutOfStock from '../../components/outOfStock/OutOfStock';

export default function DetailPage() {
    const [product, setProduct] = useState({});
    const [quantity, setQuantity] = useState(1);
    const [modalMessage, setModalMessage] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    const { productId } = useParams();

    const discount = product.price && product.salePrice ? ((1 - (product.salePrice / product.price)) * 100).toFixed(2) : 0;

    useEffect(() => {
        meBeSrc.getProductById(productId)
            .then((res) => {
                setProduct(res.data);
                console.log(res.data);
            }).catch((err) => {
                console.log(err);
            });
    }, [productId]);

    useEffect(() => {
        const token = localStorage.getItem('USER_INFO');
        if (token) {
            const decoded = jwtDecode(token);
            const username = decoded.sub;

            meBeSrc.getUserByUserName(username)
                .then((res) => {
                    const userData = {
                        ...res.data,
                    };
                    setUser(userData);
                })
                .catch((err) => {
                    console.log("Error fetching user", err);
                });
        }
    }, []);

    const showModalnotify = (message) => {
        setModalMessage(message);
        setIsModalVisible(true);
    };

    const handleAddToCart = () => {
        if (product.status === 'Hết hàng' || product.quantity === 0) {
            setShowModal(true);
            setTimeout(() => {
                setShowModal(false);
            }, 3000);
            return;
        }

        const item = {
            productId: product.productId,
            subCateId: product.subCateId,
            salePrice: product.salePrice,
            images: product.images,
            name: product.name,
            categoryId: product.categoryId,
            quantity,
            max: product.quantity,
            price: product.price,
            totalPrice: quantity * (product.salePrice || product.price)
        };

        const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
        const existingItemIndex = cartItems.findIndex(cartItem => cartItem.productId === item.productId);

        if (existingItemIndex > -1) {
            const existingItem = cartItems[existingItemIndex];
            const newQuantity = existingItem.quantity + item.quantity;
            if (newQuantity > existingItem.max) {
                showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Số lượng đã đạt tối đa</h1></div>);
                existingItem.quantity = existingItem.max;
                existingItem.totalPrice = existingItem.price * existingItem.max;
            } else {
                existingItem.quantity = newQuantity;
                existingItem.totalPrice += item.totalPrice;
                showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Cập nhật thành công</h1></div>);
            }
        } else {
            if (item.quantity > item.max) {
                item.quantity = item.max;
                item.totalPrice = item.price * item.max;
                showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Số lượng đã đạt tối đa</h1></div>);
            } else {
                showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Thêm sản phẩm thành công</h1></div>);
            }
            cartItems.push(item);
        }

        localStorage.setItem('cartItems', JSON.stringify(cartItems));
    };

    const handleAddToWishlist = async () => {
        if (!user) {
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Vui lòng đăng nhập để thêm sản phẩm vào danh sách đặt trước</h1></div>);
            return;
        }

        const item = {
            productId: product.productId,
            subCateId: product.subCateId,
            salePrice: product.salePrice,
            images: product.images,
            name: product.name,
            categoryId: product.categoryId,
            quantity: 1,
            max: product.quantity,
            price: product.price,
            totalPrice: product.salePrice || product.price
        };

        const wishlistItems = JSON.parse(localStorage.getItem('wishlistItems')) || [];
        const existingItemIndex = wishlistItems.findIndex(wishlistItem => wishlistItem.productId === item.productId);

        if (existingItemIndex > -1) {
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Sản phẩm đã có trong danh sách đặt trước</h1></div>);
        } else {
            wishlistItems.push(item);
            localStorage.setItem('wishlistItems', JSON.stringify(wishlistItems));
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Thêm vào danh sách đặt trước thành công</h1></div>);

            try {
                await meBeSrc.createWishlist({
                    userId: user.id,
                    productId: item.productId,
                    quantity: item.quantity,
                    totalAmount: item.totalPrice
                });
            } catch (error) {
                console.error('Error adding to wishlist:', error);
            }

            window.dispatchEvent(new Event('storage'));
        }
    };

    const handleQuantityChange = (e) => {
        const value = parseInt(e.target.value);
        if (value > product.quantity) {
            setQuantity(product.quantity);
        } else if (value < 1) {
            setQuantity(1);
        } else {
            setQuantity(value);
        }
    };

    return (
        <div className='productdetail-container'>
            <section className="product-details container">
                <nav aria-label="product-details-breadcrumb">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <NavLink to="/">
                                Trang chủ
                            </NavLink>
                        </li>
                        {product.category && (
                            <li className="breadcrumb-item">
                                <NavLink to={`/category/${product.category.slug}`}>
                                    {product.category.name}
                                </NavLink>
                            </li>
                        )}
                        {product.subCategory && (
                            <li className="breadcrumb-item">
                                <NavLink to={`/subcategory/${product.subCategory.slug}`}>
                                    {product.subCategory.name}
                                </NavLink>
                            </li>
                        )}
                        <li className="breadcrumb-item active" aria-current="page">{product.name}</li>
                    </ol>
                </nav>
                <div className="row">
                    <div className="col-md-6">
                        <div id="productCarousel" className="carousel slide" data-ride="carousel">
                            <div className="carousel-inner">
                                <div className="carousel-item active">
                                    <ImageMagnifier src={product.images} className="d-block w-100" alt={product.name}></ImageMagnifier>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <h3>{product.name}</h3>
                        <p className="text-danger">{product.status}</p>
                        <p><strong>Mã sản phẩm:</strong> {product.slug}</p>
                        <div className='price'>
                            <span className={product.salePrice > 0 ? "sale-price" : "normal-price"}>
                                {product.salePrice > 0
                                    ? product.salePrice.toLocaleString('vi-VN')
                                    : product.price ? product.price.toLocaleString('vi-VN') : '0'}₫
                            </span>
                            {product.salePrice > 0 && (
                                <span className="price-line_through">{product.price ? product.price.toLocaleString('vi-VN') : '0'}₫</span>
                            )}
                            {product.salePrice > 0 && (
                                <span className='sale-percent'>
                                    <span className={discount < 100 && discount > 0 ? "discount" : "not-discount"}>{discount}%</span>
                                </span>
                            )}
                        </div>
                        <p>Số lượt bán: {product.totalSold}</p>
                        <div className="product-quantity mb-3">
                            {
                            product.status === 'Hết hàng' ? (
                                <>
                                    <strong>Số lượng:</strong>
                                        <input
                                            type="number"
                                            value={quantity}
                                            min="1"
                                            max={product.quantity}
                                            onChange={handleQuantityChange}
                                            className="form-control d-inline-block ml-2"
                                        />
                                    <strong  className="ml-5">{product.quantity} sản phẩm có sẵn</strong>
                                    <button className="btn btn-secondary mt-5" onClick={handleAddToWishlist}>Thêm vào danh sách đặt trước</button>
                                </>     
                            ) : product.status === 'Không còn bán' ? (
                                null
                            ) : (
                                <>
                                    <strong>Số lượng:</strong>
                                        <input
                                            type="number"
                                            value={quantity}
                                            min="1"
                                            max={product.quantity}
                                            onChange={handleQuantityChange}
                                            className="form-control d-inline-block ml-2"
                                        />
                                    <strong className="ml-5">{product.quantity} sản phẩm có sẵn</strong>
                                    <button className="btn btn-primary mt-5" onClick={handleAddToCart}>Thêm vào giỏ hàng</button>
                                </>
                                
                            )
                        }
                        </div>
                        <OutOfStock show={showModal} onHide={() => setShowModal(false)} />

                    </div>
                </div>
                <div className="product-description mt-5">
                    <h4>Mô tả</h4>
                    <p>{product.description}</p>
                </div>
                <Modal
                    title="Notification"
                    open={isModalVisible}
                    footer={null}
                    onCancel={() => setIsModalVisible(false)}
                    className="custom-modal"
                >
                    <div>{modalMessage}</div>
                </Modal>
            </section>
        </div>
    );
}
