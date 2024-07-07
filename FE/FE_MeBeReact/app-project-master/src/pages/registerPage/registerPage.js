import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import { meBeSrc } from '../../service/meBeSrc';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import bannerLogin from "../../images/Logo_Login.jpg";

export default function RegisterPage() {
    const navigate = useNavigate();
    const [errors, setErrors] = useState({});
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        userName: '',
        email: '',
        password: '',
        birthOfDate: '',
        phone: '',
    });
    const [isCheckingEmail, setIsCheckingEmail] = useState(true);
    const [isExistingUser, setIsExistingUser] = useState(false);
    const [userId, setUserId] = useState(null); 

    const formatDateToDDMMYYYY = (date) => {
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

        return `${day}/${month}/${year}`; // Định dạng ngày theo dd/MM/yyyy
    };

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

    const handleDateChange = (e) => {
        const date = e.target.value;
        setFormData({
            ...formData,
            birthOfDate: formatDateToDDMMYYYY(date)
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (isCheckingEmail) {
            checkEmail();
        } else {
            validateAndSubmitForm();
        }
    };

    const checkEmail = () => {
        if (!formData.email.trim()) {
            setIsCheckingEmail(false);
            return;
        }

        meBeSrc.getUserByEmail(formData.email)
            .then(response => {
                const user = response.data;
                if (user && user.username) {
                    toast.error('Email đã có tài khoản, vui lòng đăng nhập.');
                } else {
                    setIsExistingUser(true);
                    setUserId(user ? user.id : null); // Lưu userId nếu có
                    setFormData({
                        ...formData,
                        firstName: user ? (user.firstName || '') : '',
                        lastName: user ? (user.lastName || '') : '',
                        userName: user ? (user.username || '') : '',
                        birthOfDate: user ? (user.birthOfDate ? formatDateToDDMMYYYY(new Date(user.birthOfDate)) : '') : '',
                        phone: user ? (user.phoneNumber || '') : ''
                    });
                    setIsCheckingEmail(false);
                }
            })
            .catch(error => {
                setIsCheckingEmail(false);
                setIsExistingUser(false);
                console.error('Error checking email:', error);
                toast.error('Đã xảy ra lỗi khi kiểm tra email, vui lòng thử lại sau.');
            });
    };

    const validateAndSubmitForm = () => {
        const newErrors = {};
        const isOnlyLetters = (name) => /^[a-zA-ZàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlKmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ\s]+$/.test(name);
        const isValidEmail = (email) => /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email);
        const isValidPassword = (password) => password.length >= 6 && /[0-9]/.test(password);
        const isValidPhone = (phone) => /^\d{10,11}$/.test(phone);
        const isFutureDate = (date) => {
            const [day, month, year] = date.split('/');
            return new Date(`${year}-${month}-${day}`) > new Date();
        };

        if (!formData.firstName) {
            newErrors.firstName = 'Vui lòng nhập họ.';
        } else if (!isOnlyLetters(formData.firstName)) {
            newErrors.firstName = 'Họ chỉ được phép chứa chữ cái.';
        }

        if (!formData.lastName) {
            newErrors.lastName = 'Vui lòng nhập tên.';
        } else if (!isOnlyLetters(formData.lastName)) {
            newErrors.lastName = 'Tên chỉ được phép chứa chữ cái.';
        }

        if (!formData.userName) newErrors.userName = 'Vui lòng nhập tên đăng nhập.';
        if (!formData.email) {
            newErrors.email = 'Vui lòng nhập email.';
        } else if (!isValidEmail(formData.email)) {
            newErrors.email = 'Email không hợp lệ.';
        }
        if (!formData.password) {
            newErrors.password = 'Vui lòng nhập mật khẩu.';
        } else if (!isValidPassword(formData.password)) {
            newErrors.password = 'Mật khẩu phải có ít nhất 6 ký tự số.';
        }
        if (!formData.birthOfDate) {
            newErrors.birthOfDate = 'Vui lòng nhập ngày sinh.';
        } else if (isFutureDate(formData.birthOfDate)) {
            newErrors.birthOfDate = 'Ngày sinh không thể ở tương lai.';
        }
        if (!formData.phone) {
            newErrors.phone = 'Vui lòng nhập số điện thoại.';
        } else if (!isValidPhone(formData.phone)) {
            newErrors.phone = 'Số điện thoại phải có 10-11 số.';
        }

        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
        } else {
            const userData = {
                firstName: formData.firstName.trim(),
                lastName: formData.lastName.trim(),
                username: formData.userName.trim(),
                email: formData.email.trim(),
                password: formData.password.trim(),
                birthOfDate: formData.birthOfDate.trim(), // Giữ định dạng dd/MM/yyyy
                phoneNumber: formData.phone.trim(),
            };

            console.log('Sending user data:', userData); // Log payload để kiểm tra

            if (isExistingUser && userId) {
                meBeSrc.updateGuestToUser(userId, userData)
                    .then(response => {
                        console.log('Đăng ký thành công', response.data);
                        toast.success('Đăng ký thành công');
                        setTimeout(() => {
                            navigate('/signin');
                        }, 1000);
                    })
                    .catch(error => {
                        console.error('Đăng ký thất bại:', error);
                        if (error.response && error.response.data) {
                            console.error('Error response data:', error.response.data);
                            toast.error(error.response.data.msg); // Hiển thị thông báo lỗi từ phản hồi API
                        } else {
                            toast.error('Đã xảy ra lỗi, vui lòng thử lại sau.');
                        }
                    });
            } else {
                meBeSrc.userSignUp(userData)
                    .then(response => {
                        console.log('Đăng ký thành công', response.data);
                        toast.success('Đăng ký thành công');
                        setTimeout(() => {
                            navigate('/signin');
                        }, 1000);
                    })
                    .catch(error => {
                        console.error('Đăng ký thất bại:', error);
                        if (error.response && error.response.data) {
                            console.error('Error response data:', error.response.data);
                            toast.error(error.response.data.msg); // Hiển thị thông báo lỗi từ phản hồi API
                        } else {
                            toast.error('Đã xảy ra lỗi, vui lòng thử lại sau.');
                        }
                    });
            }
        }
    };

    return (
        <div className="container col-12 mt-5">
            <ToastContainer position="top-right" autoClose={5000} hideProgressBar={false} newestOnTop={true} closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover />
            <div className="row">
                <div className="col-md-6">
                    <img src={bannerLogin} alt="Descriptive Text" style={{ width: "100%", height: "100%", objectFit: "cover" }} />
                </div>
                <div className="col-md-6">
                    <h2>Tham gia cùng chúng tôi</h2>
                    <form onSubmit={handleSubmit}>
                        {isCheckingEmail ? (
                            <>
                                <div className="form-group mb-3">
                                    <div className="form-div">Email</div>
                                    <input
                                        type="email"
                                        name="email"
                                        className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                                        value={formData.email}
                                        onChange={handleChange}
                                        style={{ width: "100%" }}
                                    />
                                    {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                                </div>
                                <div className="form-group mb-3">
                                    <button type="button" className="btn btn-outline-primary btn-lg" onClick={checkEmail}>Kiểm Tra Email</button>
                                    <button type="button" className="btn btn-outline-primary btn-lg" onClick={() => setIsCheckingEmail(false)}>Bỏ Qua</button>
                                </div>
                            </>
                        ) : (
                            <>
                                <div className="form-group mb-3">
                                    <div className="form-div">Họ</div>
                                    <input
                                        type="text"
                                        name="firstName"
                                        className={`form-control ${errors.firstName ? 'is-invalid' : ''}`}
                                        value={formData.firstName}
                                        onChange={handleChange}
                                        style={{ width: "100%" }}
                                    />
                                    {errors.firstName && <div className="invalid-feedback">{errors.firstName}</div>}
                                </div>
                                <div className="form-group mb-3">
                                    <div className="form-div">Tên</div>
                                    <input
                                        type="text"
                                        name="lastName"
                                        className={`form-control ${errors.lastName ? 'is-invalid' : ''}`}
                                        value={formData.lastName}
                                        onChange={handleChange}
                                        style={{ width: "100%" }}
                                    />
                                    {errors.lastName && <div className="invalid-feedback">{errors.lastName}</div>}
                                </div>
                                <div className="form-group mb-3">
                                    <div className="form-div">Tên Đăng Nhập</div>
                                    <input
                                        type="text"
                                        name="userName"
                                        className={`form-control ${errors.userName ? 'is-invalid' : ''}`}
                                        value={formData.userName}
                                        onChange={handleChange}
                                        style={{ width: "100%" }}
                                    />
                                    {errors.userName && <div className="invalid-feedback">{errors.userName}</div>}
                                </div>
                                <div className="form-group mb-3">
                                    <div className="form-div">Email</div>
                                    <input
                                        type="email"
                                        name="email"
                                        className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                                        value={formData.email}
                                        onChange={handleChange}
                                        style={{ width: "100%" }}
                                        readOnly={isExistingUser}
                                    />
                                    {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                                </div>
                                <div className="form-group mb-3">
                                    <div className="form-div">Mật Khẩu</div>
                                    <input
                                        type="password"
                                        name="password"
                                        className={`form-control ${errors.password ? 'is-invalid' : ''}`}
                                        value={formData.password}
                                        onChange={handleChange}
                                        style={{ width: "100%" }}
                                    />
                                    {errors.password && <div className="invalid-feedback">{errors.password}</div>}
                                </div>
                                <div className="form-group mb-3">
                                    <div className="form-div">Ngày Sinh</div>
                                    <input
                                        type="date"
                                        name="birthOfDate"
                                        className={`form-control ${errors.birthOfDate ? 'is-invalid' : ''}`}
                                        value={formData.birthOfDate ? formData.birthOfDate.split('/').reverse().join('-') : ''}
                                        onChange={handleDateChange}
                                        style={{ width: "100%" }}
                                    />
                                    {errors.birthOfDate && <div className="invalid-feedback">{errors.birthOfDate}</div>}
                                </div>
                                <div className="form-group mb-3">
                                    <div className="form-div">Số Điện Thoại</div>
                                    <input
                                        type="text"
                                        name="phone"
                                        className={`form-control ${errors.phone ? 'is-invalid' : ''}`}
                                        value={formData.phone}
                                        onChange={handleChange}
                                        style={{ width: "100%" }}
                                    />
                                    {errors.phone && <div className="invalid-feedback">{errors.phone}</div>}
                                </div>
                                <div className="form-group mb-3">
                                    <button type="submit" className="btn btn-outline-primary btn-lg">Đăng Ký</button>
                                </div>
                            </>
                        )}
                    </form>
                </div>
            </div>
        </div>
    );
};
