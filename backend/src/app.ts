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
import IpurchaseDataAccess from './dataAccessInterface/IpurchaseDataAccess';
import { PurchaseDataAccess } from './dataAccess/purchaseDataAccess';
import { IpurchaseService } from './serviceInterface/IpurchaseService';
import { purchaseService } from './service/purchaseService';
import purchaseController from './controller/purchaseController';
import { IuserService } from './serviceInterface/IuserService';
import { userService } from './service/userService';
import { userController } from './controller/userController';
import IstoreDataAccess from './dataAccessInterface/IstoreDataAccess';
import { storeDataAccess } from './dataAccess/storeDataAccess';
import { IstoreService } from './serviceInterface/IstoreService';
import { storeService } from './service/storeService';
import storeController from './controller/storeController';
import Store from './domain/store';

dotenv.config();

let app = express();
let server = http.createServer(app);
let PORT = parseInt(process.env.PORT as string) || 4000;

let container = new Container();


container.bind<IstoreDataAccess>('IstoreDataAccess').to(storeDataAccess);
let _storeDataAccess = container.get<IstoreDataAccess>('IstoreDataAccess');

container.bind<IcartDataAccess>('IcartDataAccess').to(cartDataAccess);
let _cartDataAccess = container.get<IcartDataAccess>('IcartDataAccess');
container.bind<IcartService>('IcartService').to(cartService);
let _cartService = container.get<IcartService>('IcartService');
let _cartController = new cartController(_cartService);

container.bind<IstoreService>('IstoreService').to(storeService);
let _storeService = container.get<IstoreService>('IstoreService');
let _storeController = new storeController(_storeService);


container.bind<IpurchaseDataAccess>('IpurchaseDataAccess').to(PurchaseDataAccess);
let _purchaseDataAccess = container.get<IpurchaseDataAccess>('IpurchaseDataAccess');
container.bind<IpurchaseService>('IpurchaseService').to(purchaseService);
let _purchaseService = container.get<IpurchaseService>('IpurchaseService');
let _purchaseController = new purchaseController(_purchaseService);

container.bind<IuserService>('IuserService').to(userService);
let _userService = container.get<IuserService>('IuserService');
let _userController = new userController(_userService);

let cors = require('cors');
app.use(express.json());

app.use(cors({
  origin: '*',
  methods: 'GET,HEAD,PUT,PATCH,POST,DELETE',
  credentials: true,
}));

app.put('/carts/:id', async (req, res) => {await _cartController.overwriteCart(req, res)});
app.post('/carts/:id', async (req, res) => {await _cartController.addToCart(req, res)});
app.get('/carts/:id', async (req, res) => {await _cartController.getCart(req, res)});

app.post('/purchases', async (req, res) => {await _purchaseController.createPurchase(req, res)});
app.get('/purchases/:id', async (req, res) => {await _purchaseController.getPurchase(req, res)});
app.get('/products', async (req, res) => {await _purchaseController.getProducts(req, res)});

app.get('/users', async (req, res) => {await _userController.createUser(req, res)});

app.get('/stats/:id', async (req, res) => { await _purchaseController.getLastPurchases(req, res) });

app.post('/products/routes/:id', async (req, res) => {await _storeController.calculateRoute(req, res)});
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