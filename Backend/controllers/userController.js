require('dotenv').config()
const User = require('../models/User')
const bcrypt = require('bcryptjs')
const jwt = require('jsonwebtoken')

const login = async (req, res) => {
    try {
        const { username, password } = req.body
        const user = await User.findOne({ username })
        if (!user) {
            return res.json({ success: false, message: "User doesn't exists!!" });
        }
        const passwordMatch = await bcrypt.compare(password, user.password);
        if (!passwordMatch) {
            return res.json({ success: false, message: "Invalid password!!" });
        }
        const data = {
            user: {
                id: user._id,
                username: user.username
            }
        }
        const authtoken = jwt.sign(data, process.env.JWT_SECRET);
        return res.status(201).json({ success: true, token: authtoken });
    } catch (error) {
        console.log(error.message)
        res.json({ status: false, message: "Some internal server error occured." })
    }
}

const register = async (req, res) => {
    try {
        const { name, username, password } = req.body
        const user = await User.findOne({ username });
        if (user) {
            return res.json({ success: false, message: "Username already exists!!" });
        }
        const salt = await bcrypt.genSalt(10);
        const securedPassword = await bcrypt.hash(password, salt);
        const account = await User.create({
            name: name,
            username: username,
            password: securedPassword
        })
        if (!account) {
            return res.json({ success: false, message: "Error creating account. Try Again!!" })
        }
        return res.json({ success: true, message: "Account created successfully" })
    } catch (error) {
        console.log(error.message)
        res.json({ status: false, message: "Some internal server error occured." })
    }
}

const getUser = async (req, res) => {
    try {
        res.setHeader('Access-Control-Allow-Credentials', true)
        res.setHeader('Access-Control-Allow-Origin', '*')
        res.setHeader('Access-Control-Allow-Methods', 'GET,OPTIONS,PATCH,DELETE,POST,PUT')
        res.setHeader(
            'Access-Control-Allow-Headers',
            'X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5, Content-Type, Date, X-Api-Version')
        const username = req.params.username
        const user = await User.findOne({ username }).select('-password').select('-messages').select('-created_at')
        if (!user) {
            return res.json({ success: false, message: "User doesn't exists!!" });
        }
        res.json({ success: true, data: user })
    } catch (error) {
        console.log(error.message)
        res.json({ status: false, message: "Some internal server error occured." })
    }
}
module.exports = { login, register, getUser }