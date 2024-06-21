import React, { useState, useEffect } from 'react';
import { NavLink, useParams } from 'react-router-dom';
import { meBeSrc } from '../../service/meBeSrc';
import "bootstrap/dist/css/bootstrap.min.css";
import "./ProductDetail.css";

export default function DetailPage() {

    const [product, setProduct] = useState({})
    const [relatedProducts, setRelatedProducts] = useState([])

    const discount = ((1 - (product.salePrice / product.price)) * 100).toFixed(2);
    const { productId } = useParams();

    // Get product detail by productId
    useEffect(() => {
        meBeSrc.getProductDetail(productId)
            .then((res) => {
                setProduct(res.data)
                console.log(res.data);
            }).catch((err) => {
                console.log(err);
            });
    }, [productId])

    // useEffect(() => {
    //     if (product.subCategory && product.subCategory.category) {
    //         meBeSrc.getListCategory(product.subCategory.category)
    //             .then((res) => {
    //                 const filterProducts = res.data.filter((item) => item._id !== productId.slice(0, 4))
    //                 setRelatedProducts(filterProducts)
    //                 console.log(filterProducts);
    //             })
    //             .catch((err) => {
    //                 console.log(err);
    //             })
    //     }

    // }, [product])

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
                                    <img src={product.images} className="d-block w-100" alt="Bodysuit Xanh" />
                                </div>
                                <div className="carousel-item">
                                    <img src="/images/ao.pnj.webp" className="d-block w-100" alt="Bodysuit Be" />
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
                        <p><strong>Mã sản phẩm:</strong> NB2524-TB2-UO1-MG-OM</p>
                        <div className='price'>
                            {product.price && <span className="sale-price">{product.price.toLocaleString('vi-VN')}₫</span>}
                            {product.salePrice && <span className="main-price">{product.salePrice.toLocaleString('vi-VN')}₫</span>}
                            {product.price && product.salePrice && (
                                <span className='sale-percent'>
                                    Giảm {discount} %
                                </span>
                            )}
                        </div>
                        <div className="product-quantity mb-3">
                            <strong>Số lượng:</strong>
                            <input type="number" value="1" min="1" className="form-control d-inline-block w-auto ml-2" />
                        </div>
                        <button className="btn btn-primary">Thêm vào giỏ hàng</button>
                    </div>
                </div>
                <div className="product-description mt-5">
                    <h4>Mô tả</h4>
                    <p>{product.description}</p>
                </div>

                {/* Đánh giá sản phẩm */}
                <div className="product-reviews mt-5">
                    <h4>Đánh giá sản phẩm</h4>
                    <div className="rating-summary mb-3">
                        <span className="rating-score">4.8/5</span>
                        <span className="rating-stars">★★★★★</span>
                    </div>
                    <ul className="nav nav-tabs" id="reviewTab" role="tablist">
                        <li className="nav-item">
                            <a className="nav-link active" id="review-tab" data-toggle="tab" href="#review" role="tab" aria-controls="review" aria-selected="true">Đánh giá</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" id="image-tab" data-toggle="tab" href="#image" role="tab" aria-controls="image" aria-selected="false">Hình ảnh</a>
                        </li>
                    </ul>
                    <div className="tab-content" id="reviewTabContent">
                        <div className="tab-pane fade show active" id="review" role="tabpanel" aria-labelledby="review-tab">
                            <div className="review-item mt-3">
                                <strong>******</strong>
                                <span className="text-muted">2-6-2024</span>
                                <div className="rating-stars">★★★★★</div>
                                <p>Chất liệu đẹp. Màu sắc đúng mô tả. Đúng với mô tả: Đúng với mô tả, dáng vừa vặn, chất liệu đẹp.</p>
                            </div>
                            <div className="review-item mt-3">
                                <strong>lnginhinh88</strong>
                                <span className="text-muted">3-5-2024</span>
                                <div className="rating-stars">★★★★★</div>
                                <p>Nous Admin: Cảm ơn Anh/Chị đã tin tưởng và lựa chọn mua hàng tại Nous. Nous hy vọng anh/chị có trải nghiệm mua sắm thú vị và sự yêu mến, ủng hộ Nous trong thời gian tới ạ. Mọi ý kiến đóng góp của anh/chị Nous sẽ tiếp thu và hoàn thiện hơn trong tương lai. Chúc Em bé và gia đình mình luôn mạnh khỏe, hạnh phúc nhé ạ ❤️❤️❤️</p>
                            </div>
                            <div className="review-item mt-3">
                                <strong>kimoanh39</strong>
                                <span className="text-muted">2-5-2024</span>
                                <div className="rating-stars">★★★★★</div>
                                <p>Nous Admin: Cảm ơn Anh/Chị đã tin tưởng và lựa chọn mua hàng tại Nous. Nous hy vọng anh/chị có trải nghiệm mua sắm thú vị và sự yêu mến, ủng hộ Nous trong thời gian tới ạ. Mọi ý kiến đóng góp của anh/chị Nous sẽ tiếp thu và hoàn thiện hơn trong tương lai. Chúc Em bé và gia đình mình luôn mạnh khỏe, hạnh phúc nhé ạ ❤️❤️❤️</p>
                            </div>
                            <button className="btn-review">Xem thêm đánh giá</button>
                        </div>
                        <div className="tab-pane fade" id="image" role="tabpanel" aria-labelledby="image-tab">
                            <p>Hình ảnh đánh giá sản phẩm.</p>
                        </div>
                    </div>
                </div>

                {/* <div className="related-products mt-5">
                    <h4>Sản phẩm liên quan</h4>
                    <div className="row">
                        {/* Repeat similar product cards for each related product */}
                {/* <div className="row">
                            {relatedProducts.map((relatedProduct) => (
                                <div className="col-md-3 product" key={relatedProduct.productId}>
                                    <div className="card justify-content-center align-items-center">
                                        <img src={relatedProduct.images} className="card-img-top" alt={relatedProduct.name} />
                                        <div className="card-body">
                                            <h5 className="card-title">{relatedProduct.name}</h5>
                                            <p className="card-text">{relatedProduct.price.toLocaleString('vi-VN')}₫</p>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                        <div className="col-md-3 product">
                            <div className="card justify-content-center align-items-center">
                                <img src="/images/ao.pnj.webp" className="card-img-top" alt="Related Product 2" />
                                <div className="card-body">
                                    <h5 className="card-title">Bodysuit 2</h5>
                                    <p className="card-text">250,000đ</p>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-3 product">
                            <div className="card justify-content-center align-items-center">
                                <img src="/images/ao.pnj.webp" className="card-img-top" alt="Related Product 3" />
                                <div className="card-body">
                                    <h5 className="card-title">Bodysuit 3</h5>
                                    <p className="card-text">220,000đ</p>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-3 product">
                            <div className="card justify-content-center align-items-center">
                                <img src="/images/ao.pnj.webp" className="card-img-top" alt="Related Product 4" />
                                <div className="card-body">
                                    <h5 className="card-title">Bodysuit 4</h5>
                                    <p className="card-text">240,000đ</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> */}
                {/* <div className="viewed-products mt-5">
                    <h4>Sản phẩm đã xem</h4>
                    <div className="row">
                        {/* Repeat similar product cards for each viewed product */}
                {/* <div className="col-md-3 product">
                            <div className="card">
                                <img src="/images/ao.pnj.webp" className="card-img-top" alt="Viewed Product 1" />
                                <div className="card-body">
                                    <h5 className="card-title">Bodysuit đã xem 1</h5>
                                    <p className="card-text">255,000đ</p>
                                </div>
                            </div>
                        </div> */}
                {/* Thêm các sản phẩm đã xem khác tương tự */}
                {/* </div>
                </div > */}
            </section >
        </div >
    )
}
