import { inject, injectable } from 'inversify';
import { IuserService } from '../serviceInterface/IuserService';
import { Request, Response } from 'express';

@injectable()
export class userController {
    _userService: IuserService;
    constructor(@inject("IuserService") userService: IuserService) {
        this._userService = userService;
    }

    createUser = async (req: Request, res: Response) => {
        try {
            let userId = await this._userService.createUser();
            res.status(200).json({userId: userId});
        } catch (error) {
            res.status(400).json({error: error});
        }
    }
}
