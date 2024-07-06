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
            <h1><img src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1720256949/z5607751146663_d034ee3d84a21e12c99d7e3a8d90c1df_csloch.gif" /></h1>
            <p>{message}</p>
            <p>Chuyển hướng trong {countdown} giây</p>
        </div>
    )
}

export default ErrorPage;