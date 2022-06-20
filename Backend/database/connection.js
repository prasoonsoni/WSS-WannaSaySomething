require('dotenv').config()
const mongoose = require('mongoose')
const MONGO_URI = process.env.MONGO_URI

const connectToDatabase = () => {
    try {
        mongoose.connect(MONGO_URI, {
            useNewUrlParser: true, useUnifiedTopology: true, keepAlive: true
        }, () => {
            console.log("Mongoose is Connected.")
        })
    } catch (err) {
        console.log("Could not connect: " + err)
    }
    const dbConnection = mongoose.connection

    dbConnection.on("error", (err) => {
        console.log(`Connection Error: ${err}`)
    })

    dbConnection.once("open", () => {
        console.log("Connected to DB!");
    })
}

module.exports = connectToDatabase