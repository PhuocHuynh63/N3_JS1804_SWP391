import { useEffect, useState } from 'react';
import { NavLink } from 'react-router-dom';
import './CartDetail.css'
import 'bootstrap/dist/css/bootstrap.min.css'

export default function CartDetail() {
  const [cartItems, setCartItems] = useState([]);

  /**
   * *Get cart items from local storage
   */
  useEffect(() => {
    const storedCartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
    setCartItems(storedCartItems);
  }, []);


  /**
   * *Update quantity of an item in the cart 
   */
  const updateQuantity = (productId, change) => {
    const updatedCartItems = cartItems.map(item => {
        if (item.productId === productId) {
            const newQuantity = item.quantity + change;
            if (newQuantity > 0 && newQuantity <= item.max) {
                item.quantity = newQuantity;
                item.totalPrice = item.price * newQuantity;
            }
        }
        return item;
    }).filter(item => item.quantity > 0); // Remove items with 0 quantity
    setCartItems(updatedCartItems);
    localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
}

  /**
   * *Get total quantity of items in the cart
   */
  const getTotalQuantity = () => {
    return cartItems.reduce((total, item) => total + item.quantity, 0);
  };

  // Get total price of items in the cart
  const getTotalPrice = () => {
    return cartItems.reduce((total, item) => total + item.totalPrice, 0).toLocaleString('vi-VN');
  };

  // Remove item from cart
  const handleRemoveItem = (productId) => {
    const updatedCartItems = cartItems.filter(item => item.productId !== productId);
    setCartItems(updatedCartItems);
    localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
  }

  return (
    <section className="cart-details container py-5 mt-5">
      <nav aria-label="breadcrumb">
        <ol className="breadcrumb">
          <li className="breadcrumb-item">
            <NavLink to={"/"}>
              Trang chủ
            </NavLink>
          </li>
          <li className="breadcrumb-item active" aria-current="page">
            Giỏ hàng
          </li>
        </ol>
      </nav>
      <div className="row">
        {cartItems.length === 0 ? (
          <div className="col-md-12 text-center">
            <div className='cart-detail_emty'>
              <img src='https://file.hstatic.net/200000259653/file/empty-cart_large_46db8e27ff56473ca63e3c4bb8981b64.png' alt="Empty cart" />
              <NavLink to={"/"}>
                Tiếp tục mua sắm →
              </NavLink>
            </div>
          </div>
        ) : (
          <>
            <div className="col-md-8">
              <div className="cart-item">
                {cartItems.map((item, index) => (
                  <div className="row" key={index}>
                    <div className="col-md-3">
                      <NavLink to={`/product/${item.productId}`}>
                        <img
                          src={item.images}
                          className="img-fluid"
                          alt={item.name}
                        />
                      </NavLink>
                    </div>

                    <div className="col-md-3">
                      <NavLink to={`/product/${item.productId}`} style={{ color: "black" }}>
                        <h5>{item.name}</h5>
                      </NavLink>
                      <button className="btn btn-link p-0" onClick={() => handleRemoveItem(item.productId)}>Xóa</button>
                    </div>
                    <div className="col-md-3 text-center">
                      <span className="price">{item.price.toLocaleString('vi-VN')}₫</span>
                    </div>
                    <div className="col-md-3">
                      <div className="input-group justify-content-center">
                        <div className="input-group-prepend">
                          <button className="btn btn-outline-secondary" type="button" onClick={() => updateQuantity(item.productId, -1)}>
                            -
                          </button>
                        </div>
                        <input type="text" className="form-control" value={item.quantity} readOnly />
                        <div className="input-group-append">
                          <button className="btn btn-outline-secondary" type="button" onClick={() => updateQuantity(item.productId, 1)}>
                            +
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
            <div className="col-md-4">
              <div className="cart-summary">
                <h4 id='cart-title'>Thông tin đơn hàng</h4>
                <p className='description'>
                  Tạm tính ({getTotalQuantity()} sản phẩm): <span className="price">{getTotalPrice()}₫</span>
                </p>
                <p className='description'>
                  Phí vận chuyển sẽ được tính ở trang thanh toán. Bạn cũng có thể
                  nhập mã giảm giá ở trang thanh toán.
                </p>
                <h5 id='summary'>
                  Tổng cộng: <span className="price">{getTotalPrice()}₫</span>
                </h5>
                <NavLink to={"/checkout"} className="btn btn-primary">
                  ĐẶT HÀNG
                </NavLink>
              </div>
            </div>
          </>
        )}
      </div>
    </section>
  );
}
