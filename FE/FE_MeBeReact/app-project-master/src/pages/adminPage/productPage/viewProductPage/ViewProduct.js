import { useState, useEffect } from "react";
import "./ViewProduct.css";
import { meBeSrc } from "../../../../service/meBeSrc";
import { Link } from "react-router-dom";
import User from "../../../../components/user/User";

export default function ViewProduct() {
    const productId = window.location.pathname.split("/").pop();
    const [discount, setDiscount] = useState(0);
    const [subCategory, setSubCategory] = useState([]);
    const [product, setProduct] = useState({
        productName: '',
        subCategory: '',
        price: 0,
        salePrice: 0,
        quantity: 0,
        description: '',
        image: '',
        totalSold: 0
    });

    /**
     * Call API to get sub category
     */
    useEffect(() => {
        meBeSrc.getListSubCategory()
            .then((res) => {
                setSubCategory(res.data);
            }).catch((err) => {
                console.log(err);
            });
    }, []);
    //-----End-----//

    /**
     * Call API to get product by id
     */
    useEffect(() => {
        meBeSrc.getProductById(productId)
            .then((res) => {
                setProduct(res.data);
                setDiscount(((res.data.price - res.data.salePrice) / res.data.price * 100).toFixed(2));
            })
            .catch((err) => {
                console.log(err);
            });
    }, [productId]);
    //-----End-----//

    const calculateRevenue = () => {
        return product.totalSold * product.salePrice;
    };

    return (
        <div className="admin-product-view">
            <User />
            <h1>Chi tiết sản phẩm</h1>
            <form>
                <div className="form-row">
                    <div className="form-group">
                        <label htmlFor="productName">Tên sản phẩm: <span className="obligatory">*</span></label>
                        <input
                            type="text"
                            id="productName"
                            value={product.name}
                            disabled
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="subCategory">Loại sản phẩm: <span className="obligatory">*</span></label>
                        <select
                            id="subCategory"
                            value={product.subCategory}
                            disabled
                        >
                            {subCategory.map((item) => (
                                <option key={item.subCategoryId} value={item.subCategoryId}>{item.name}</option>
                            ))}
                        </select>
                    </div>
                </div>

                <div className="form-row">
                    <div className="form-group">
                        <label htmlFor="price">Giá: <span className="obligatory">*</span></label>
                        <input
                            type="number"
                            id="price"
                            value={product.price}
                            disabled
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="discount">Giảm giá: <span style={{ color: "red", fontSize: "13px" }}>{product.salePrice.toLocaleString()}đ</span></label>
                        <input
                            type="number"
                            id="discount"
                            value={discount}
                            disabled
                        />
                    </div>
                </div>

                <div className="form-row">
                    <div className="form-group">
                        <label htmlFor="quantity">Số lượng:</label>
                        <input
                            type="number"
                            id="quantity"
                            value={product.quantity}
                            disabled
                        />
                    </div>
                </div>

                <div className="form-row">
                    <div className="form-group image">
                        <label htmlFor="image">Ảnh: <span className="obligatory">*</span></label>
                        <input type="file" id="image" disabled />
                    </div>
                </div>

                <div className="form-group">
                    <label htmlFor="description">Mô tả:</label>
                    <textarea
                        id="description"
                        rows="4"
                        value={product.description}
                        disabled
                    />
                </div>
                <div className="form-row">
                    <div className="form-group">
                        <label htmlFor="total_sold">Số lượt bán: </label>
                        <input
                            type="text"
                            id="total_sold"
                            value={product.totalSold}
                            disabled
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="revenue">Doanh thu: </label>
                        <input
                            type="text"
                            id="revenue"
                            value={calculateRevenue().toLocaleString()}
                            disabled
                        />
                    </div>
                </div>
                <div className="form-group_btn">
                    <Link to={'/admin/product'} className="btn-close_add">Quay lại</Link>
                    <Link to={`/admin/product/update/${productId}`} className="btn-add" style={{ textAlign: "center" }}>Cập nhật <i className="fa-solid fa-arrow-right"></i></Link>
                </div>
            </form>
        </div>
    );
}
