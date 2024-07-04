import { NavLink } from "react-router-dom";
import "./AdminProduct.css";

export default function AdminProduct() {
  return (
    <div class="admin-product">
      <h1 class="header-product">Quản lý sản phẩm</h1>

      <NavLink
        to="/admin/product/add"
        style={{
          backgroundColor: "red",
          border: "2px solid black",
          margin: "20px 0px 50px 10px",
          borderRadius: "5px",
          color: "white",
          display: "flex",
          paddingLeft: "20px",
          height: "30px",
          width: "200px",
        }}
      >
        Thêm Sản Phẩm Mới +
      </NavLink>

      <div class="box-product">
        <table class="product-list">
          <thead>
            <tr>
              <th>Ảnh</th>
              <th>Tên sản phẩm</th>
              <th>Loại sản phẩm</th>
              <th>Giá</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <img class="product-image" src="https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=4DSdNERHVRMQ7kNvgEdmHZm&_nc_ht=scontent.fsgn5-5.fna&gid=AgZq26kWZCfCOQ8DvjUrf6w&oh=00_AYB8W25ohYBOR5Nzw9HBpOvbFBJwMbL7s89WWZqZVk4xfA&oe=668AD024"></img>
              </td>
              <td>Anh Dat ne cu</td>
              <td>Quy da</td>
              <td>20.000 VND</td>
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

          <tr>
            <td>
              <img class="product-image" src="https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=4DSdNERHVRMQ7kNvgEdmHZm&_nc_ht=scontent.fsgn5-5.fna&gid=AgZq26kWZCfCOQ8DvjUrf6w&oh=00_AYB8W25ohYBOR5Nzw9HBpOvbFBJwMbL7s89WWZqZVk4xfA&oe=668AD024"></img>
            </td>
            <td>Anh Dat ne cu</td>
            <td>Quy da</td>
            <td>20.000 VND</td>
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

        <nav aria-label="Page navigation example">
          <ul class="pagination">
            <li class="page-item">
              <a class="page-link" href="#">
                Previous
              </a>
            </li>
            <li class="page-item">
              <a class="page-link" href="#">
                1
              </a>
            </li>
            <li class="page-item">
              <a class="page-link" href="#">
                2
              </a>
            </li>
            <li class="page-item">
              <a class="page-link" href="#">
                3
              </a>
            </li>
            <li class="page-item">
              <a class="page-link" href="#">
                Next
              </a>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  );
}
