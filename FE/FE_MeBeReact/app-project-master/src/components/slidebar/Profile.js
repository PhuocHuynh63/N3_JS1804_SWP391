import { NavLink } from "react-router-dom";
import './Profile.css';
import { meBeSrc } from "../../service/meBeSrc";
import {jwtDecode} from "jwt-decode";
import React, { useEffect, useState } from "react";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function Profile() {
    const [user, setUser] = useState({
        username: "",
        avatar: ""
    });
    const [selectedFile, setSelectedFile] = useState(null);

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

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
        // Automatically submit the form after selecting a file
        const formData = new FormData();
        formData.append("file", event.target.files[0]);
        const userId = user.id; // Make sure user object contains id

        meBeSrc.setAvatar(userId, formData)
            .then((res) => {
                toast.success("Cập nhập ảnh đại diện thành công");
                // Reload the window after successful update
                window.location.reload();
            })
            .catch((err) => {
                console.log("Error uploading file", err);
                toast.error("Cập nhập ảnh đại diện thất bại");
            });
    };

    return (
        <div className="profile-small_slidebar-container">
            <div className="profile-small_slidebar header">
                <div className="profile-small_slidebar header__avatar">
                    <img src={user.avatar} alt="avatar" />
                </div>
                <form className="profile-small_slidebar header__info" onSubmit={(e) => e.preventDefault()}>
                    <input 
                        type="file" 
                        onChange={handleFileChange} 
                        style={{ display: 'none' }} 
                        id="fileInput"
                    />
                    <label 
                        htmlFor="fileInput" 
                        className="profile-small_slidebar header__update-avatar-label"
                    >
                        Đổi ảnh đại diện
                    </label>
                </form>
                <div className="profile-small_slidebar header__info">
                    <div className="profile-small_slidebar header__info-name">{user.username}</div>
                </div>
            </div>

            <div className="profile-small_slidebar body">
                <div className="profile-small_slidebar body__item">
                    <div className="profile-small_slidebar body__item-title">
                        <i className="fa-regular fa-user"></i>
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

            <ToastContainer />
        </div>
    );
}
