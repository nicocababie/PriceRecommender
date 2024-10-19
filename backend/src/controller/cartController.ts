import { inject, injectable } from 'inversify';
import 'reflect-metadata';
import { IcartService } from '../serviceInterface/IcartService';
import { NextFunction, Request, Response } from 'express';
import { cartProductDto } from '../dtos/cartProductDto';

@injectable()
export class cartController {
  _cartService: IcartService;
  constructor(@inject("IcartService") cartService: IcartService) {
    this._cartService = cartService;
  }

  overwriteCart = async (req: Request, res: Response) => {
    let data = req.body;
    let userId = req.params.id;
    let products : cartProductDto[] = data;

    return await this._cartService.overwriteCart(products, userId);
  }
  addToCart = async (req: Request, res: Response) => {
    let data = req.body;
    let userId = req.params.id;
    let products : cartProductDto[] = data;

    return await this._cartService.addToCart(products, userId);
  }
}

export default cartController;
