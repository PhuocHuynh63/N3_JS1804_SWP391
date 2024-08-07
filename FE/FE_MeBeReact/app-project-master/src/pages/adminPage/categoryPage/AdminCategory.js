import { useEffect, useState } from "react";
import "./AdminCategory.css";
import { meBeSrc } from "../../../service/meBeSrc";
import PopupAddCategory from "./addCategory/PopupAddCategory";
import PopupAddSubCategory from "./addSubCategory/PopupAddSubCategory";
import PopupUpdateCategory from "./updateCategory/PopupUpdateCategory";
import PopupDeleteCategory from "./deleteCategory/PopupDeleteCategory";
import PopupDeleteSubCategory from "./deleteSubcategory/PopupDeleteSubCategory";
import PopupUpdateSubCategory from "./updateSubcategory/PopupUpdateSubCategory";

export default function AdminCategory() {
  const [showAddCategory, setShowAddCategory] = useState(false);        //Add category
  const [showUpdateCategory, setShowUpdateCategory] = useState(false);  //Update category
  const [showDeleteCategory, setShowDeleteCategory] = useState(false);  //Delete category
  const [showAddSubCategory, setShowAddSubCategory] = useState(false);  //Add subcategory
  const [showUpdateSubCategory, setShowUpdateSubCategory] = useState(false);  //Update subcategory
  const [showDeleteSubCategory, setShowDeleteSubCategory] = useState(false);  //Delete subcategory

  const [activeCategoryId, setActiveCategoryId] = useState(null); // State to track the currently active category
  const [activeSubCategoryId, setActiveSubCategoryId] = useState(null); // State to track the currently active category
  const [activeCategoryName, setActiveCategoryName] = useState('');

  /**
   * List of categories
   */
  const [categories, setCategories] = useState([]);
  useEffect(() => {
    meBeSrc.getListCategory()
      .then((res) => {
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
  const toggleCategories = (categoryId) => {
    setActiveCategoryId(prevCategoryId => (prevCategoryId === categoryId ? null : categoryId));
  };
  //-----End-----//


  /**
   * Handle show add subcategory
   * @param {*} categoryId 
   * @param {*} categoryName 
   */
  const handleShowAddSubCategory = (categoryId, categoryName) => {
    setActiveCategoryId(categoryId);
    setActiveCategoryName(categoryName);
    setShowAddSubCategory(true);
  };
  //-----End-----//


  /**
   * Handle show update category
   * @param {*} categoryId 
   */
  const handleShowUpdateCategory = (categoryId) => {
    setActiveCategoryId(categoryId);
    setShowUpdateCategory(true);
  };
  //-----End-----//


  /**
   * Handle show update category
   * @param {*} categoryId 
   */
  const handleShowUpdateSubCategory = (subCategoryId) => {
    setActiveSubCategoryId(subCategoryId);
    setShowUpdateSubCategory(true);
  };
  //-----End-----//


  /**
   * Handle show delete category
   * @param {*} categoryId 
   */

  const handleShowDeleteCategory = (categoryId) => {
    setActiveCategoryId(categoryId);
    setShowDeleteCategory(true);
  };
  //-----End-----//


  /**
   * Handle show delete category
   * @param {*} categoryId 
   */

  const handleShowDeleteSubCategory = (subCategoryId) => {
    setActiveSubCategoryId(subCategoryId);
    setShowDeleteSubCategory(true);
  };
  //-----End-----//


  return (
    <div className="admin-category">
      <PopupAddCategory show={showAddCategory} handleClose={() => setShowAddCategory(false)} />
      <PopupAddSubCategory show={showAddSubCategory} handleClose={() => setShowAddSubCategory(false)} parent_id={activeCategoryId} parentName={activeCategoryName} />
      <PopupUpdateCategory show={showUpdateCategory} handleClose={() => setShowUpdateCategory(false)} categoryId={activeCategoryId} />
      <PopupUpdateSubCategory show={showUpdateSubCategory} handleClose={() => setShowUpdateSubCategory(false)} subCategoryId={activeSubCategoryId} />
      <PopupDeleteCategory show={showDeleteCategory} handleClose={() => setShowDeleteCategory(false)} categoryId={activeCategoryId} length={categories.length} />
      <PopupDeleteSubCategory show={showDeleteSubCategory} handleClose={() => setShowDeleteSubCategory(false)} subCategoryId={activeSubCategoryId} length={subcategories.length} />

      <div className="category-header-container">
        <h1>Quản lý danh mục</h1>
        <button className="btn-add_category" onClick={() => setShowAddCategory(true)}> + Thêm danh mục mới</button>
      </div>
      <div className="category-container">
        {categories.map(category => (
          <div key={category.categoryId} className="category-card">
            <div className="category-header">
              <span onClick={() => toggleCategories(category.categoryId)}>
                {category.name} {activeCategoryId === category.categoryId ? '▲' : '▼'}
              </span>
              <button className="btn-add_subcategory" onClick={() => handleShowAddSubCategory(category.categoryId, category.name)}> + Thêm tiểu danh mục mới</button>
            </div>
            <div className="category-content">
              <div className="category-info">
                <span>Có {getSubcategoriesByParent(category.name).length} tiểu danh mục</span>
                <div className="action">
                  <button className="delete btn btn-danger btn-sm" onClick={() => handleShowDeleteCategory(category.categoryId)}>
                    <i className="fa-solid fa-trash"></i>
                  </button>
                  <button className="edit btn btn-success btn-sm" onClick={() => handleShowUpdateCategory(category.categoryId)}>
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
                          <i className="fa-solid fa-trash" onClick={() => handleShowDeleteSubCategory(subcategory.subCategoryId)}></i>
                        </button>
                        <button className="edit btn btn-success btn-sm" onClick={() => handleShowUpdateSubCategory(subcategory.subCategoryId)}>
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
