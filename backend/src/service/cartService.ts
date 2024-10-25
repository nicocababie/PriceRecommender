import { inject, injectable } from 'inversify';
import 'reflect-metadata';
import { IcartService } from '../serviceInterface/IcartService';
import { IcartDataAccess } from '../dataAccessInterface/IcartDataAccess';
import { cartProductDto, cartProductIdDto } from '../dtos/cartProductDto';
import { IstoreService } from '../serviceInterface/IstoreService';
import IstoreDataAccess from '../dataAccessInterface/IstoreDataAccess';
import { productDto, productNameBrand } from '../dtos/productDto';

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
  async getCartProducts(userId: string): Promise<cartProductDto[]> {
    let productData : cartProductIdDto[] =  await this._cartDataAccess.getCart(userId);
    let productsId : number[] = productData.map(e => e.productId);
    let products : productDto[] = await this._storeDataAccess.getProducts(productsId);
    let result : cartProductDto[] = products.map(e => {
      let product = productData.find(p => p.productId == e.id);
      return {id:e.id, name: e.name, brand: e.brand, amount: product?.amount || 0}
    });
    return result;

  }
}

export default cartService;
