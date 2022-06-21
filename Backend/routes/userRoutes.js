const express = require('express')
const router = express.Router()
const userController = require('../controllers/userController')
const fetchUser = require('../middleware/fetchUser')

router.post('/login', userController.login)
router.post('/register', userController.register)
router.get('/:username', userController.getUser)
router.get('/token/getuser', fetchUser, userController.getUserByToken)

module.exports = router