import React, { useEffect, useState } from 'react';
import './SuccessPage.css';

const SuccessPage = ({ message }) => {
    const [countdown, setCountdown] = useState(3);

    useEffect(() => {
        if (countdown === 0) {
            return window.location.href = '/account/tracking';
        }
        const timer = setTimeout(() => {
            setCountdown(countdown - 1);
        }, 1000);
    }, [countdown]);

    // const navigate = () => {
    //     setTimeout(() => {
    //         window.location.href = '/account/tracking';
    //     }, 2000);
    // }

    return (
        <div className='susscess'>
            <h1><img src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1720256749/z5607740215391_7fa95663f97ae3afd540a2abb82dc7fc_iycg8w.gif" /></h1>
            <p>{message}</p>
            <p>Chuyển hướng trong {countdown} giây</p>
        </div>
    )
}

export default SuccessPage;