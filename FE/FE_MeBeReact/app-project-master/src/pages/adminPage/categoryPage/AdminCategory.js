import { useEffect, useState } from "react";
import "./AdminCategory.css";
import { meBeSrc } from "../../../service/meBeSrc";

export default function AdminCategory() {
  const [showAddCategory, setShowAddCategory] = useState(false);
  const [showAddSubCategory, setShowAddSubCategory] = useState(false);
  const [activeCategoryId, setActiveCategoryId] = useState(null); // State to track the currently active category

  /**
   * List of categories
   */
  const [categories, setCategories] = useState([]);
  useEffect(() => {
    meBeSrc.getListCategory()
      .then((res) => {
        console.log("Categories from API:", res.data);
        setCategories(res.data);
      }).catch((err) => {
        console.log(err);
      });
  }, []);
  //-----End-----//

  /**
   * List of subcategories
   */
  const [subcategories, setSubcategories] = useState([]);
  useEffect(() => {
    meBeSrc.getListSubCategory()
      .then((res) => {
        console.log("Subcategories from API:", res.data);
        setSubcategories(res.data);
      }).catch((err) => {
        console.log(err);
      });
  }, []);

  const getSubcategoriesByParent = (parentName) => {
    return subcategories.filter(sub => sub.category_parent === parentName);
  };
  //-----End-----//

  /**
   * List of products
   */
  const [product, setProduct] = useState([]);
  useEffect(() => {
    meBeSrc.getProduct()
      .then((res) => {
        console.log("Products from API:", res.data);
        setProduct(res.data);
      }).catch((err) => {
        console.log(err);
      });
  }, []);

  const getProductBySubCategory = (parentName) => {
    return product.filter(pro => pro.subCategory.name === parentName);
  };
  //-----End-----//

  /**
   * Handle toggle subcategories
   * @param {*} categoryId 
   */
  const toggleSubcategories = (categoryId) => {
    setActiveCategoryId(prevCategoryId => (prevCategoryId === categoryId ? null : categoryId));
  };
  //-----End-----//

  return (
    <div className="admin-category">
      <div className="category-header-container">
        <h1>Quản lý danh mục</h1>
        <button className="btn-add_category" onClick={() => setShowAddCategory(true)}> + Thêm danh mục mới</button>
      </div>
      <div className="category-container">
        {categories.map(category => (
          <div key={category.categoryId} className="category-card">
            <div className="category-header">
              <span onClick={() => toggleSubcategories(category.categoryId)}>
                {category.name} {activeCategoryId === category.categoryId ? '▲' : '▼'}
              </span>
              <button className="btn-add_subcategory" onClick={() => setShowAddSubCategory(category.categoryId)}> + Thêm tiểu danh mục mới</button>
            </div>
            <div className="category-content">
              <div className="category-info">
                <span>Có {getSubcategoriesByParent(category.name).length} tiểu danh mục</span>
                <div className="action">
                  <button className="delete btn btn-danger btn-sm">
                    <i className="fa-solid fa-trash"></i>
                  </button>
                  <button className="edit btn btn-success btn-sm">
                    <i className="fa-solid fa-pen-to-square"></i>
                  </button>
                </div>
              </div>
              {activeCategoryId === category.categoryId && (
                <div className="subcategory-list">
                  {getSubcategoriesByParent(category.name).map(subcategory => (
                    <div key={subcategory.subCategoryId} className="subcategory-card">
                      <span>{subcategory.name}</span>
                      <span>Sản phẩm: {getProductBySubCategory(subcategory.name).length}</span>
                      <div className="action">
                        <button className="delete btn btn-danger btn-sm">
                          <i className="fa-solid fa-trash"></i>
                        </button>
                        <button className="edit btn btn-success btn-sm">
                          <i className="fa-solid fa-pen-to-square"></i>
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
