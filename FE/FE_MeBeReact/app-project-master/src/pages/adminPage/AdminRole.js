import "./AdminRole.css";

export default function AdminRole() {
  return (
    <div class="admin-role">
        <h1>Quản lý quyền</h1>
        <div class="sort-role">
            <label>Sắp xếp <i class="fa-solid fa-filter"></i></label>
            <select>
                <option value="">Chọn danh mục</option>
                <option value="category1">Danh mục 1</option>
                <option value="category2">Danh mục 2</option>
                <option value="category3">Danh mục 3</option>
            </select>
        </div>
      <div class="box-role">
        <table class="role-list">
          <thead>
            <tr>
              <th>STT</th>
              <th>Tên người dùng</th>
              <th>Ủy thác</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>1</td>
              <td>Anh Dat ne cu</td>
              <td>Admin</td>
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
              <td>3</td>
              <td>Anh Dat ne cu</td>
              <td>Vô danh</td>
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
          </tbody>
        </table>
      </div>
    </div>
  );
}
