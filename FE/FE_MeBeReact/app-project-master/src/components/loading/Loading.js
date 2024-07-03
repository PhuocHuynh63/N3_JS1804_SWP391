import React, { useEffect, useState } from 'react';
import loadingIcon from '../../images/Loading...gif';
import './Loading.css';

const LoadingIcon = () => {

    const [countdown, setCountdown] = useState(0);

    useEffect(() => {
        if (countdown > 6) {
            return window.location.href = '/order-error';
        }
        const timer = setTimeout(() => {
            setCountdown(countdown + 1);
        }, 1000);
    }, [countdown]);

    return (
        <div className='loading-opacity'>
            <div className="loading-overlay"></div>
            <div className="loading-icon">
                <img src={loadingIcon} alt="loading" />
            </div>
        </div>
    );
}

export default LoadingIcon;
