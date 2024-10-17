import { purchaseDto } from "../dtos/purchaseDto";

export interface IpurchaseService {
    createPurchase: (data: purchaseDto) => Promise<string>;
    getPurchase: (purchaseId: string) => Promise<purchaseDto>;
}