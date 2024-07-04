import React from 'react';
import './Pagination.css';

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
    return (
        <div className="pagination">
            {[...Array(totalPages).keys()].map(number => (
                <button
                    key={number + 1}
                    onClick={() => onPageChange(number + 1)}
                    className={`page-button ${currentPage === number + 1 ? 'active' : ''}`}
                >
                    {number + 1}
                </button>
            ))}
        </div>
    );
};

export default Pagination;
