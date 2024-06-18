import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import HomePage from './pages/homePage/HomePage';
import Layout from './layout/Layout';
import DetailPage from './pages/detailPage/ProductDetailPage';
import CartDetail from './pages/cartPage/CartDetail';
import Category from './pages/categoryPage/Category';

function App() {
  return (
    <div className="min-h-screen">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout Component={HomePage} />} />
          <Route path="/product/:productId" element={<Layout Component={DetailPage} />} />
          <Route path="/category/:name" element={<Layout Component={Category} />} />
          <Route path="/cart" element={<Layout Component={CartDetail} />} />
          {/* <Route path="/" element={<About />} /> */}
          {/* <Route path="dashboard" element={<Dashboard />} /> */}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
