import { inject, injectable } from "inversify";
import { purchaseDto } from "../dtos/purchaseDto";
import { IpurchaseService } from "../serviceInterface/IpurchaseService";
import IpurchaseDataAccess from "../dataAccessInterface/IpurchaseDataAccess";
import { IstoreService } from "../serviceInterface/IstoreService";
import { storeDto } from "../dtos/storeDto";

@injectable()
export class purchaseService implements IpurchaseService {

    _purchaseDataAccess: IpurchaseDataAccess;
    _storeService: IstoreService;
    constructor(@inject("IpurchaseDataAccess") IpurchaseDataAccess: IpurchaseDataAccess,
    @inject("IstoreService") storeService: IstoreService) {
        this._purchaseDataAccess = IpurchaseDataAccess;
        this._storeService = storeService;
    }
    
    createPurchase = async (data: purchaseDto) => {
        let result = await this._purchaseDataAccess.createPurchase(data);
        let storeData : storeDto = { 
            name: data.storeName,
            addressName: data.storeAddress,
            latitude: data.storeLatitude,
            longitude: data.storeLongitude,
            products: data.listProducts
        }
        await this._storeService.addStore(storeData);
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