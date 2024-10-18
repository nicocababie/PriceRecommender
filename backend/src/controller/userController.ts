import { injectable, inject } from "inversify";
import { IuserService } from "../serviceInterface/IuserService";
import { Request, Response } from 'express';

@injectable()
export class userController {
    _userService: IuserService;
    constructor(@inject("IuserService") userService: IuserService) {
        this._userService = userService;
    }
    
    createUser = async (req: Request, res: Response) => {
        let data = req.body;
        return await this._userService.createUser(data);
    }
    
    getUser = async (req: Request, res: Response) => {
        let userId = req.params.id;
        return await this._userService.getUser(userId);
    }
}