import React, { useEffect, useState } from 'react';
import './PopupUpdateSubCategory.css';
import { Modal } from 'antd';
import { meBeSrc } from '../../../../service/meBeSrc';
import Successful from '../../../../components/popupSuccessful/Successful';
import PopupError from '../../../../components/popupError/PopupError';

const PopupUpdateSubCategory = ({ show, handleClose, subCategoryId, parentName }) => {
    const [showModalSuccess, setShowModalSuccess] = useState(false);
    const [showModalError, setShowModalError] = useState(false);

    const [formData, setFormData] = useState({
        img1: null,
        img2: null,
        categoryParentName: '',
        name: '',
        slug: '',
    });

    useEffect(() => {
        setFormData((prevData) => ({
            ...prevData,
            categoryParentName: parentName,
        }));
    }, [parentName]);

    const [imagePreview1, setImagePreview1] = useState(null);
    const [imagePreview2, setImagePreview2] = useState(null);

    useEffect(() => {
        if (subCategoryId) {
            meBeSrc.getSubCategoryById(subCategoryId)
                .then((res) => {
                    setFormData({
                        img1: null,
                        img2: null,
                        categoryParentName: parentName,
                        name: res.data.name,
                        slug: res.data.slug,
                    });
                    setImagePreview1(res.data.image || null);
                    setImagePreview2(res.data.image2 || null);
                })
                .catch((err) => {
                    console.log("Error fetching subcategory: ", err);
                });
        }
    }, [subCategoryId, parentName]);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.id]: e.target.value,
        });
    };

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

    console.log(formData);

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!formData.name || !formData.slug) {
            setShowModalError(true);
            setTimeout(() => setShowModalError(false), 3000);
            return;
        }

        const subcategory = new FormData();
        subcategory.append('img1', formData.img1);
        subcategory.append('img2', formData.img2);
        subcategory.append('requestJson', JSON.stringify({
            categoryParentName: parentName,
            name: formData.name,
            slug: formData.slug,
        }));

        meBeSrc.putSubCatory(subCategoryId, subcategory)
            .then((res) => {
                console.log("Update response: ", res);
                setShowModalSuccess(true);
                setTimeout(() => {
                    setShowModalSuccess(false);
                    handleClose();
                    window.location.reload();
                }, 3000);
            })
            .catch((err) => {
                console.log("Error updating subcategory: ", err);
                setShowModalError(true);
                setTimeout(() => setShowModalError(false), 3000);
            });
    };

    const resetForm = () => {
        setFormData({
            img1: null,
            img2: null,
            categoryParentName: parentName,
            name: '',
            slug: '',
        });
        setImagePreview1(null);
        setImagePreview2(null);
    };

    useEffect(() => {
        if (!show) resetForm();
    }, [show]);

    return (
        <Modal visible={show} onCancel={handleClose} footer={null} width={"auto"} centered>
            <Successful show={showModalSuccess} onHide={() => setShowModalSuccess(false)} message={`Cập nhật danh mục ${formData.name} thành công`} />
            <PopupError show={showModalError} onHide={() => setShowModalError(false)} message={`Danh mục ${formData.name} đã tồn tại hoặc có lỗi xảy ra`} />
            <div className="admin-subCategory-update">
                <h1>Cập nhật thông tin tiểu danh mục</h1>
                <form onSubmit={handleSubmit}>
                    <div className="form-group name">
                        <label htmlFor="name">Tên danh mục</label>
                        <input
                            type="text"
                            id="name"
                            value={formData.name}
                            onChange={handleChange}
                            placeholder='Ví dụ: Ăm dặm'
                            required
                        />
                    </div>

                    <div className="form-group slug">
                        <label htmlFor="slug">Đường dẫn</label>
                        <input
                            type="text"
                            id="slug"
                            value={formData.slug}
                            onChange={handleChange}
                            placeholder='am-dam'
                            required
                        />
                    </div>

                    <div className='form-group img1'>
                        <label htmlFor="img1">Hình ảnh 1</label>
                        <input type="file" id="img1" onChange={handleImageChange} />
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
                        <button className="btn-close_add" type="button" onClick={handleClose}>Đóng</button>
                        <button className="btn-add" type="submit">Lưu</button>
                    </div>
                </form>
            </div>
        </Modal>
    );
};

export default PopupUpdateSubCategory;
