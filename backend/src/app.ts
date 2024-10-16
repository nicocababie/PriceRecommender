import 'reflect-metadata';
import * as dotenv from 'dotenv';
import express from 'express';
import http from 'http';
import { Container } from 'inversify';
import { dbSync, sequelize } from './config/database';
import { IcartDataAccess } from './dataAccessInterface/IcartDataAccess';
import { cartDataAccess } from './dataAccess/cartDataAccess';
import { IcartService } from './serviceInterface/IcartService';
import cartService from './service/cartService';
import cartController from './controller/cartController';

dotenv.config();

let app = express();
let server = http.createServer(app);
let PORT = parseInt(process.env.PORT as string) || 4000;

let container = new Container();




container.bind<IcartDataAccess>('IcartDataAccess').to(cartDataAccess);
let _cartDataAccess = container.get<IcartDataAccess>('IcartDataAccess');
container.bind<IcartService>('IcartService').to(cartService);
let _cartService = container.get<IcartService>('IcartService');
let _cartController = new cartController(_cartService);

let cors = require('cors');
app.use(express.json());

app.use(cors({
  origin: '*',
  methods: 'GET,HEAD,PUT,PATCH,POST,DELETE',
  credentials: true,
}));

app.put('/carts/:id', async (req, res) => {await _cartController.overwriteCart(req, res)});
app.post('/carts/:id', async (req, res) => {await _cartController.addToCart(req, res)});
app.post('/carts/:id', async (req, res) => {await _cartController.addToNewCart(req, res)});
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