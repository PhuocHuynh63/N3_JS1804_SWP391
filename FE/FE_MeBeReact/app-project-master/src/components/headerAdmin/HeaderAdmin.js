import "./HeaderAdmin.css";

import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Link, NavLink } from "react-router-dom";
import { localService } from "../../service/localService";
import { jwtDecode } from "jwt-decode";
import { meBeSrc } from "../../service/meBeSrc";

export default function HeaderAdmin() {

  /**
     * Take user info (username) from local storage by token
     */
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('USER_INFO');
    if (token) {
      const decoded = jwtDecode(token);
      const username = decoded.sub;

      meBeSrc.getUserByUserName(username)
        .then((res) => {
          const userData = {
            ...res.data,
          };
          setUser(userData);
        })
        .catch((err) => {
          console.log("Error fetching user", err);
        });
    }
  }, []);
  //-----End-----//

  /**
    * Logout
    */
  let handleLogout = () => {
    localService.remove()
    window.location.href = "/";
  }
  //-----End Logout-----//

  return (
    <div id="wrapper-header">
      <nav className="navbar navbar-expand navbar-light bg-white topbar static-top shadow">
        <ul className="navbar-nav ml-auto">
          <li className="nav-item dropdown no-arrow d-sm-none">
            <a
              className="nav-link dropdown-toggle"
              href="#"
              id="searchDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <i className="fas fa-search fa-fw"></i>
            </a>

            <div
              className="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
              aria-labelledby="searchDropdown"
            >
              <form className="form-inline mr-auto w-100 navbar-search">
                <div className="input-group">
                  <input
                    type="text"
                    className="form-control bg-light border-0 small"
                    placeholder="Search for..."
                    aria-label="Search"
                    aria-describedby="basic-addon2"
                  />
                  <div className="input-group-append">
                    <button className="btn btn-primary" type="button">
                      <i className="fas fa-search fa-sm"></i>
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </li>

          <li className="nav-item dropdown no-arrow mx-1">
            <a
              className="nav-link dropdown-toggle"
              href="#"
              id="alertsDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <i className="fas fa-bell fa-fw"></i>

              <span className="badge badge-danger badge-counter">3+</span>
            </a>
          </li>

          <li className="nav-item dropdown no-arrow mx-1">
            <a
              className="nav-link dropdown-toggle"
              href="#"
              id="messagesDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <i className="fas fa-envelope fa-fw"></i>

              <span className="badge badge-danger badge-counter">7</span>
            </a>
          </li>

          <div className="topbar-divider d-none d-sm-block"></div>

          <li className="nav-item dropdown no-arrow">
            <a
              className="nav-link dropdown-toggle"
              id="userDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <span className="mr-2 d-none d-lg-inline text-gray-600 small">
                {user && user.firstName + " " + user.lastName}
              </span>
              <img
                className="img-profile rounded-circle"
                src={user && user.avatar}
              />
            </a>

            <div
              className="dropdown-menu dropdown-menu-right shadow animated--grow-in"
              aria-labelledby="userDropdown"
            >
              <a href="/" className="dropdown-item">
                <i className="fa-solid fa-house fa-sm fa-fw mr-2 text-gray-400"></i>
                Quay về trang chủ
              </a>
              <div class="dropdown-divider"></div>
              <Link className="dropdown-item" data-toggle="modal" data-target="#logoutModal" onClick={handleLogout}>
                <i className="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                Đăng xuất
              </Link>
            </div>
          </li>
        </ul>
      </nav>
    </div>
  );
}
