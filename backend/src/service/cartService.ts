import { inject, injectable } from 'inversify';
import 'reflect-metadata';
import { IcartService } from '../serviceInterface/IcartService';
import { IcartDataAccess } from '../dataAccessInterface/IcartDataAccess';
import { cartProductDto, cartProductIdDto } from '../dtos/cartProductDto';
import { IstoreService } from '../serviceInterface/IstoreService';
import IstoreDataAccess from '../dataAccessInterface/IstoreDataAccess';
import { productNameBrand } from '../dtos/productDto';

@injectable()
export class cartService implements IcartService {
  _cartDataAccess: IcartDataAccess
  _storeDataAccess : IstoreDataAccess
  constructor(@inject("IcartDataAccess") IcartDataAccess: IcartDataAccess, @inject("IstoreDataAccess") IstoreDataAccess: IstoreDataAccess) {
    this._cartDataAccess = IcartDataAccess;
    this._storeDataAccess = IstoreDataAccess;
  }

  overwriteCart = async (data: cartProductDto[], userId : string) => {
    let productNames : productNameBrand[] = data.map(e => {return {name: e.name, brand: e.brand}}); 
    let products : number[] = await this._storeDataAccess.getProductsId(productNames); 
    let result = await this._cartDataAccess.overwriteCart(data, userId, products);
    return userId;
  }
  async addToCart(data: cartProductDto[], userId: string): Promise<string> {
    let productNames : productNameBrand[] = data.map(e => {return {name: e.name, brand: e.brand}});
    let products : number[] = await this._storeDataAccess.getProductsId(productNames);
    return await this._cartDataAccess.addToCart(data, userId, products);
  }
  async getCart(userId: string): Promise<cartProductIdDto[]> {
    return await this._cartDataAccess.getCart(userId);

  }
}

export default cartService;
