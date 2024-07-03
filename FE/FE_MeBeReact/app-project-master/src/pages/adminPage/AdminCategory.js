import { useState } from "react";
import "./AdminCategory.css";
import { NavLink } from "react-router-dom";

export default function AdminCategory() {


  return (
    <div class="admin-category">
      <h1>Quản lý danh mục</h1>
      <NavLink to="/admin/category/add"
      
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
        Thêm Danh Mục Mới +
      </NavLink>
      <div class="box-category">
        <table class="category-list">
          <thead>
          <tr>
            <th>STT</th>
            <th>Tên danh mục</th>
            <th>Sản phẩm</th>
            <th>Thao tác</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>1</td>
            <td>Anh Dat ne cu</td>
            <td>10</td>
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
            <td>10</td>
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
            <td>10</td>
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
