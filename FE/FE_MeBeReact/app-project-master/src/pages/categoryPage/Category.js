import React, { useEffect, useState } from 'react';
import './Category.css';
import { meBeSrc } from '../../service/meBeSrc';
import { useParams } from 'react-router-dom';
import { Modal } from 'antd';

export default function Category() {
    const [category, setCategory] = useState({});
    const { slug } = useParams();

    useEffect(() => {
        meBeSrc.getCategoryBySlug(slug)
            .then((res) => {
                setCategory(res.data);
            })
            .catch((err) => {
                console.log("Error fetching category", err);
            });
    }, [slug]);

    const [subCategories, setSubCategories] = useState([]);

    useEffect(() => {
        meBeSrc.getListSubCategory()
            .then((res) => {
                const filteredSubCategories = res.data.filter((subCategory) => subCategory.category_parent === category.name);
                setSubCategories(filteredSubCategories);
            }).catch((err) => {
                console.log('Error fetching subcategories', err);
            });
    }, [category.name]);

    const [products, setProducts] = useState([]);

    useEffect(() => {
        meBeSrc.getProduct()
            .then((res) => {
                setProducts(res.data);
            }).catch((err) => {
                console.log('Error fetching products', err);
            });
    }, [category.name]);

    const [modalMessage, setModalMessage] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);

    const handleClickCart = (e, product) => {
        e.preventDefault();
        const item = {
            productId: product.productId,
            subCateId: product.subCateId,
            images: product.images,
            salePrice: product.salePrice,
            name: product.name,
            categoryId: product.categoryId,
            quantity: 1,
            price: product.price,
            totalPrice: product.price,
        };

        const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
        const existingItemIndex = cartItems.findIndex(cartItem => cartItem.productId === item.productId);

        if (existingItemIndex > -1) {
            cartItems[existingItemIndex].quantity += item.quantity;
            cartItems[existingItemIndex].totalPrice += item.totalPrice;
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Product quantity updated successfully!</h1></div>);
        } else {
            cartItems.push(item);
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Product added successfully!</h1></div>);
        }

        localStorage.setItem('cartItems', JSON.stringify(cartItems));
    };

    const showModalnotify = (message) => {
        setModalMessage(message);
        setIsModalVisible(true);
    };

    return (
        <div className="category-container">
            <div className="category-header">
                <h2>Bạn cần tìm</h2>
                <div className="category-icons">
                    {subCategories.map((subCategory) => (
                        <div className="category-icon" key={subCategory.name}>
                            <a href={`/subcategory/${subCategory.slug}`}>
                                <img src={subCategory.image} alt={subCategory.name} />
                                <p>{subCategory.name}</p>
                            </a>
                        </div>
                    ))}
                </div>
            </div>

            {subCategories.map((subCategory) => {
                const filteredProducts = products.filter(product => product.subCategory.name === subCategory.name).slice(0, 3);
                return (
                    <div className="category-section" key={subCategory.name}>
                        <div className='title'>
                            <h3>{subCategory.name}</h3>
                            <a href={`/subcategory/${subCategory.slug}`}>Xem thêm →</a>
                        </div>
                        <div className="products">
                            <a href={`/subcategory/${subCategory.slug}`}>
                                <img id='banner' src={subCategory.image2} alt={subCategory.name} />
                            </a>
                            {filteredProducts.map((product) => {
                                const discount = ((1 - (product.salePrice / product.price)) * 100).toFixed(0);
                                return (
                                    <div className="product" key={product.id}>
                                        <a href={`/product/${product.productId}`}>
                                            <img src={`${product.images}`} alt={product.name} />
                                            <span className={discount < 100 ? "discount" : "not-discount"}>{discount}%</span>
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
                );
            })}

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
    );
}
