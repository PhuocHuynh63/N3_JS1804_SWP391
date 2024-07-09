import { useState, useEffect } from "react";
import "./UpdateProduct.css";
import { meBeSrc } from "../../../../service/meBeSrc";
import Successful from "../../../../components/popupSuccessful/Successful";
import { Link } from "react-router-dom";

export default function UpdateProduct() {
    const productId = window.location.pathname.split("/").pop();
    const [showModal, setShowModal] = useState(false);


    /**
     * State form data
     */
    const [formData, setFormData] = useState({
        image: '',
        subCategory: "",
        slug: "",
        productName: "",
        description: "",
        price: "",
        salePrice: "",
        status: "",
        totalSold: 0,
        quantity: 0,
        productView: 0,
    });
    //-----End-----//

    const [discount, setDiscount] = useState(0);


    /**
     * Handle Change Input
     * @param {*} e 
     */
    const handleChange = (e) => {
        const { id, value } = e.target;
        setFormData({
            ...formData,
            [id]: value,
        });
    };

    const handleDiscountChange = (e) => {
        const value = Math.max(0, Math.min(100, e.target.value));
        setDiscount(value);
    };
    //-----End-----//


    /**
     * Call API to get sub category
     */
    const [subCategory, setSubCategory] = useState([]);

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
    const [product, setProduct] = useState(null);

    useEffect(() => {
        const productId = window.location.pathname.split("/").pop();
        meBeSrc.getProductById(productId)
            .then((res) => {
                setProduct(res.data);
                // console.log(res.data);
                setFormData({
                    ...formData,
                    image: res.data.images,
                    subCategory: res.data.subCategory.subCateId,
                    slug: res.data.slug,
                    productName: res.data.name,
                    description: res.data.description,
                    price: res.data.price,
                    salePrice: res.data.salePrice,
                    status: res.data.status,
                    totalSold: res.data.totalSold,
                    quantity: res.data.quantity,
                    productView: res.data.productView,
                });
                setDiscount(((res.data.price - res.data.salePrice) / res.data.price * 100).toFixed(2));
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);
    //-----End-----//


    /**
     * Handle Image Change
     */
    const [imagePreview, setImagePreview] = useState(null);

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        setFormData({
            ...formData,
            image: file,
        });
        setImagePreview(URL.createObjectURL(file));
    };
    //-----End-----//


    /**
     * Handle Submit Form
     * @param {*} e 
     */
    const handleSubmit = (e) => {
        e.preventDefault();
        const productData = new FormData();
        productData.append('file', formData.image);
        productData.append('product', JSON.stringify({
            subCategoryId: formData.subCategory,
            slug: formData.slug,
            name: formData.productName,
            description: formData.description,
            price: formData.price,
            salePrice: formData.salePrice,
            status: formData.status,
            totalSold: formData.totalSold,
            quantity: formData.quantity,
            productView: formData.productView,
        }));

        console.log(productId);
        console.log(formData);

        meBeSrc.putProduct(productId, productData)
            .then((res) => {
                setShowModal(true);
                console.log('Product created successfully', res.data);
            })
            .catch((err) => {
                console.error('Error creating product', err);
            });
    };
    //-----End-----//


    /**
     * Handle status product
     */
    const handleStatus = () => {
        if (formData.quantity > 0) {
            setFormData({ ...formData, status: "Còn hàng" });
        } else {
            setFormData({ ...formData, status: "Hết hàng" });
        }
    };

    useEffect(() => {
        handleStatus();
    }, [formData.quantity]);
    //-----End-----//


    /**
     * Handle sale price
     */
    const handleSalePrice = () => {
        const price = formData.price;
        const salePrice = price - (price * discount) / 100;
        setFormData({ ...formData, salePrice: salePrice });
    };

    useEffect(() => {
        if (formData.price && discount) {
            handleSalePrice();
        }
    }, [formData.price, discount]);
    //-----End-----//


    return (
        <div className="admin-product-add">
            <Successful show={showModal} onHide={() => setShowModal(false)} message={"Sản phẩm đã được cập nhật thành công"} />
            <h1>Cập nhật sản phẩm</h1>
            <form onSubmit={handleSubmit}>
                <div className="form-row">
                    <div className="form-group">
                        <label htmlFor="productName">Tên sản phẩm: <span className="obligatory">*</span></label>
                        <input
                            type="text"
                            id="productName"
                            value={formData.productName}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="subCategory">Loại sản phẩm: <span className="obligatory">*</span></label>
                        <select
                            id="subCategory"
                            value={formData.subCategory}
                            onChange={handleChange}
                            required
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
                            value={formData.price.toLocaleString()}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="discount">Giảm giá: <span style={{ color: "red", fontSize: "13px" }}>{formData.salePrice.toLocaleString()}đ</span></label>
                        <input
                            type="number"
                            id="discount"
                            value={discount}
                            onChange={handleDiscountChange}
                            min="0"
                            max="100"
                        />
                    </div>
                </div>

                <div className="form-row">
                    <div className="form-group">
                        <label htmlFor="quantity">Số lượng:</label>
                        <input
                            type="number"
                            id="quantity"
                            value={formData.quantity}
                            onChange={handleChange}
                        />
                    </div>
                </div>

                <div className="form-row">
                    <div className="form-group image">
                        <label htmlFor="image">Ảnh: <span className="obligatory">*</span></label>
                        <input type="file" id="image" onChange={handleImageChange} />
                        {imagePreview && <img src={imagePreview} alt="Image Preview" className="image-preview" />}
                    </div>
                </div>

                <div className="form-group">
                    <label htmlFor="description">Mô tả:</label>
                    <textarea
                        id="description"
                        rows="4"
                        value={formData.description}
                        onChange={handleChange}
                    />
                </div>

                <div className="form-group_btn">
                    <Link to={'/admin/product'} className="btn-close_add">Quay lại</Link>
                    <button className="btn-add" type="submit">Lưu</button>
                </div>
            </form>
        </div>
    );
}
