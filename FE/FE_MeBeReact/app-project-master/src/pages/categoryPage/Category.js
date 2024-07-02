import React, { useEffect, useState } from 'react';
import './Category.css';
import { meBeSrc } from '../../service/meBeSrc';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal } from 'antd';
import OutOfStock from '../../components/outOfStock/OutOfStock';

export default function Category() {
    const [category, setCategory] = useState({}); // State để lưu thông tin category hiện tại
    const { slug } = useParams(); // Lấy slug từ URL params
    const navigate = useNavigate(); // Hook để điều hướng
    const location = useLocation(); // Hook để lấy thông tin về vị trí hiện tại
    const parentCategory = location.state?.parentCategory; // Lấy parentCategory từ state nếu có

    // Gọi API để lấy thông tin category theo slug
    useEffect(() => {
        meBeSrc.getCategoryBySlug(slug)
            .then((res) => {
                setCategory(res.data);
            })
            .catch((err) => {
                console.log("Error fetching category", err);
            });
    }, [slug]);

    // Gọi API để lấy danh sách subcategories theo tên category
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

    // Gọi API để lấy danh sách products
    const [products, setProducts] = useState([]);
    useEffect(() => {
        meBeSrc.getProduct()
            .then((res) => {
                setProducts(res.data);
            }).catch((err) => {
                console.log('Error fetching products', err);
            });
    }, [category.name]);

    // State và các hàm liên quan đến hiển thị modal
    const [modalMessage, setModalMessage] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [showModal, setShowModal] = useState(false);

    // Hàm xử lý khi nhấn nút thêm vào giỏ hàng
    const handleClickCart = (e, product) => {
        e.preventDefault();
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
            images: product.images,
            salePrice: product.salePrice,
            name: product.name,
            categoryId: product.categoryId,
            quantity: 1,
            price: product.price,
            totalPrice: product.price,
        };

        // Lấy danh sách sản phẩm từ giỏ hàng trong localStorage
        const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
        const existingItemIndex = cartItems.findIndex(cartItem => cartItem.productId === item.productId);

        // Cập nhật số lượng và tổng giá nếu sản phẩm đã tồn tại trong giỏ hàng
        if (existingItemIndex > -1) {
            cartItems[existingItemIndex].quantity += item.quantity;
            cartItems[existingItemIndex].totalPrice += item.totalPrice;
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Product quantity updated successfully!</h1></div>);
        } else {
            // Thêm sản phẩm mới vào giỏ hàng
            cartItems.push(item);
            showModalnotify(<div className='notice__content'><i className="check__icon fa-solid fa-circle-check"></i><h1>Product added successfully!</h1></div>);
        }

        // Lưu danh sách giỏ hàng vào localStorage
        localStorage.setItem('cartItems', JSON.stringify(cartItems));
    };

    // Hàm hiển thị modal thông báo
    const showModalnotify = (message) => {
        setModalMessage(message);
        setIsModalVisible(true);
    };

    // Hàm xử lý khi nhấn vào subcategory
    const handleSubCategoryClick = (subCategory) => {
        // Điều hướng đến trang subcategory và truyền thông tin parent category qua state
        navigate(`/subcategory/${subCategory.slug}`, { state: { parentCategory: category } });
    };

    return (
        <div className="category-container">
            <div className="category-header">
                <h2>Bạn cần tìm</h2>
                <div className="category-icons">
                    {subCategories.map((subCategory) => (
                        <div className="category-icon" key={subCategory.name} onClick={() => handleSubCategoryClick(subCategory)}>
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
                                            <OutOfStock show={showModal} onHide={() => setShowModal(false)} />
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
