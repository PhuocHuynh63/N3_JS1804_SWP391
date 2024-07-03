import { NavLink } from "react-router-dom";
import "./AdminUser.css";

export default function AdminUser() {
  return (
    <div class="admin-user">
      <h1>Quản lý người dùng</h1>

      <NavLink to="/admin/user/add"
        style={{
          backgroundColor: "red",
          border: "2px solid black",
          margin: "20px 0px 50px 10px",
          borderRadius: "5px",
          color: "white",
          display: "flex",
          paddingLeft: "20px",
          height: "30px",
          width: "220px",
        }}
      >
        Thêm Người Dùng Mới +
      </NavLink>
      <div class="box-user">
        <table class="user-list">
          <tr>
            <th>STT</th>
            <th>Tên người dùng</th>
            <th>Email</th>
            <th>Ngày lập</th>
            <th>Khóa</th>
            <th>Ủy quyền</th>
            <th>Thao tác</th>
          </tr>
          <tr>
            <td>1</td>
            <td>Anh Dat ne cu</td>
            <td>truongdat0612@gmail.com</td>
            <td>02/07/2024</td>
            <td>
              <div class="container-checkbox">
                <input type="checkbox" class="checkbox" id="checkbox1" />
                <label class="switch" for="checkbox1">
                  <span class="slider"></span>
                </label>
              </div>
            </td>
            <td>Khách hàng</td>
            <td>
              <div class="action">
                <a class="delete btn btn-danger btn-sm" href="#">
                  <i class="fa-solid fa-trash"></i>
                </a>
                <a class="edit btn btn-success btn-sm" href="#">
                  <i class="fa-solid fa-pen-to-square"></i>
                </a>
              </div>
            </td>
          </tr>

          <tr>
            <td>2</td>
            <td>Anh Dat ne cu</td>
            <td>truongdat0612@gmail.com</td>
            <td>02/07/2024</td>
            <td>
              <div class="container-checkbox">
                <input type="checkbox" class="checkbox" id="checkbox2" />
                <label class="switch" for="checkbox2">
                  <span class="slider"></span>
                </label>
              </div>
            </td>
            <td>Quản trị viên</td>
            <td>
              <div class="action">
                <a class="delete btn btn-danger btn-sm" href="#">
                  <i class="fa-solid fa-trash"></i>
                </a>
                <a class="edit btn btn-success btn-sm" href="#">
                  <i class="fa-solid fa-pen-to-square"></i>
                </a>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
  );
}
