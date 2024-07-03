import "./AdminPage.css";
import User from "../../components/user/User";

export default function AdminPage() {
  return (
    <div>
      <User />
      <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
      </div>

      <div class="row">
        <div class="col-xl-3 col-md-6 mb-4">
          <div class="card border-left-primary shadow h-100 py-2">
            <div class="card-body">
              <div class="row no-gutters align-items-center">
                <div class="col mr-2">
                  <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                    Đơn hàng
                  </div>
                  <div class="h5 mb-0 font-weight-bold text-gray-800">
                    $40,000
                  </div>
                </div>
                <div class="col-auto">
                  <i class="fas fa-calendar fa-2x text-gray-300"></i>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
          <div class="card border-left-success shadow h-100 py-2">
            <div class="card-body">
              <div class="row no-gutters align-items-center">
                <div class="col mr-2">
                  <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                    Tổng doanh thu
                  </div>
                  <div class="h5 mb-0 font-weight-bold text-gray-800">
                    $215,000
                  </div>
                </div>
                <div class="col-auto">
                  <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
          <div class="card border-left-info shadow h-100 py-2">
            <div class="card-body">
              <div class="row no-gutters align-items-center">
                <div class="col mr-2">
                  <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                    Sản phẩm
                  </div>
                  <div class="row no-gutters align-items-center">
                    <div class="col-auto">
                      <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">
                        50%
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-auto">
                  <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
          <div class="card border-left-warning shadow h-100 py-2">
            <div class="card-body">
              <div class="row no-gutters align-items-center">
                <div class="col mr-2">
                  <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                    Báo cáo
                  </div>
                  <div class="h5 mb-0 font-weight-bold text-gray-800">18</div>
                </div>
                <div class="col-auto">
                  <i class="fas fa-comments fa-2x text-gray-300"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-order">
        <h2 class="title">Đơn hàng cần xác nhận</h2>
        <table class="order">
          <thead>
            <tr>
              <th>Khách hàng</th>
              <th>Ngày đặt hàng</th>
              <th>Giỏ hàng</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Trương Thành Đạt</td>
              <td>06/12/2024</td>
              <td>3 sản phẩm</td>
              <td>Đang chờ xử lý</td>
              <td>
                <div class="action">
                  <a class="view btn btn-danger btn-sm">
                    <i class="fa-solid fa-eye"></i>
                  </a>
                  <a class="check btn btn-success btn-sm">
                    <i class="fa-solid fa-check"></i>
                  </a>
                </div>
              </td>
            </tr>
            <tr>
              <td>Trương Thành Đạt</td>
              <td>06/12/2024</td>
              <td>3 sản phẩm</td>
              <td>Đang chờ xử lý</td>
              <td>
                <div class="action">
                  <a class="view btn btn-danger btn-sm">
                    <i class="fa-solid fa-eye"></i>
                  </a>
                  <a class="check btn btn-success btn-sm">
                    <i class="fa-solid fa-check"></i>
                  </a>
                </div>
              </td>
            </tr>
            <tr>
              <td>Trương Thành Đạt</td>
              <td>06/12/2024</td>
              <td>3 sản phẩm</td>
              <td>Đang chờ xử lý</td>
              <td>
                <div class="action">
                  <a class="view btn btn-danger btn-sm">
                    <i class="fa-solid fa-eye"></i>
                  </a>
                  <a class="check btn btn-success btn-sm">
                    <i class="fa-solid fa-check"></i>
                  </a>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="showAll">
          <a href="#">
            Xem tất cả đơn hàng
            <i class="icon-show fa-solid fa-arrow-right"></i>
          </a>
        </div>
        <hr />
      </div>
      <div class="box-detail">
        <div class="chart">Vứt biểu đồ vào đây</div>

        <div class="newuser-container">
          <h4>Khách hàng mới</h4>
          <div class="newuser">
            <img src="https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=4DSdNERHVRMQ7kNvgEdmHZm&_nc_ht=scontent.fsgn5-5.fna&gid=AgZq26kWZCfCOQ8DvjUrf6w&oh=00_AYB8W25ohYBOR5Nzw9HBpOvbFBJwMbL7s89WWZqZVk4xfA&oe=668AD024" class="avt-user" />
            <div class="info-newuser">
              <div class="name"> Trương Thành Đạt</div>
              <div class="status">Truy cập 25 phút trước</div>
            </div>
            <button class="view-details">Xem chi tiet</button>
          </div>

          <div class="newuser">
            <img src="https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=4DSdNERHVRMQ7kNvgEdmHZm&_nc_ht=scontent.fsgn5-5.fna&gid=AgZq26kWZCfCOQ8DvjUrf6w&oh=00_AYB8W25ohYBOR5Nzw9HBpOvbFBJwMbL7s89WWZqZVk4xfA&oe=668AD024" class="avt-user" />
            <div class="info-newuser">
              <div class="name"> Trương Thành Đạt</div>
              <div class="status">Truy cập 25 phút trước</div>
            </div>
            <button class="view-details">Xem chi tiết</button>
          </div>

          <div class="newuser">
            <img src="https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=4DSdNERHVRMQ7kNvgEdmHZm&_nc_ht=scontent.fsgn5-5.fna&gid=AgZq26kWZCfCOQ8DvjUrf6w&oh=00_AYB8W25ohYBOR5Nzw9HBpOvbFBJwMbL7s89WWZqZVk4xfA&oe=668AD024" class="avt-user" />
            <div class="info-newuser">
              <div class="name"> Trương Thành Đạt</div>
              <div class="status">Vừa mới truy cập</div>
            </div>
            <button class="view-details">Xem chi tiết</button>
          </div>

          <div>
            <button>1</button>
            <button>2</button>
            <button>3</button>
          </div>
        </div>
      </div>

      <div class="box-message">
        <h3>Phản hồi từ người dùng</h3>
        <table class="message">
          <tr>
            <th>Người dùng</th>
            <th>Họ tên</th>
            <th>Bình luận</th>
            <th>Đánh giá</th>
          </tr>
          <tr>
            <td>
              <img class="user-avatar" src="https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=4DSdNERHVRMQ7kNvgEdmHZm&_nc_ht=scontent.fsgn5-5.fna&gid=AgZq26kWZCfCOQ8DvjUrf6w&oh=00_AYB8W25ohYBOR5Nzw9HBpOvbFBJwMbL7s89WWZqZVk4xfA&oe=668AD024" alt="Avatar" />
            </td>
            <td>
              <div class="user-name">Trương Thành Đạt</div>
            </td>
            <td>
              <div class="message-user">
                <p>Hài lòng</p>
              </div>
            </td>
            <td>
              <div class="user-rate">
                <i class="fa-solid fa-star"></i>
                <i class="fa-solid fa-star"></i>
                <i class="fa-solid fa-star"></i>
                <i class="fa-solid fa-star-half-stroke"></i>
                <i class="fa-regular fa-star"></i>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <img class="user-avatar" src="https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=4DSdNERHVRMQ7kNvgEdmHZm&_nc_ht=scontent.fsgn5-5.fna&gid=AgZq26kWZCfCOQ8DvjUrf6w&oh=00_AYB8W25ohYBOR5Nzw9HBpOvbFBJwMbL7s89WWZqZVk4xfA&oe=668AD024" alt="Avatar" />
            </td>
            <td>
              <div class="user-name">Trương Thành Đạt</div>
            </td>
            <td>
              <div class="message-user">
                <p>Hài lòng</p>
              </div>
            </td>
            <td>
              <div class="user-rate">
                <i class="fa-solid fa-star"></i>
                <i class="fa-solid fa-star"></i>
                <i class="fa-solid fa-star"></i>
                <i class="fa-solid fa-star-half-stroke"></i>
                <i class="fa-regular fa-star"></i>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
  );
}
