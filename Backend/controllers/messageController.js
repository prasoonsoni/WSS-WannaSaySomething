require('dotenv').config()
const User = require('../models/User')
const Message = require('../models/Message')
const { ObjectId } = require('mongodb')

const sendMessage = async (req, res) => {
    try {
        const { message } = req.body
        const username = req.params.username
        const user = await User.findOne({ username })
        if (!user) {
            return res.json({ success: false, message: "User doesn't exists!!" });
        }
        const userMessage = new Message({ message: message })
        const addMessage = await User.updateOne({ username }, { $push: { messages: userMessage } })
        if (!addMessage.acknowledged) {
            return res.json({ success: false, message: "Error sending message." })
        }
        res.json({ success: true, message: "Message sent successfully." })
    } catch (error) {
        console.log(error.message)
        res.json({ status: "error", message: "Some internal server error occured." })
    }
}

const getMessages = async (req, res) => {
    try {
        const id = new ObjectId(req.user.id)
        const username = req.user.username
        const user = await User.findOne({ _id: id, username: username })
        if (!user) {
            return res.json({ success: false, message: "User doesn't exists!!" });
        }
        const messages = user.messages
        res.json({ success: false, messages: messages })
    } catch (error) {
        console.log(error.message)
        res.json({ status: "error", message: "Some internal server error occured." })
    }
}
module.exports = { sendMessage, getMessages }
