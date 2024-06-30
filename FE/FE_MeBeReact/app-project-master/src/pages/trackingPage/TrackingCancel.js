import { Modal } from "react-bootstrap";
import React, { useState } from "react";
import "./TrackingCancel.css";
import { meBeSrc } from "../../service/meBeSrc";

const TrackingCancel = ({ show, handleClose, order_id }) => {
    const [selectedReason, setSelectedReason] = useState('');
    const [otherReason, setOtherReason] = useState('');

    const handleReasonChange = (e) => {
        setSelectedReason(e.target.value);
    };

    const handleOtherReasonChange = (e) => {
        setOtherReason(e.target.value);
    };

    const handleCancelOrder = () => {
        const reason = selectedReason === 'other' ? otherReason : selectedReason;
        if (reason) {
            meBeSrc.putCancelOrder(order_id, { reason })
                .then((res) => {
                    console.log('Order canceled: ', res.data);
                    handleClose();
                }).catch((err) => {
                    console.error('Error canceling order', err);
                });
        } else {
            alert('Please select a reason for cancellation.');
        }
    };

    return (
        <Modal show={show} onHide={handleClose} size="xl" centered>
            <Modal.Header closeButton>
                <Modal.Title>Chọn lý do hủy</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h5>Hãy cho chúng tôi biết lý do bạn hủy đơn hàng được không?</h5>
                <ul>
                    <div className="reason">
                        <input
                            type="radio"
                            id="reason1"
                            name="reason"
                            value="Tôi muốn thay đổi địa chỉ giao hàng"
                            onChange={handleReasonChange}
                        />
                        <label htmlFor="reason1">Tôi muốn thay đổi địa chỉ giao hàng</label>
                    </div>
                    <div className="reason">
                        <input
                            type="radio"
                            id="reason2"
                            name="reason"
                            value="Tôi muốn thay đổi sản phẩm trong đơn hàng"
                            onChange={handleReasonChange}
                        />
                        <label htmlFor="reason2">Tôi muốn thay đổi sản phẩm trong đơn hàng</label>
                    </div>
                    <div className="reason">
                        <input
                            type="radio"
                            id="reason3"
                            name="reason"
                            value="Thủ tục thanh toán quá rắc rối"
                            onChange={handleReasonChange}
                        />
                        <label htmlFor="reason3">Thủ tục thanh toán quá rắc rối</label>
                    </div>
                    <div className="reason">
                        <input
                            type="radio"
                            id="reason4"
                            name="reason"
                            value="Tìm thấy giá rẻ hơn ở chỗ khác"
                            onChange={handleReasonChange}
                        />
                        <label htmlFor="reason4">Tìm thấy giá rẻ hơn ở chỗ khác</label>
                    </div>
                    <div className="reason">
                        <input
                            type="radio"
                            id="reason5"
                            name="reason"
                            value="Đổi ý không mua nữa"
                            onChange={handleReasonChange}
                        />
                        <label htmlFor="reason5">Đổi ý không mua nữa</label>
                    </div>
                    <div className="reason">
                        <input
                            type="radio"
                            id="reason6"
                            name="reason"
                            value="other"
                            onChange={handleReasonChange}
                        />
                        <label htmlFor="reason6">Lý do khác:</label>
                        <input
                            type="text"
                            id="otherReason"
                            name="otherReason"
                            value={otherReason}
                            onChange={handleOtherReasonChange}
                            disabled={selectedReason !== 'other'}
                        />
                    </div>
                </ul>
            </Modal.Body>
            <Modal.Footer>
                <button className="tracking-cancel no" onClick={handleClose}>Không</button>
                <button className="tracking-cancel yes" onClick={handleCancelOrder}>Có</button>
            </Modal.Footer>
        </Modal>
    );
};

export default TrackingCancel;
