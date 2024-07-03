  import "./HeaderAdmin.css";

  import React, { useState } from "react";
  import "bootstrap/dist/css/bootstrap.min.css";
  import { NavLink } from "react-router-dom";

  export default function HeaderAdmin() {
    return (
      <div id="wrapper-header">
            <nav class="navbar navbar-expand navbar-light bg-white topbar static-top shadow">
              <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                <div class="input-group">
                  <input
                    type="text"
                    class="header-search form-control bg-white border-2 small"
                    placeholder="Tìm kiếm..."
                    aria-label="Search"
                    aria-describedby="basic-addon2"
                  />
                  <div class="input-group-append">
                    <button class="btn btn-search" type="button">
                      <i class="fas fa-search fa-sm"></i>
                    </button>
                  </div>
                </div>
              </form>

              <ul class="navbar-nav ml-auto">
                <li class="nav-item dropdown no-arrow d-sm-none">
                  <a
                    class="nav-link dropdown-toggle"
                    href="#"
                    id="searchDropdown"
                    role="button"
                    data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                  >
                    <i class="fas fa-search fa-fw"></i>
                  </a>

                  <div
                    class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                    aria-labelledby="searchDropdown"
                  >
                    <form class="form-inline mr-auto w-100 navbar-search">
                      <div class="input-group">
                        <input
                          type="text"
                          class="form-control bg-light border-0 small"
                          placeholder="Search for..."
                          aria-label="Search"
                          aria-describedby="basic-addon2"
                        />
                        <div class="input-group-append">
                          <button class="btn btn-primary" type="button">
                            <i class="fas fa-search fa-sm"></i>
                          </button>
                        </div>
                      </div>
                    </form>
                  </div>
                </li>

                <li class="nav-item dropdown no-arrow mx-1">
                  <a
                    class="nav-link dropdown-toggle"
                    href="#"
                    id="alertsDropdown"
                    role="button"
                    data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                  >
                    <i class="fas fa-bell fa-fw"></i>

                    <span class="badge badge-danger badge-counter">3+</span>
                  </a>
                </li>

                <li class="nav-item dropdown no-arrow mx-1">
                  <a
                    class="nav-link dropdown-toggle"
                    href="#"
                    id="messagesDropdown"
                    role="button"
                    data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                  >
                    <i class="fas fa-envelope fa-fw"></i>

                    <span class="badge badge-danger badge-counter">7</span>
                  </a>
                </li>

                <div class="topbar-divider d-none d-sm-block"></div>

                <li class="nav-item dropdown no-arrow">
                  <a
                    class="nav-link dropdown-toggle"
                    id="userDropdown"
                    role="button"
                    data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                  >
                    <span class="mr-2 d-none d-lg-inline text-gray-600 small">
                      Anh Dat ne Ku!
                    </span>
                    <img
                      class="img-profile rounded-circle"
                      src="https://scontent.fsgn18-1.fna.fbcdn.net/v/t39.30808-1/449211428_1548454112768538_6701101107381770885_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=0ecb9b&_nc_eui2=AeH8SJHoFsaDuMCLDER7WV4UffuU2YKQ7Px9-5TZgpDs_BSfofbZSbeU8HRUJO5SfEEwfrNUIW9lCDMNgSjU9IxJ&_nc_ohc=7f2BC83wgKoQ7kNvgGcSkBa&_nc_ht=scontent.fsgn18-1.fna&oh=00_AYCB5yRscvzIIOzvvmN9tTOeKhhCGad45LoEWErcQ2eIcA&oe=668713E4"
                    />
                  </a>

                  <div
                    class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                    aria-labelledby="userDropdown"
                  >
                    <a class="dropdown-item">
                      <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                      Thông tin cá nhân
                    </a>
                    <a class="dropdown-item">
                      <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                      Đơn hàng
                    </a>
                    <div class="dropdown-divider"></div>
                    <a
                      href="#"
                      class="dropdown-item"
                      data-toggle="modal"
                      data-target="#logoutModal"
                    >
                      <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                      Đăng xuất
                    </a>
                  </div>
                </li>
              </ul>
            </nav>
          </div>
    );
  }
