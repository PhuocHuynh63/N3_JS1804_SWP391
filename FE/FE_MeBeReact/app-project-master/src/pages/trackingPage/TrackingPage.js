import React, { useEffect, useState } from "react";
import "./TrackingPage.css";
import { NavLink } from "react-router-dom";
import TrackingHeader from "../../components/trackingHeader/TrackingHeader";
import { meBeSrc } from "../../service/meBeSrc";
import { jwtDecode } from "jwt-decode";
import TrackingCancel from "./TrackingCancel";

export default function TrackingPage() {
    const [data, setData] = useState([]);
    const [user, setUser] = useState({});
    const [statusFilter, setStatusFilter] = useState('Tất cả');
    const [selectedOrderId, setSelectedOrderId] = useState(null); // Thêm trạng thái này

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

    const switchStatus = (status) => {
        switch (status) {
            case 'Chờ xác nhận': return 'confirm';
            case 'Đang được xử lý': return 'pending';
            case 'Đang giao': return 'shipping';
            case 'Đã giao': return 'success';
            case 'Đã hủy': return 'cancel';
            default: return '';
        }
    }

    const handleStatusClassButton = (status) => {
        switch (switchStatus(status)) {
            case 'confirm':
                return 'btn-cancel';
            case 'pending':
                return 'btn-cancel';
            case 'shipping':
                return 'btn-cancel';
            case 'success':
                return 'btn-buy-again';
            case 'cancel':
                return 'btn-buy-again';
            default:
                break;
        }
    }

    const handleStatusButton = (status) => {
        switch (switchStatus(status)) {
            case 'confirm':
                return 'Hủy đơn hàng';
            case 'pending':
                return 'Hủy đơn hàng';
            case 'shipping':
                return 'Hủy đơn hàng';
            case 'success':
                return 'Mua lại';
            case 'cancel':
                return 'Mua lại';
            default:
                break;
        }
    }

    const handleStatusChange = (status) => {
        setStatusFilter(status);
    }

    const [showCancel, setShowCancel] = useState(false);
    const handleCloseCancel = () => setShowCancel(false);
    const handleShowCancel = (orderId) => {
        setSelectedOrderId(orderId); // Đặt ID đơn hàng được chọn
        setShowCancel(true);
    };

    const sortData = data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
    const filteredData = sortData.filter(order => statusFilter === 'Tất cả' || order.status === statusFilter);

    return (
        <div className="tracking-container">
            <TrackingHeader onStatusChange={handleStatusChange} />

            {filteredData.length === 0 ? (
                <p>Chưa có đơn hàng</p>
            ) : (
                filteredData.map((order, index) => (
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
                                <NavLink className={`${switchStatus(order.status)}`}>
                                    {order.status}
                                </NavLink>
                                <button className={handleStatusClassButton(order.status)} onClick={handleStatusButton(order.status) === 'Hủy đơn hàng' ? () => handleShowCancel(order.orderId) : null}>
                                    {handleStatusButton(order.status)}
                                </button>
                            </div>
                        </div>
                    </div>
                ))
            )}
            <TrackingCancel show={showCancel} handleClose={handleCloseCancel} order_id={selectedOrderId} />
        </div>
    );
}
