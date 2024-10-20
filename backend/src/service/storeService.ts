import { inject, injectable } from "inversify";
import { IstoreService } from "../serviceInterface/IstoreService";
import { storeDto } from "../dtos/storeDto";
import IstoreDataAccess from "../dataAccessInterface/IstoreDataAccess";

@injectable()
export class storeService implements IstoreService {

    _storeDataAccess: IstoreDataAccess;
    constructor(@inject("IstoreDataAccess") storeDataAccess: IstoreDataAccess) {
        this._storeDataAccess = storeDataAccess;
    }
    
    addStore = async (data: storeDto) => {
        return await this._storeDataAccess.addStore(data);
    }



}