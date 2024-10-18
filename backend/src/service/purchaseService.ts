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

    getLastPurchases = async (userId: string) => {
        let result = await this._purchaseDataAccess.getAllPurchases();
        result = result.filter(purchase => purchase.userId === userId);
        result = result.sort((a, b) => {
            return new Date(b.date).getTime() - new Date(a.date).getTime();
        });
        result = result.slice(0, 5);
        return result;
    }
}