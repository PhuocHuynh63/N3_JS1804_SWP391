import React from "react";
import { Button, Modal } from "react-bootstrap";

const Successful = ({ show, onHide, message }) => {
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Body className="text-center">
                <div className="success-icon">
                    <img src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1720256749/z5607740215391_7fa95663f97ae3afd540a2abb82dc7fc_iycg8w.gif" alt="success" />
                </div>
                <h4 className="mt-3">Thành công!</h4>
                <p>{message}</p>
                <Button variant="primary" onClick={onHide}>Đóng!</Button>
            </Modal.Body>
        </Modal>
    );
}

export default Successful;