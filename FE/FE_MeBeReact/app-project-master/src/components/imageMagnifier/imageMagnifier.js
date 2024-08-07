import React, { useState } from 'react';

const ImageMagnifier = ({
    src,
    width = '100%',
    height = 'auto',
    magnifierHeight = 150,
    magnifierWidth = 150,
    zoomLevel = 2,
}) => {
    const [[x, y], setXY] = useState([0, 0]);
    const [[imgWidth, imgHeight], setSize] = useState([0, 0]);
    const [showMagnifier, setShowMagnifier] = useState(false);

    return (
        <div
            style={{
                position: 'relative',
                height: height,
                width: width,
                overflow: 'hidden'
            }}
        >
            <img
                src={src}
                style={{ height: '100%', width: '100%', objectFit: 'contain' }}
                onMouseEnter={(e) => {
                    const elem = e.currentTarget;
                    const { width, height } = elem.getBoundingClientRect();
                    setSize([width, height]);
                    setShowMagnifier(true);
                }}
                onMouseMove={(e) => {
                    const elem = e.currentTarget;
                    const { top, left } = elem.getBoundingClientRect();
                    const x = e.pageX - left - window.pageXOffset;
                    const y = e.pageY - top - window.pageYOffset;
                    setXY([x, y]);
                }}
                onMouseLeave={() => {
                    setShowMagnifier(false);
                }}
                alt='img'
            />

            <div
                style={{
                    display: showMagnifier ? '' : 'none',
                    position: 'absolute',
                    pointerEvents: 'none',
                    height: `${magnifierHeight}px`,
                    width: `${magnifierWidth}px`,
                    top: `${y - magnifierHeight / 2}px`,
                    left: `${x - magnifierWidth / 2}px`,
                    opacity: '1',
                    border: '1px solid lightgray',
                    backgroundColor: 'white',
                    backgroundImage: `url('${src}')`,
                    backgroundRepeat: 'no-repeat',
                    backgroundSize: `${imgWidth * zoomLevel}px ${imgHeight * zoomLevel}px`,
                    backgroundPositionX: `${-x * zoomLevel + magnifierWidth / 2}px`,
                    backgroundPositionY: `${-y * zoomLevel + magnifierHeight / 2}px`,
                }}
            ></div>
        </div>
    );
};

export default ImageMagnifier;
