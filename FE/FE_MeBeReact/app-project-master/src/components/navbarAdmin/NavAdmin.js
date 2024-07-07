import { NavLink } from "react-router-dom";
import "./NavAdmin.css";
import { useState } from "react";
import logo from "../../images/Logo_Header_RemoveBackground.png";

export default function NavAdmin() {
  const [isCollapsedUser, setIsCollapsedUser] = useState(true);
  const [isCollapsedCategory, setIsCollapsedCategory] = useState(true);
  const [isCollapsedProduct, setIsCollapsedProduct] = useState(true);
  const [isCollapsedOrder, setIsCollapsedOrder] = useState(true);
  const [isCollapsedFeedBack, setIsCollapsedFeedBack] = useState(true);

  const handleToggleUser = () => {
    setIsCollapsedUser(!isCollapsedUser);
  };

  const handleToggleCategory = () => {
    setIsCollapsedCategory(!isCollapsedCategory);
  };

  const handleToggleProduct = () => {
    setIsCollapsedProduct(!isCollapsedProduct);
  };

  const handleToggleOrder = () => {
    setIsCollapsedOrder(!isCollapsedOrder);
  };

  const handleToggleFeedBack = () => {
    setIsCollapsedFeedBack(!isCollapsedFeedBack);
  };

  return (
    <div id="wrapper-navbar">
      <div class="sidebar-brand-text mx-3">
        <img src={logo} alt="Nous Logo" height="50" />
      </div>

      <ul
        class="navbar-nav  sidebar sidebar-dark accordion"
        id="accordionSidebar"
      >
        <li class="nav-item active">
          <a class="nav-link" href="/admin">
            <i class="fa-solid fa-house"></i>
            <span>Trang chủ</span>
          </a>
        </li>
        <li className="nav-item">
          <NavLink
            className={`nav-link ${isCollapsedUser ? "collapsed" : ""}`}
            // onClick={handleToggleUser}
            aria-expanded={!isCollapsedUser}
            aria-controls="collapseTwo"
            to="/admin/user"
          >
            <i class="fa-solid fa-user"></i>
            <span>Người dùng</span>
          </NavLink>

          {/* <div
            id="collapseTwo"
            className={`collapse ${isCollapsedUser ? "" : "show"}`}
            aria-labelledby="headingTwo"
            data-parent="#accordionSidebar"
          >
            <div className="bg-white py-2 collapse-inner rounded">
              <NavLink to="/admin/user" className="collapse-item">
                Quản lý người dùng
              </NavLink>
            </div>
          </div> */}
        </li>

        <li className="nav-item">
          <NavLink
            className={`nav-link ${isCollapsedCategory ? "collapsed" : ""}`}
            // onClick={handleToggleCategory}
            aria-expanded={!isCollapsedCategory}
            aria-controls="collapseCategory"
            to="/admin/category"
          >
            <i class="fa-solid fa-layer-group"></i>
            <span>Danh mục</span>
          </NavLink>

          {/* <div
            id="collapseCategory"
            className={`collapse ${isCollapsedCategory ? "" : "show"}`}
            aria-labelledby="headingUtilities"
            data-parent="#accordionSidebar"
          >
            <div className="bg-white py-2 collapse-inner rounded">
              <NavLink to="/admin/category" className="collapse-item">
                Quản lý danh muc
              </NavLink>
            </div>
          </div> */}
        </li>

        <li className="nav-item">
          <NavLink
            className={`nav-link ${isCollapsedProduct ? "collapsed" : ""}`}
            onClick={handleToggleProduct}
            aria-expanded={!isCollapsedProduct}
            aria-controls="collapseProduct"
            to="/admin/product"
          >
            <i class="fa-solid fa-cube"></i>
            <span>Sản phẩm</span>
          </NavLink>

          <div
            id="collapseProduct"
            className={`collapse ${isCollapsedProduct ? "" : "show"}`}
            aria-labelledby="headingUtilities"
            data-parent="#accordionSidebar"
          >
            <div className="bg-white py-2 collapse-inner rounded">
              <NavLink to="/admin/product/add" className="collapse-item">
                Thêm sản phẩm mới
              </NavLink>
              <NavLink to="/admin/product/update" className="collapse-item">
                Quản lý sản phẩm
              </NavLink>
            </div>
          </div>
        </li>

        <li className="nav-item">
          <NavLink
            className={`nav-link ${isCollapsedOrder ? "collapsed" : ""}`}
            // onClick={handleToggleOrder}
            aria-expanded={!isCollapsedOrder}
            aria-controls="collapseOrder"
            to="/admin/order"
          >
            <i class="fa-solid fa-money-bill"></i>
            <span>Đơn hàng</span>
          </NavLink>

          <div
            id="collapseOrder"
            className={`collapse ${isCollapsedOrder ? "" : "show"}`}
            aria-labelledby="headingUtilities"
            data-parent="#accordionSidebar"
          >
            <div className="bg-white py-2 collapse-inner rounded">
              {/* <NavLink to="/admin/order" className="collapse-item" >
                Quản lý đơn hàng
              </NavLink> */}
            </div>
          </div>
        </li>

        {/* <li className="nav-item">
          <NavLink
            className={`nav-link ${isCollapsedFeedBack ? "collapsed" : ""}`}
            onClick={handleToggleFeedBack}
            aria-expanded={!isCollapsedFeedBack}
            aria-controls="collapseFeedBack"
          >
            <i class="fa-solid fa-message"></i>
            <span>Phản hồi</span>
          </NavLink>

          <div
            id="collapseFeedBack"
            className={`collapse ${isCollapsedFeedBack ? "" : "show"}`}
            aria-labelledby="headingUtilities"
            data-parent="#accordionSidebar"
          >
            <div className="bg-white py-2 collapse-inner rounded">
              <a className="collapse-item" href="utilities-color.html">
                Phản hồi khách hàng
              </a>
            </div>
          </div>
        </li>

        <li class="nav-item">
          <a class="nav-link" href="charts.html">
            <i class="fa-solid fa-ranking-star"></i>
            <span>Thống kê</span>
          </a>
        </li>

        <li class="nav-item">
          <a class="nav-link" href="tables.html">
            <i class="fa-solid fa-envelope"></i>
            <span>Hộp thư</span>
          </a>
        </li> */}
      </ul>
    </div>
  );
}
