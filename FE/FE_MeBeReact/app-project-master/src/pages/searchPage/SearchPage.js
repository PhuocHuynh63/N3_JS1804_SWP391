import React, { useEffect, useState } from 'react';
import './SearchPage.css';
import { NavLink, useParams } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Dropdown, Menu, Modal, Space } from 'antd';
import OutOfStock from '../../components/outOfStock/OutOfStock';
import { meBeSrc } from '../../service/meBeSrc';

export default function SearchPage() {
    const [selectedPrices, setSelectedPrices] = useState([]);
    const [products, setProducts] = useState([]);
    const [modalMessage, setModalMessage] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [sortOption, setSortOption] = useState('');
    const [showModal, setShowModal] = useState(false);

    const { name } = useParams();

    useEffect(() => {
        if (name) {
            meBeSrc.getProductBySearch(name)
                .then(res => {
                    setProducts(res.data);
                }).catch(err => {
                    console.log(err);
                });
        }
    }, [name]);

     /**
     * Filter and sort products whenever the product list, sort option, or selected prices change
     */
     useEffect(() => {
        const sortedProducts = sortProducts(products);
        const filteredAndSortedProducts = filterProducts(sortedProducts);
        setFilteredProducts(filteredAndSortedProducts);
    }, [products, sortOption, selectedPrices]);

    /**
     * Handle click on cart icon
     * @param {*} e 
     * @param {*} product 
     * @returns 
     */
    //Handle click on cart icon
    const handleClickCart = (e, product) => {
        e.preventDefault();
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

        const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
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

        localStorage.setItem('cartItems', JSON.stringify(cartItems));
    };

    const showModalnotify = (message) => {
        setModalMessage(message);
        setIsModalVisible(true);
    };
    //------End------//

    /**
     * Sort products by selected option
     * @param {*} products 
     * @returns 
     */
    //State
    const [filteredProducts, setFilteredProducts] = useState([]);

    //Sort products
    const sortProducts = (products) => {
        console.log('Sorting with option:', sortOption);
        return products.slice().sort((a, b) => {  // Use slice() to avoid mutating the original array
            const aPrice = a.salePrice || a.price;
            const bPrice = b.salePrice || b.price;

            switch (sortOption) {
                case 'priceAsc':
                    return aPrice - bPrice;
                case 'priceDesc':
                    return bPrice - aPrice;
                case 'nameAsc':
                    return a.name.localeCompare(b.name , 'vi', { sensitivity: 'base', ignorePunctuation: true });
                case 'nameDesc':
                    return b.name.localeCompare(a.name , 'vi', { sensitivity: 'base', ignorePunctuation: true });
                case 'newest':
                    return b.productId - a.productId;
                default:
                    return 0;
            }
        });
    };
    //End//

    //Filter products by price range and status
    const filterProducts = (products) => {
        const filteredByStatus = products.filter(product => product.status !== 'Không còn bán');
        if (selectedPrices.length === 0) {
            return filteredByStatus;
        }
        return filteredByStatus.filter(product => {
            const price = product.salePrice || product.price;
            return selectedPrices.some(range => {
                switch (range) {
                    case 'under200':
                        return price < 200000;
                    case '200to300':
                        return price >= 200000 && price <= 300000;
                    case '300to500':
                        return price >= 300000 && price <= 500000;
                    case '500to1000':
                        return price >= 500000 && price <= 1000000;
                    case 'upper1000':
                        return price > 1000000;
                    default:
                        return false;
                }
            });
        });
    };
    //End//

    /**
     * Menu for sorting products
     */
    const handleSortChange = ({ key }) => {
        console.log('Setting sort option to:', key);
        setSortOption(key);
    };

    const sortMenu = (
        <Menu onClick={handleSortChange}>
            <Menu.Item key="priceAsc">Giá: Tăng dần</Menu.Item>
            <Menu.Item key="priceDesc">Giá: Giảm dần</Menu.Item>
            <Menu.Item key="nameAsc">Tên: A-Z</Menu.Item>
            <Menu.Item key="nameDesc">Tên: Z-A</Menu.Item>
            <Menu.Item key="newest">Mới nhất</Menu.Item>
        </Menu>
    );
    //------End------//

    /**
     * Menu for filtering products by price range
     */
    //Handle div click
    const handleDivClick = (id) => {
        const checkbox = document.getElementById(id);
        if (checkbox) {
            checkbox.checked = !checkbox.checked;
            handleCheckboxChange({ target: checkbox });
        }
    };
    //End//

    /**
     * Handle checkbox change
     */
    //Handle checkbox change
    const handleCheckboxChange = (event) => {
        const value = event.target.value;
        setSelectedPrices(prevState =>
            prevState.includes(value)
                ? prevState.filter(price => price !== value)
                : [...prevState, value]
        );
    };
    //End//

    return (
        <div className='search'>
            <div className="breadcrumbs">
                <NavLink to="/">
                    Trang chủ
                </NavLink>
            </div>
            <div className="filters">
                <button>Bộ lọc <i className="fa-solid fa-filter"></i></button>
                <Dropdown
                    overlay={
                        <Menu>
                            <Menu.Item key="under200" onClick={() => handleDivClick('under200')}>
                                <div className='filter-price under200'>
                                    <input type="checkbox" id="under200" className='filter-input' name="under200" value="under200" onChange={handleCheckboxChange} />
                                    <label htmlFor="under200">Dưới 200.000₫</label>
                                </div>
                            </Menu.Item>
                            <Menu.Item key="200to300" onClick={() => handleDivClick('200to300')}>
                                <div className='filter-price 200to300'>
                                    <input type="checkbox" id="200to300" className='filter-input' name="200to300" value="200to300" onChange={handleCheckboxChange} />
                                    <label htmlFor="200to300">200.000₫ - 300.000₫</label>
                                </div>
                            </Menu.Item>
                            <Menu.Item key="300to500" onClick={() => handleDivClick('300to500')}>
                                <div className='filter-price 300to500'>
                                    <input type="checkbox" id="300to500" className='filter-input' name="300to500" value="300to500" onChange={handleCheckboxChange} />
                                    <label htmlFor="300to500">300.000₫ - 500.000₫</label>
                                </div>
                            </Menu.Item>
                            <Menu.Item key="500to1000" onClick={() => handleDivClick('500to1000')}>
                                <div className='filter-price 500to1000'>
                                    <input type="checkbox" id="500to1000" className='filter-input' name="500to1000" value="500to1000" onChange={handleCheckboxChange} />
                                    <label htmlFor="500to1000">500.000₫ - 1.000.000₫</label>
                                </div>
                            </Menu.Item>
                            <Menu.Item key="upper1000" onClick={() => handleDivClick('upper1000')}>
                                <div className='filter-price upper1000'>
                                    <input type="checkbox" id="upper1000" className='filter-input' name="upper1000" value="upper1000" onChange={handleCheckboxChange} />
                                    <label htmlFor="upper1000">Trên 1.000.000₫</label>
                                </div>
                            </Menu.Item>
                        </Menu>
                    }
                    trigger={['click']}
                    overlayClassName='price-dropdown'
                >
                    <a onClick={(e) => e.preventDefault()} className='filter-link'>
                        <Space>
                            <span>Giá bán</span><i className="fa-solid fa-chevron-down filter"></i>
                        </Space>
                    </a>
                </Dropdown>
                <Dropdown overlay={sortMenu} trigger={['click']} overlayClassName='sort-dropdown'>
                    <a onClick={(e) => e.preventDefault()} className='sort-link'>
                        <Space>
                            Sắp xếp theo
                        </Space>
                    </a>
                </Dropdown>
            </div>

            <p className='notification'>Tìm thấy <span>{filteredProducts.length}</span> sản phẩm cho từ khóa <span>{name}</span></p>
            <div className="products">
                {filteredProducts.length === 0 ? (
                    <p style={{ textAlign: "center", width: "100%" }}>Không tìm thấy sản phẩm nào</p>
                ) : (
                    filteredProducts.map((product) => {
                        const discount = product.salePrice ? ((1 - (product.salePrice / product.price)) * 100).toFixed(0) : null;
                        const finalPrice = product.salePrice || product.price;
                        return (
                            <a href={`/product/${product.productId}`} key={product.productId}>
                                <div className="product-item">
                                    <img src={product.images} alt={product.name} />
                                    {discount && <span className={(discount < 100) &&(discount >0) ? "discount" : "not-discount"}>{discount}%</span>}
                                    <OutOfStock show={showModal} onHide={() => setShowModal(false)} />
                                    <img id='cart' src='https://file.hstatic.net/200000692427/file/asset_2_901a91642639466aa75b2019a34ccebd.svg' onClick={(e) => handleClickCart(e, product)} alt="Add to cart" />
                                    <p>{product.name}</p>
                                    <span className={product.salePrice > 0 ? "sale-price" : "normal-price"}>{product.salePrice > 0 ? product.salePrice.toLocaleString('vi-VN') : product.price.toLocaleString('vi-VN')}₫</span>
                                    {product.salePrice > 0 && (
                                        <span className="price-line_through">{product.price.toLocaleString('vi-VN')}₫</span>
                                    )}
                                </div>
                            </a>
                        );
                    })
                )}
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
    );
}