import { productDto } from "../dtos/productDto";
import { purchaseDto } from "../dtos/purchaseDto";

export interface IpurchaseService {
    createPurchase: (data: purchaseDto) => Promise<string>;
    getPurchase: (purchaseId: string) => Promise<purchaseDto>;
    getLastPurchases: (userId: string) => Promise<purchaseDto[]>;
    getAllProducts(amountProducts: number, offset: number): Promise<productDto[]>;
}