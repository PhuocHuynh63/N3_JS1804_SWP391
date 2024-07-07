import React, { useState } from 'react';
import './PopupAddUser.css';
import { Modal } from 'antd';

const PopupAddUser = ({ show, handleClose }) => {

    /**
     * Form data
     */
    const [formData, setFormData] = useState({
        userName: '',
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        password: '',
        status: '',
        birthOfDate: '',
        role: '',
    });
    //-----End-----//


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
        console.log({
            userName: formData.userName,
            firstName: formData.firstName,
            lastName: formData.lastName,
            email: formData.email,
            phoneNumber: formData.phoneNumber,
            password: formData.password,
            status: formData.status,
            role: formData.role,
        });
    };
    //-----End-----//


    return (
        <Modal visible={show} onCancel={handleClose} footer={null} width={"auto"} centered>
            <div className="admin-user-add">
                <h1>Thêm người dùng mới</h1>
                <form onSubmit={handleSubmit}>
                    <div className="form-group username">
                        <label htmlFor="userName">Tên đăng nhập</label>
                        <input
                            type="text"
                            id="userName"
                            value={formData.userName}
                            onChange={handleChange}
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


                    <div className="form-group email">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            value={formData.email}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group email">
                        <label htmlFor="phoneNumber">Số điện thoại</label>
                        <input
                            type="phoneNumber"
                            id="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group birthOfDate">
                        <label htmlFor="birthOfDate">Ngày sinh</label>
                        <input
                            type="date"
                            id="birthOfDate"
                            value={formData.birthOfDate}
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
                            required
                        />
                    </div>

                    <div className="form-group role">
                        <label htmlFor="role">Ủy quyền</label>
                        <select
                            id="role"
                            value={formData.role}
                            onChange={handleChange}
                            required
                        >
                            <option value="">--- Chọn quyền ---</option>
                            <option value="member">Thành viên</option>
                            <option value="staff">Nhân viên</option>
                            <option value="admin">Quản trị viên</option>
                        </select>
                    </div>

                    <div className="form-group hidden">
                        <input
                            id="status"
                            value='active'
                            onChange={handleChange}
                            type="hidden"
                        />
                    </div>

                    <div className="form-group">
                        <button className="btn-add" type="submit">Thêm người dùng</button>
                    </div>
                </form>
            </div>
        </Modal>
    );
};

export default PopupAddUser;
