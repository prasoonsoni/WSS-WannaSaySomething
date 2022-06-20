import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import NotFound from '../components/NotFound/NotFound'
import SendMessage from '../components/SendMessage/SendMessage'
import Loading from '../components/Loading/Loading'
const BASE_URL = process.env.REACT_APP_BASE_URL

const UserPage = () => {
    const { username } = useParams()
    const [name, setName] = useState("")
    const [userFound, setUserFound] = useState(false)
    const [userNotFound, setUserNotFound] = useState(false)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const getUser = async () => {
            const response = await fetch(`${BASE_URL}/user/${username}`, {
                method: "GET",
                mode: 'cors',
                headers: {
                    "Content-Type": "application/json",
                }
            });
            const json = await response.json()
            if (json.success) {
                setUserFound(true)
                setUserNotFound(false)
                setName(json.data.name)
            } else {
                setUserFound(false)
                setUserNotFound(true)
            }
            setLoading(false)
        }
        getUser()
    }, [username])
    return (
        <div className='container'>
            {loading && <Loading />}
            {userNotFound && <NotFound />}
            {userFound && <SendMessage name={name} username={username} />}
        </div>

    )
}

export default UserPage