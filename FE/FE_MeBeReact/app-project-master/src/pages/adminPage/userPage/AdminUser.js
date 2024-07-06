import React, { useEffect, useState } from "react";
import "./AdminUser.css";
import { meBeSrc } from "../../../service/meBeSrc";
import Pagination from '../../../components/pagination/Pagination';
import PopupAddUser from "./addUser/PopupAddUser";
import PopupUpdateUser from "./updateUser/PopupUpdateUser";
import PopupDetailUser from "./detailUser/PopupDetailUser";

export default function AdminUser() {
  const [showAddUser, setShowAddUser] = useState(false);
  const [showDetailUser, setShowDetailUser] = useState(false);
  const [showUpdateUser, setShowUpdateUser] = useState(false);
  const [selectedUserId, setSelectedUserId] = useState(null);

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

  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    if (searchTerm) {
      meBeSrc.getSearchUserByName(searchTerm)
        .then((res) => {
          setUsers(res.data);
          console.log(res.data);
        })
        .catch((err) => {
          console.log("Error fetching users", err);
        });
    } else {
      meBeSrc.getListUser()
        .then((res) => {
          setUsers(res.data);
        })
        .catch((err) => {
          console.log("Error fetching users", err);
        });
    }
  }, [searchTerm]);

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleStatusChange = (userId, event) => {
    const isChecked = event.target.checked;
    const newStatus = isChecked ? 'ban' : 'active';
    meBeSrc.putUserStatus(userId, newStatus)
      .then((res) => {
        console.log(res.data);
        alert(res.data); // Hiển thị thông báo thành công hoặc thất bại
      })
      .catch((err) => {
        console.log("Error updating user status", err);
      });
  };

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

  const [currentPage, setCurrentPage] = useState(1);
  const usersPerPage = 10;

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const currentUsers = users.slice((currentPage - 1) * usersPerPage, currentPage * usersPerPage);
  const totalPages = Math.ceil(users.length / usersPerPage);

  return (
    <div className="admin-user">
      <PopupAddUser show={showAddUser} handleClose={() => setShowAddUser(false)} />
      <PopupUpdateUser show={showUpdateUser} handleClose={() => setShowUpdateUser(false)} user_id={selectedUserId} />
      <PopupDetailUser show={showDetailUser} handleClose={() => setShowDetailUser(false)} user_id={selectedUserId} />

      <h1>Quản lý người dùng</h1>

      <div className="admin-user_action">
        <button className="btn-add_user" onClick={() => setShowAddUser(true)}> + Thêm người dùng mới</button>
        <div className="admin-user_search">
          <input type="text" className="admin-user_searchinput" placeholder="Nhập người dùng cần tìm" onChange={handleSearchChange} value={searchTerm} />
          <i id="search" className="fa-solid fa-magnifying-glass"></i>
        </div>
      </div>

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
                    <input
                      type="checkbox"
                      className="checkbox"
                      id={`checkbox-${index}`}
                      value={user.status === 'active' ? false : true}
                    // onChange={(event) => handleStatusChange(user.id, event)}
                    />
                    {console.log(user.status)}
                    <label className="switch" htmlFor={`checkbox-${index}`}>
                      <span className="slider"></span>
                    </label>
                  </div>
                </td>
                <td>{getRole(user.role)}</td>
                <td>
                  <div className="action">
                    <button className="btn btn-warning btn-sm" onClick={() => { setShowDetailUser(true); setSelectedUserId(user.id) }}>
                      <i className="fa-solid fa-eye"></i>
                    </button>
                    <button className="btn btn-success btn-sm" onClick={() => { setShowUpdateUser(true); setSelectedUserId(user.id) }}>
                      <i className="fa-solid fa-pen-to-square"></i>
                    </button>
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
