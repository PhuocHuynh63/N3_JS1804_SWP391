import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { meBeSrc } from "../../service/meBeSrc";
import {jwtDecode} from 'jwt-decode';  

export default function ChangePasswordPage() {
    const navigate = useNavigate();
    const [passwordOld, setPasswordOld] = useState('');
    const [passwordNew, setPasswordNew] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [userId, setUserId] = useState(null);
    const [errors, setErrors] = useState({
        passwordOld: '',
        passwordNew: '',
        confirmPassword: '',
    });

    useEffect(() => {
        const token = localStorage.getItem('USER_INFO');
        if (!token) {
            navigate('/');
        } else {
            const decoded = jwtDecode(token);
            const username = decoded.sub;
            meBeSrc.getUserByUserName(username)
                .then((res) => {
                    setUserId(res.data.id);
                })
                .catch((err) => {
                    toast.error('Không tìm thấy user ID');
                });
        }
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
    
        let valid = true;
        const newErrors = {
            passwordOld: '',
            passwordNew: '',
            confirmPassword: '',
        };

        if (passwordOld.length < 6) {
            newErrors.passwordOld = 'Mật khẩu cũ phải có ít nhất 6 ký tự';
            valid = false;
        }
        if (passwordNew.length < 6) {
            newErrors.passwordNew = 'Mật khẩu mới phải có ít nhất 6 ký tự';
            valid = false;
        }
        if (confirmPassword.length < 6) {
            newErrors.confirmPassword = 'Xác nhận mật khẩu mới phải có ít nhất 6 ký tự';
            valid = false;
        }
        if (passwordNew !== confirmPassword) {
            newErrors.confirmPassword = 'Mật khẩu mới không khớp';
            valid = false;
        }

        setErrors(newErrors);

        if (!valid) {
            return;
        }

        if (!userId) {
            toast.error('Không tìm thấy user ID');
            return;
        }
    
        const data = {
            passwordOld,
            passwordNew,
        };
    
        meBeSrc.changePassword(userId, data)
            .then((res) => {
                console.log("API response:", res);
                if (res.data === "Password old does not match") {
                    toast.error('Mật khẩu cũ không khớp');
                } else {
                    toast.success('Mật khẩu đã được thay đổi. Bạn sẽ đăng xuất trong giây lát.', {
                        onClose: () => {
                            localStorage.removeItem('USER_INFO');  
                            window.location = '/'; 
                        },
                        autoClose: 2000
                    });
                }
            })
            .catch((err) => {
                toast.error('Lỗi khi thay đổi mật khẩu');
            });
    };

    return (
        <div className="changePasswordPage">
            <ToastContainer 
                position="top-right" 
                autoClose={5000} 
                hideProgressBar={false} 
                newestOnTop={false} 
                closeOnClick 
                rtl={false} 
                pauseOnFocusLoss 
                draggable 
                pauseOnHover 
            />
            <div className="change-password-details container small-change-password mt-5">
                <div className="row change-password">
                    <div className="col-md-12 change-password2">
                        <div className="change-password-header">
                            <h1 className="small-header">Đổi Mật Khẩu</h1>
                        </div>
                        <form className="change-password-form small-change-password-form" onSubmit={handleSubmit}>
                            <div className="form-group small-form-group">
                                <label htmlFor="passwordOld" className="small-label">Mật khẩu cũ</label>
                                <input type="password" id="passwordOld" className="form-control small-input" value={passwordOld} onChange={(e) => setPasswordOld(e.target.value)} required />
                                {errors.passwordOld && <small className="text-danger">{errors.passwordOld}</small>}
                            </div>
                            <div className="form-group small-form-group">
                                <label htmlFor="passwordNew" className="small-label">Mật khẩu mới</label>
                                <input type="password" id="passwordNew" className="form-control small-input" value={passwordNew} onChange={(e) => setPasswordNew(e.target.value)} required />
                                {errors.passwordNew && <small className="text-danger">{errors.passwordNew}</small>}
                            </div>
                            <div className="form-group small-form-group">
                                <label htmlFor="confirmPassword" className="small-label">Xác nhận mật khẩu mới</label>
                                <input type="password" id="confirmPassword" className="form-control small-input" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
                                {errors.confirmPassword && <small className="text-danger">{errors.confirmPassword}</small>}
                            </div>
                            <button type="submit" className="btn btn-success btn-lg float-right">Lưu</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}
