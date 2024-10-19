import { purchaseDto } from "../dtos/purchaseDto";

export interface IpurchaseDataAccess {
    createPurchase: (data: purchaseDto) => Promise<string>;
    getPurchase: (purchaseId: string) => Promise<purchaseDto>;
    getAllPurchases: (userId: string) => Promise<purchaseDto[]>;
}

export default IpurchaseDataAccess;
