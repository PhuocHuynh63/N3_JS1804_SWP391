import React, { useEffect, useState } from 'react';
import './PopupDetailUser.css';
import { Modal } from 'antd';
import { meBeSrc } from '../../../../service/meBeSrc';
import PopupUpdateUser from '../updateUser/PopupUpdateUser';

const PopupDetailUser = ({ show, handleClose, user_id }) => {
    const [showUpdateUser, setShowUpdateUser] = useState(false);

    /**
     * Call API to get a user by id
     */
    const [user, setUser] = useState({});
    const [defaultAddress, setDefaultAddress] = useState({});
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        if (user_id) {
            meBeSrc.getUserById(user_id)
                .then((res) => {
                    const userData = res.data;
                    const defaultAddr = userData.listAddress.find(address => address.default);
                    setUser({
                        ...userData,
                        birthOfDate: formatDateToInput(userData.birthOfDate)
                    });
                    setDefaultAddress(defaultAddr || {});
                    setOrders(userData.order || []);
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
     * Calculate total amount spent by the user
     * @returns revenue
     */
    const calculateRevenue = () => {
        let completedOrders = orders.filter((item) => item.status === "Đã giao");
        let revenue = completedOrders.reduce((total, item) => total + item.totalAmount, 0);
        return revenue.toLocaleString('vi-VN');
    }
    //-----End-----//

    return (
        <>
            <Modal visible={show} onCancel={handleClose} footer={null} width={"auto"} centered>
                <div className="admin-user-detail">
                    <h1>Thông tin người dùng</h1>
                    <form>
                        <div className="form-group username">
                            <label htmlFor="userName">Tên đăng nhập</label>
                            <input
                                type="text"
                                id="userName"
                                value={user.username}
                                readOnly
                            />
                        </div>

                        <div className='name'>
                            <div className="form-group firstname">
                                <label htmlFor="firstName">Họ</label>
                                <input
                                    type="text"
                                    id="firstName"
                                    value={user.firstName}
                                    readOnly
                                />
                            </div>

                            <div className="form-group lastname">
                                <label htmlFor="lastName">Tên</label>
                                <input
                                    type="text"
                                    id="lastName"
                                    value={user.lastName}
                                    readOnly
                                />
                            </div>
                        </div>

                        <div className="form-group email">
                            <label htmlFor="email">Email</label>
                            <input
                                type="email"
                                id="email"
                                value={user.email}
                                readOnly
                            />
                        </div>

                        <div className="form-group phoneNumber">
                            <label htmlFor="phoneNumber">Số điện thoại</label>
                            <input
                                type="text"
                                id="phoneNumber"
                                value={user.phoneNumber}
                                readOnly
                            />
                        </div>

                        <div className="form-group password">
                            <label htmlFor="password">Mật khẩu</label>
                            <input
                                type="password"
                                id="password"
                                value={user.password}
                                readOnly
                            />
                        </div>

                        <div className="form-group birthOfDate">
                            <label htmlFor="birthOfDate">Ngày sinh</label>
                            <input
                                type="date"
                                id="birthOfDate"
                                value={user.birthOfDate}
                                readOnly
                            />
                        </div>

                        <div className="form-group role">
                            <label htmlFor="role">Ủy quyền</label>
                            <select
                                id="role"
                                value={user.role}
                                readOnly
                            >
                                <option value="member">Thành viên</option>
                                <option value="staff">Nhân viên</option>
                                <option value="admin">Quản trị viên</option>
                            </select>
                        </div>

                        <div className="form-group address">
                            <label>Địa chỉ mặc định</label>
                            <div className="address-details">
                                <p><strong>Tiêu đề:</strong> {defaultAddress.title}</p>
                                <p><strong>Địa chỉ:</strong> {defaultAddress.address}</p>
                            </div>
                        </div>

                        <div className="orders">
                            <h5>Có {orders.length} đơn hàng đã được đặt</h5>
                            <h5>Tổng số tiền đã tiêu: {calculateRevenue()} VND</h5>
                            <h5>Tổng số điểm đã tích lũy được: {user.point}</h5>
                        </div>

                        <div className="form-group_btn">
                            <button className="btn-detail" type="button" onClick={handleClose}>Đóng</button>
                            <button className="btn-update" type="button" onClick={() => { handleClose(); setShowUpdateUser(true) }}>
                                Cập nhật <i className="fa-solid fa-arrow-right"></i>
                            </button>
                        </div>
                    </form>
                </div>
            </Modal>
            <PopupUpdateUser show={showUpdateUser} handleClose={() => setShowUpdateUser(false)} user_id={user_id} />
        </>
    );
};

export default PopupDetailUser;
