import { productDto } from "./productDto";

export class purchaseDto {
    id?: number; 
    storeName: string;
    storeAddress: string;
    listProducts: productDto[];
    userId: string;
    date: Date;
}
