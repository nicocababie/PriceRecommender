import { cartProductDto } from "../dtos/cartProductDto";

export interface IcartDataAccess {
  overwriteCart(data: cartProductDto[], userId: string): Promise<string>;
  addToCart(data: cartProductDto[], userId: string): Promise<string>;
}