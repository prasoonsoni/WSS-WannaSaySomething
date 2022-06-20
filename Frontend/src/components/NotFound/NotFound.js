import { Player, Controls } from '@lottiefiles/react-lottie-player';
import React from 'react'
import './notfound.css'

const NotFound = () => {
    return (
        <div className='notfound-container'>
            <Player
                autoplay
                loop
                src="assets/notfound.json"
                style={{ height: '400px', width: '400px' }}>
            </Player>
            <h1 className='notfound-heading'>User Not Found</h1>
        </div>
    )
}

export default NotFound