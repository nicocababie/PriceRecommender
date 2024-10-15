import * as dotenv from 'dotenv';
import express from 'express';
import http from 'http';
import { Container } from 'inversify';
import { dbSync, sequelize } from './config/database';

dotenv.config();

let app = express();
let server = http.createServer(app);
let PORT = parseInt(process.env.PORT as string) || 4000;

let container = new Container();

let cors = require('cors');
app.use(express.json());

app.use(cors({
  origin: '*',
  methods: 'GET,HEAD,PUT,PATCH,POST,DELETE',
  credentials: true,
}));


app.listen(PORT, '0.0.0.0', async () => {
  console.log(`IHC backend running on port ${PORT}`);
  try {
    await sequelize.authenticate();
    console.log('MySQL connected');
    await dbSync();
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
});