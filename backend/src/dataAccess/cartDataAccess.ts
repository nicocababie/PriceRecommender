import { injectable } from "inversify";
import 'reflect-metadata';
import { IcartDataAccess } from "../dataAccessInterface/IcartDataAccess";
import { cartProductDto, cartProductIdDto } from "../dtos/cartProductDto";
import Cart from "../domain/cart";
import { InternalException } from "../exceptions/exceptions/InternalException";
import { sequelize } from "../config/database";

@injectable()
export class cartDataAccess implements IcartDataAccess {
  overwriteCart = async (data: cartProductDto[] , userId:string, productsId : number[]) => {
    try {
      await Cart.destroy({ where: { userId: userId } });
      for (let i = 0; i < data.length; i++) {
        await Cart.create({ userId: userId, 
                          productId: productsId[i],
                          productAmount: data[i].amount});
      }

      return userId;
    } catch (error) {
      throw new InternalException(error.message, 500);
    }
  };


  addToCart = async (data: cartProductDto[] , userId:string, productsId : number[]): Promise<string> => {
    const transaction = await sequelize.transaction();
    try {
      for (let i = 0; i < data.length; i++) {
        console.log("Iteration ", i);
        let alreadyInCart = await Cart.findOne({ where: { userId: userId, productId:productsId[i] } });
        if (alreadyInCart) {
          let prodAmount = alreadyInCart.getDataValue("productAmount") + data[i].amount;
          await Cart.update({ productAmount: prodAmount }, { where: { userId: userId, productId: productsId[i] } });
        }else{
        await Cart.create({ userId: userId, 
                          productId: productsId[i],
                          productAmount: data[i].amount}, {transaction});
        }
      }

      await transaction.commit();
      return userId;
    } catch (error) {
      await transaction.rollback();
      throw new InternalException(error.message, 500);
    }
  }

  getCart = async (userId: string): Promise<cartProductIdDto[]> => {
    try {
      const cart = await Cart.findAll({ where: { userId: userId } });
      return cart.map((product) => {
        return {
          id: product.getDataValue("id"),
          productId: product.getDataValue("productId"),
          amount: product.getDataValue("productAmount"),
          brand: product.getDataValue("productBrand"),
        };
      });
    } catch (error) {
      throw new InternalException(error.message, 500);
    }
  }
}
