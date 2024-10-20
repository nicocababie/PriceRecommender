import { inject, injectable } from "inversify";
import { IpurchaseService } from "../serviceInterface/IpurchaseService";
import { Request, Response } from 'express';
import { IrouteService } from "../serviceInterface/IrouteService";
import { routeDto } from "../dtos/routeDto";

@injectable()
export class routeController {
    _storeService: IrouteService;
    constructor(@inject("IstoreService") storeService: IrouteService) {
        this._storeService = storeService;
    }

    calculateRoute = async (req: Request, res: Response) => {
      let data : routeDto = req.body;
      try {
          let result = await this._storeService.calculateRoute(data);
          
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

export default routeController;
