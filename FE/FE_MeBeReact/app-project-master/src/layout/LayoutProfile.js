import React from 'react';
import Header from '../components/header/Header';
import Footer from '../components/footer/Footer';
import './css/LayoutProfile.css';
import Profile from '../components/slidebar/Profile';

export default function ({ Component }) {
    return (
        <div className='layout-container'>
            <Header />

            <div className='main-content'>
                <Profile />
                <Component />
            </div>
            <Footer />
        </div>
    );
}
