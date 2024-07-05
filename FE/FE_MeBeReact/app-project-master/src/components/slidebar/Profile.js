import { NavLink } from "react-router-dom";
import './Profile.css';
import { meBeSrc } from "../../service/meBeSrc";
import { jwtDecode } from "jwt-decode";
import React, { useEffect, useState } from "react";

export default function Profile() {

    const [user, setUser] = useState({
        username: "",
        avatar:""
    });

    useEffect(() => {
        const token = localStorage.getItem('USER_INFO');
        if (token) {
            const decoded = jwtDecode(token);
            const username = decoded.sub;

            meBeSrc.getUserByUserName(username)
                .then((res) => {
                    const userData = {
                        ...res.data,
                    };
                    setUser(userData);
                })
                .catch((err) => {
                    console.log("Error fetching user", err);
                });
        }
    }, []);

    return (
        <div className="profile-small_slidebar-container">
            <div className="profile-small_slidebar header">
                <div className="profile-small_slidebar header__avatar">
                    <img src={user.avatar} alt="avatar" />
                </div>
                <div className="profile-small_slidebar header__info">
                    <div className="profile-small_slidebar header__info-name">{user.username}</div>
                </div>
            </div>

            <div className="profile-small_slidebar body">
                <div className="profile-small_slidebar body__item">
                    <div className="profile-small_slidebar body__item-title">
                        <i class="fa-regular fa-user"></i>
                        Tài khoản của tôi
                    </div>
                    <div className="profile-small_slidebar body__item-content">
                        <NavLink to={"/account/profile"}>
                            <span className="content__profile">Hồ sơ</span>
                        </NavLink>
                        <NavLink to={"/account/address"}>
                            <span className="content__profile">Địa chỉ</span>
                        </NavLink>
                        <NavLink to={"/account/changePassword"}>
                            <span className="content__profile">Đổi mật khẩu</span>
                        </NavLink>
                        <NavLink to={"/account/tracking"}>
                            <span className="content__profile">Đơn mua</span>
                        </NavLink>
                    </div>
                </div>
            </div>
        </div>
    )
}