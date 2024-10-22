import { cartProductDto, cartProductIdDto } from "../dtos/cartProductDto";

export interface IcartService {
  overwriteCart(data: cartProductDto[], userId: string): Promise<string>;
  addToCart(data: cartProductDto[], userId: string): Promise<string>;
  getCart(userId: string): Promise<cartProductIdDto[]>;
}