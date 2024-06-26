import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './HomePage.css'
import { useParams } from 'react-router-dom';
import { meBeSrc } from '../../service/meBeSrc';
import { Carousel } from 'react-bootstrap';

export default function HomePage() {

    //GET - Product lastest
    const [products, setProducts] = useState([]);
    useEffect(() => {
        meBeSrc.getProductLastest()
            .then((res) => {
                setProducts(res.data.slice(0, 10));
            }).catch((err) => {
                console.log('Error fetching products', err);
            });
    }, []);



    return (
        <div className='homepage'>
            
            {/* //Banner */}
            <Carousel interval={3000}>
                <Carousel.Item>
                    <img
                    className="d-block w-100"
                    src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062472/gat_nang_banner_web_1440x450_b9734db3f6d94a39a5cf23c52afd81e9.pnj_odbjws.webp"
                    alt="First slide"
                    />
                </Carousel.Item>
                <Carousel.Item>
                    <img
                    className="d-block w-100"
                    src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062471/cover___banner_web_website_1440x450_29768825b3d84005a3c489e63103dc3d_uknc88.webp"
                    alt="Second slide"
                    />
                </Carousel.Item>
                <Carousel.Item>
                    <img
                    className="d-block w-100"
                    src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062468/banner3_qudfkk.webp"
                    alt="Third slide"
                    />
                </Carousel.Item>
            </Carousel>    
            
            {/* //Gallery */}
            <section class="home-gallery">
                <div class="container">
                    <div class="home-gallery-wrap">
                        <div class="gallery-header row">
                            <h3 class="header-title">Bộ sưu tập</h3>
                        </div>
                        <div class="gallery row">
                            <div class="col-lg-4 col-md-12 mb-4 mb-lg-0">
                                <img
                                    src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062493/gallery1_lciftl.webp"
                                    class="w-100 shadow-1-strong rounded mb-4"
                                    alt=""
                                />
                                <img
                                    src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062528/gallery2_hefnix.jpg"
                                    class="w-100 shadow-1-strong rounded mb-4"
                                    alt=""
                                />
                            </div>

                            <div class="col-lg-4 mb-4 mb-lg-0">
                                <img
                                src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062561/gallery3_mrl4sx.jpg"
                                class="w-100 shadow-1-strong rounded mb-4"
                                alt=""
                                />       
                                <img
                                src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062754/gallery4_vcx56l.webp"
                                class="w-100 shadow-1-strong rounded mb-4"
                                alt=""
                                />
                            </div>

                            <div class="col-lg-4 mb-4 mb-lg-0">
                                <img
                                src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062754/gallery5_x2xfxn.webp"
                                class="w-100 shadow-1-strong rounded mb-4"
                                alt=""
                                />

                                <img
                                src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719062755/gallery6_zftqhs.webp"
                                class="w-100 shadow-1-strong rounded mb-4"
                                alt=""
                                />
                                
                            </div>
                        </div>
                    </div>
                </div>
            </section>


            {/* Product lastest */}
            
            {/* <div className="category-section">
                <div class="container">
                    <h3>Sản phẩm mới về</h3>
                    <div className="products">
                        {products.map((product) => (
                            <div className="product" key={product.id}>
                                <img src={`/images/${product.images}`} alt={product.name} />
                                <p>{product.name}</p>
                                <p>{product.price}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </div> */}

            <div class="pb-3 pb-md-5 pb-xl-8">
                <div class="container">
                <div class="row justify-content-md-center">
                    <div class="col-12 col-md-10 col-lg-8 col-xl-7 col-xxl-6">
                    <h2 class="mb-4 display-5 text-center">Sản phẩm mới về </h2>
                    <hr class="w-50 mx-auto mb-5 mb-xl-9 border-dark-subtle"/>
                    </div>
                </div>
                </div>

                <div class="container overflow-hidden">
                <div class="row gy-4 gy-xxl-5">
                    {products.map((product) => (
                        <div class="col-12 col-md-6 col-lg-4 col-xxl-3">
                        <div class="card text-center border-dark overflow-hidden">
                            <div class="card-body p-5 position-relative">
                            <figure class="m-0 p-0">
                                {/* {`/images/${product.images}`} */}
                                <img class="img-fluid" loading="lazy" src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719076828/sign_up_baw61i.jpg" alt="product.name"/>
                                <figcaption class="mb-0 mt-4 p-0">
                                <h4 class="mb-2">{product.name}</h4>
                                <p class="d-flex justify-content-center align-items-center gap-2 mb-0">
                                    <span class="text-primary">{product.price}</span>
                                </p>
                                </figcaption>
                            </figure>
                            <a href="#!" class="btn btn-accent mt-4 d-flex align-items-center justify-content-center gap-2">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-plus" viewBox="0 0 16 16">
                                <path d="M9 5.5a.5.5 0 0 0-1 0V7H6.5a.5.5 0 0 0 0 1H8v1.5a.5.5 0 0 0 1 0V8h1.5a.5.5 0 0 0 0-1H9z" />
                                <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1zm3.915 10L3.102 4h10.796l-1.313 7zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0m7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0" />
                                </svg>
                                <span>Add to cart</span>
                            </a>
                            <div class="position-absolute top-0 end-0 m-2 fs-5">
                                <span class="badge text-bg-primary">Mới</span>
                            </div>
                            </div>
                        </div>
                        </div>
                    ))}
                </div>
                </div>
            </div>


        </div>
    )
}
