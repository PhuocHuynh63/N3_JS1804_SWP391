import { NavLink } from 'react-router-dom';
import './CartDetail.css'
import 'bootstrap/dist/css/bootstrap.min.css'

export default function CartDetail() {
  return (
    <section class="cart-details container py-5 mt-5">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <NavLink to={"/"}>
              Trang chủ
            </NavLink>
          </li>
          <li class="breadcrumb-item active" aria-current="page">
            Giỏ hàng
          </li>
        </ol>
      </nav>
      <div class="row">
        <div class="col-md-8">
          <div class="cart-item">
            <div class="row">
              <div class="col-md-3">
                <img
                  src="images/ao.pnj.webp"
                  class="img-fluid"
                  alt="Bánh AD Little Spoon vị cam quýt 30g"
                />
              </div>
              <div class="col-md-3">
                <h5>QUANG MINH</h5>
                <p>Bánh AD Little Spoon vị cam quýt 30g</p>
                <p>SKU: 125728 Default Title</p>
                <button class="btn btn-link p-0">Xóa</button>
              </div>
              <div class="col-md-3 text-center">
                <span class="price">75,000đ</span>
              </div>
              <div class="col-md-3">
                <div class="input-group justify-content-center">
                  <div class="input-group-prepend">
                    <button class="btn btn-outline-secondary" type="button">
                      -
                    </button>
                  </div>
                  <input type="text" class="form-control" value="1" />
                  <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button">
                      +
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          {/*  Repeat similar blocks for other cart items */}
          <div class="cart-item">
            <div class="row">
              <div class="col-md-3">
                <img
                  src="images/ao.pnj.webp"
                  class="img-fluid"
                  alt="Bộ quần áo dài tay Nous petit (trắng/hồng)"
                />
              </div>
              <div class="col-md-3">
                <h5>NOUS</h5>
                <p>Bộ quần áo dài tay Nous petit (trắng/hồng)</p>
                <p>SKU: 118013 0-3M / Trắng</p>
                <button class="btn btn-link p-0">Xóa</button>
              </div>
              <div class="col-md-3 text-center">
                <span class="price">195,000đ</span>
              </div>
              <div class="col-md-3">
                <div class="input-group justify-content-center">
                  <div class="input-group-prepend">
                    <button class="btn btn-outline-secondary" type="button">
                      -
                    </button>
                  </div>
                  <input type="text" class="form-control" value="4" />
                  <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button">
                      +
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="cart-item">
            <div class="row">
              <div class="col-md-3 justify-content-center align-items-center">
                <img
                  src="images/ao.pnj.webp"
                  class="img-fluid"
                  alt="Gạc Răng Miệng Chippi Baby"
                />
              </div>
              <div class="col-md-3">
                <h5>EIFFEL</h5>
                <p>Gạc Răng Miệng Chippi Baby</p>
                <p>SKU: 8938528034493 Default Title</p>
                <button class="btn btn-link p-0">Xóa</button>
              </div>
              <div class="col-md-3 text-center">
                <span class="price">110,000đ</span>
              </div>
              <div class="col-md-3">
                <div class="input-group justify-content-center">
                  <div class="input-group-prepend">
                    <button class="btn btn-outline-secondary" type="button">
                      -
                    </button>
                  </div>
                  <input type="text" class="form-control" value="1" />
                  <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button">
                      +
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="cart-summary">
            <h4 id='cart-title'>Thông tin đơn hàng</h4>
            <p className='description'>
              Tạm tính (6 sản phẩm): <span class="price">965,000đ</span>
            </p>
            <p className='description'>
              Phí vận chuyển sẽ được tính ở trang thanh toán. Bạn cũng có thể
              nhập mã giảm giá ở trang thanh toán.
            </p>
            <h5 id='summary'>
              Tổng cộng: <span class="price">965,000đ</span>
            </h5>
            <div class="form-check">
              <input
                type="checkbox"
                class="form-check-input"
                id="invoiceCheck"
              />
              <label class="form-check-label" for="invoiceCheck">
                Xuất Hóa Đơn
              </label>
            </div>
            <button class="btn btn-primary btn-block mt-3">ĐẶT HÀNG</button>
          </div>
        </div>
      </div>
    </section>
  );
}
