import React from "react";
import { Button, Modal } from "react-bootstrap";

const Successful = ({ show, onHide, message }) => {
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Body className="text-center">
                <div className="success-icon">
                    <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="green" viewBox="0 0 24 24">
                        <path d="M12 0C5.373 0 0 5.373 0 12s5.373 12 12 12 12-5.373 12-12S18.627 0 12 0zm6 9l-7 7-3.5-3.5 1.414-1.414L11 13.172 16.586 7.586 18 9z" />
                    </svg>
                </div>
                <h4 className="mt-3">Thành công!</h4>
                <p>{message}</p>
                <Button variant="primary" onClick={onHide}>Close!</Button>
            </Modal.Body>
        </Modal>
    );
}

export default Successful;