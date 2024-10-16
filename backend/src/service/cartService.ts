import { inject, injectable } from 'inversify';
import 'reflect-metadata';
import { IcartService } from '../serviceInterface/IcartService';
import { IcartDataAccess } from '../dataAccessInterface/IcartDataAccess';
import { cartProductDto } from '../dtos/cartProductDto';
import generateApiKey from 'generate-api-key';


@injectable()
export class cartService implements IcartService {
  _cartDataAccess: IcartDataAccess
  constructor(@inject("IcartDataAccess") IcartDataAccess: IcartDataAccess) {
    this._cartDataAccess = IcartDataAccess;
  }

  overwriteCart = async (data: cartProductDto[], userId : string) => {
    let result = await this._cartDataAccess.overwriteCart(data, userId);
    return userId;
  }
  async addToCart(data: cartProductDto[], userId: string): Promise<string> {
    return await this._cartDataAccess.addToCart(data, userId);
  }
  addToNewCart = async (data: cartProductDto[]) => {
    let apiKey : string = generateApiKey({method: 'string',
      pool: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', batch: 6}).toString();
    let result = await this._cartDataAccess.addToCart(data, apiKey);
    return apiKey;
  }

}

export default cartService;
