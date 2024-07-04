import React, { useEffect, useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import './ProfileUser.css';
import { meBeSrc } from "../../service/meBeSrc";
import { jwtDecode } from "jwt-decode";
import Successful from "../../components/successful/Successful";

export default function ProfileUser() {
    const [user, setUser] = useState({
        username: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        birthOfDate: "",
    });

    const [showModal, setShowModal] = useState(false);


    /**
     * Format the date to yyyy-MM-dd for input type="date"
     */
    const formatDateToInput = (date) => {
        const d = new Date(date);
        const month = `0${d.getMonth() + 1}`.slice(-2);
        const day = `0${d.getDate()}`.slice(-2);
        const year = d.getFullYear();
        return `${year}-${month}-${day}`;
    };
    //-----End------//


    /**
     * Format the date to dd/MM/yyyy for backend
     */
    const formatDateToBackend = (date) => {
        const [year, month, day] = date.split('-');
        return `${day}/${month}/${year}`;
    };
    //-----End------//


    /**
     * Take user info (username) from local storage by token
     */
    useEffect(() => {
        const token = localStorage.getItem('USER_INFO');
        if (token) {
            const decoded = jwtDecode(token);
            const username = decoded.sub;

            meBeSrc.getUserByUserName(username)
                .then((res) => {
                    const userData = {
                        ...res.data,
                        birthOfDate: formatDateToInput(res.data.birthOfDate) // Formatting the date for input
                    };
                    setUser(userData);
                    console.log("User data", {
                        username: userData.username,
                        firstName: userData.firstName,
                        lastName: userData.lastName,
                        phoneNumber: userData.phoneNumber,
                        birthOfDate: userData.birthOfDate
                    });
                })
                .catch((err) => {
                    console.log("Error fetching user", err);
                });
        }
    }, []);
    //-----End------//


    /**
     * Change value when input change
     * @param {*} e 
     */
    const handleInputChange = (e) => {
        const { id, value } = e.target;
        setUser((prevState) => ({
            ...prevState,
            [id]: value
        }));
    };
    //-----End------//


    /**
     * Handle form submission
     */
    const handleSubmit = (e) => {
        e.preventDefault();
        const updatedUser = {
            ...user,
            birthOfDate: formatDateToBackend(user.birthOfDate) // Formatting the date for backend
        };

        meBeSrc.updateUserProfile(user.id, updatedUser)
            .then((res) => {
                setShowModal(true);
                setTimeout(() => setShowModal(false), 3000); // Hide modal after 3 seconds
                console.log("User updated successfully", {
                    username: user.username,
                    firstName: user.firstName,
                    lastName: user.lastName,
                    phoneNumber: user.phoneNumber,
                    birthOfDate: user.birthOfDate
                });
            })
            .catch((err) => {
                console.log("Error updating user", err);
            });
    };
    //-----End------//


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
                                <label htmlFor="firstName" className="small-label profile">Họ</label>
                                <input type="text" id="firstName" className="form-control small-input profile" value={user.firstName} onChange={handleInputChange} />
                            </div>
                            <div className="form-group-profile small-form-group">
                                <label htmlFor="lastName" className="small-label profile">Tên</label>
                                <input type="text" id="lastName" className="form-control small-input profile" value={user.lastName} onChange={handleInputChange} />
                            </div>
                            <div className="form-group-profile small-form-group">
                                <label htmlFor="phoneNumber" className="small-label profile">Số điện thoại</label>
                                <input type="text" id="phoneNumber" className="form-control small-input profile" value={user.phoneNumber} readOnly />
                            </div>
                            <div className="form-group-profile small-form-group">
                                <label htmlFor="birthOfDate" className="small-label profile">Ngày sinh</label>
                                <input type="date" id="birthOfDate" className="form-control small-input profile" value={user.birthOfDate} onChange={handleInputChange} />
                            </div>
                            <button type="submit" className="small-btn profile-save">Lưu</button>
                        </form>
                    </div>
                </div>
            </section>
        </div>
    );
}
