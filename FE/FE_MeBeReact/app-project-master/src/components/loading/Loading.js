import React from 'react';
import loadingIcon from '../../images/Loading...gif';
import './Loading.css';

const LoadingIcon = () => {
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
