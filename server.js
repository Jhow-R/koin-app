//Precisa instalar as deps
//npm install express --save
//npm install body-parser --save

const express = require("express");
const bodyParser = require('body-parser');
const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.text());
app.use(bodyParser.raw());
app.use(bodyParser.json());

const taskNotes = []

let insertedId = 1

app.get("/tasks", (req, res, next) => {
  res.json(taskNotes)
});


app.post('/new-task', (req, res) => {
    
    let newTask = req.body
    newTask.taskId = insertedId
    insertedId ++ 
    
    taskNotes.push(newTask);
    console.log('Got body:', req.body);
    res.sendStatus(200);
});

app.listen(3000, () => {
 console.log("Server running on port 3000");
});