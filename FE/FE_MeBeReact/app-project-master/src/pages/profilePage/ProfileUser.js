import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import './ProfileUser.css';
import { meBeSrc } from "../../service/meBeSrc";
import {jwtDecode} from "jwt-decode";
import Successful from "../../components/popupSuccessful/Successful";

export default function ProfileUser() {
    const navigate = useNavigate();
    const [user, setUser] = useState({
        username: "",
        email:"",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        birthOfDate: "",
    });

    const [showModal, setShowModal] = useState(false);
    const [errors, setErrors] = useState({});

    const formatDateToInput = (date) => {
        const d = new Date(date);
        const month = `0${d.getMonth() + 1}`.slice(-2);
        const day = `0${d.getDate()}`.slice(-2);
        const year = d.getFullYear();
        return `${year}-${month}-${day}`;
    };

    const formatDateToBackend = (date) => {
        const [year, month, day] = date.split('-');
        return `${day}/${month}/${year}`;
    };

    useEffect(() => {
        const token = localStorage.getItem('USER_INFO');
        if (!token) {
            navigate('/');
        } else {
            const decoded = jwtDecode(token);
            const username = decoded.sub;

            meBeSrc.getUserByUserName(username)
                .then((res) => {
                    const userData = {
                        ...res.data,
                        birthOfDate: formatDateToInput(res.data.birthOfDate)
                    };
                    setUser(userData);
                })
                .catch((err) => {
                    console.log("Error fetching user", err);
                });
        }
    }, [navigate]);

    const handleInputChange = (e) => {
        const { id, value } = e.target;
        setUser((prevState) => ({
            ...prevState,
            [id]: value
        }));
    };

    const validateForm = () => {
        const errors = {};

        if (!user.firstName || /\d/.test(user.firstName)) {
            errors.firstName = "Họ không được để trống hoặc có số.";
        }
        if (!user.lastName || /\d/.test(user.lastName)) {
            errors.lastName = "Tên không được để trống hoặc có số.";
        }

        if (new Date(user.birthOfDate) > new Date()) {
            errors.birthOfDate = "Ngày sinh không được ở tương lai.";
        }

        setErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (validateForm()) {
            const updatedUser = {
                ...user,
                birthOfDate: formatDateToBackend(user.birthOfDate)
            };

            meBeSrc.updateUserProfile(user.id, updatedUser)
                .then((res) => {
                    setShowModal(true);
                    setTimeout(() => setShowModal(false), 3000);
                })
                .catch((err) => {
                    console.log("Error updating user", err);
                });
        }
    };

    return (
        <div className="profileUser">
            <Successful show={showModal} onHide={() => setShowModal(false)} message={"Thông tin đã được cập nhật"} />
            <section className="profile-details container small-profile">
                <div className="row profile">
                    <div className="col-md-6 profile2">
                        <div className="profile-header">
                            <h1 className="small-header">Hồ Sơ Của Tôi</h1>
                            <p className="small-subheader">Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
                        </div>
                        <form className="profile-form small-profile-form profile-form" onSubmit={handleSubmit}>
                            <div className="form-group-profile small-form-group">
                                <label htmlFor="username" className="small-label profile">Tên đăng nhập</label>
                                <input type="text" id="username" className="form-control small-input profile" value={user.username} readOnly style={{ backgroundColor: `#e0e0e0` }} />
                            </div>
                            <div className="form-group-profile small-form-group">
                                <label htmlFor="email" className="small-label profile">Email</label>
                                <input type="text" id="email" className="form-control small-input profile" value={user.email} readOnly style={{ backgroundColor: `#e0e0e0` }} />
                            </div>
                            <div className="form-group-profile small-form-group">
                                <label htmlFor="firstName" className="small-label profile">Họ</label>
                                <input type="text" id="firstName" className="form-control small-input profile" value={user.firstName} onChange={handleInputChange} />
                                {errors.firstName && <div className="error">{errors.firstName}</div>}
                            </div>
                            <div className="form-group-profile small-form-group">
                                <label htmlFor="lastName" className="small-label profile">Tên</label>
                                <input type="text" id="lastName" className="form-control small-input profile" value={user.lastName} onChange={handleInputChange} />
                                {errors.lastName && <div className="error">{errors.lastName}</div>}
                            </div>
                            <div className="form-group-profile small-form-group">
                                <label htmlFor="phoneNumber" className="small-label profile">Số điện thoại</label>
                                <input type="text" id="phoneNumber" className="form-control small-input profile" value={user.phoneNumber} readOnly  style={{ backgroundColor: `#e0e0e0` }} />
                            </div>
                            <div className="form-group-profile small-form-group">
                                <label htmlFor="birthOfDate" className="small-label profile">Ngày sinh</label>
                                <input type="date" id="birthOfDate" className="form-control small-input profile" value={user.birthOfDate} onChange={handleInputChange} />
                                {errors.birthOfDate && <div className="error">{errors.birthOfDate}</div>}
                            </div>
                            <button type="submit" className="small-btn profile-save">Lưu</button>
                        </form>
                    </div>
                </div>
            </section>
        </div>
    );
}
