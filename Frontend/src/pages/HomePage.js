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
        </div>
    )
}

export default HomePage