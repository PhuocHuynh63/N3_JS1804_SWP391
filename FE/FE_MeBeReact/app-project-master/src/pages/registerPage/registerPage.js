import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import { meBeSrc } from '../../service/meBeSrc';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


export default function RegisterPage(){
    
    const navigate = useNavigate();
    const [errors, setErrors] = useState({});
    const formatDate = (date) => {
        const d = new Date(date);
        let day = d.getDate();
        let month = d.getMonth() + 1;
        const year = d.getFullYear();
    
        if (day < 10) {
            day = '0' + day;
        }
        if (month < 10) {
            month = '0' + month;
        }
    
        return `${day}/${month}/${year}`;
    };

    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        userName: '',
        email: '',
        password: '',
        birthOfDate: '',
        phone: '',
    });

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

        if (!formData.firstName) newErrors.firstName = 'Vui lòng nhập họ.';
        if (!formData.lastName) newErrors.lastName = 'Vui lòng nhập tên.';
        if (!formData.userName) newErrors.userName = 'Vui lòng nhập tên đăng nhập.';
        if (!formData.email) newErrors.email = 'Vui lòng nhập email.';
        if (!formData.password) newErrors.password = 'Vui lòng nhập mật khẩu.';
        if (!formData.birthOfDate) newErrors.birthOfDate = 'Vui lòng nhập ngày sinh.';
        if (!formData.phone) newErrors.phone = 'Vui lòng nhập số điện thoại.';

        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
        } else {
            const userData = {
                firstName: formData.firstName.trim(),
                lastName: formData.lastName.trim(),
                username: formData.userName.trim(),
                email: formData.email.trim(),
                password: formData.password.trim(),
                birthOfDate: formatDate(formData.birthOfDate),
                phoneNumber: formData.phone.trim(),
            };


            meBeSrc.userSignUp(userData)
                .then(response => {
                    console.log('Đăng ký thành công', response.data);
                    toast.success('Đăng ký thành công');
                    setTimeout(() => {
                        navigate('/');
                    }, 1000);
                })
                .catch(error => {
                    console.error('Đăng ký thất bại:', error);
                    if (error.response && error.response.data) {
                        console.error('Error response data:', error.response.data);
                        toast.error(error.response.data.msg); // Hiển thị thông báo lỗi
                    } else {
                        toast.error('Đã xảy ra lỗi, vui lòng thử lại sau.');
                    }
                });
        }
    };

    return (
        <div className="container col-6 mt-5  ">
            <ToastContainer position="top-right" autoClose={5000} hideProgressBar={false} newestOnTop={true} closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover />
            <h2>Đăng Ký</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group mb-3">
                    <div class="form-div">Họ</div>
                    <input
                        type="text"
                        name="firstName"
                        className={`form-control-sm ${errors.firstName ? 'is-invalid' : ''}`}
                        value={formData.firstName}
                        onChange={handleChange}
                    />
                    {errors.firstName && <div className="invalid-feedback">{errors.firstName}</div>}
                </div>
                <div className="form-group mb-3">
                    <div class="form-div">Tên</div>
                    <input
                        type="text"
                        name="lastName"
                        className={`form-control-sm ${errors.lastName ? 'is-invalid' : ''}`}
                        value={formData.lastName}
                        onChange={handleChange}
                    />
                    {errors.lastName && <div className="invalid-feedback">{errors.lastName}</div>}
                </div>
                <div className="form-group mb-3">
                    <div class="form-div">Tên Đăng Nhập</div>
                    <input
                        type="text"
                        name="userName"
                        className={`form-control-sm ${errors.userName ? 'is-invalid' : ''}`}
                        value={formData.userName}
                        onChange={handleChange}
                    />
                    {errors.userName && <div className="invalid-feedback">{errors.userName}</div>}
                </div>
                <div className="form-group mb-3">
                    <div class="form-div">Email</div>
                    <input
                        type="email"
                        name="email"
                        className={`form-control-sm ${errors.email ? 'is-invalid' : ''}`}
                        value={formData.email}
                        onChange={handleChange}
                    />
                    {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                </div>
                <div className="form-group mb-3">
                    <div class="form-div">Mật Khẩu</div>
                    <input
                        type="password"
                        name="password"
                        className={`form-control-sm ${errors.password ? 'is-invalid' : ''}`}
                        value={formData.password}
                        onChange={handleChange}
                    />
                    {errors.password && <div className="invalid-feedback">{errors.password}</div>}
                </div>
                <div className="form-group mb-3">
                    <div class="form-div">Ngày Sinh</div>
                    <input
                        type="date"
                        name="birthOfDate"
                        className={`form-control-sm ${errors.birthOfDate ? 'is-invalid' : ''}`}
                        value={formData.birthOfDate}
                        onChange={handleChange}
                    />
                    {errors.birthOfDate && <div className="invalid-feedback">{errors.birthOfDate}</div>}
                </div>
                <div className="form-group mb-3">
                    <div class="form-div">Số Điện Thoại</div>
                    <input
                        type="text"
                        name="phone"
                        className={`form-control-sm ${errors.phone ? 'is-invalid' : ''}`}
                        value={formData.phone}
                        onChange={handleChange}
                    />
                    {errors.phone && <div className="invalid-feedback">{errors.phone}</div>}
                </div>
                <div class="d-grid gap-2 col-6 mx-auto">
                    <button type="submit" className="btn btn-outline-primary btn-lg ">Đăng Ký</button>
                </div>
            </form>
        </div>    
    );

};

    
