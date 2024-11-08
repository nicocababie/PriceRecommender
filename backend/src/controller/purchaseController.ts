import { inject, injectable } from "inversify";
import { IpurchaseService } from "../serviceInterface/IpurchaseService";
import { NextFunction, Request, Response } from "express";
import { productDto } from "../dtos/productDto";
import { purchaseDto } from "../dtos/purchaseDto";
const axios = require('axios');
const FormData = require('form-data');
const fs = require('fs');
const express = require('express')
const mindee = require("mindee");
const mindeeClient = new mindee.Client({ apiKey: "c13c9cd01b573127cd4816e99d6faca9" });
import { saveImage } from "../middleware/multer";
import { MulterErrorException } from "../exceptions/exceptions/MulterErrorException";
import { InternalException } from "../exceptions/exceptions/InternalException";
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
            let userId = req.params.id;
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
    getProducts = async (req: Request, res: Response) => {
        let offsetHeader = req.headers['offset'];
        let amountHeader = req.headers['amount'];
        let offset: number;
        if (offsetHeader) {
            offset = parseInt(offsetHeader as string, 10);
            if (isNaN(offset)) {
                offset = 0;
            }
        } else {
            offset = 0;
        }
        let amount: number;
        if (amountHeader) {
            amount = parseInt(req.headers['amount'] as string, 10);
            if (isNaN(amount)) {
                amount = 50;
            }
        } else {
            amount = 50;
        }

        try {
            let products : productDto[] = await this._purchaseService.getAllProducts(amount, offset);
            
            res.status(200).json({
                message: "Showing a maximum of " + amount + " products with offset of " + offset,
                data: products
            });
        } catch (error) {
            res.status(500).json({
                message: "Error getting products",
                error: error.message
            });
        }
    }
    createPurchaseWithPicture = async (req: Request, res: Response) => {
        let images = [];
        let storeLatitude = req.body.storeLatitude;
        let storeLongitude = req.body.storeLongitude;
        let userId = req.body.userId;
        try{
            images = await Promise.all((req.files as Express.Multer.File[]).map(async (file) => await saveImage(file)));
        }catch(error){
            return res.status(500).json({"message": "Error creating purchase", "error": error.message});
        }
        try{
        for (let i = 0; i < images.length; i++) {
            let receiptName = images[i];
            let  inputSource = mindeeClient.docFromPath("/app/data/pictures/"+receiptName);


            let apiResponse = mindeeClient.enqueueAndParse(
                mindee.product.ReceiptV5,
                inputSource
            );
            let items : any[];
            let storeName : string;
            let storeAddress : string;
            let price : string;
            let date : string;
            let products : productDto[] = [];
            await apiResponse.then((resp) => {
                items = resp.document.inference.prediction.lineItems;
                storeName = resp.document.inference.prediction.supplierName.value;
                price = resp.document.inference.prediction.totalAmount.value;
                date = resp.document.inference.prediction.date.value;
                storeAddress = resp.document.inference.prediction.supplierAddress.value;

            });
            for (let j = 0; j < items.length; j++) {
                let prod : productDto = {
                    name: items[j].description,
                    price: items[j].totalAmount/items[j].quantity,
                    amount: items[j].quantity,
                    brand: items[j].description,
                    store: storeName,
                }
                products.push(prod);
            }
            let purchase : purchaseDto = {
                storeName: storeName,
                storeAddress: storeAddress,
                storeLatitude: storeLatitude,
                storeLongitude: storeLongitude,
                listProducts: products,
                userId: userId,
                date: new Date()
            }

            fs.unlinkSync("/app/data/pictures/"+receiptName);
            console.log(purchase);
        let result = await this._purchaseService.createPurchase(purchase);
            return res.status(201).json({ message: "Purchase created successfully" , product: purchase});
        }

    }catch(error){
        return res.status(500).json({"message": "Error creating purchase", "error": error.message});
    }
        
    }
}

export default purchaseController;
