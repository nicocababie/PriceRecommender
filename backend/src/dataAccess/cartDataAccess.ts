import { injectable } from "inversify";
import 'reflect-metadata';
import { IcartDataAccess } from "../dataAccessInterface/IcartDataAccess";
import { cartProductDto } from "../dtos/cartProductDto";
import Cart from "../domain/cart";
import { InternalException } from "../exceptions/exceptions/InternalException";

@injectable()
export class cartDataAccess implements IcartDataAccess {
  overwriteCart = async (data: cartProductDto[] , userId:string) => {
    try {
      await Cart.destroy({ where: { userId: userId } });
      for (const product of data) {
        await Cart.create({ userId: userId, 
                          productName: product.name,
                          productAmount: product.amount,
                          productBrand: product.brand});
      }
      return userId;
    } catch (error) {
      throw new InternalException(error.message, 500);
    }
  };


  addToCart = async (data: cartProductDto[] , userId:string): Promise<string> => {
    try {
      for (const product of data) {
        let alreadyInCart = await Cart.findOne({ where: { userId: userId, productName:product.name } });
        if (alreadyInCart) {
          let prodAmount = alreadyInCart.getDataValue("productAmount") + product.amount;
          await Cart.update({ productAmount: prodAmount }, { where: { userId: userId, productName: product.name } });
        }else{
        await Cart.create({ userId: userId, 
                          productName: product.name,
                          productAmount: product.amount,
                          productBrand: product.brand});
        }
      }

      return userId;
    } catch (error) {
      throw new InternalException(error.message, 500);
    }
  }
}
