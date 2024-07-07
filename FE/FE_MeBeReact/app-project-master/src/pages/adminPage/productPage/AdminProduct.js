import { Link, NavLink } from "react-router-dom";
import "./AdminProduct.css";
import { useEffect, useState } from "react";
import { meBeSrc } from "../../../service/meBeSrc";
import Pagination from "../../../components/pagination/Pagination";

export default function AdminProduct() {

  /**
   * Call API to get products
   */
  const [products, setProducts] = useState([]);
  useEffect(() => {
    meBeSrc.getProduct()
      .then((res) => {
        setProducts(res.data);
        console.log(res);
      })
  }, []);
  //-----End-----//


  /**
     * Search Product
     */
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    if (searchTerm) {
      meBeSrc.getProductBySearch(searchTerm)
        .then((res) => {
          setProducts(res.data);
        })
        .catch((err) => {
          console.log("Error fetching product", err);
        });
    } else {
      meBeSrc.getProduct()
        .then((res) => {
          setProducts(res.data);
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
   * Handle Active Product Id
   */
  const [activeProductId, setActiveProductId] = useState(null);

  const handleActiveProductId = (id) => {
    setActiveProductId(id);
  }
  //-----End-----//


  /**
     * Pagination
     */
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 15;

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const currentProducts = products.slice((currentPage - 1) * productsPerPage, currentPage * productsPerPage);
  const totalPages = Math.ceil(products.length / productsPerPage);
  //-----End-----//


  return (
    <div class="admin-product">
      <h1 class="header-product">Quản lý sản phẩm</h1>

      <div className="admin-product_action">
        <Link to={'/admin/product/add'}>
          <button className="btn-add_product"> + Thêm sản phẩm mới</button>
        </Link>
        <div className="admin-product_search">
          <input type="text" className="admin-product_searchinput" placeholder="Nhập sản phẩm cần tìm" onChange={handleSearchChange} value={searchTerm} />
          <i id="search" className="fa-solid fa-magnifying-glass"></i>
        </div>
      </div>


      {/* Table Product */}
      <div class="box-product">
        <table class="product-list">
          <thead>
            <tr>
              <th>ID</th>
              <th>Ảnh</th>
              <th style={{ textAlign: "left" }}>Tên sản phẩm</th>
              <th>Trạng thái</th>
              <th>Loại sản phẩm</th>
              <th>Giá giảm</th>
              <th>Đơn Giá</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          {currentProducts.map(product => (
            <tbody>
              <tr>
                <td>{product.productId}</td>
                <td>
                  <img class="product-image" src={product.images}></img>
                </td>
                <td style={{ textAlign: "left" }}>{product.name}</td>
                <td>{product.status}</td>
                <td>{product.subCategory.category.name}</td>
                <td>{product.salePrice.toLocaleString()} đ</td>
                <td>{(product.price).toLocaleString()} đ</td>
                <td>
                  <div class="action">
                    <a class="delete btn btn-danger btn-sm" href="#">
                      <i class="fa-solid fa-trash"></i>
                    </a>
                    <Link class="edit btn btn-success btn-sm" to={`/admin/product/update/${product.productId}`}>
                      <i class="fa-solid fa-pen-to-square"></i>
                    </Link>
                  </div>
                </td>
              </tr>
            </tbody>
          ))}
        </table>
        {/* ----End----- */}
      </div>

      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={handlePageChange}
      />
    </div>
  );
}
