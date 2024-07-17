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
    const [otp, setOtp] = useState('');
    const [otpSent, setOtpSent] = useState(false);
    const [otpVerified, setOtpVerified] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [isOtpLoading, setIsOtpLoading] = useState(false); 

    const formatDateToBackend = (date) => {
        const [year, month, day] = date.split('-');
        return `${day}/${month}/${year}`;
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === "birthOfDate") {
            setFormData({
                ...formData,
                birthOfDate: formatDateToBackend(value)
            });
        } else {
            setFormData({
                ...formData,
                [name]: value
            });
        }

        if (errors[name]) {
            setErrors({
                ...errors,
                [name]: ''
            });
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setIsLoading(true);
        if (isCheckingEmail) {
            if (!otpSent) {
                sendOtp();
            } else if (!otpVerified) {
                verifyOtp();
            }
        } else {
            validateAndSubmitForm();
        }
    };

    const sendOtp = () => {
        if (!formData.email.trim()) {
            toast.error('Vui lòng nhập email trước khi gửi mã OTP.');
            setIsLoading(false);
            return;
        }

        setIsOtpLoading(true);
        meBeSrc.sendOtp(formData.email)
            .then(response => {
                console.log('sendOtp response:', response);
                if (response.data.success) {
                    toast.success('Gửi mã xác minh thành công!');
                    setOtpSent(true);
                } else {
                    toast.error(response.data.description || 'Không thể gửi mã OTP, vui lòng thử lại sau.');
                }
            })
            .catch(error => {
                console.error('Error sending OTP:', error);
                const errorMessage = error.response && error.response.data && error.response.data.msg ? error.response.data.msg : 'Đã xảy ra lỗi khi gửi mã OTP, vui lòng thử lại sau.';
                toast.error(errorMessage);
            })
            .finally(() => {
                setIsOtpLoading(false);
                setIsLoading(false);
            });
    };

    const verifyOtp = () => {
        if (!otp.trim()) {
            toast.error('Vui lòng nhập mã OTP.');
            setIsLoading(false);
            return;
        }

        setIsOtpLoading(true);
        meBeSrc.checkOtp(otp)
            .then(response => {
                if (response.data === "Xác minh thành công") {
                    toast.success('Xác minh mã OTP thành công!');
                    setOtpVerified(true);
                    fetchUserData(); 
                } else {
                    toast.error(response.data || 'Mã xác minh không đúng!');
                    setOtpVerified(false);
                }
            })
            .catch(error => {
                console.error('Error verifying OTP:', error);
                const errorMessage = error.response && error.response.data ? error.response.data : 'Đã xảy ra lỗi khi xác minh mã OTP, vui lòng thử lại sau.';
                toast.error(errorMessage);
                setOtpVerified(false);
            })
            .finally(() => {
                setIsOtpLoading(false);
                setIsLoading(false);
            });
    };

    const fetchUserData = () => {
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
                        birthOfDate: user ? (user.birthOfDate ? formatDateToBackend(new Date(user.birthOfDate).toISOString().split('T')[0]) : '') : '',
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
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    const validateAndSubmitForm = () => {
        const newErrors = {};
        const isOnlyLetters = (name) => name.trim().length > 0 && /^[a-zA-ZàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlKmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ\s]+$/.test(name);
        const isValidEmail = (email) => email.trim().length > 0 && /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email);
        const isValidPassword = (password) => password.trim().length > 0 && password.length >= 6;
        const isValidPhone = (phone) => phone.trim().length > 0 &&/^\d{10,11}$/.test(phone);
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

        if (!formData.userName) {
            newErrors.userName = 'Vui lòng nhập tên đăng nhập.';
        } else if (!isOnlyLetters(formData.lastName)) {
            newErrors.userName = 'Tên đăng nhập chỉ được phép chứa chữ cái.';
        }
        if (!formData.email) {
            newErrors.email = 'Vui lòng nhập email.';
        } else if (!isValidEmail(formData.email)) {
            newErrors.email = 'Email không hợp lệ.';
        }
        if (!formData.password) {
            newErrors.password = 'Vui lòng nhập mật khẩu.';
        } else if (!isValidPassword(formData.password)) {
            newErrors.password = 'Mật khẩu phải có ít nhất 6 ký tự .';
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
            setIsLoading(false);
        } else {
            const userData = {
                firstName: formData.firstName,
                lastName: formData.lastName,
                username: formData.userName,
                email: formData.email,
                password: formData.password,
                birthOfDate: formData.birthOfDate, 
                phoneNumber: formData.phone,
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
                    })
                    .finally(() => {
                        setIsLoading(false);
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
                    })
                    .finally(() => {
                        setIsLoading(false);
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
                                    <div className="form-div">Điền email nếu bạn đã từng mua hàng để nhận voucher</div>
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
                                {!otpSent && (
                                    <div className="form-group mb-3">
                                        <button type="button" className="btn btn-outline-primary btn-lg" onClick={sendOtp} disabled={isOtpLoading}>
                                            {isOtpLoading ? 'Đang gửi...' : 'Kiểm Tra Email'}
                                        </button>
                                    </div>
                                )}
                                {otpSent && !otpVerified && (
                                    <>
                                        <div className="form-group mb-3">
                                            <div className="form-div">Mã OTP</div>
                                            <input
                                                type="text"
                                                name="otp"
                                                className={`form-control ${errors.otp ? 'is-invalid' : ''}`}
                                                value={otp}
                                                onChange={(e) => setOtp(e.target.value)}
                                                style={{ width: "100%" }}
                                            />
                                            {errors.otp && <div className="invalid-feedback">{errors.otp}</div>}
                                        </div>
                                        <div className="form-group mb-3">
                                            <button type="button" className="btn btn-outline-primary btn-lg" onClick={verifyOtp} disabled={isOtpLoading}>
                                                {isOtpLoading ? 'Đang xác minh...' : 'Xác Minh OTP'}
                                            </button>
                                        </div>
                                        <div className="form-group mb-3">
                                            <button type="button" className="btn btn-outline-primary btn-lg" onClick={sendOtp} disabled={isOtpLoading}>
                                                {isOtpLoading ? 'Đang gửi lại...' : 'Gửi lại OTP'}
                                            </button>
                                        </div>
                                    </>
                                )}
                                <div className="form-group mb-3">
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
                                        onChange={handleChange}
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
                                    <button type="submit" className="btn btn-outline-primary btn-lg" disabled={isLoading}>
                                        {isLoading ? 'Đang đăng ký...' : 'Đăng Ký'}
                                    </button>
                                </div>
                            </>
                        )}
                    </form>
                </div>
            </div>
        </div>
    );
};
