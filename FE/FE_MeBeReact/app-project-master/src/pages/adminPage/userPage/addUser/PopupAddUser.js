import React, { useState } from 'react';
import './PopupAddUser.css';
import { Modal } from 'antd';
import { meBeSrc } from '../../../../service/meBeSrc';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const PopupAddUser = ({ show, handleClose }) => {

    //-----------------------------Form Data-----------------------------//
    /**
     * Form data for adding a new user
     */
    const [formData, setFormData] = useState({
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        password: '',
        status: 'active',
        birthOfDate: '',
        role: '',
        point: 0,
    });
    //------End------//

    const [errors, setErrors] = useState({});

    /**
     * Handle input change
     * @param {*} e 
     */
    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.id]: e.target.value,
        });
    };
    //------End------//

    /**
     * Change date format from 'yyyy-mm-dd' to 'dd/mm/yyyy'
     * @param {*} date 
     * @returns 
     */
    const formatDateToBackend = (date) => {
        const [year, month, day] = date.split('-');
        return `${day}/${month}/${year}`;
    };
    //------End------//
    //-------------------------------------------------------------------//

    /**
     * Validate the form data
     */
    const validateForm = () => {
        const newErrors = {};
        const today = new Date().toISOString().split('T')[0];

        if (!formData.username) newErrors.username = 'Tên đăng nhập là bắt buộc';
        if (!formData.firstName) newErrors.firstName = 'Họ là bắt buộc';
        if (!formData.lastName) newErrors.lastName = 'Tên là bắt buộc';
        if (!formData.email) newErrors.email = 'Email là bắt buộc';
        if (!formData.phoneNumber) {
            newErrors.phoneNumber = 'Số điện thoại là bắt buộc';
        } else if (!/^\d{10,11}$/.test(formData.phoneNumber)) {
            newErrors.phoneNumber = 'Số điện thoại phải từ 10 đến 11 ký tự số';
        }
        if (!formData.birthOfDate) {
            newErrors.birthOfDate = 'Ngày sinh là bắt buộc';
        } else if (formData.birthOfDate > today) {
            newErrors.birthOfDate = 'Ngày sinh không được ở tương lai';
        }
        if (!formData.password) {
            newErrors.password = 'Mật khẩu là bắt buộc';
        } else if (formData.password.length < 6) {
            newErrors.password = 'Mật khẩu tối thiểu 6 ký tự';
        }
        if (!formData.role) newErrors.role = 'Ủy quyền là bắt buộc';
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };
    //------End------//

    /**
     * Handle Submit
     * @param {*} e 
     */
    const handleSubmit = (e) => {
        e.preventDefault();
        if (!validateForm()) {
            toast.error('Vui lòng kiểm tra lại thông tin');
            return;
        }

        const formattedData = {
            ...formData,
            birthOfDate: formData.birthOfDate ? formatDateToBackend(formData.birthOfDate) : '',
        };

        meBeSrc.postUserForAdmin(formattedData)
            .then((res) => {
                toast.success('Thêm người dùng thành công');
                setTimeout(() => {
                    handleClose();
                    window.location.reload();
                }, 1000); // Wait 1 second before closing and reloading
            })
            .catch((error) => {
                toast.error('Có lỗi xảy ra: ' + (error.response?.data?.msg || error.message));
            });
    };
    //------End------//

    return (
        <Modal visible={show} onCancel={handleClose} footer={null} width="auto" centered>
            <ToastContainer />
            <div className="admin-user-add">
                <h1>Thêm người dùng mới</h1>
                <form onSubmit={handleSubmit}>
                    <div className="form-group username">
                        <label htmlFor="username">Tên đăng nhập</label>
                        <input
                            type="text"
                            id="username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                        />
                        {errors.username && <p className="error">{errors.username}</p>}
                    </div>

                    <div className='name'>
                        <div className="form-group firstname">
                            <label htmlFor="firstName">Họ</label>
                            <input
                                type="text"
                                id="firstName"
                                value={formData.firstName}
                                onChange={handleChange}
                                required
                            />
                            {errors.firstName && <p className="error">{errors.firstName}</p>}
                        </div>

                        <div className="form-group lastname">
                            <label htmlFor="lastName">Tên</label>
                            <input
                                type="text"
                                id="lastName"
                                value={formData.lastName}
                                onChange={handleChange}
                                required
                            />
                            {errors.lastName && <p className="error">{errors.lastName}</p>}
                        </div>
                    </div>

                    <div className="form-group email">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                        {errors.email && <p className="error">{errors.email}</p>}
                    </div>

                    <div className="form-group phoneNumber">
                        <label htmlFor="phoneNumber">Số điện thoại</label>
                        <input
                            type="text"
                            id="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                            required
                        />
                        {errors.phoneNumber && <p className="error">{errors.phoneNumber}</p>}
                    </div>

                    <div className="form-group birthOfDate">
                        <label htmlFor="birthOfDate">Ngày sinh</label>
                        <input
                            type="date"
                            id="birthOfDate"
                            value={formData.birthOfDate}
                            onChange={handleChange}
                            required
                        />
                        {errors.birthOfDate && <p className="error">{errors.birthOfDate}</p>}
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
                        {errors.password && <p className="error">{errors.password}</p>}
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
                        {errors.role && <p className="error">{errors.role}</p>}
                    </div>

                    <div className="form-group hidden">
                        <input
                            type="hidden"
                            id="status"
                            value='active'
                            onChange={handleChange}
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
