import { cartProductDto } from "../dtos/cartProductDto";

export interface IcartService {
  overwriteCart(data: cartProductDto[], userId: string): Promise<string>;
  addToCart(data: cartProductDto[], userId: string): Promise<string>;
  addToNewCart(data: cartProductDto[]): Promise<string>;
}