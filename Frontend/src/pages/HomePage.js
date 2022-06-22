import React from 'react'
import { useNavigate } from 'react-router-dom'
import Logo from '../components/Logo/Logo'
import Typed from '../components/Typed/Typed'
import AppPage from './AppPage/AppPage'
import './pages.css'
const HomePage = () => {
    const navigate = useNavigate()
    return (
        <>
        <div className='home-container'>
            <Logo />
            <h1 className='home-heading'>WSS - <span className='fullform'>Wanna Share Something??</span></h1>
            <p className='home-typed'>Get Anonymous&nbsp;<span className='typed'><Typed /></span>.</p>
            <a className='download-button' href='/assets/WSS.apk' download><i class="fa-solid fa-download download"></i> Download Now</a>
            <a href='#steps'><i class="fa-solid fa-angles-down navigate-down"></i></a>
        </div>
        <AppPage/>
        
        </>
        
    )
}

export default HomePage