import { useState } from "react";
import "./AdminUserAdd.css";


export default function AdminUserAdd() {
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [dateCreate, setDateCreate] = useState("");
  const [status, setStatus] = useState("");
  const [role, setRole] = useState("");

  const currentDate = new Date();
  const date = currentDate.toUTCString();


  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({
      userName,
      email,
      dateCreate,
      status,
      role,
    });
  };

  return (
    <div class="admin-user-add">
      <h1>Thêm người dùng mới</h1>
      <form onSubmit={handleSubmit}>
        <div class="form-group">
          <label htmlFor="userName">Tên người dùng</label>
          <input
            type="text"
            id="userName"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
            required
          />
        </div>
        <div class="form-group">
          <label htmlFor="email">Email:</label>
          <input
            type="text"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div class="form-group">
          <label htmlFor="dateCreate">Ngày tạo:</label>
          <input
            type="text"
            id="dateCreate"
            value={date}
            onChange={(e) => setDateCreate(e.target.value)}
            required
          />
        </div>


        <div class="form-group">
          <label htmlFor="role">Ủy quyền:</label>
          <select
            id="role"
            value={role}
            onChange={(e) => setRole(e.target.value)}
            required
          >
            <option value="">Chọn quyền</option>
            <option value="role1">Admin</option>
            <option value="role2">Member</option>
            <option value="role3">Staff</option>
            <option value="role4">Guest</option>
          </select>
        </div>

        <div class="form-group">
          <input
            id="status"
            value={1}
            onChange={(e) => setStatus(e.target.value)}
            required
            type="hidden"
          />
        </div>

        <div class="form-group">
          <button class="btn-add" type="submit">Thêm người dùng</button>
        </div>
      </form>
    </div>
  );
}
