import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './HomePage.css'
import { NavLink } from 'react-router-dom';
import { meBeSrc } from '../../service/meBeSrc';
import { Carousel } from 'react-bootstrap';
import { Modal } from 'antd';

export default function HomePage() {

    const [modalMessage, setModalMessage] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);
    //GET - Product lastest
    const [products, setProducts] = useState([]);
    useEffect(() => {
        meBeSrc.getProductLastest()
            .then((res) => {
                setProducts(res.data.slice(0, 20));
            }).catch((err) => {
                console.log('Error fetching products', err);
            });
    }, []);

    //CART

    const showModalnotify = (message) => {
        setModalMessage(message);
        setIsModalVisible(true);
    };

    const handleClickCart = (e, product) => {
        e.preventDefault();

        // Prevent adding to cart if product is out of stock
        if (product.status === 'Hết hàng' || product.quantity === 0) {
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Sản phẩm đã hết hàng</h1></div>);
            return;
        }

        const item = {
            productId: product.productId,
            subCateId: product.subCateId,
            images: product.images,
            salePrice: product.salePrice,
            name: product.name,
            categoryId: product.categoryId,
            quantity: 1,
            max: product.quantity,
            price: product.salePrice || product.price,
            totalPrice: product.salePrice || product.price,
        };

        // Get existing cart items from local storage
        const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];

        // Check if the item is already in the cart
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
                showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Cập nhật số lượng thành công</h1></div>);
            }
        } else {
            if (item.quantity > item.max) {
                item.quantity = item.max;
                item.totalPrice = item.price * item.max;
                showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Số lượng đã đạt tối đa</h1></div>);
            } else {
                cartItems.push(item);
                showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Thêm sản phẩm thành công</h1></div>);
            }
        }

        // Save updated cart items to local storage
        localStorage.setItem('cartItems', JSON.stringify(cartItems));
    };

    const filterProduct = products.filter((product) => product.status !== 'Không còn bán');

    return (
        <div className='homepage'>

            {/* //Banner */}
            <Carousel interval={3000}>
                <Carousel.Item>
                    <NavLink to="/category/sua-binh-sua">
                        <img
                            className="d-block w-100"
                            src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062472/gat_nang_banner_web_1440x450_b9734db3f6d94a39a5cf23c52afd81e9.pnj_odbjws.webp"
                            alt="First slide"
                        />
                    </NavLink>

                </Carousel.Item>
                <Carousel.Item>
                    <NavLink to="/category/bim-ta-ve-sinh">
                        <img
                            className="d-block w-100"
                            src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062471/cover___banner_web_website_1440x450_29768825b3d84005a3c489e63103dc3d_uknc88.webp"
                            alt="Second slide"
                        />
                    </NavLink>
                </Carousel.Item>
                <Carousel.Item>
                    <NavLink to="/category/do-cho-me">
                        <img
                            className="d-block w-100"
                            src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062468/banner3_qudfkk.webp"
                            alt="Third slide"
                        />
                    </NavLink>
                </Carousel.Item>
            </Carousel>

            {/* Product lastest */}

            <div class="pb-3 mt-5 pb-md-5 pb-xl-8">
                <div class="container">
                    <div class="row justify-content-md-center">
                        <div class="col-12 col-md-10 col-lg-8 col-xl-7 col-xxl-6">
                            <h2 class="mb-4 display-5 text-center">Sản phẩm mới về </h2>
                            <hr class="w-50 mx-auto mb-5 mb-xl-9 border-dark-subtle" />
                        </div>
                    </div>
                </div>

                <div class="container overflow-hidden">
                    <div class="row gy-4 gy-xxl-5">
                    {filterProduct.map((product) => {
                                const discount = ((1 - (product.salePrice / product.price)) * 100).toFixed(0);
                                return (
                                    <div className="product" key={product.id}>
                                        <a href={`/product/${product.productId}`}>
                                            <img src={`${product.images}`} alt={product.name} />
                                            <span className={discount < 100 &&(discount >0) ? "discount" : "not-discount"}>{discount}%</span>
                                            <img id='cart' src='https://file.hstatic.net/200000692427/file/asset_2_901a91642639466aa75b2019a34ccebd.svg' onClick={(e) => handleClickCart(e, product)} alt="Add to cart" />
                                            <p>{product.name}</p>
                                            <div className="product-price-container">
                                                {product.salePrice > 0 ? (
                                                    <>
                                                        <span className="sale-price">{product.salePrice.toLocaleString('vi-VN')}₫</span>
                                                        <span className="price-line_through">{product.price.toLocaleString('vi-VN')}₫</span>
                                                    </>
                                                ) : (
                                                    <span className="normal-price">{product.price.toLocaleString('vi-VN')}₫</span>
                                                )}
                                            </div>
                                        </a>
                                    </div>
                                );
                            })}
                    </div>
                </div>

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


        </div>
    )
}
