const mongoose = require('mongoose')
const { Schema } = mongoose

const UserSchema = new Schema({
    name: {
        type: String,
        required: true
    },
    username: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    messages: {
        type: [{ type: Object, ref: 'Message' }],
        default: []
    },
    created_at: {
        type: Date,
        default: Date.now
    }
})

module.exports = mongoose.model('User', UserSchema)