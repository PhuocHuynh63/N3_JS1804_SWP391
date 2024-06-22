import React, { useEffect, useState } from 'react';
import './Category.css';
import { meBeSrc } from '../../service/meBeSrc';
import { NavLink, useParams } from 'react-router-dom';

export default function Category() {
    // Call API to get category by name
    const [category, setCategory] = useState({});
    const { slug } = useParams();

    useEffect(() => {
        meBeSrc.getCategoryBySlug(slug)
            .then((res) => {
                setCategory(res.data);
            })
            .catch((err) => {
                console.log("Error fetching category", err);
            });
    }, [slug]);

    // Call API to get subCategory
    const [subCategories, setSubCategories] = useState([]);

    useEffect(() => {
        meBeSrc.getListSubCategory()
            .then((res) => {
                const filteredSubCategories = res.data.filter((subCategory) => subCategory.category_parent === category.name);
                setSubCategories(filteredSubCategories);
            }).catch((err) => {
                console.log('Error fetching subcategories', err);
            });
    }, [category.name]);

    // Call API to get products
    const [products, setProducts] = useState([]);

    useEffect(() => {
        meBeSrc.getProduct()
            .then((res) => {
                setProducts(res.data);
            }).catch((err) => {
                console.log('Error fetching products', err);
            });
    }, [category.name]);

    return (
        <div className="category-container">
            <div className="category-header">
                <h2>Bạn cần tìm</h2>
                <div className="category-icons">
                    {subCategories.map((subCategory) => (
                        <div className="category-icon" key={subCategory.name}>
                            <a href={`/subcategory/${subCategory.slug}`}>
                                <img src={subCategory.image} alt={subCategory.name} />
                                <p>{subCategory.name}</p>
                            </a>
                        </div>
                    ))}
                </div>
            </div>

            {subCategories.map((subCategory) => {
                const filteredProducts = products.filter(product => product.subCategory.name === subCategory.name).slice(0, 3);
                return (
                    <div className="category-section" key={subCategory.name}>
                        <div className='title'>
                            <h3>{subCategory.name}</h3>
                            <a href={`/subcategory/${subCategory.slug}`}>Xem thêm →</a>
                        </div>
                        <div className="products">
                            <a href={`/subcategory/${subCategory.slug}`}>
                                <img id='banner' src={subCategory.image2} alt={subCategory.name} />
                            </a>
                            {filteredProducts.map((product) => (
                                <div className="product" key={product.id}>
                                    <a href={`/product/${product.productId}`}>
                                        <img src={`${product.images}`} alt={product.name} />
                                        <p>{product.name}</p>
                                        <p className={product.salePrice > 0 ? "sale-price" : "normal-price"}>{product.price.toLocaleString('vi-VN')}</p>
                                        {product.salePrice > 0 && (
                                            <p>{product.salePrice.toLocaleString('vi-VN')}₫</p>
                                        )}
                                    </a>
                                </div>
                            ))}
                        </div>
                    </div>
                );
            })}

        </div >
    );
}
