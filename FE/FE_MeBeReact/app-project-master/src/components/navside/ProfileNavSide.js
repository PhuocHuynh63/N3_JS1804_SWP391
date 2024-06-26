import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import './ProfileNavSide.css'
import { NavLink } from "react-router-dom";

export default function ProfileNavSide() {
    return (
        <div className="col-md-6 profile1">
            <div className="sidebar small-sidebar">
                <div className="profile-overview text-center mb-4 small-profile-overview">
                    <img src="https://via.placeholder.com/80" alt="Profile Picture" className="rounded-circle small-profile-img" />
                    <p className="mt-2 small-profile-name">thuanvt212003</p>
                    <a href="#" className="edit-profile small-edit-profile">Sửa Hồ Sơ</a>
                </div>
                <div>
                    <div className="profile-title">
                        <i className="fa-solid fa-user"></i>
                        <NavLink to={"/profile"} className="main-menu small-link">Tài Khoản Của Tôi</NavLink>
                    </div>
                    <div className="submenu small-submenu profile-title_submenu">
                        <NavLink to={"/profile"} className="small-link">
                            <span className="profile-submenu">Hồ sơ</span>
                        </NavLink>
                        <NavLink to={"/profile/address"} className="small-link">
                            <span className="profile-submenu">Địa chỉ</span>
                        </NavLink>
                        <NavLink to={"/profile/change_password"} className="small-link">
                            <span className="profile-submenu">Đổi mật khẩu</span>
                        </NavLink>
                        <NavLink to={"/profile/tracking"} className="small-link">
                            <span className="profile-submenu">Đơn mua</span>
                        </NavLink>
                    </div>
                </div>
            </div>
        </div>
    )
}