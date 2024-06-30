import React, { useState } from 'react';
import './TrackingHeader.css';

const TrackingHeader = ({ onStatusChange }) => {
    const [activeStatus, setActiveStatus] = useState('Tất cả')

    const handleStatusCick = (status) => {
        setActiveStatus(status)
        onStatusChange(status)
    }

    return (
        <div className="tracking-header">
            {['Tất cả', 'Chờ xác nhận', 'Đang được xử lý', 'Đang giao', 'Đã giao', 'Đã hủy'].map((status) => (
                <span key={status} className={activeStatus === status ? 'active' : ''} onClick={() => handleStatusCick(status)}>
                    {status}
                </span>
            ))}
        </div>
    );
}

export default TrackingHeader;