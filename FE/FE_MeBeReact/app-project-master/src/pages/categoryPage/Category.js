import React, { useEffect, useState } from 'react';
import './Category.css';
import { meBeSrc } from '../../service/meBeSrc';
import { useParams } from 'react-router-dom';

export default function Category() {
    // Initialize category as an object
    const [category, setCategory] = useState({});
    const { name } = useParams();

    useEffect(() => {
        meBeSrc.getCategoryByName(name)
            .then((res) => {
                setCategory(res.data);
            })
            .catch((err) => {
                console.log("Error fetching category", err);
            });
    }, [name]);

    const [isActive, setIsActive] = useState(false);
    useEffect(() => {
        setIsActive(category.name === name);
    }, [category, name]);

    // Call API to get subCategory
    const [subCategories, setSubCategories] = useState([]);
    const [products, setProducts] = useState([]);

    useEffect(() => {
        meBeSrc.getListSubCategory()
            .then((res) => {
                const filteredSubCategories = res.data.filter((subCategory) => subCategory.category_parent === category.name);
                setSubCategories(filteredSubCategories);
                console.log(subCategories)
            }).catch((err) => {
                console.log('Error fetching subcategories', err);
            });
    }, [category.name]);

    return (
        <div className="category-container">
            <div className="category-header">
                <h2>Bạn cần tìm</h2>
                <div className="category-icons">
                    {subCategories.map((subCategory) => (
                        <div className="category-icon" key={subCategory.name}>
                            <img src={subCategory.image} alt={subCategory.name} />
                            <p>{subCategory.name}</p>
                        </div>
                    ))}
                </div>
            </div>
            <div className="category-section">
                <h3>Bộ tay dài</h3>
                <div className="products">
                    <div className="product">
                        <img src="link-to-image" alt="Product 1" />
                        <p>Bộ dài chui đầu màu vàng</p>
                        <p>195,000đ</p>
                    </div>
                    <div className="product">
                        <img src="link-to-image" alt="Product 2" />
                        <p>Bộ dài chui đầu cổ bèo màu hồng nhạt</p>
                        <p>195,000đ</p>
                    </div>
                    <div className="product">
                        <img src="link-to-image" alt="Product 3" />
                        <p>Bộ dài chui đầu màu xanh nhạt</p>
                        <p>195,000đ</p>
                    </div>
                </div>
                <a href="#" className="view-more">Xem thêm</a>
            </div>
            <div className="category-section">
                <h3>Bộ bodysuite tay dài</h3>
                <div className="products">
                    <div className="product">
                        <img src="link-to-image" alt="Product 4" />
                        <p>Bộ bodysuite tay dài</p>
                        <p>195,000đ</p>
                    </div>
                    <div className="product">
                        <img src="link-to-image" alt="Product 5" />
                        <p>Bộ bodysuite tay dài</p>
                        <p>195,000đ</p>
                    </div>
                    <div className="product">
                        <img src="link-to-image" alt="Product 6" />
                        <p>Bộ bodysuite tay dài</p>
                        <p>195,000đ</p>
                    </div>
                </div>
                <a href="#" className="view-more">Xem thêm</a>
            </div>
        </div>
    );
}
