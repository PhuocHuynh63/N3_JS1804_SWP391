import React, { useEffect, useState } from "react";
import "./TrackingPage.css";
import { NavLink } from "react-router-dom";
import TrackingHeader from "../../components/trackingHeader/TrackingHeader";
import { meBeSrc } from "../../service/meBeSrc";
import { jwtDecode } from "jwt-decode";

export default function TrackingPage() {
    const [data, setData] = useState([]);
    const [user, setUser] = useState({});

    /**
     * Get Tracking API 
     */
    useEffect(() => {
        if (user.id) {
            meBeSrc.getTrackingOrder(user.id)
                .then((res) => {
                    setData(res.data.order);
                    console.log('Data: ', res.data);
                }).catch((err) => {
                    console.log('Error fetching products', err);
                });
        }
    }, [user.id]);

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
                })
                .catch((err) => {
                    console.log("Error fetching user", err);
                });
        }
    }, []);

    const switchStatus = (order) => {
        switch (order.status) {
            case 'Chờ xác nhận': return 'confirm';
            case 'Đang được xử lý': return 'pending';
            case 'Đang giao': return 'shipping';
            case 'Đã giao': return 'success';
            case 'Đã hủy': return 'cancel';
            default: return '';
        }
    }

    return (
        <div className="tracking-container">
            <TrackingHeader />

            {data.length === 0 ? (
                <p>No orders found</p>
            ) : (
                data.map((order, index) => (
                    <div className="tracking-body" key={index}>
                        <p>Created At: {new Date(order.createdAt).toLocaleString()}</p>

                        {order.items.map((item) => (
                            <div className="tracking-body_item" key={item.odId}>
                                <div className="left">
                                    <img src={item.product.images} alt={item.product.name} />
                                    <div className="content">
                                        <p>{item.product.name}</p>
                                        <p>x{item.quantity}</p>
                                    </div>
                                </div>
                                <div className="right">
                                    <p>{(item.quantity * item.price).toLocaleString('vi-VN')}đ</p>
                                </div>
                            </div>
                        ))}

                        <div className="tracking-body_bot">
                            <div className="total">
                                <span>Total Price: </span>
                                <span>
                                    {order.items.reduce((total, item) => total + item.price * item.quantity, 0).toLocaleString()}đ
                                </span>
                            </div>
                            <div className="btn">
                                <NavLink to={"/account/tracking/success"} className={switchStatus(order.status)}>
                                    {order.status}
                                </NavLink>
                                <button className="btn-buy_again">Mua lại</button>
                            </div>
                        </div>
                    </div>
                ))
            )}
        </div>
    );
}
