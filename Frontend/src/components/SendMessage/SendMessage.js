import React, { useState } from 'react'
import Button from '../Button/Button'
import Loading from '../Loading/Loading'
import Typed from '../Typed/Typed'
import './sendmessage.css'
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import { useNavigate } from 'react-router-dom'
const BASE_URL = process.env.REACT_APP_BASE_URL

const Alert = React.forwardRef(function Alert(props, ref) {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const SendMessage = (props) => {
    const [message, setMessage] = useState("")
    const [loading, setLoading] = useState(false)
    const [snackbarOpen, setSnackbarOpen] = useState(false)
    const [snackbarText, setSnackbarText] = useState("")
    const [snackbarSeverity, setSnackbarSeverity] = useState("")
    const navigate = useNavigate()

    const sendMessage = async () => {
        setLoading(true)
        const response = await fetch(`${BASE_URL}/message/${props.username}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ message: message })
        })
        const json = await response.json()
        setSnackbarText(json.message)
        if (json.success) {
            setSnackbarSeverity("success")
        } else {
            setSnackbarSeverity("error")
        }
        setSnackbarOpen(true)
        setLoading(false)
        setMessage("")
    }

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setSnackbarOpen(false);
    };

    return (
        <div className='sendmessage-container'>
            {loading && <Loading />}
            <Snackbar open={snackbarOpen} autoHideDuration={2000} onClose={handleClose} anchorOrigin={{ vertical: 'bottom', horizontal: 'center', }}>
                <Alert onClose={handleClose} severity={snackbarSeverity} sx={{ width: '100%' }}>
                    {snackbarText}
                </Alert>
            </Snackbar>
            <h1 className='user-name'>Send Anonymous&nbsp;<span className='typed'><Typed /></span>&nbsp;{props.name} 🤫</h1>
            <div className='sendmessage-card'>
                <h1 className='user-username'>@{props.username}</h1>
                <textarea className='user-message' value={message} placeholder='Enter your message here' onChange={(event) => { setMessage(event.target.value) }}></textarea>
            </div>
            {message.length > 0 && <Button text='Send' onClick={sendMessage} />}
            <div className='bottom-button-container'>
                <Button text='Get your own messages! 🤩' onClick={() => navigate('/')} />
                <p className='made-footer'>Made in 🇮🇳 with ❤️ by <a className='portfolio-link' href='https://prasoon.codes' target='_blank'>Prasoon.</a></p>
            </div>
        </div>
    )
}

export default SendMessage