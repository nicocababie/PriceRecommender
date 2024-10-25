import { productDto } from "../dtos/productDto";
import { purchaseDto } from "../dtos/purchaseDto";

export interface IpurchaseDataAccess {
    createPurchase: (data: purchaseDto) => Promise<string>;
    getPurchase: (purchaseId: string) => Promise<purchaseDto>;
    getAllPurchases: (userId: string) => Promise<purchaseDto[]>;
    getAllProducts: (amountProducts: number, offset: number) => Promise<productDto[]>;
}

export default IpurchaseDataAccess;
