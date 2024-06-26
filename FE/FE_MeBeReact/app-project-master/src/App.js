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
// import AdminDashboardPage from './pages/adminPage/AdminDashboardPage';

function App() {
  return (
    <div className="min-h-screen">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout Component={HomePage} />} />
          <Route path="/product/:productId" element={<Layout Component={DetailPage} />} />
          <Route path="/category/:slug" element={<Layout Component={Category} />} />
          <Route path="/subcategory/:subCategoryId" element={<Layout Component={SubCategory} />} />
          <Route path="/cart" element={<Layout Component={CartDetail} />} />
          <Route path="/checkout" element={<OrderPage />} />
          <Route path="/account/profile" element={<LayoutProfile Component={ProfileUser} />} />
          <Route path="/account/address" element={<LayoutProfile Component={AddressPage} />} />
          <Route path="/account/tracking" element={<LayoutProfile Component={TrackingPage} />} />
          {/* <Route path="/admin/dashboard" element={<Layout Component={AdminDashboardPage} />} /> */}
          {/* <Route path="/" element={<About />} /> */}
          {/* <Route path="dashboard" element={<Dashboard />} /> */}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
