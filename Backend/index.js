const express = require('express')
const dotenv = require('dotenv')
const app = express()
const cors = require('cors')
const connectToDatabase = require('./database/connection')
const port = 5000 || process.env.PORT

connectToDatabase()
dotenv.config()
app.use(cors())
app.use(express.json())

app.get('/', (req, res) => {
    res.send('Welcome to WSS Backend.')
})

app.use('/user', require('./routes/userRoutes'))
app.use('/message', require('./routes/messageRoutes'))

app.listen(port, () => {
    console.log(`WSS - Wanna Say Something listening on http://localhost:${port}`)
})