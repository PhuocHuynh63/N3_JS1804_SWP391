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
                setProducts(res.data.slice(0, 4));
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
            <div className="category-section">
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
            </div>
        </div>
    )
}
