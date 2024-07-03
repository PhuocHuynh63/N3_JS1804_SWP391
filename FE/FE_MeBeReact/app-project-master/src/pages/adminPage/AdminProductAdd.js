import { useState } from "react";
import "./AdminProductAdd.css";
import { NavLink } from "react-router-dom";

export default function AdminProductAdd() {
  const [productName, setProductName] = useState("");
  const [image, setImage] = useState(null);
  const [quantity, setQuantity] = useState(0);
  const [category, setCategory] = useState("");
  const [price, setPrice] = useState(0);

  const handleImageChange = (e) => {
    setImage(e.target.files[0]);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({
      productName,
      image,
      quantity,
      category,
      price,
    });
  };

  return (
    <div class="admin-product-add">
      <h1>Thêm sản phẩm mới</h1>
      <form onSubmit={handleSubmit}>
        <div class="form-group">
          <label htmlFor="productName">Tên sản phẩm:</label>
          <input
            type="text"
            id="productName"
            value={productName}
            onChange={(e) => setProductName(e.target.value)}
            required
          />
        </div>
        <div class="form-group">
          <label htmlFor="category">Danh mục:</label>
          <select
            id="category"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            required
          >
            <option value="">Chọn danh mục</option>
            <option value="category1">Danh mục 1</option>
            <option value="category2">Danh mục 2</option>
            <option value="category3">Danh mục 3</option>
          </select>
        </div>

        <div class="form-group">
          <label htmlFor="quantity">Số lượng </label>
          <input
            type="text"
            id="quantity"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
            required
          />
        </div>

        <div class="form-group">
          <label htmlFor="price">Giá tiền:</label>
          <input
            type="number"
            id="price"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            required
          />
        </div>

        <div class="form-group">
          <label htmlFor="image">Hình ảnh:</label>
          <input type="file" id="image" onChange={handleImageChange} />
        </div>

        <div class="form-group">
            <button class="btn-add" type="submit">Thêm sản phẩm</button>
        </div>
      </form>
    </div>
  );
}
