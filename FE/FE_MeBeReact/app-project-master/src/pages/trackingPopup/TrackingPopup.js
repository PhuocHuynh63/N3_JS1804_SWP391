import React, { useEffect, useState } from "react";
import { Modal } from "antd";
import "./TrackingPopup.css";
import { meBeSrc } from "../../service/meBeSrc";

const TrackingPopup = ({ show, handleClose, orderId }) => {

    /**
     * Call API to get order details
     */
    const [orderDetails, setOrderDetails] = useState([]);
    useEffect(() => {
        meBeSrc.getOrderDetail(orderId)
            .then((res) => {
                setOrderDetails(res.data);
            }).catch((err) => {
                console.log('Error fetching products', err);
            });
    }, [orderId]);
    //------End-------//


    /**
     * Get order details
     */
    const orderDate = orderDetails[0]?.order?.createdAt ? new Date(orderDetails[0]?.order?.createdAt).toLocaleDateString('en-GB') : "";
    const totalPrice = orderDetails.reduce((total, item) => total + item.salePrice * item.quantity, 0).toLocaleString();
    const orderCode = orderDetails[0]?.order?.orderCode;
    const userName = `${orderDetails[0]?.order?.firstName} ${orderDetails[0]?.order?.lastName}`;
    const phoneNumber = orderDetails[0]?.order?.phoneNumber;
    const shipAddress = orderDetails[0]?.order?.shipAddress;
    const orderType = orderDetails[0]?.order?.orderType;
    const paymentStatus = orderDetails[0]?.order?.paymentStatus;
    //------End-------//

    return (
        <Modal visible={show} onCancel={handleClose} footer={null} centered>
            <div className="tracking-popup">
                <div className="tracking-popup_header">
                    <h2 style={{ textAlign: "start" }}>Chi tiết đơn hàng</h2>
                </div>
                <div className="tracking-popup_infobase">
                    <span style={{ fontSize: "17px" }}>Ngày đặt hàng: {orderDate}</span>
                    <span style={{ fontSize: "17px" }}>Mã đơn hàng: <span style={{ color: "red" }}>{orderCode}</span></span>
                </div>
                <div className="tracking-popup_infodetails">
                    <span style={{ fontSize: "15px", fontWeight: "normal" }}>Tên khách hàng: <span style={{ fontWeight: "500" }}>{userName}</span></span>
                    <span style={{ fontSize: "15px", fontWeight: "normal" }}>Số điện thoại: <span style={{ fontWeight: "500" }}>{phoneNumber}</span></span>
                    <span style={{ fontSize: "15px", fontWeight: "normal" }}>Địa chỉ giao hàng: <span style={{ fontWeight: "500" }}>{shipAddress}</span></span>
                    <span style={{ fontSize: "15px", fontWeight: "normal" }}>Loại đơn hàng: <span style={{ fontWeight: "500" }}>{orderType}</span></span>
                    <span style={{ fontSize: "15px", fontWeight: "normal" }}>Trạng thái thanh toán: <span style={{ fontWeight: "500" }}>{paymentStatus}</span></span>
                </div>
                <div className="tracking-popup_body">
                    {orderDetails.map((item) => (
                        <div className="tracking-popup_body_item" key={item.odId}>
                            <div className="left">
                                <img src={item.product.images} alt="Hình ảnh" style={{ textAlign: "start" }} />
                                <div className="content">
                                    <p style={{ marginBottom: "0px", fontSize: "17px" }}>{item.product.name}</p>
                                    <p style={{ textAlign: "start" }}>x{item.quantity}</p>
                                </div>
                            </div>
                            <div className="right">
                                <p style={{ textAlign: "end" }}>{item.salePrice.toLocaleString()}đ</p>
                            </div>
                        </div>
                    ))}
                </div>
                <div className="total">
                    <span>Tổng: </span>
                    <span style={{ fontSize: "22px", color: "red" }}>
                        {totalPrice}đ
                    </span>
                </div>
                <div className="btn">
                    <button onClick={handleClose}>Đóng</button>
                </div>
            </div>
        </Modal>
    );
}

export default TrackingPopup;
