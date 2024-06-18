import React, { useEffect, useState } from 'react';
import './Category.css';
import { meBeSrc } from '../../service/meBeSrc';
import { useParams } from 'react-router-dom';

export default function Category() {
    //Call API to get category
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

    //Call API to get products
    const [products, setProducts] = useState([]);

    useEffect(() => {
        meBeSrc.getProduct()
            .then((res) => {
                const filteredProducts = res.data.filter((product) => product.category === category.name).slice(0, 3);
                setProducts(filteredProducts);
            }).catch((err) => {
                console.log('Error fetching products', err);
            });
    }, []);
    return (
        <div className="category-container">
            <div className="category-header">
                <h2>Bạn cần tìm</h2>
                <div className="category-icons">
                    {subCategories.map((subCategory) => (
                        <div className="category-icon" key={subCategory.name}>
                            <a href=''>
                                <img src={subCategory.image} alt={subCategory.name} />
                                <p>{subCategory.name}</p>
                            </a>
                        </div>
                    ))}
                </div>
            </div>

            {subCategories.map((subCategory) => (
                <div className="category-section" key={subCategory.name}>
                    <h3>{subCategory.name}</h3>
                    <a href="#" className="view-more">Xem thêm</a>
                    <div className="products">
                        {products.map((product) => (
                            <div className="product" key={product.id}>
                                <img src={`/images/${product.images}`} alt={product.name} />
                                <p>{product.name}</p>
                                <p>{product.price}</p>
                            </div>
                        ))}
                    </div>
                </div>
            ))}

        </div>
    );
}
