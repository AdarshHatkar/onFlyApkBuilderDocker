import { exec } from 'child_process';
import express from 'express';

const app = express();
const port = 3000;

console.log("hello world");

// exec('gradle --version', (error, stdout, stderr) => {
//   if (error) {
//     console.error(`exec error: ${error}`);
//     return;
//   }

//   console.log(`stdout: ${stdout}`);
//   console.error(`stderr: ${stderr}`);
// });



app.get('/', (req, res) => {
  res.send('Hello World!');
});

app.listen(port, () => {
  console.log(`Server listening on port ${port}`);
});