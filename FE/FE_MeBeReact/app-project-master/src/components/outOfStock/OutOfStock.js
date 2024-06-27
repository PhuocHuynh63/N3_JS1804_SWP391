import React from 'react';
import { Button, Modal } from 'react-bootstrap';

const OutOfStock = ({ show, onHide }) => {
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Body className="text-center">
                <div className="success-icon">
                    <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="red" viewBox="0 0 24 24">
                        <path d="M12 0C5.373 0 0 5.373 0 12s5.373 12 12 12 12-5.373 12-12S18.627 0 12 0zm1 17h-2v-2h2v2zm0-4h-2V7h2v6z" />
                    </svg>
                </div>
                <h4 className="mt-3">Thất bại!</h4>
                <p>Sản phẩm tạm thời hết hàng</p>
                <Button variant="primary" onClick={onHide}>Đóng</Button>
            </Modal.Body>
        </Modal>
    );
}

export default OutOfStock;