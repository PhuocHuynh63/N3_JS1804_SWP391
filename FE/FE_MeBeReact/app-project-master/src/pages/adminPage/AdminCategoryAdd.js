import { useState } from "react";
import "./AdminCategoryAdd.css";

export default function AdminCategoryAdd() {
  const [categoryName, setCategoryName] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({
      categoryName,
    });
  };
  return (
    <div class="admin-category-add">
      <h1>Thêm danh mục mới</h1>
      <form onSubmit={handleSubmit}>
        <div class="form-group">
          <label htmlFor="categoryName">Tên Danh Mục:</label>
          <input
            type="text"
            id="categoryName"
            value={categoryName}
            onChange={(e) => setCategoryName(e.target.value)}
            required
          />
        </div>
        <div class="form-group">
        <button class="btn-add" type="submit">
          Thêm danh mục
        </button>
        </div>
      </form>
    </div>
  );
}
