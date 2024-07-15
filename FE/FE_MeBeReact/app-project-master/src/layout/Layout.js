import React from 'react';
import Header from '../components/header/Header';
import Footer from '../components/footer/Footer';
import './css/Layout.css';

export default function ({ Component }) {
    return (
        <div className='layout-container'>
            <Header />

            <div className='main-content'>
                <Component />
            </div>
            <Footer />
        </div>
    );
}
