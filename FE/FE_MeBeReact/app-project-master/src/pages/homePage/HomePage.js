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
                setProducts(res.data.slice(0, 8));
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

            {/* //Gallery */}
            <section class="home-gallery">
                <div class="container">
                    <div class="home-gallery-wrap">
                        <div class="row justify-content-md-center">
                            <div class="col-12 col-md-10 col-lg-8 col-xl-7 col-xxl-6">
                                <hr class="w-50 mx-auto mb-5 mb-xl-9 border-dark-subtle" />
                                <h2 class="mb-4 display-5 text-center"> Cùng khám phá </h2>
                            </div>
                        </div>
                        <div class="gallery row">
                            <div class="col-lg-4 col-md-12 mb-4 mb-lg-0">
                                <NavLink to="/subcategory/sua-bot">
                                    <img
                                        src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062493/gallery1_lciftl.webp"
                                        class="w-100 shadow-1-strong rounded mb-4"
                                        alt=""
                                    />
                                </NavLink>
                                <NavLink to="/subcategory/sua-pha-san">
                                    <img
                                        src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062528/gallery2_hefnix.jpg"
                                        class="w-100 shadow-1-strong rounded mb-4"
                                        alt=""
                                    />
                                </NavLink>
                            </div>

                            <div class="col-lg-4 mb-4 mb-lg-0">
                                <NavLink to="/subcategory/binh-sua">
                                    <img
                                        src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062561/gallery3_mrl4sx.jpg"
                                        class="w-100 shadow-1-strong rounded mb-4"
                                        alt=""
                                    />
                                </NavLink>

                                <NavLink to="/subcategory/bim-ta">
                                    <img
                                        src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062754/gallery4_vcx56l.webp"
                                        class="w-100 shadow-1-strong rounded mb-4"
                                        alt=""
                                    />
                                </NavLink>

                            </div>

                            <div class="col-lg-4 mb-4 mb-lg-0">
                                <NavLink to="/subcategory/bim-nguoi-lon">
                                    <img
                                        src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062754/gallery5_x2xfxn.webp"
                                        class="w-100 shadow-1-strong rounded mb-4"
                                        alt=""
                                    />
                                </NavLink>
                                <NavLink to="/subcategory/bo">
                                    <img
                                        src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062755/gallery6_zftqhs.webp"
                                        class="w-100 shadow-1-strong rounded mb-4"
                                        alt=""
                                    />
                                </NavLink>
                            </div>
                        </div>
                    </div>
                </div>
            </section>


            {/* Product lastest */}

            <div class="pb-3 pb-md-5 pb-xl-8">
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
                        {filterProduct.map((product) => (
                            <div class="col-12 col-md-6 col-lg-4 col-xxl-3">
                                <NavLink to={`/product/${product.productId}`}>
                                    <div class="card text-center border-dark overflow-hidden h-100">
                                        <div class="card-body p-5 position-relative d-flex flex-column justify-content-between">
                                            <figure class="m-0 p-0">
                                                <img class="img-fluid" loading="lazy" src={`${product.images}`} alt={product.name} />
                                                <figcaption class="mb-0 mt-4 p-0">
                                                    <h4 class="mb-2">{product.name}</h4>
                                                    <p class="d-flex justify-content-center align-items-center gap-2 mb-0">
                                                        {product.salePrice ? (
                                                            <span class="text-primary">{product.salePrice.toLocaleString('vi-VN')}₫</span>
                                                        ) : (
                                                            <span class="text-primary">{product.price.toLocaleString('vi-VN')}₫</span>
                                                        )}
                                                    </p>

                                                </figcaption>
                                            </figure>
                                            <span onClick={(e) => handleClickCart(e, product)} class="btn btn-accent mt-4 d-flex align-items-center justify-content-center gap-2">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-plus" viewBox="0 0 16 16">
                                                    <path d="M9 5.5a.5.5 0 0 0-1 0V7H6.5a.5.5 0 0 0 0 1H8v1.5a.5.5 0 0 0 1 0V8h1.5a.5.5 0 0 0 0-1H9z" />
                                                    <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1zm3.915 10L3.102 4h10.796l-1.313 7zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0m7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0" />
                                                </svg>
                                                <span>Thêm vào giỏ hàng</span>
                                            </span>
                                            <div class="position-absolute top-0 end-0 m-2 fs-5">
                                                <span class="badge text-bg-primary">Mới</span>
                                            </div>
                                        </div>
                                    </div>
                                </NavLink>
                            </div>
                        ))}
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
