import React, { useEffect, useState } from "react";
import "./AddressPage.css";
import { jwtDecode } from "jwt-decode";
import { meBeSrc } from "../../service/meBeSrc";

export default function AddressPage() {

    const [user, setUser] = useState({
    });

    /**
     * Take user info (username) from local storage by token
     */
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
                    console.log("User data", {
                    });
                })
                .catch((err) => {
                    console.log("Error fetching user", err);
                });
        }
    }, []);

    /**
     * Event change when input
     * @param {*} e 
     */
    const handleInputChange = (e) => {
        const { id, value } = e.target;
        setUser((prevState) => ({
            ...prevState,
            [id]: value
        }));
    };


    return (
        <div className="address-container">
            <div className="address-header">
                <span>Địa chỉ của tôi</span>
                <button className="btn-address">+ Thêm địa chỉ mới</button>
            </div>
            <p className="address-title">Địa chỉ</p>
            <div className="address-body">
                <div className="address-item">
                    <div className="address-item_left">
                        <span>Huỳnh Minh Phước</span> | <span>0901234567</span>
                        <p className="address-item_location">10 Đường Số 494</p>
                        <p className="address-item_location">Phường Tăng Nhơn Phú A, Thành Phố Thủ Đức, Tp.Hồ Chí Minh</p>
                        <p className="address-text_default">Mặc định</p>
                    </div>
                    <div className="address-item_right">
                        <button className="address-btn_update">Cập nhật</button>
                        <button className="address-btn_default">Thiết lập mặc định</button>
                    </div>
                </div>
                <div className="address-item">
                    <div className="address-item_left">
                        <span>Huỳnh Minh Phước</span> | <span>0901234567</span>
                        <p className="address-item_location">10 Đường Số 494</p>
                        <p className="address-item_location">Phường Tăng Nhơn Phú A, Thành Phố Thủ Đức, Tp.Hồ Chí Minh</p>
                    </div>
                    <div className="address-item_right">
                        <div className="address-btn_top">
                            <button className="address-btn_update">Cập nhật</button>
                            <button className="address-btn_delete">Xóa</button>
                        </div>
                        <button className="address-btn_default">Thiết lập mặc định</button>
                    </div>
                </div>
            </div>
        </div>
    )
}