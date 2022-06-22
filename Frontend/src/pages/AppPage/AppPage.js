import React from 'react'
import './apppage.css'
import Typed from '../../components/Typed/Typed'
const AppPage = () => {
  return (
    <div className='app-page-container' id='steps'>
      <div>
        <img src='/assets/mockup.png' className='mockup' />
      </div>
      <div className='steps'>
        <h1 className='steps-heading'>Steps to Get Anonymous Messages 🤫.</h1>
        <p className='steps-points'>
          👉 Download the App.<br></br>
          👉 Create an account.<br></br>
          👉 Login to account.<br></br>
          👉 Share link with friends.<br></br>
          👉 Receive messages.<br></br>
          👉 Reply to messages.<br></br>
        </p>
      </div>
      {/* <p className='made-footer home-footer'>Made in 🇮🇳 with ❤️ by <a className='portfolio-link' href='https://prasoon.codes' target='_blank'>Prasoon.</a></p> */}
    </div>
  )
}

export default AppPage