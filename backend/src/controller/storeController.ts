import { inject, injectable } from "inversify";
import { IpurchaseService } from "../serviceInterface/IpurchaseService";
import { Request, Response } from 'express';
import { routeDto } from "../dtos/routeDto";
import { IstoreService } from "../serviceInterface/IstoreService";

@injectable()
export class storeController {
    _storeService: IstoreService;
    constructor(@inject("IstoreService") storeService: IstoreService) {
        this._storeService = storeService;
    }

    calculateRoute = async (req: Request, res: Response) => {
      let userId = req.params.id;
      let data : routeDto = req.body;
      try {
          let result = await this._storeService.calculateRoute(data, userId);
          
          res.status(200).json({
              message: "Route calculated successfully",
              data: result
          });
      } catch (error) {
          res.status(500).json({
              message: "Error calculating route",
              error: error.message
          });
      }
    }

  
}

export default storeController;
