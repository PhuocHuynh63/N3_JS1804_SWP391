import React from 'react';
import './TrackingHeader.css';

const TrackingHeader = () => {
    return (
        <div className="tracking-header">
            <span>Tất cả</span>
            <span>Chờ thanh toán</span>
            <span>Vận chuyển</span>
            <span>Chờ giao hàng</span>
            <span>Hoàn thành</span>
            <span>Đã hủy</span>
        </div>
    );
}

export default TrackingHeader;