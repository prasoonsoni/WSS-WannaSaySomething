import React from 'react'
import './button.css'
const Button = (props) => {
    return (
        <button className='button' onClick={async () => { props.onClick() }}>{props.text}</button>
    )
}

export default Button