import React, { useEffect, useState } from "react";
import "./AdminUser.css";
import PopupAddUser from "./PopupAddUser";
import { meBeSrc } from "../../../service/meBeSrc";
import Pagination from '../../../components/pagination/Pagination';

export default function AdminUser() {
  const [showAddUser, setShowAddUser] = useState(false);


  /**
   * List of users
   */
  const [users, setUsers] = useState([]);
  useEffect(() => {
    meBeSrc.getListUser()
      .then((res) => {
        setUsers(res.data);
        console.log(res.data);
      }).catch((err) => {
        console.log(err);
      });
  }, []);
  //-----End-----//


  /**
   * Get role of user
   * @param {*} role 
   * @returns 
   */
  const getRole = (role) => {
    switch (role) {
      case 'admin':
        return "Quản trị viên";
      case 'staff':
        return "Nhân viên";
      case 'member':
        return "Thành viên";
      case 'guest':
        return "Khách vãng lai";
      default:
        return "Unknown";
    }
  };
  //-----End-----//


  /**
   * Pagination
   */
  const [currentPage, setCurrentPage] = useState(1);
  const usersPerPage = 10;

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const currentUsers = users.slice((currentPage - 1) * usersPerPage, currentPage * usersPerPage);
  const totalPages = Math.ceil(users.length / usersPerPage);
  //-----End-----//

  return (
    <div className="admin-user">
      <PopupAddUser show={showAddUser} handleClose={() => setShowAddUser(false)} />

      <h1>Quản lý người dùng</h1>

      <button className="btn-add_user" onClick={() => setShowAddUser(true)}> + Thêm người dùng mới</button>

      <div className="box-user">
        <table className="user-list">
          <thead>
            <tr>
              <th>ID</th>
              <th>Tên người dùng</th>
              <th>Email</th>
              <th>Ngày lập</th>
              <th>Khóa</th>
              <th>Ủy quyền</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            {currentUsers.map((user, index) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.firstName} {user.lastName}</td>
                <td>{user.email}</td>
                <td>{new Date(user.createAt).toLocaleDateString()}</td>
                <td>
                  <div className="container-checkbox">
                    <input type="checkbox" className="checkbox" id={`checkbox-${index}`} />
                    <label className="switch" htmlFor={`checkbox-${index}`}>
                      <span className="slider"></span>
                    </label>
                  </div>
                </td>
                <td>{getRole(user.role)}</td>
                <td>
                  <div className="action">
                    <a className="delete btn btn-warning btn-sm" href="#">
                      <i className="fa-solid fa-eye"></i>
                    </a>
                    <a className="edit btn btn-success btn-sm" href="#">
                      <i className="fa-solid fa-pen-to-square"></i>
                    </a>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={handlePageChange}
      />
    </div>
  );
}
