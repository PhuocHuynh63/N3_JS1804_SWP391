import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import { meBeSrc } from '../../service/meBeSrc';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ForgotPassword = () => {
    const navigate = useNavigate();
    const [errors, setErrors] = useState({});
    const [formData, setFormData] = useState({ email: '' });
    const [isLoading, setIsLoading] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });

        if (errors[name]) {
            setErrors({
                ...errors,
                [name]: ''
            });
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const newErrors = {};
        // Validate
        if (!formData.email) {
            newErrors.email = 'Vui lòng nhập email.';
        } else if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(formData.email)) {
            newErrors.email = 'Email không hợp lệ.';
        }
        
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
        } else {
            setIsLoading(true);
            const email = formData.email.trim();
        
            meBeSrc.forgotPassword(email)
                .then(response => {
                    toast.success('Vui lòng kiểm tra mail để nhận mật khẩu tạm thời', {
                        onClose: () => {
                            toast.success('Chuyển về trang đăng nhập trong giây lát...');
                            setTimeout(() => {
                                navigate('/signin');
                            }, 1500);  
                        }
                    });
                })
                .catch(error => {
                    if (error.response && error.response.data) {
                        toast.error(error.response.data.msg); 
                    } else {
                        toast.error('Đã xảy ra lỗi, vui lòng thử lại sau.');
                    }
                })
                .finally(() => {
                    setIsLoading(false);
                });
        }
    };

    return (
        <div className="container col-12 mt-5">
            <ToastContainer position="top-right" autoClose={5000} hideProgressBar={false} newestOnTop={true} closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover />
            <div className="row">
                <div className="col-md-6">
                    <img src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1719076828/sign_up_baw61i.jpg" alt="Descriptive Text" style={{ width: "100%", height: "100%", objectFit: "cover" }} />
                </div>
                <div className="col-md-6">
                    <h2 className="mr-5">Tìm lại mật khẩu!!!</h2>
                    <form onSubmit={handleSubmit}>
                        <div className="form-group mb-3">
                            <label htmlFor="email">Email</label>
                            <input
                                type="email"
                                name="email"
                                className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                                value={formData.email}
                                onChange={handleChange}
                                disabled={isLoading}
                                required
                            />
                            {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                        </div>
                        <div className="form-group">
                            <button type="submit" className="btn btn-primary btn-lg" disabled={isLoading}>
                                {isLoading ? <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> : "Gửi"}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default ForgotPassword;
