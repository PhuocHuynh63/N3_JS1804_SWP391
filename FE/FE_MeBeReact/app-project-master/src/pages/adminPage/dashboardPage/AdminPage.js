import "./AdminPage.css";
import User from "../../../components/user/User";
import { useEffect, useState } from "react";
import { meBeSrc } from "../../../service/meBeSrc";
import Successful from "../../../components/popupSuccessful/Successful";
import TrackingPopup from "../../trackingPopup/TrackingPopup";

export default function AdminPage() {
  const [showModal, setShowModal] = useState(false);

  /**
   * Get order API 
   */
  const [order, setOrder] = useState([]);
  const [currentOrderId, setCurrentOrderId] = useState(null);

  useEffect(() => {
    meBeSrc.getListOrder()
      .then((res) => {
        const orderData = res.data;
        setOrder(orderData);
      })
      .catch((err) => {
        console.log("Error fetching order", err);
      });
  }, []);

  const sortOrders = order.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
  //-----End-----//


  /**
   * Get product API
   */
  const [product, setProduct] = useState([]);

  useEffect(() => {
    meBeSrc.getProduct()
      .then((res) => {
        const productData = res.data;
        setProduct(productData);
      })
      .catch((err) => {
        console.log("Error fetching product", err);
      });
  }, []);
  //-----End-----//


  /**
   * Get order status API
   */

  const handleStatusOrder = (orderId) => {
    const payload = { orderId, status: "Đang được xử lý" };

    meBeSrc.putStatusOrder(payload)
      .then((res) => {
        setOrder(prevOrders => prevOrders.map(order =>
          order.orderId === orderId ? { ...order, status: "Đang được xử lý" } : order
        ));
        setShowModal(true);
        setTimeout(() => {
          setShowModal(false);
        }, 2000);
        console.log("Order status updated from AdminPage", res.data);
      })
      .catch((err) => {
        console.log("Error updating order status from AdminPage", err);
      });
  }
  //-----End-----//


  /**
   * Get API to get user
   */
  const [newUsers, setNewUsers] = useState([]);
  useEffect(() => {
    meBeSrc.getListUser()
      .then((res) => {
        const userData = res.data;
        setNewUsers(userData);
      })
      .catch((err) => {
        console.log("Error fetching user", err);
      });
  }, []);

  const sortUsers = newUsers.sort((a, b) => new Date(b.createAt) - new Date(a.createAt));
  const filterNewUsers = () => sortUsers.filter((user) => user.role === "member").slice(0, 3);
  console.log(filterNewUsers());
  //-----End-----//


  /**
   * revenue calculation
   * @returns revenue
   */
  const calculateRevenue = () => {
    let complatedOrders = order.filter((item) => item.status === "Đã giao");
    let revenue = complatedOrders.reduce((total, item) => total + item.totalAmount, 0);
    return revenue.toLocaleString('vi-VN');
  }
  //-----End-----//


  /**
   * Filter order by status
   */
  const orderNeedConfirm = order.filter((item) => item.status === "Chờ xác nhận");
  //-----End-----//


  /**
   * calculateAccountAge
   * @param {*} createAt 
   * @returns 
   */
  const calculateAccountAge = (createAt) => {
    const createDate = new Date(createAt);
    const currentDate = new Date();
    const diffTime = Math.abs(currentDate - createDate);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays;
  }
  //-----End-----//


  /**
   * Pagination
   */
  const [currentPage, setCurrentPage] = useState(1);
  const ordersPerPage = 5;
  const indexOfLastOrder = currentPage * ordersPerPage;
  const indexOfFirstOrder = indexOfLastOrder - ordersPerPage;
  const currentOrders = orderNeedConfirm.slice(indexOfFirstOrder, indexOfLastOrder);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  const pageNumbers = [];
  for (let i = 1; i <= Math.ceil(orderNeedConfirm.length / ordersPerPage); i++) {
    pageNumbers.push(i);
  }
  //-----End-----//


  /**
   * Handle tracking popup
   */
  const [showTrackingPopUp, setShowTrackingPopUp] = useState(false);

  const handleCloseTrackingPopUp = () => setShowTrackingPopUp(false);
  const handleShowTrackingPopUp = (orderId) => {
    setCurrentOrderId(orderId);
    setShowTrackingPopUp(true)
  };
  //-----End-----//


  return (
    <div className="adminpage">
      <User />
      <Successful show={showModal} onHide={() => setShowModal(false)} message={"Cập nhật trạng thái thành công"} />
      <TrackingPopup show={showTrackingPopUp} handleClose={handleCloseTrackingPopUp} orderId={currentOrderId} />

      <div className="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 className="h3 mb-0 text-gray-800">Dashboard</h1>
      </div>

      <div className="row">
        <div className="col-xl-3 col-md-6 mb-4">
          <div className="card border-left-primary shadow h-100 py-2">
            <div className="card-body">
              <div className="row no-gutters align-items-center">
                <div className="col mr-2">
                  <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">
                    Đơn hàng
                  </div>
                  <div className="h5 mb-0 font-weight-bold text-gray-800">
                    {(order.length).toLocaleString('vi-VN')}
                  </div>
                </div>
                <div className="col-auto">
                  <i className="fas fa-calendar fa-2x text-gray-300"></i>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col-xl-3 col-md-6 mb-4">
          <div className="card border-left-success shadow h-100 py-2">
            <div className="card-body">
              <div className="row no-gutters align-items-center">
                <div className="col mr-2">
                  <div className="text-xs font-weight-bold text-success text-uppercase mb-1">
                    Tổng doanh thu
                  </div>
                  <div className="h5 mb-0 font-weight-bold text-gray-800">
                    {calculateRevenue()} VNĐ
                  </div>
                </div>
                <div className="col-auto">
                  <i className="fas fa-dollar-sign fa-2x text-gray-300"></i>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col-xl-3 col-md-6 mb-4">
          <div className="card border-left-info shadow h-100 py-2">
            <div className="card-body">
              <div className="row no-gutters align-items-center">
                <div className="col mr-2">
                  <div className="text-xs font-weight-bold text-info text-uppercase mb-1">
                    Sản phẩm
                  </div>
                  <div className="row no-gutters align-items-center">
                    <div className="col-auto">
                      <div className="h5 mb-0 mr-3 font-weight-bold text-gray-800">
                        {product.length}
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col-auto">
                  <i className="fas fa-clipboard-list fa-2x text-gray-300"></i>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col-xl-3 col-md-6 mb-4">
          <div className="card border-left-warning shadow h-100 py-2">
            <div className="card-body">
              <div className="row no-gutters align-items-center">
                <div className="col mr-2">
                  <div className="text-xs font-weight-bold text-warning text-uppercase mb-1">
                    Báo cáo
                  </div>
                  <div className="h5 mb-0 font-weight-bold text-gray-800">0</div>
                </div>
                <div className="col-auto">
                  <i className="fas fa-comments fa-2x text-gray-300"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="box-order">
        <h2 className="title">Đơn hàng cần xác nhận</h2>
        <table className="order">
          <thead>
            <tr>
              <th>Khách hàng</th>
              <th>Ngày đặt hàng</th>
              <th>Tổng tiền</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            {currentOrders.length === 0 ? (
              <tr>
                <td colSpan="5" style={{ backgroundColor: "white", fontWeight: "500" }}>Không có đơn hàng nào cần xác nhận</td>
              </tr>
            ) : currentOrders.map((order) => (
              <tr className="adminpage" key={order.orderId}>
                <td>{`${order.user.firstName} ${order.user.lastName}`}</td>
                <td>{new Date(order.createdAt).toLocaleDateString('vi-VN')}</td>
                <td>{(order.totalAmount).toLocaleString('vi-VN')}</td>
                <td>{order.status}</td>
                <td>
                  <div className="action">
                    <a className="view btn btn-warning btn-sm" onClick={() => handleShowTrackingPopUp(order.orderId)}>
                      <i className="fa-solid fa-eye"></i>
                    </a>
                    <a className="check btn btn-success btn-sm" onClick={() => handleStatusOrder(order.orderId)}>
                      <i className="fa-solid fa-check"></i>
                    </a>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <div className="pagination">
          {pageNumbers.map(number => (
            <button key={number} onClick={() => paginate(number)} className="page-link">
              {number}
            </button>
          ))}
        </div>

        <div className="showAll">
          <a href="#">
            Xem tất cả đơn hàng
            <i className="icon-show fa-solid fa-arrow-right"></i>
          </a>
        </div>
        <hr />
      </div>
      <div className="box-detail">
        <div className="chart">Vứt biểu đồ vào đây</div>

        <div className="newuser-container">
          <h4>Khách hàng mới</h4>
          {filterNewUsers().map((user) => (
            <div className="newuser">
              <img src={user.avatar} className="avt-user" style={{ height: "50px", width: "50px" }} />
              <div className="info-newuser">
                <div className="name"> {`${user.firstName} ${user.lastName}`}</div>
                <div className="status"> {`Được tạo cách đây ${calculateAccountAge(user.createAt)} ngày`}</div>
              </div>
              <button className="view-details">Xem chi tiet</button>
            </div>
          ))}
        </div>
      </div>

      <div className="box-message">
        <h3>Phản hồi từ người dùng</h3>
        <table className="message">
          <tr>
            <th>Người dùng</th>
            <th>Họ tên</th>
            <th>Bình luận</th>
            <th>Đánh giá</th>
          </tr>
          <tr>
            <td>
              <img className="user-avatar" src="https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=4DSdNERHVRMQ7kNvgEdmHZm&_nc_ht=scontent.fsgn5-5.fna&gid=AgZq26kWZCfCOQ8DvjUrf6w&oh=00_AYB8W25ohYBOR5Nzw9HBpOvbFBJwMbL7s89WWZqZVk4xfA&oe=668AD024" alt="Avatar" />
            </td>
            <td>
              <div className="user-name">Trương Thành Đạt</div>
            </td>
            <td>
              <div className="message-user">
                <p>Hài lòng</p>
              </div>
            </td>
            <td>
              <div className="user-rate">
                <i className="fa-solid fa-star"></i>
                <i className="fa-solid fa-star"></i>
                <i className="fa-solid fa-star"></i>
                <i className="fa-solid fa-star-half-stroke"></i>
                <i className="fa-regular fa-star"></i>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <img className="user-avatar" src="https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=4DSdNERHVRMQ7kNvgEdmHZm&_nc_ht=scontent.fsgn5-5.fna&gid=AgZq26kWZCfCOQ8DvjUrf6w&oh=00_AYB8W25ohYBOR5Nzw9HBpOvbFBJwMbL7s89WWZqZVk4xfA&oe=668AD024" alt="Avatar" />
            </td>
            <td>
              <div className="user-name">Trương Thành Đạt</div>
            </td>
            <td>
              <div className="message-user">
                <p>Hài lòng</p>
              </div>
            </td>
            <td>
              <div className="user-rate">
                <i className="fa-solid fa-star"></i>
                <i className="fa-solid fa-star"></i>
                <i className="fa-solid fa-star"></i>
                <i className="fa-solid fa-star-half-stroke"></i>
                <i className="fa-regular fa-star"></i>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
  );
}
