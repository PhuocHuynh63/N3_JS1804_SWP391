import React, { useState } from 'react';
import PopupConfirm from '../../../../components/popupConfirm/PopupConfirm';
import { meBeSrc } from '../../../../service/meBeSrc';
import Successful from '../../../../components/popupSuccessful/Successful';
import PopupError from '../../../../components/popupError/PopupError';

const PopupDeleteProduct = ({ show, handleClose, productId }) => {
    const [showModalConfirm, setShowModalConfirm] = useState(false);
    const [showModalSuccess, setShowModalSuccess] = useState(false);
    const [showModalError, setShowModalError] = useState(false);

    console.log(productId);

    /**
     * Delete product
     */
    const handleDelete = () => {
        meBeSrc.deleteProduct(productId)
            .then((res) => {
                setShowModalConfirm(false);

                if (handleClose) {
                    setTimeout(() => window.location.reload(), 150);
                }
            })
            .catch((err) => {
                console.log(err);
                setShowModalError(true);
                setTimeout(() => setShowModalError(false), 3000);
            });
    };
    //-----End-----//


    return (
        <>
            <PopupConfirm show={show} onHide={handleClose} message={"Bạn có chắc chắn xóa không?"} action={() => handleDelete()} textConfirm={"Xóa"} />
            <Successful show={showModalSuccess} onHide={() => setShowModalSuccess(false)} message={"Xoá thành công"} />
            <PopupError show={showModalError} onHide={() => setShowModalError(false)} message={"Sản phẩm đang được bán không thể xóa"} />
        </>
    );
};

export default PopupDeleteProduct;
