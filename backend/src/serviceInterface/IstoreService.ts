import { cartProductIdDto } from "../dtos/cartProductDto";
import { productDto } from "../dtos/productDto";
import { storeDto } from "../dtos/storeDto"

export interface IstoreService {
    addStore: (data: storeDto) => Promise<string>;
    calculateRoute: (data: any, userId : string) => Promise<any[]>;
    getProducts: (productsId: number[]) => Promise<productDto[]>;
    getProductsId: (products: any[]) => Promise<number[]>;
}