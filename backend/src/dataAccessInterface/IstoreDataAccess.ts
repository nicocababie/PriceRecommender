import { productDto, productNameBrand } from "../dtos/productDto";
import { storeDto } from "../dtos/storeDto";

export interface IstoreDataAccess {
    addStore: (data: storeDto) => Promise<string>;
    getStoresInArea: (targetLat: number, targetLng: number, radius: number) => Promise<storeDto[]>;
    getProductsIdInStore: (storeAddress: string) => Promise<any[]>;
    getProducts: (productsId: number[]) => Promise<productDto[]>;
    getProductsId: (products: productNameBrand[]) => Promise<number[]>;
}

export default IstoreDataAccess;
