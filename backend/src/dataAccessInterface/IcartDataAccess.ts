import { cartProductDto, cartProductIdDto } from "../dtos/cartProductDto";

export interface IcartDataAccess {
  overwriteCart(data: cartProductDto[], userId: string, productsId : number[]): Promise<string>;
  addToCart(data: cartProductDto[], userId: string, productsId : number[]): Promise<string>;
  getCart(userId: string): Promise<cartProductIdDto[]>;
}