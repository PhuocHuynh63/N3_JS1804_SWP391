import React, { useEffect, useState } from "react";
import './ErrorPage.css';

const ErrorPage = ({ message }) => {

    const [countdown, setCountdown] = useState(3);

    useEffect(() => {
        if (countdown === 0) {
            return window.location.href = '/';
        }
        const timer = setTimeout(() => {
            setCountdown(countdown - 1);
        }, 999);
    }, [countdown]);

    return (
        <div className="error">
            <h1><img src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719930252/icons8-cancel_gxbzhe.gif" /></h1>
            <p>{message}</p>
            <p>Chuyển hướng trong {countdown} giây</p>
        </div>
    )
}

export default ErrorPage;