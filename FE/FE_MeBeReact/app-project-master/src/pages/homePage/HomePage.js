import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './HomePage.css'

export default function HomePage() {

    const [isActive, setIsActive] = useState('');

    const handleClick = (tab) => {
        setIsActive(tab);
    }

    const [prevButton, setPrevButton] = useState(false);
    const [nextButton, setNextButton] = useState(false);

    return (
        <div className='homepage'>
            <section className='featured-products container text-center py-5 outstanding-product'>
                <h2>SẢN PHẨM CHO BÉ MỚI VỀ</h2>
                <p>Nếu bạn còn băn khoăn khi lựa chọn sữa cho bé, hãy để MeBe giúp bạn nhé!</p>

                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="product-item">
                                    <img src="images/ao.pnj.webp" alt="Sản phẩm 1" class="img-fluid" />
                                    <h3>Sản phẩm 1</h3>
                                    <p>Giá: 500,000 VND</p>
                                    <a href="#" class="btn btn-outline-dark">Chi tiết</a>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product-item">
                                    <img src="images/ao.pnj.webp" alt="Sản phẩm 1" class="img-fluid" />
                                    <h3>Sản phẩm 1</h3>
                                    <p>Giá: 500,000 VND</p>
                                    <a href="#" class="btn btn-outline-dark">Chi tiết</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='carousel-control-prev'>
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </div>
                <div className='carousel-control-next'>
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </div>
            </section>
        </div>
    )
}
