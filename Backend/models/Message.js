const mongoose = require('mongoose')
const { Schema } = mongoose

const MessageSchema = new Schema({
    message: {
        type: String,
        required: true
    },
    created_at: {
        type: Date,
        default: Date.now
    }
})

module.exports = mongoose.model('Message', MessageSchema)