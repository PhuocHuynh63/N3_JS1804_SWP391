import React, { useEffect, useState } from 'react';
import './PopupUpdateUser.css';
import { Modal } from 'antd';
import { meBeSrc } from '../../../../service/meBeSrc';
import Successful from '../../../../components/popupSuccessful/Successful';

const PopupUpdateUser = ({ show, handleClose, user_id }) => {
    const [showModal, setShowModal] = useState(false);

    /**
     * Call API to get a user by id
     */
    const [user, setUser] = useState({});
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        birthOfDate: '',
        phoneNumber: '',
        role: '',
        point: 0,
    });

    useEffect(() => {
        if (user_id) {
            meBeSrc.getUserById(user_id)
                .then((res) => {
                    setUser(res.data);
                    setFormData({
                        firstName: res.data.firstName,
                        lastName: res.data.lastName,
                        email: res.data.email,
                        password: res.data.password,
                        birthOfDate: formatDateToInput(res.data.birthOfDate),
                        phoneNumber: res.data.phoneNumber,
                        role: res.data.role,
                        point: res.data.point,
                    });
                }).catch((err) => {
                    console.log(err);
                });
        }
    }, [user_id]);
    //-----End-----//

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
     * Handle change input
     * @param {*} e 
     */
    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.id]: e.target.value,
        });
    };
    //-----End-----//

    /**
     * Handle form submission
     * @param {*} e 
     */
    const handleSubmit = (e) => {
        e.preventDefault();
        const updatedFormData = {
            ...formData,
            birthOfDate: formatDateToBackend(formData.birthOfDate)
        };
        meBeSrc.putUserForAdmin(user_id, updatedFormData)
            .then((res) => {
                console.log(res.data);
                setShowModal(true);
                setTimeout(() => {
                    setShowModal(false);
                    handleClose();
                    window.location.reload();
                }, 3000);
            }).catch((err) => {
                console.log(err);
            });
    };
    //-----End-----//

    return (
        <Modal visible={show} onCancel={handleClose} footer={null} width={"auto"} centered>
            <Successful show={showModal} onHide={() => setShowModal(false)} message="Cập nhật thông tin thành công!" />
            <div className="admin-user-update">
                <h1>Cập nhật thông tin</h1>
                <form onSubmit={handleSubmit}>
                    <div className="form-group username">
                        <label htmlFor="userName">Tên đăng nhập</label>
                        <input
                            type="text"
                            id="userName"
                            value={user.username}
                            onChange={handleChange}
                            readOnly
                        />
                    </div>

                    <div className='name'>
                        <div className="form-group firstname">
                            <label htmlFor="firstName">Họ</label>
                            <input
                                type="text"
                                id="firstName"
                                value={formData.firstName}
                                onChange={handleChange}
                            />
                        </div>

                        <div className="form-group lastname">
                            <label htmlFor="lastName">Tên</label>
                            <input
                                type="text"
                                id="lastName"
                                value={formData.lastName}
                                onChange={handleChange}
                            />
                        </div>
                    </div>

                    <div className="form-group date">
                        <label htmlFor="birthOfDate">Ngày sinh</label>
                        <input
                            type="date"
                            id="birthOfDate"
                            value={formData.birthOfDate}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group email">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            value={formData.email}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group phoneNumber">
                        <label htmlFor="phoneNumber">Số điện thoại</label>
                        <input
                            type="text"
                            id="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group password">
                        <label htmlFor="password">Mật khẩu</label>
                        <input
                            type="password"
                            id="password"
                            value={formData.password}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group role_point">
                        <div className="form-group role">
                            <label htmlFor="role">Ủy quyền</label>
                            <select
                                id="role"
                                value={formData.role}
                                onChange={handleChange}
                            >
                                <option value="member">Thành viên</option>
                                <option value="staff">Nhân viên</option>
                                <option value="admin">Quản trị viên</option>
                            </select>
                        </div>


                        <div className="form-group point">
                            <label htmlFor="point">Điểm</label>
                            <input
                                type="number"
                                id="point"
                                value={formData.point}
                                onChange={handleChange}
                            />
                        </div>
                    </div>

                    <div className="form-group_btn">
                        <button className="btn-close_update" type="button" onClick={handleClose}>Đóng</button>
                        <button className="btn-update" type="submit">Lưu</button>
                    </div>
                </form>
            </div>
        </Modal>
    );
};

export default PopupUpdateUser;
