import { inject, injectable } from "inversify";
import { purchaseDto } from "../dtos/purchaseDto";
import { IpurchaseService } from "../serviceInterface/IpurchaseService";
import IpurchaseDataAccess from "../dataAccessInterface/IpurchaseDataAccess";
import { IrouteService } from "../serviceInterface/IrouteService";
import { routeDto } from "../dtos/routeDto";

@injectable()
export class routeService implements IrouteService {
    _purchaseDataAccess: IpurchaseDataAccess
    constructor(@inject("IpurchaseDataAccess") IpurchaseDataAccess: IpurchaseDataAccess) {
        this._purchaseDataAccess = IpurchaseDataAccess;
    }
    
    calculateRoute = async (data: routeDto) => {
        let squareRange = {
            point1: {lat: data.addressLat - data.range, long: data.addressLng - data.range},
            point2: {lat: data.addressLat + data.range, long: data.addressLng + data.range},
            point3: {lat: data.addressLat + data.range, long: data.addressLng - data.range},
            point4: {lat: data.addressLat - data.range, long: data.addressLng + data.range}
        }
        return "result";
    }
}