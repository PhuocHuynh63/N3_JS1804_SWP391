import React, { useEffect, useState } from 'react';
import './SubCategoryPage.css';
import { NavLink, useParams } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Dropdown, Space } from 'antd';
import { meBeSrc } from '../../service/meBeSrc';

export default function SubCategory() {

    const [selectedPrices, setSelectedPrices] = useState([]);

    const handleCheckboxChange = (event) => {
        const value = event.target.value;
        setSelectedPrices(prevState =>
            prevState.includes(value)
                ? prevState.filter(price => price !== value)
                : [...prevState, value]
        );
    };

    const handleDivClick = (id) => {
        const checkbox = document.getElementById(id);
        if (checkbox) {
            checkbox.checked = !checkbox.checked;
            handleCheckboxChange({ target: checkbox });
        }
    };

    //Call API to get products
    const [products, setProducts] = useState([])
    const { subCategoryId } = useParams()

    useEffect(() => {
        meBeSrc.getProductBySubCategory(subCategoryId)
            .then(res => {
                setProducts(res.data)
                console.log(res.data);
            }).catch(err => {
                console.log(err)
            })
    }, [subCategoryId])
    //-----End-----

    return (
        <div className='subcategory'>
            <div className="breadcrumbs">
                <NavLink to="/">
                    Trang chủ
                </NavLink>
            </div>
            <div className="filters">
                <button>Bộ lọc <i className="fa-solid fa-filter"></i></button>
                <Dropdown
                    menu={{
                        items: [{
                            label: (
                                <div className='filter-price under200' onClick={() => handleDivClick('under200')}>
                                    <input type="checkbox" clas="under200" className='filter-input' name="under200" value="under200" onChange={handleCheckboxChange} />
                                    <label htmlFor="under200">Dưới 200.000₫</label>
                                </div>
                            ),
                            key: "1",
                        },
                        {
                            label: (
                                <div className='filter-price 200to300' onClick={() => handleDivClick('200to300')}>
                                    <input type="checkbox" id="200to300" className='filter-input' name="200to300" value="200to300" onChange={handleCheckboxChange} />
                                    <label htmlFor="200to300">200.000₫ - 300.000₫</label>
                                </div>
                            ),
                            key: "2",
                        },
                        {
                            label: (
                                <div className='filter-price 300to500' onClick={() => handleDivClick('300to500')}>
                                    <input type="checkbox" id="300to500" className='filter-input' name="300to500" value="300to500" onChange={handleCheckboxChange} />
                                    <label htmlFor="300to500">300.000₫ - 500.000₫</label>
                                </div>
                            ),
                            key: "3",
                        },
                        {
                            label: (
                                <div className='filter-price 500to1000' onClick={() => handleDivClick('500to1000')}>
                                    <input type="checkbox" id="500to1000" className='filter-input' name="500to1000" value="500to1000" onChange={handleCheckboxChange} />
                                    <label htmlFor="500to1000">500.000₫ - 1.000.000₫</label>
                                </div>
                            ),
                            key: "4",
                        },
                        {
                            label: (
                                <div className='filter-price upper1000' onClick={() => handleDivClick('upper1000')}>
                                    <input type="checkbox" id="upper1000" className='filter-input' name="upper1000" value="upper1000" onChange={handleCheckboxChange} />
                                    <label htmlFor="upper1000">Trên 1.000.000₫</label>
                                </div>
                            ),
                            key: "5",
                        },
                        ],
                    }}
                    trigger={['click']}
                    overlayClassName='price-dropdown'
                >
                    <a onClick={(e) => e.preventDefault()} className='filter-link'>
                        <Space>
                            <span>Giá bán</span><i className="fa-solid fa-chevron-down filter"></i>
                        </Space>
                    </a>
                </Dropdown>
                <Dropdown
                    menu={{
                        items: [{
                            label: <span className='custom-span sort'>Giá: Tăng dần</span>,
                            key: "1",
                        },
                        {
                            label: <span className='custom-span sort'>Giá: Giảm dần</span>,
                            key: "2",
                        },
                        {
                            label: <span className='custom-span sort'>Tên: Tăng dần</span>,
                            key: "3",
                        },
                        {
                            label: <span className='custom-span sort'>Giá: Giảm dần</span>,
                            key: "4",
                        },
                        {
                            label: <span className='custom-span sort'>Mới nhất</span>,
                            key: "5",
                        },
                        ],
                    }}
                    trigger={['click']}
                    overlayClassName='sort-dropdown'
                >
                    <a onClick={(e) => e.preventDefault()} className='sort-link'>
                        <Space>
                            Sắp xếp theo
                        </Space>
                    </a>
                </Dropdown>
            </div>

            <div className="products">
                {products.map((product) => {
                    const discount = ((1 - (product.salePrice / product.price)) * 100).toFixed(2);
                    return (
                        <a href={`/product/${product.productId}`}>
                            <div className="product-item" key={product.product_id}>
                                <img src={product.images} alt={product.name} />
                                <span className={discount < 100 ? "discount" : "not-discount"}>{discount}%</span>
                                <img id='cart' src='https://file.hstatic.net/200000692427/file/asset_2_901a91642639466aa75b2019a34ccebd.svg' />
                                <p>{product.name}</p>
                                <span className={product.salePrice > 0 ? "sale-price" : "normal-price"}>{product.salePrice > 0 ? product.salePrice.toLocaleString('vi-VN') : product.price.toLocaleString('vi-VN')}₫</span>
                                {product.salePrice > 0 && (
                                    <span className="price-line_through">{product.price.toLocaleString('vi-VN')}₫</span>
                                )}
                            </div>
                        </a>
                    );
                })}
            </div>
        </div>
    )
}