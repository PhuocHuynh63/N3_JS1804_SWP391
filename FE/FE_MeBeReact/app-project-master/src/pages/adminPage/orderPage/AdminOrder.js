import "./AdminOrder.css";

export default function AdminOrder() {
  return (
    <div class="admin-order">
      <h1>Quản lý đơn hàng</h1>
      <div class="box-order">
        <table class="order-list">
            <thead>
            <tr>
            <th>Mã đơn hàng</th>
            <th>Người đặt</th>
            <th>Ngày đặt</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
          </thead>
          
            
         <tbody>
         <tr>
            <td>áhdKJHJKH</td>
            <td>Anh Dat ne cu</td>
            <td>02/07/2024</td>
            <td>Đã giao hàng</td>
            <td>
              <a class="view btn btn-danger btn-sm" href="#">
                <i class="fa-solid fa-eye"></i>
              </a>
            </td>
          </tr>
          <tr>
            <td>UIYÁ123hGS1</td>
            <td>Anh Dat ne cu</td>
            <td>02/08/2024</td>
            <td>Đang giao hàng</td>
            <td>
              <a class="view btn btn-danger btn-sm" href="#">
                <i class="fa-solid fa-eye"></i>
              </a>
            </td>
          </tr>
         </tbody>
        
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
