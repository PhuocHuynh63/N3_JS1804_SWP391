import React, { useEffect, useState } from "react";
import "./AddressPage.css";
import {jwtDecode} from "jwt-decode";
import { meBeSrc } from "../../service/meBeSrc";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function AddressPage() {
    const [user, setUser] = useState({});
    const [addresses, setAddresses] = useState([]);
    const [newAddress, setNewAddress] = useState({ title: "", address: "", default: false });
    const [editAddressId, setEditAddressId] = useState(null);
    const [showAddForm, setShowAddForm] = useState(false); // State to control the visibility of the add address form
    const [errors, setErrors] = useState({}); // State to hold validation errors
    const [formSubmitted, setFormSubmitted] = useState(false); // State to track if form was submitted

    useEffect(() => {
        const token = localStorage.getItem('USER_INFO');
        if (token) {
            const decoded = jwtDecode(token);
            const username = decoded.sub;
            meBeSrc.getUserByUserName(username)
                .then((res) => {
                    const userData = res.data;
                    setUser(userData);
                    fetchAddresses(userData.id);
                })
                .catch((err) => {
                    console.log("Error fetching user", err);
                });
        }
    }, []);

    const fetchAddresses = (userId) => {
        meBeSrc.getAddressByUserId(userId)
            .then((res) => {
                console.log("Fetched addresses:", res.data);
                setAddresses(res.data);
            })
            .catch((err) => {
                console.log("Error fetching addresses", err);
            });
    };

    const handleAddAddress = () => {
        setFormSubmitted(true);
        if (validate(newAddress)) {
            console.log("Adding new address:", newAddress);
            meBeSrc.createAddress(user.id, newAddress)
                .then(() => {
                    fetchAddresses(user.id);
                    setNewAddress({ title: "", address: "", default: false });
                    setShowAddForm(false); // Hide the add address form after adding the address
                    setErrors({}); // Clear errors
                    setFormSubmitted(false); // Reset form submission state
                    toast.success("Địa chỉ đã được thêm thành công!");
                })
                .catch((err) => {
                    if (err.response && err.response.data && err.response.data.msg) {
                        toast.error(err.response.data.msg);
                    } else {
                        console.log("Error adding address", err);
                        toast.error("Có lỗi xảy ra, vui lòng thử lại.");
                    }
                });
        } else {
            toast.error("Vui lòng nhập đầy đủ thông tin.");
        }
    };

    const handleUpdateAddress = (addressId) => {
        setFormSubmitted(true);
        const addressToUpdate = addresses.find(address => address.addressId === addressId);
        if (validate(addressToUpdate)) {
            console.log("Updating address with ID:", addressId);
            console.log("Updated address data:", addressToUpdate);
            if (addressId) {
                meBeSrc.updateAddress(addressId, addressToUpdate)
                    .then(() => {
                        fetchAddresses(user.id);
                        setEditAddressId(null);
                        setErrors({}); // Clear errors
                        setFormSubmitted(false); // Reset form submission state
                        toast.success("Địa chỉ đã được cập nhật thành công!");
                    })
                    .catch((err) => {
                        if (err.response && err.response.data && err.response.data.msg) {
                            toast.error(err.response.data.msg);
                        } else {
                            console.log("Error updating address", err);
                            toast.error("Có lỗi xảy ra, vui lòng thử lại.");
                        }
                    });
            } else {
                console.log("Address ID is undefined");
            }
        } else {
            toast.error("Vui lòng nhập đầy đủ thông tin.");
        }
    };

    const handleDeleteAddress = (addressId) => {
        console.log("Deleting address with ID:", addressId);
        if (addressId) {
            meBeSrc.deleteAddress(addressId)
                .then((res) => {
                    console.log("Delete response:", res);
                    setAddresses(addresses.filter(address => address.addressId !== addressId));
                    toast.success("Địa chỉ đã được xóa thành công!");
                })
                .catch((err) => {
                    if (err.response && err.response.data && err.response.data.msg) {
                        toast.error(err.response.data.msg);
                    } else {
                        console.log("Error deleting address", err);
                        toast.error("Có lỗi xảy ra, vui lòng thử lại.");
                    }
                });
        } else {
            console.log("Address ID is undefined");
        }
    };

    const handleSetDefault = (addressId) => {
        console.log("Setting default address with ID:", addressId);
        if (addressId) {
            const oldDefaultAddress = addresses.find(address => address.default);

            if (oldDefaultAddress && oldDefaultAddress.addressId !== addressId) {
                // Unset the old default address
                meBeSrc.setDefaultAddress(oldDefaultAddress.addressId, false)
                    .then(() => {
                        // Set the new default address
                        meBeSrc.setDefaultAddress(addressId, true)
                            .then(() => {
                                fetchAddresses(user.id);
                                toast.success("Địa chỉ mặc định đã được thay đổi!");
                            })
                            .catch((err) => {
                                if (err.response && err.response.data && err.response.data.msg) {
                                    toast.error(err.response.data.msg);
                                } else {
                                    console.log("Error setting new default address", err);
                                    toast.error("Có lỗi xảy ra, vui lòng thử lại.");
                                }
                            });
                    })
                    .catch((err) => {
                        if (err.response && err.response.data && err.response.data.msg) {
                            toast.error(err.response.data.msg);
                        } else {
                            console.log("Error unsetting old default address", err);
                            toast.error("Có lỗi xảy ra, vui lòng thử lại.");
                        }
                    });
            } else {
                // Set the new default address if there is no old default
                meBeSrc.setDefaultAddress(addressId, true)
                    .then(() => {
                        fetchAddresses(user.id);
                        toast.success("Địa chỉ mặc định đã được thay đổi!");
                    })
                    .catch((err) => {
                        if (err.response && err.response.data && err.response.data.msg) {
                            toast.error(err.response.data.msg);
                        } else {
                            console.log("Error setting new default address", err);
                            toast.error("Có lỗi xảy ra, vui lòng thử lại.");
                        }
                    });
            }
        } else {
            console.log("Address ID is undefined");
        }
    };

    const handleEditClick = (addressId) => {
        setEditAddressId(addressId);
        setFormSubmitted(false); // Reset form submission state when editing
    };

    const handleEditChange = (e, addressId) => {
        const { name, value } = e.target;
        setAddresses(prev => prev.map(address =>
            address.addressId === addressId ? { ...address, [name]: value } : address
        ));
    };

    const validate = (address) => {
        let valid = true;
        let newErrors = {};

        if (!address.title) {
            newErrors.title = "Tiêu đề là bắt buộc.";
            valid = false;
        }
        if (!address.address) {
            newErrors.address = "Địa chỉ là bắt buộc.";
            valid = false;
        }

        setErrors(newErrors);
        return valid;
    };

    return (
        <div className="address-container">
            <ToastContainer />
            <div className="address-header">
                <span>Địa chỉ của tôi</span>
                <button className="btn-address" onClick={() => setShowAddForm(true)}>+ Thêm địa chỉ mới</button>
            </div>
            {showAddForm && ( // Only show the add address form if showAddForm is true
                <div className="address-form">
                    <input
                        type="text"
                        placeholder="Tiêu đề"
                        value={newAddress.title}
                        onChange={(e) => setNewAddress({ ...newAddress, title: e.target.value })}
                    />
                    {formSubmitted && errors.title && <p className="error">{errors.title}</p>}
                    <input
                        type="text"
                        placeholder="Địa chỉ"
                        value={newAddress.address}
                        onChange={(e) => setNewAddress({ ...newAddress, address: e.target.value })}
                    />
                    {formSubmitted && errors.address && <p className="error">{errors.address}</p>}
                    <button onClick={handleAddAddress}>Thêm địa chỉ</button>
                    <button onClick={() => setShowAddForm(false)} className="cancel-btn">Hủy</button>
                </div>
            )}
            <div className="address-body">
                {addresses.map((address) => (
                    <div key={address.addressId} className="address-item">
                        {editAddressId === address.addressId ? (
                            <div className="address-item_left">
                                <input
                                    type="text"
                                    name="title"
                                    placeholder="Tiêu đề"
                                    value={address.title}
                                    onChange={(e) => handleEditChange(e, address.addressId)}
                                />
                                {formSubmitted && errors.title && <p className="error">{errors.title}</p>}
                                <input
                                    type="text"
                                    name="address"
                                    placeholder="Địa chỉ"
                                    value={address.address}
                                    onChange={(e) => handleEditChange(e, address.addressId)}
                                />
                                {formSubmitted && errors.address && <p className="error">{errors.address}</p>}
                                <button onClick={() => handleUpdateAddress(address.addressId)}>Lưu thay đổi</button>
                                <button onClick={() => setEditAddressId(null)} className="cancel-btn">Hủy</button>
                            </div>
                        ) : (
                            <div className="address-item_left">
                                <span>{user.name}</span>
                                <p className="address-item_location">{address.title}</p>
                                <p className="address-item_location">{address.address}</p>
                                {address.default && <p className="address-text_default">Mặc định</p>}
                            </div>
                        )}
                        <div className="address-item_right">
                            <button className="address-btn_update" onClick={() => handleEditClick(address.addressId)}>Cập nhật</button>
                            {!address.default && <button className="address-btn_delete" onClick={() => handleDeleteAddress(address.addressId)}>Xóa</button>}
                            {!address.default && <button className="address-btn_default" onClick={() => handleSetDefault(address.addressId)}>Thiết lập mặc định</button>}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
