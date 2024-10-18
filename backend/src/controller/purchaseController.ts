import { inject, injectable } from "inversify";
import { IpurchaseService } from "../serviceInterface/IpurchaseService";
import { Request, Response } from 'express';

@injectable()
export class purchaseController {
    _purchaseService: IpurchaseService;
    constructor(@inject("IpurchaseService") purchaseService: IpurchaseService) {
        this._purchaseService = purchaseService;
    }

    createPurchase = async (req: Request, res: Response) => {
        try {
            let data = req.body;
            let result = await this._purchaseService.createPurchase(data);
            
            res.status(201).json({
                message: "Purchase created successfully",
                data: result
            });
        } catch (error) {
            res.status(500).json({
                message: "Error creating purchase",
                error: error.message
            });
        }
    }

    getPurchase = async (req: Request, res: Response) => {
        try {
            let purchaseId = req.params.id;
            let result = await this._purchaseService.getPurchase(purchaseId);
            
            if (!result) {
                return res.status(404).json({
                    message: "Purchase not found"
                });
            }

            res.status(200).json({
                message: "Purchase retrieved successfully",
                data: result
            });
        } catch (error) {
            res.status(500).json({
                message: "Error retrieving purchase",
                error: error.message
            });
        }
    }

    getLastPurchases = async (req: Request, res: Response) => {
        try {
            let userId = req.params.userId;
            let result = await this._purchaseService.getLastPurchases(userId);
            if (!result) {
                return res.status(404).json({
                    message: "Purchases not found"
                });
            }

            res.status(200).json({
                message: "Purchases retrieved successfully",
                data: result
            });
        } catch (error) {
            res.status(500).json({
                message: "Error retrieving purchases",
                error: error.message
            });
        }
    }
}

export default purchaseController;
