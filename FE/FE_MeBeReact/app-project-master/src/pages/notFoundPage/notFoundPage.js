import React from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import "./notFoundPage.css"
export default function NotFoundPage(){
   return(
        <div className="error-container">
            <span className="four"><span className="screen-reader-text">4</span></span>
            <span className="zero"><span className="screen-reader-text">0</span></span>
            <span className="four"><span className="screen-reader-text">4</span></span>
            <div class="link-container">
                <a href="/" class="more-link">TRỞ VỀ TRANG CHỦ</a>
            </div>
        </div>     
   )
}