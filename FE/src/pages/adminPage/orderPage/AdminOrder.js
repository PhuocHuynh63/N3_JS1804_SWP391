import { useEffect, useState } from "react";
import "./AdminOrder.css";
import { meBeSrc } from "../../../service/meBeSrc";
import Pagination from "../../../components/pagination/Pagination";
import { Link } from "react-router-dom";
import TrackingPopup from "../../trackingPopup/TrackingPopup";

export default function AdminOrder() {

  /**
   * Call API to get list order
   */
  const [orders, setOrders] = useState([]);
  useEffect(() => {
    meBeSrc.getListOrder()
      .then((res) => {
        setOrders(res.data);
        // console.log(res.data);
      })
      .catch((err) => {
        console.log("Error fetching orders", err);
      });
  }, []);
  //-----End-----//


  //---------------------------------OrderDetail---------------------------------//
  const [showModalOrderDetail, setShowModalOrderDetail] = useState(false);

  /**
   * Call API to get order detail
   */
  const [activeOrderId, setActiveOrderId] = useState(null);
  const [orderDetail, setOrderDetail] = useState([]);

  const handleOrderDetail = (orderId) => {
    setActiveOrderId(orderId);
    setShowModalOrderDetail(true);

    meBeSrc.getOrderDetail(orderId)
      .then((res) => {
        setOrderDetail(res.data);
      })
      .catch((err) => {
        console.log("Error fetching orders", err);
      });
  };
  //-----End-----//
  //---------------------------------End---------------------------------//


  /**
   * Search Order
   */
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    if (searchTerm) {
      meBeSrc.getSearchOrderByEmailOrPhone(searchTerm)
        .then((res) => {
          setOrders(res.data);
        })
        .catch((err) => {
          console.log("Error fetching product", err);
        });
    } else {
      meBeSrc.getListUser()
        .then((res) => {
          // setOrders(res.data);
        })
        .catch((err) => {
          console.log("Error fetching product", err);
        });
    }
  }, [searchTerm]);

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };
  //-----End-----//


  /**
   * Pagination
   */
  const [currentPage, setCurrentPage] = useState(1);
  const ordersPerPage = 15;

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const currentUsers = orders.slice((currentPage - 1) * ordersPerPage, currentPage * ordersPerPage);
  const totalPages = Math.ceil(orders.length / ordersPerPage);
  //-----End-----//


  /**
   * Update order status
   */
  const handleStatusChange = (orderId, newStatus) => {
    const payload = {
      orderId: orderId,
      status: newStatus
    };

    meBeSrc.putStatusOrder(payload)
      .then(res => {
        setOrders(prevOrders =>
          prevOrders.map(order =>
            order.orderId === orderId ? { ...order, status: newStatus } : order
          )
        );
        window.location.reload();
        console.log("Order status updated from AdminOrder", res.data);
      })
      .catch(err => {
        console.log("Error updating order status from AdminOrder", err);
      });
  };
  //-----End-----//


  return (
    <div className="admin-order">
      <TrackingPopup show={showModalOrderDetail} handleClose={() => setShowModalOrderDetail(false)} orderId={activeOrderId} />

      <h1>Quản lý đơn hàng</h1>

      <div className="admin-order_action">
        <div className="admin-order_search">
          <input type="text" className="admin-order_searchinput" placeholder="Nhập id hoặc email, số điện thoại" onChange={handleSearchChange} value={searchTerm} />
          <i id="search" className="fa-solid fa-magnifying-glass"></i>
        </div>
      </div>

      <div className="box-order">
        <table className="order-list">
          <thead>
            <tr>
              <th>ID</th>
              <th style={{ textAlign: "left" }}>Người đặt</th>
              <th style={{ textAlign: "left" }}>Email</th>
              <th>Ngày đặt</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>

          <tbody>
            {currentUsers.map((order, index) => (
              <tr key={index}>
                <td>{order.orderId}</td>
                <td style={{ textAlign: "left" }}>{order.user.firstName} {order.user.lastName}</td>
                <td style={{ textAlign: "left" }}>{order.user.email}</td>
                <td>{new Date(order.createdAt).toLocaleDateString()}</td>
                <td>
                  <select
                    className="custom-select"
                    value={order.status}
                    onChange={(e) => handleStatusChange(order.orderId, e.target.value)}
                  >
                    <option value="Chờ xác nhận">Chờ xác nhận</option>
                    <option value="Đang được xử lý">Đang được xử lý</option>
                    <option value="Đang giao">Đang giao</option>
                    <option value="Đã giao">Đã giao</option>
                    <option value="Đã hủy">Đã hủy</option>
                  </select>
                </td>
                <td>
                  <a className="view btn btn-warning btn-sm" onClick={() => handleOrderDetail(order.orderId)}>
                    <i className="fa-solid fa-eye"></i>
                  </a>
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
