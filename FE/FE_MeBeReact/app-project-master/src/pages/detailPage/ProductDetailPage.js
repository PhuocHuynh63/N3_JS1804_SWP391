import React, { useState, useEffect } from 'react';
import { NavLink, useParams } from 'react-router-dom';
import { meBeSrc } from '../../service/meBeSrc';
import { Modal } from 'antd';
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

    const { productId } = useParams();

    const discount = product.price && product.salePrice ? ((1 - (product.salePrice / product.price)) * 100).toFixed(2) : 0;

    // Get product detail by productId
    useEffect(() => {
        meBeSrc.getProductDetail(productId)
            .then((res) => {
                setProduct(res.data);
                console.log(res.data);
            }).catch((err) => {
                console.log(err);
            });
    }, [productId]);

    const showModalnotify = (message) => {
        setModalMessage(message);
        setIsModalVisible(true);
    };

    const handleAddToCart = () => {
        // Check if the product is out of stock or if quantity exceeds available stock
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

        // Get existing cart items from local storage
        const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];

        // Check if the item is already in the cart
        const existingItemIndex = cartItems.findIndex(cartItem => cartItem.productId === item.productId);

        if (existingItemIndex > -1) {
            // Update the quantity and total price if the item exists, ensuring it does not exceed the max quantity
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
            // Add new item to the cart, ensuring it does not exceed the max quantity
            if (item.quantity > item.max) {
                item.quantity = item.max;
                item.totalPrice = item.price * item.max;
                showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Số lượng đã đạt tối đa</h1></div>);
            } else {
                showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Thêm sản phẩm thành công</h1></div>);
            }
            cartItems.push(item);
        }

        // Save updated cart items to local storage
        localStorage.setItem('cartItems', JSON.stringify(cartItems));
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
                        {/* Carousel for product images */}
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
                                    <span className={discount < 100 ? "discount" : "not-discount"}>{discount}%</span>
                                </span>
                            )}
                        </div>
                        <div className="product-quantity mb-3">
                            <strong>Số lượng:</strong>
                            <input
                                type="number"
                                value={quantity}
                                min="1"
                                max={product.quantity}
                                onChange={handleQuantityChange}
                                className="form-control d-inline-block ml-2"
                            />
                            <span>{product.quantity} sản phẩm có sẵn</span>
                        </div>
                        <OutOfStock show={showModal} onHide={() => setShowModal(false)} />
                        <button className="btn btn-primary" onClick={handleAddToCart}>Thêm vào giỏ hàng</button>
                    </div>
                </div>
                <div className="product-description mt-5">
                    <h4>Mô tả</h4>
                    <p>{product.description}</p>
                </div>
                <Modal
                    title="Notification"
                    visible={isModalVisible}
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
