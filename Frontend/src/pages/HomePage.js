import React from 'react'
import Logo from '../components/Logo/Logo'
import Typed from '../components/Typed/Typed'
import './pages.css'
const HomePage = () => {
    return (
        <div className='home-container'>
            <Logo />
            <h1 className='home-heading'>WSS - <span className='fullform'>Wanna Share Something??</span></h1>
            <p className='home-typed'>Get Anonymous&nbsp;<span className='typed'><Typed/></span>.</p>
            <p className='launching-soon'>Launching Soon, Stay Tuned...</p>
            <p className='made-footer home-footer'>Made in 🇮🇳 with ❤️ by <a className='portfolio-link' href='https://prasoon.codes' target='_blank'>Prasoon.</a></p>
        </div>
    )
}

export default HomePage