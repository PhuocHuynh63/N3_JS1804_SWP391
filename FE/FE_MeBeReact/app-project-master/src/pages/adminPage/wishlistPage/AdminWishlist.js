import { Link, NavLink } from "react-router-dom";
import "./AdminWishlist.css";
import { useEffect, useState } from "react";
import { meBeSrc } from "../../../service/meBeSrc";
import Pagination from "../../../components/pagination/Pagination";
import PopupWishlist from "./PopupWishlist/PopupWishlist";

export default function AdminWishlist() {

  /**
   * Call API to get products
   */
  const [wishlists, setWishlists] = useState([]);
  useEffect(() => {
    meBeSrc.getWishlist()
      .then((res) => {
        setWishlists(res.data);
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
          setWishlists(res.data);
        })
        .catch((err) => {
          console.log("Error fetching product", err);
        });
    } else {
      meBeSrc.getProduct()
        .then((res) => {
          setWishlists(res.data);
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
   * 
   */
  const [showModal, setShowModal] = useState(false);
  const handlePopupWishlist = (userId) => {
    setShowModal(true);
    // meBeSrc.get
  }

  /**
    * Filter Product
    * */
  const filterStockOut = wishlists.filter(product => product.status === 'Hết hàng');
  //-----End-----//


  /**
  * Pagination
  */
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 15;

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const currentProducts = wishlists.slice((currentPage - 1) * productsPerPage, currentPage * productsPerPage);
  const totalPages = Math.ceil(wishlists.length / productsPerPage);
  //-----End-----//





  return (
    <div className="admin-product">
      <PopupWishlist show={showModal} handleClose={() => setShowModal(false)} ></PopupWishlist>

      <h1 className="header-product">Quản lý danh sách đặt trước</h1>

      <div className="admin-product_action">
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
              <th>Thao tác</th>
            </tr>
          </thead>
          {currentPage.length === 0 ? (
            <>
              <span className="no-product">Không có sản phẩm nào</span>
            </>
          ) : currentProducts.map(product => (
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
                <td style={{ textAlign: "end" }}>
                  <div className="action">
                    <a className="view btn btn-warning btn-sm">
                      <i className="fa-solid fa-eye"></i>
                    </a>
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
