import React, { useState, useEffect } from 'react';
import { NavLink, useParams } from 'react-router-dom';
import { meBeSrc } from '../../service/meBeSrc';
import { Modal } from 'antd';
import "bootstrap/dist/css/bootstrap.min.css";
import "./ProductDetail.css";

export default function DetailPage() {
    const [product, setProduct] = useState({});
    const [quantity, setQuantity] = useState(1);
    const [modalMessage, setModalMessage] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);

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
        const item = {
            productId: product.productId,
            subCateId: product.subCateId,
            salePrice: product.salePrice,
            images: product.images,
            name: product.name,
            categoryId: product.categoryId,
            quantity,
            price: product.price,
            totalPrice: quantity * (product.salePrice || product.price)
        };

        // Get existing cart items from local storage
        const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];

        // Check if the item is already in the cart
        const existingItemIndex = cartItems.findIndex(cartItem => cartItem.productId === item.productId);

        if (existingItemIndex > -1) {
            // Update the quantity and total price if the item exists
            cartItems[existingItemIndex].quantity += item.quantity;
            cartItems[existingItemIndex].totalPrice += item.totalPrice;
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Product quantity updated successfully!</h1></div>);
        } else {
            // Add new item to the cart
            cartItems.push(item);
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Product added successfully!</h1></div>);
        }

        // Save updated cart items to local storage
        localStorage.setItem('cartItems', JSON.stringify(cartItems));
    };

    return (
        <div className='productdetail-container'>
            <section className="product-details container py-5 mt-5">
                <nav aria-label="breadcrumb">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <NavLink to="/">
                                Trang chủ
                            </NavLink>
                        </li>
                        <li className="breadcrumb-item"><a href="#">Bodysuit cộc tay</a></li>
                        <li className="breadcrumb-item active" aria-current="page">Set 2 bodysuit xanh phối be</li>
                    </ol>
                </nav>
                <div className="row">
                    <div className="col-md-6">
                        {/* Carousel for product images */}
                        <div id="productCarousel" className="carousel slide" data-ride="carousel">
                            <div className="carousel-inner">
                                <div className="carousel-item active">
                                    <img src={product.images} className="d-block w-100" alt={product.name} />
                                </div>
                                <div className="carousel-item">
                                    <img src="/images/ao.pnj.webp" className="d-block w-100" alt={product.name} />
                                </div>
                            </div>
                            <a className="carousel-control-prev" href="#productCarousel" role="button" data-slide="prev">
                                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span className="sr-only">Previous</span>
                            </a>
                            <a className="carousel-control-next" href="#productCarousel" role="button" data-slide="next">
                                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                                <span className="sr-only">Next</span>
                            </a>
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
                                onChange={(e) => setQuantity(parseInt(e.target.value))}
                                className="form-control d-inline-block w-auto ml-2"
                            />
                        </div>
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
