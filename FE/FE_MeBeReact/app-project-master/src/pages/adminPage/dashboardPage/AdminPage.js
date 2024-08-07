import "./AdminPage.css";
import User from "../../../components/user/User";
import { useEffect, useState, useMemo } from "react";
import { meBeSrc } from "../../../service/meBeSrc";
import Successful from "../../../components/popupSuccessful/Successful";
import TrackingPopup from "../../trackingPopup/TrackingPopup";
import RevenueChart from "../../../components/chart/chart";

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
        calculateDailyRevenue(orderData);
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
  const [dailyRevenue, setDailyRevenue] = useState([]);

  const calculateRevenue = () => {
    let completedOrders = order.filter((item) => item.status === "Đã giao");
    let revenue = completedOrders.reduce((total, item) => total + item.totalAmount, 0);
    return revenue.toLocaleString('vi-VN');
  }

  const calculateDailyRevenue = (orderData) => {
    let completedOrders = orderData.filter((item) => item.status === "Đã giao");

    let dailyRevenueData = completedOrders.reduce((acc, item) => {
      let date = new Date(item.updatedAt); // Use updatedAt for the delivery date
      let formattedDate = date.toLocaleDateString('vi-VN', { day: '2-digit', month: '2-digit', year: 'numeric' });
      if (!acc[formattedDate]) {
        acc[formattedDate] = 0;
      }
      acc[formattedDate] += item.totalAmount;
      return acc;
    }, {});

    let formattedDailyRevenueData = Object.keys(dailyRevenueData).map(date => ({
      date,
      revenue: dailyRevenueData[date]
    }));

    setDailyRevenue(formattedDailyRevenueData);
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

  const memoizedDailyRevenue = useMemo(() => dailyRevenue, [dailyRevenue]);

  return (
    <div className="adminpage">
      <User />
      <Successful show={showModal} onHide={() => setShowModal(false)} message={"Cập nhật trạng thái thành công"} />
      <TrackingPopup show={showTrackingPopUp} handleClose={handleCloseTrackingPopUp} orderId={currentOrderId} />

      <div className="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 className="h3 mb-0 text-gray-800">Dashboard</h1>
      </div>

      <div className="row">
        <div className="col-lg-4 col-md-6 mb-4">
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

        <div className="col-lg-4 col-md-6 mb-4">
          <div className="card border-left-success shadow h-100 py-2">
            <div className="card-body">
              <div className="row no-gutters align-items-center">
                <div className="col mr-2">
                  <div className="text-xs font-weight-bold text-success text-uppercase mb-1">
                    Tổng doanh thu
                  </div>
                  <div className="h5 mb-0 font-weight-bold text-gray-800">
                    {calculateRevenue()}
                  </div>
                </div>
                <div className="col-auto">
                  <i className="fa-solid fa-dong-sign fa-2x text-gray-300"></i>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col-lg-4 col-md-6 mb-4">
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
                <td>{`${order?.firstName} ${order?.lastName}`}</td>
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
        <div className="chart">
          <RevenueChart data={memoizedDailyRevenue} />
        </div>

        <div className="newuser-container">
          <h4>Khách hàng mới</h4>
          {filterNewUsers().map((user) => (
            <div className="newuser" key={user.id}>
              <img src={user.avatar} className="avt-user" style={{ height: "50px", width: "50px" }} alt="user avatar" />
              <div className="info-newuser">
                <div className="name"> {`${user.firstName} ${user.lastName}`}</div>
                <div className="status"> {`Được tạo cách đây ${calculateAccountAge(user.createAt)} ngày`}</div>
              </div>
              <div></div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
