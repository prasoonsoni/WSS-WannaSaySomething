import React from 'react'
import Typed from 'react-typed'
const Typing = () => {
    return (
        <div>
            <Typed
                strings={["<b> Messages </b>", "<b> Advices </b>", "<b> Compilments </b>", "<b> Opinions </b>", "<b> Questions </b>"]}
                typeSpeed={40}
                backSpeed={40}
                loop
                smartBackspace
                showCursor={false}
            />
            <br />
        </div>
    )
}

export default Typing