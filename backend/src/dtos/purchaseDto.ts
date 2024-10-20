import { productDto } from "./productDto";

export class purchaseDto {
    id?: number; 
    storeName: string;
    storeAddress: string;
    storeLatitude: number;
    storeLongitude: number;
    listProducts: productDto[];
    userId: string;
    date: Date;
}
