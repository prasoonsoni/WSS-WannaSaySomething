const express = require('express')
const router = express.Router()
const fetchUser = require('../middleware/fetchUser')
const messageController = require('../controllers/messageController')

router.post('/:username', messageController.sendMessage)
router.get('/all', fetchUser, messageController.getMessages)

module.exports = router