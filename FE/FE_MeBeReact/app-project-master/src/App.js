import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import HomePage from './pages/homePage/HomePage';
import Layout from './layout/Layout';
import DetailPage from './pages/detailPage/ProductDetailPage';
import CartDetail from './pages/cartPage/CartDetail';
import Category from './pages/categoryPage/Category';
import SubCategory from './pages/subcategoryPage/SubCategoryPage';
import ProfileUser from './pages/profilePage/ProfileUser';
import AddressPage from './pages/addressPage/AddressPage';
import LayoutProfile from './layout/LayoutProfile';
import TrackingPage from './pages/trackingPage/TrackingPage';
import OrderPage from './pages/orderPage/OrderPage';
import NotFoundPage from './pages/notFoundPage/notFoundPage';
import ForgotPasswordPage from './pages/forgotPassword/forgotPassword';
import RegisterPage from './pages/registerPage/registerPage';
import SuccessPage from './components/success/SuccessPage';
import changePassword from './pages/profilePage/changePassword';
import ErrorPage from './components/error/ErrorPage';
import SearchPage from './pages/searchPage/SearchPage';

import LayoutAdmin from './layout/LayoutAdmin';
import AdminPage from './pages/adminPage/AdminPage';
import AdminProduct from './pages/adminPage/AdminProduct';
import AdminCategory from './pages/adminPage/AdminCategory';
import AdminUser from './pages/adminPage/AdminUser';
import AdminProductAdd from './pages/adminPage/AdminProductAdd';
import AdminCategoryAdd from './pages/adminPage/AdminCategoryAdd';
import AdminUserAdd from './pages/adminPage/AdminUserAdd';
import AdminOrder from './pages/adminPage/AdminOrder';
import AdminRole from './pages/adminPage/AdminRole';
// import AdminDashboardPage from './pages/adminPage/AdminDashboardPage';

function App() {
  return (
    <div className="min-h-screen">
      <BrowserRouter>
        <Routes>
          <Route path="*" element={<Layout Component={NotFoundPage} />} />
          <Route path="/" element={<Layout Component={HomePage} />} />
          <Route path="/signup" element={<Layout Component={RegisterPage} />} />
          <Route path="/reset-password" element={<Layout Component={ForgotPasswordPage} />} />
          <Route path="/product/:productId" element={<Layout Component={DetailPage} />} />
          <Route path="/category/:slug" element={<Layout Component={Category} />} />
          <Route path="/subcategory/:subCategoryId" element={<Layout Component={SubCategory} />} />
          <Route path="/search/:name" element={<Layout Component={SearchPage} />} />
          <Route path="/cart" element={<Layout Component={CartDetail} />} />
          <Route path="/checkout" element={<OrderPage />} />
          <Route path="/order-success" element={<Layout Component={() => <SuccessPage message={"Đơn hàng đã được tạo thành công"} />} />} />
          <Route path="/order-error" element={<Layout Component={() => <ErrorPage message={"Có lỗi xảy ra khi tạo đơn hàng. Vui lòng thử lại!"} />} />} />
          <Route path="/account/profile" element={<LayoutProfile Component={ProfileUser} />} />
          <Route path="/account/address" element={<LayoutProfile Component={AddressPage} />} />
          <Route path="/account/tracking" element={<LayoutProfile Component={TrackingPage} />} />
          <Route path="/account/changePassword" element={<LayoutProfile Component={changePassword} />} />

          <Route path="/admin" element={<LayoutAdmin Component={AdminPage} />} />

          <Route path="/admin/product" element={<LayoutAdmin Component={AdminProduct} />} />
          <Route path="/admin/product/add" element={<LayoutAdmin Component={AdminProductAdd} />} />

          <Route path="/admin/category" element={<LayoutAdmin Component={AdminCategory} />} />
          <Route path="/admin/category/add" element={<LayoutAdmin Component={AdminCategoryAdd} />} />

          <Route path="/admin/user" element={<LayoutAdmin Component={AdminUser} />} />
          <Route path="/admin/user/add" element={<LayoutAdmin Component={AdminUserAdd} />} />

          <Route path="/admin/order" element={<LayoutAdmin Component={AdminOrder} />} />
          <Route path="/admin/role" element={<LayoutAdmin Component={AdminRole} />} />
          {/* <Route path="/admin/dashboard" element={<Layout Component={AdminDashboardPage} />} /> */}
          {/* <Route path="/" element={<About />} /> */}
          {/* <Route path="dashboard" element={<Dashboard />} /> */}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
