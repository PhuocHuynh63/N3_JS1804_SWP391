import React, { useEffect, useState } from "react";
import "./Header.css";
import LoginPage from "../../pages/loginPage/LoginPage";
import CartPage from "../../pages/cartPage/Cart";
import { NavLink, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import UserDropdown from "./UserDropDown";
import { localService } from "../../service/localService";
import { meBeSrc } from "../../service/meBeSrc";
import logo from "../../images/Logo_Header_RemoveBackground.png";

export default function Header() {

    //Lấy thông tin user từ store
    let userInfo = useSelector((state) => state.userReducer.userInfo);
    const [user, setUser] = useState(null);
    useEffect(() => {
        if (userInfo) {
            setUser(userInfo);
        }
    }, [userInfo])
    //-----End Lấy thông tin user từ store-----//


    //Logout
    let handleLogout = () => {
        localService.remove()
        window.location.reload()
    }
    //-----End Logout-----//


    const [categories, setCategories] = useState([]);

    useEffect(() => {
        meBeSrc.getListCategory()
            .then((res) => {
                setCategories(res.data);
            })
            .catch((err) => {
                console.log("Error fetching category", err);
            });
    }, []);

    const [isActive, setIsActive] = useState(false);
    const handleClick = (tab) => {
        setIsActive(tab);
    };


    //Pop-up Login, Cart
    const [showLogin, setShowLogin] = useState(false);
    const [showCart, setShowCart] = useState(false);

    const handleCloseLogin = () => setShowLogin(false);
    const handleShowLogin = () => setShowLogin(true);

    const handleCloseCart = () => setShowCart(false);
    const handleShowCart = () => setShowCart(true);
    //-----End Pop-up Login, Cart-----//


    //Search
    const [searchTerm, setSearchTerm] = useState("");
    const [suggestions, setSuggestions] = useState([]);
    const suggestionsRef = useRef(null);

    //Call API to get product by search term
    useEffect(() => {
        if (searchTerm) {
            meBeSrc.getProductBySearch(searchTerm)
                .then((res) => {
                    setSuggestions(res.data);
                })
                .catch((err) => {
                    console.log("Error fetching product", err);
                });
        } else {
            setSuggestions([]);
        }
    }, [searchTerm]);

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (suggestionsRef.current && !suggestionsRef.current.contains(event.target)) {
                setSuggestions([]);
            }
        }

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        }
    }, []);
    //-----End Search-----//

    if (!userInfo) {
        return (
            <div className="header_container">
                <header>
                    <NavLink to="/">
                        <img src={logo} alt="Nous Logo" height="50" />
                    </NavLink>

                    <form className="form_inline">
                        <input className="form_control" type="search" placeholder="Nhập tên sản phẩm" aria-label="Search" value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} />
                        <i id="search" className="fa-solid fa-magnifying-glass"></i>
                        {suggestions.length > 0 && (
                            <div className="suggestions">
                                <ul>
                                    {suggestions.map((suggestion, index) => (
                                        <li key={index}>
                                            <NavLink to={`/product/${suggestion.productId}`}>
                                                <div className="search-item">
                                                    <img src={suggestion.images} alt={suggestion.name} />
                                                    <div className="text">
                                                        <p>{suggestion.name}</p>
                                                        <div className="price">
                                                            <span className={suggestion.salePrice > 0 ? "sale-price" : "normal-price"}>
                                                                {suggestion.salePrice > 0
                                                                    ? suggestion.salePrice.toLocaleString('vi-VN')
                                                                    : suggestion.price ? suggestion.price.toLocaleString('vi-VN') : '0'}₫
                                                            </span>
                                                            {suggestion.salePrice > 0 && (
                                                                <span className="price-line_through">{suggestion.price ? suggestion.price.toLocaleString('vi-VN') : '0'}₫</span>
                                                            )}
                                                        </div>
                                                    </div>
                                                </div>
                                            </NavLink>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        )}
                    </form>

                    <div className="icons">
                        <span className="icons-header" onClick={handleShowLogin}>
                            <i className="fa-regular fa-user"></i>
                        </span>

                        <span className="icons-header" onClick={handleShowCart}>
                            <i className="fa-solid fa-cart-shopping"></i>
                        </span>
                    </div>
                </header>

                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav">
                            <li className="nav-item active">
                                <a className="nav-link" href="/about">Giới Thiệu MeBe</a>
                            </li>
                            <li className="nav-item dropdown">
                                <NavLink to={`/category/sua-binh-sua`} className="nav-link dropdown-toggle">
                                    Sữa & Bình Sữa
                                </NavLink>

                                <div className="dropdown-menu" aria-labelledby="navbarDropdown1">
                                    <span className="dropdown-item_title" href="">Sữa</span>
                                    <a className="dropdown-item" href="/subcategory/sua-bot">Sữa bột</a>
                                    <a className="dropdown-item" href="/subcategory/sua-pha-san">Sữa pha sẵn</a>
                                    <div className="dropdown-divider"></div>
                                    <span className="dropdown-item_title" href="">Bình sữa - Núm ti - Phụ kiện</span>
                                    <a className="dropdown-item" href="">Bình sữa</a>
                                    <a className="dropdown-item" href="/subcategory/phu-kien-binh-sua">Phụ kiện bình sữa</a>
                                    <a className="dropdown-item" href="/subcategory/num-ti">Núm ti</a>
                                </div>
                            </li>

                            <li className="nav-item dropdown">
                                <NavLink to={`/category/bim-ta-ve-sinh`} className="nav-link dropdown-toggle">
                                    Bỉm tã & Dụng cụ vệ sinh
                                </NavLink>

                                <div className="dropdown-menu" aria-labelledby="navbarDropdown1">
                                    <span className="dropdown-item_title" href="">Bỉm tã</span>
                                    <a className="dropdown-item" href="/subcategory/bim-ta">Bỉm tã</a>
                                    <a className="dropdown-item" href="/subcategory/bim-nguoi-lon">Bỉm người lớn</a>
                                    <div className="dropdown-divider"></div>
                                    <a className="dropdown-item_title" href="#">Vệ sinh cho bé</a>
                                    <a className="dropdown-item" href="/subcategory/bo">Bô</a>
                                    <a className="dropdown-item" href="/subcategory/cham-soc-rang-mieng">Chăm sóc răng miệng</a>
                                    <a className="dropdown-item" href="/subcategory/sua-tam-goi">Sữa tắm/gội</a>
                                </div>
                            </li>

                            <li className="nav-item dropdown">
                                <NavLink to={`/category/do-cho-me`} className="nav-link dropdown-toggle">
                                    Đồ cho mẹ
                                </NavLink>

                                <div className="dropdown-menu" aria-labelledby="navbarDropdown1">
                                    <span className="dropdown-item_title" href="">Đồ Cho Mẹ</span>
                                    <a className="dropdown-item" href="/subcategory/sua-bau">Sữa bầu</a>
                                    <a className="dropdown-item" href="/subcategory/vitamin-cho-me">Vitamin cho mẹ</a>
                                    <a className="dropdown-item" href="/subcategory/phu-kien-cho-me">Phụ kiện cho mẹ</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </nav>

                <LoginPage show={showLogin} handleClose={handleCloseLogin} />
                <CartPage show={showCart} handleClose={handleCloseCart} />
            </div>
        );
    } else {
        return (
            <div className="header_container">
                <header>
                    <NavLink to="/">
                        <img src={logo} alt="Nous Logo" height="50" />
                    </NavLink>

                    <form className="form_inline">
                        <input className="form_control" type="search" placeholder="Nhập tên sản phẩm" aria-label="Search" value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} />
                        <i id="search" className="fa-solid fa-magnifying-glass"></i>
                        {suggestions.length > 0 && (
                            <div className="suggestions">
                                <ul>
                                    {suggestions.map((suggestion, index) => (
                                        <li key={index}>
                                            <NavLink to={`/product/${suggestion.productId}`}>
                                                <div className="search-item">
                                                    <img src={suggestion.images} alt={suggestion.name} />
                                                    <div className="text">
                                                        <p>{suggestion.name}</p>
                                                        <div className="price">
                                                            <span className={suggestion.salePrice > 0 ? "sale-price" : "normal-price"}>
                                                                {suggestion.salePrice > 0
                                                                    ? suggestion.salePrice.toLocaleString('vi-VN')
                                                                    : suggestion.price ? suggestion.price.toLocaleString('vi-VN') : '0'}₫
                                                            </span>
                                                            {suggestion.salePrice > 0 && (
                                                                <span className="price-line_through">{suggestion.price ? suggestion.price.toLocaleString('vi-VN') : '0'}₫</span>
                                                            )}
                                                        </div>
                                                    </div>
                                                </div>
                                            </NavLink>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        )}
                    </form>


                    <div className="icons">
                        <UserDropdown
                            user={user}
                            logoutBtn={
                                <button
                                    onClick={handleLogout}
                                    className={"dropdown-item"}
                                >
                                    Logout
                                </button>

                            }></UserDropdown>
                        <span className="icons-header" onClick={handleShowCart}>
                            <i className="fa-solid fa-cart-shopping"></i>
                        </span>
                    </div>
                </header >

                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav">
                            <li className="nav-item active">
                                <a className="nav-link" href="/about">Giới Thiệu MeBe</a>
                            </li>
                            <li className="nav-item dropdown">
                                <NavLink to={`/category/sua-binh-sua`} className="nav-link dropdown-toggle">
                                    Sữa & Bình Sữa
                                </NavLink>

                                <div className="dropdown-menu" aria-labelledby="navbarDropdown1">
                                    <span className="dropdown-item_title" href="">Sữa</span>
                                    <a className="dropdown-item" href="/subcategory/sua-bot">Sữa bột</a>
                                    <a className="dropdown-item" href="/subcategory/sua-pha-san">Sữa pha sẵn</a>
                                    <div className="dropdown-divider"></div>
                                    <span className="dropdown-item_title" href="">Bình sữa - Núm ti - Phụ kiện</span>
                                    <a className="dropdown-item" href="">Bình sữa</a>
                                    <a className="dropdown-item" href="/subcategory/phu-kien-binh-sua">Phụ kiện bình sữa</a>
                                    <a className="dropdown-item" href="/subcategory/num-ti">Núm ti</a>
                                </div>
                            </li>

                            <li className="nav-item dropdown">
                                <NavLink to={`/category/bim-ta-ve-sinh`} className="nav-link dropdown-toggle">
                                    Bỉm tã & Dụng cụ vệ sinh
                                </NavLink>

                                <div className="dropdown-menu" aria-labelledby="navbarDropdown1">
                                    <span className="dropdown-item_title" href="">Bỉm tã</span>
                                    <a className="dropdown-item" href="/subcategory/bim-ta">Bỉm tã</a>
                                    <a className="dropdown-item" href="/subcategory/bim-nguoi-lon">Bỉm người lớn</a>
                                    <div className="dropdown-divider"></div>
                                    <a className="dropdown-item_title" href="#">Vệ sinh cho bé</a>
                                    <a className="dropdown-item" href="/subcategory/bo">Bô</a>
                                    <a className="dropdown-item" href="/subcategory/cham-soc-rang-mieng">Chăm sóc răng miệng</a>
                                    <a className="dropdown-item" href="/subcategory/sua-tam-goi">Sữa tắm/gội</a>
                                </div>
                            </li>

                            <li className="nav-item dropdown">
                                <NavLink to={`/category/do-cho-me`} className="nav-link dropdown-toggle">
                                    Đồ cho mẹ
                                </NavLink>

                                <div className="dropdown-menu" aria-labelledby="navbarDropdown1">
                                    <span className="dropdown-item_title" href="">Đồ Cho Mẹ</span>
                                    <a className="dropdown-item" href="/subcategory/sua-bau">Sữa bầu</a>
                                    <a className="dropdown-item" href="/subcategory/vitamin-cho-me">Vitamin cho mẹ</a>
                                    <a className="dropdown-item" href="/subcategory/phu-kien-cho-me">Phụ kiện cho mẹ</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </nav>

                <LoginPage show={showLogin} handleClose={handleCloseLogin} />
                <CartPage show={showCart} handleClose={handleCloseCart} />
            </div >
        )
    }
}
