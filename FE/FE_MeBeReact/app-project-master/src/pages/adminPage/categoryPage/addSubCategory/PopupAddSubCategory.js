import React, { useEffect, useState } from 'react';
import './PopupAddSubCategory.css';
import { Modal } from 'antd';
import { meBeSrc } from '../../../../service/meBeSrc';

const PopupAddSubCategory = ({ show, handleClose, parentName }) => {

    /**
     * Form data
     */
    const [formData, setFormData] = useState({
        img1: null,
        img2: null,
        categoryParentName: '',
        name: '',
        slug: '',
    });
    //-----End-----//

    useEffect(() => {
        setFormData((prevData) => ({
            ...prevData,
            categoryParentName: parentName,
        }));
    }, [parentName]);

    /**
     * Handle change input
     * @param {*} e 
     */
    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.id]: e.target.value,
        });
    };
    //-----End-----//


    /**
     * Handle Image Change
     */
    const [imagePreview1, setImagePreview1] = useState(null);
    const [imagePreview2, setImagePreview2] = useState(null);

    const handleImageChange = (e) => {
        const { id } = e.target;
        const file = e.target.files[0];

        setFormData((prevFormData) => ({
            ...prevFormData,
            [id]: file,
        }));

        if (id === 'img1') {
            setImagePreview1(URL.createObjectURL(file));
        } else {
            setImagePreview2(URL.createObjectURL(file));
        }
    };
    //-----End-----//


    /**
     * Handle form submission
     * @param {*} e 
     */
    const handleSubmit = (e) => {
        e.preventDefault();
        const subcategory = new FormData();
        subcategory.append('img1', formData.img1);
        subcategory.append('img2', formData.img2);
        subcategory.append('requestJson', JSON.stringify({
            categoryParentName: parentName,
            name: formData.name,
            slug: formData.slug,
        }));


        meBeSrc.postSubCategory(subcategory)
            .then((res) => {
                console.log(res.data);
                handleClose();
            }).catch((err) => {
                console.log(err);
            });
    };
    //-----End-----//


    return (
        <Modal visible={show} onCancel={handleClose} footer={null} width={"auto"} centered>
            <div className="admin-subcategory-add">
                <h1>Thêm tiểu danh mục mới</h1>
                <form onSubmit={handleSubmit}>

                    <div className="form-group parent">
                        <label htmlFor="parent">Tên danh mục</label>
                        <input
                            type="text"
                            id="parent"
                            value={parentName}
                            readOnly
                        />
                    </div>

                    <div className="form-group name">
                        <label htmlFor="name">Tên tiểu danh mục</label>
                        <input
                            type="text"
                            id="name"
                            value={formData.name}
                            onChange={handleChange}
                            placeholder='Ví dụ: Theo món ăn'
                        />
                    </div>

                    <div className="form-group slug">
                        <label htmlFor="slug">Đường dẫn</label>
                        <input
                            type="text"
                            id="slug"
                            value={formData.slug}
                            onChange={handleChange}
                            placeholder='theo-mon-an'
                        />
                    </div>

                    <div className="form-group img1">
                        <label htmlFor="img1">Hình ảnh 1</label>
                        <input
                            type="file"
                            id="img1"
                            onChange={handleImageChange}
                        />
                        {imagePreview1 && <img src={imagePreview1} alt="Image Preview" className="image-preview" />}
                    </div>

                    <div className="form-group img2">
                        <label htmlFor="img2">Hình ảnh 2</label>
                        <input
                            type="file"
                            id="img2"
                            onChange={handleImageChange}
                        />
                        {imagePreview2 && <img src={imagePreview2} alt="Image Preview" className="image-preview" />}
                    </div>

                    <div className="form-group_btn">
                        <button className="btn-close_update" type="button" onClick={handleClose}>Đóng</button>
                        <button className="btn-update" type="submit">Lưu</button>
                    </div>
                </form>
            </div>
        </Modal>
    );
};

export default PopupAddSubCategory;
