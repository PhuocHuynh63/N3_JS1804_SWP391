import { Link, NavLink } from "react-router-dom";
import "./AdminProduct.css";
import { useEffect, useState } from "react";
import { meBeSrc } from "../../../service/meBeSrc";
import Pagination from "../../../components/pagination/Pagination";
import PopupDeleteProduct from "./deleteProduct/PopupDeleteProduct";
import User from "../../../components/user/User";

export default function AdminProduct() {

  /**
   * Call API to get products
   */
  const [products, setProducts] = useState([]);
  useEffect(() => {
    meBeSrc.getProduct()
      .then((res) => {
        setProducts(res.data);
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
   * Handle Delete Product Id
   */
  const [activeProductId, setActiveProductId] = useState(null);
  const [showModalDelete, setShowModalDelete] = useState(false);

  const handleDeleteProduct = (id) => {
    setActiveProductId(id);
    setShowModalDelete(true);
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
    <div className="admin-product">
      <User />
      <PopupDeleteProduct show={showModalDelete} handleClose={() => setShowModalDelete(false)} productId={activeProductId} />

      <h1 className="header-product">Quản lý sản phẩm</h1>

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
      <div className="box-product">
        <table className="product-list">
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
            <tbody key={product.productId}>
              <tr>
                <td>{product.productId}</td>
                <td>
                  <img className="product-image" src={product.images}></img>
                </td>
                <td style={{ textAlign: "left" }}>{product.name}</td>
                <td style={{ color: product.status === 'Hết hàng' ? 'red' : 'black' }}>
                  {product.status}
                </td>
                <td>{product.subCategory.category.name}</td>
                <td>{product.salePrice > 0 ? product.salePrice.toLocaleString() : product.price.toLocaleString()} đ</td>
                <td>{(product.price).toLocaleString()} đ</td>
                <td style={{ textAlign: "end" }}>
                  <div className="action">
                    <a className="delete btn btn-danger btn-sm" onClick={() => handleDeleteProduct(product.productId)}>
                      <i className="fa-solid fa-trash"></i>
                    </a>
                    <Link className="view btn btn-warning btn-sm" to={`/admin/product/detail/${product.productId}`}>
                      <i className="fa-solid fa-eye"></i>
                    </Link>
                    <Link className="edit btn btn-success btn-sm" to={`/admin/product/update/${product.productId}`}>
                      <i className="fa-solid fa-pen-to-square"></i>
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
