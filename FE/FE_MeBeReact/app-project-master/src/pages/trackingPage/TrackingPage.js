import React, { useEffect, useState } from "react";
import "./TrackingPage.css";
import { NavLink } from "react-router-dom";
import TrackingHeader from "../../components/trackingHeader/TrackingHeader";
import { meBeSrc } from "../../service/meBeSrc";
import { jwtDecode } from "jwt-decode";
import TrackingCancel from "./TrackingCancel";
import { useNavigate } from "react-router-dom";

export default function TrackingPage() {
    const navigate = useNavigate();
    const [data, setData] = useState([]);
    const [user, setUser] = useState({});
    const [statusFilter, setStatusFilter] = useState('Tất cả');
    const [selectedOrderId, setSelectedOrderId] = useState(null); // Thêm trạng thái này

    /**
     * Call API to get tracking order
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
    //-----End-----//


    /**
     * Take user info from token
     */
    useEffect(() => {
        const token = localStorage.getItem('USER_INFO');
        if (!token) {
            navigate('/');
        } else {
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
    //-----End-----//


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
                        <p style={{ marginLeft: "30px", fontSize: "17px", fontStyle: "italic" }}>Ngày tạo đơn hàng: {new Date(order.createdAt).toLocaleString()}</p>

                        {order.items.map((item) => (
                            <NavLink to={`/product/${item.product.productId}`}>
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
                            </NavLink>
                        ))}

                        <div className="tracking-body_bot">
                            <div className="total">
                                <span>Tổng giá tiền: </span>
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
