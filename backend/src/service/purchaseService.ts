import { inject, injectable } from "inversify";
import { purchaseDto } from "../dtos/purchaseDto";
import { IpurchaseService } from "../serviceInterface/IpurchaseService";
import IpurchaseDataAccess from "../dataAccessInterface/IpurchaseDataAccess";

@injectable()
export class purchaseService implements IpurchaseService {
    _purchaseDataAccess: IpurchaseDataAccess
    constructor(@inject("IpurchaseDataAccess") IpurchaseDataAccess: IpurchaseDataAccess) {
        this._purchaseDataAccess = IpurchaseDataAccess;
    }
    
    createPurchase = async (data: purchaseDto) => {
        let result = await this._purchaseDataAccess.createPurchase(data);
        return result;
    }
    
    getPurchase = async (purchaseId: string) => {
        let result = await this._purchaseDataAccess.getPurchase(purchaseId);
        return result;
    }
}