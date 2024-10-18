import { inject, injectable } from "inversify";
import { IuserDataAccess } from "../dataAccessInterface/IuserDataAccess";
import { IuserService } from "../serviceInterface/IuserService";

@injectable()
export class userService implements IuserService {
    _userDataAccess: IuserDataAccess;
    constructor(@inject("IuserDataAccess") IuserDataAccess: IuserDataAccess) {
        this._userDataAccess = IuserDataAccess;
    }
    
    createUser = async (data: any): Promise<string> => {
        return await this._userDataAccess.createUser(data);
    }
    
    getUser = async (userId: string): Promise<any> => {
        return await this._userDataAccess.getUser(userId);
    }
}