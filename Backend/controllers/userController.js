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
        res.json({ status: "error", message: "Some internal server error occured." })
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
        res.json({ status: "error", message: "Some internal server error occured." })
    }
}

const getUser = async (req, res) => {
    try {
        const username = req.params.username
        const user = await User.findOne({ username })
        if (!user) {
            return res.json({ success: false, message: "User doesn't exists!!" });
        }
        res.json({ success: true, message: "User Found." })
    } catch (error) {
        console.log(error.message)
        res.json({ status: "error", message: "Some internal server error occured." })
    }
}
module.exports = { login, register, getUser }