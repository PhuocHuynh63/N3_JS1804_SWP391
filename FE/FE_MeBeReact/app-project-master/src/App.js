import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import HomePage from './pages/homePage/HomePage';
import Layout from './layout/Layout';
import DetailPage from './pages/detailPage/ProductDetailPage';
import CartDetail from './pages/cartPage/CartDetail';
<<<<<<< HEAD
import Category from './pages/categoryPage/Category';
=======
import Category from './pages/milkPage/Category';
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0

function App() {
  return (
    <div className="min-h-screen">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout Component={HomePage} />} />
          <Route path="/product/:productId" element={<Layout Component={DetailPage} />} />
<<<<<<< HEAD
          <Route path="/category/:name" element={<Layout Component={Category} />} />
=======
          <Route path="/category/:category" element={<Layout Component={Category} />} />
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0
          <Route path="/cart" element={<Layout Component={CartDetail} />} />
          {/* <Route path="/" element={<About />} /> */}
          {/* <Route path="dashboard" element={<Dashboard />} /> */}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
