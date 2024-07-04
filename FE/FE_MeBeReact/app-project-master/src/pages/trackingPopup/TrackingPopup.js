import React, { useEffect, useState } from "react";
import { Modal } from "antd";
import "./TrackingPopup.css";
import { meBeSrc } from "../../service/meBeSrc";

const TrackingPopup = ({ show, handleClose, orderId }) => {
    /**
     * Call API to get order detail
     */
    const [orderDetails, setOrderDetails] = useState([])
    useEffect(() => {
        meBeSrc.getOrderDetail(orderId)
            .then((res) => {
                setOrderDetails(res.data)
            }).catch((err) => {
                console.log('Error fetching products', err);
            });
    }, [orderId])
    //-----End-----//

    /**
     * parse order date & total price
     */
    const orderDate = (orderDetails[0]?.order?.createdAt) ? new Date(orderDetails[0]?.order?.createdAt).toLocaleDateString() : "";
    const totalPrice = orderDetails.reduce((total, item) => total + item.salePrice * item.quantity, 0).toLocaleString();
    //-----End-----//


    return (
        <Modal visible={show} onCancel={handleClose} footer={null} centered>
            <div className="tracking-popup">
                <div className="tracking-popup_header">
                    <h2 style={{ textAlign: "start" }}>Chi tiết đơn hàng</h2>
                </div>
                <p style={{ textAlign: "start", fontSize: "17px" }}>Ngày đặt hàng: {orderDate}</p>
                {orderDetails.map((item) => {
                    return (
                        <div key={item.odId} className="tracking-popup_body">
                            <div className="tracking-popup_body_item">
                                <div className="left">
                                    <img src={item.product.images} alt="Hình ảnh" style={{ textAlign: "start" }} />
                                    <div className="content">
                                        <p style={{ marginBottom: "0px", fontSize: "17px" }}>{item.product.name}</p>
                                        <p style={{ textAlign: "start" }}>x{item.quantity}</p>
                                    </div>
                                </div>
                                <div className="right">
                                    <p>{item.salePrice.toLocaleString()} đ</p>
                                </div>
                            </div>
                        </div>
                    );
                })}
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
