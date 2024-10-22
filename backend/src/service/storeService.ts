import { inject, injectable } from "inversify";
import { IstoreService } from "../serviceInterface/IstoreService";
import { storeDto } from "../dtos/storeDto";
import IstoreDataAccess from "../dataAccessInterface/IstoreDataAccess";
import { routeDto } from "../dtos/routeDto";
import { IcartService } from "../serviceInterface/IcartService";
import { cartProductDto, cartProductIdDto } from "../dtos/cartProductDto";
import { productDto } from "../dtos/productDto";
import { getDistance } from 'geolib';

@injectable()
export class storeService implements IstoreService {

    _storeDataAccess: IstoreDataAccess;
    _cartService: IcartService;
    constructor(@inject("IstoreDataAccess") storeDataAccess: IstoreDataAccess,
    @inject("IcartService") cartService: IcartService) {
        this._storeDataAccess = storeDataAccess;
        this._cartService = cartService;
    }
    
    addStore = async (data: storeDto) => {
        return await this._storeDataAccess.addStore(data);
    }

    calculateRoute = async (data: routeDto, userId:string) => {
        let stores : storeDto[] = await this._storeDataAccess.getStoresInArea(data.addressLat, data.addressLng, data.range);
        let cartIds : cartProductIdDto[] = await this._cartService.getCart(userId);
        let ids : number[] = cartIds.map(e => e.productId);
        let cart : productDto[] = await this._storeDataAccess.getProducts(ids);
        let result = [];
        for (const store of stores) {
            let storeProductsIds : number[] = await this._storeDataAccess.getProductsIdInStore(store.addressName);
            let storeProducts : productDto[] = await this._storeDataAccess.getProducts(storeProductsIds);
            console.log("Products in store :", storeProducts);
            console.log("Cart: ", cart);
            for (const product of storeProducts) {
                for (const cartProduct of cart) {
                    if (result.find(e => e.productName == product.name && e.price > product.price)){
                        //Update e with new price
                        result = result.map(e => {
                            if(e.productName == product.name){
                                e.storeName = store.name;
                                e.storeLatitude = store.latitude;
                                e.storeLongitude = store.longitude;
                                e.price = product.price;
                            }
                            return e;
                        });
                    }
                    console.log("Condition 1 for cartProduct.name" , cartProduct.name == product.name);
                    console.log("Condition 2" , notInResult(result, product.name));
                    if(cartProduct.name == product.name && notInResult(result, product.name)){
                        result.push({storeName: store.name, storeLatitude: store.latitude, storeLongitude: store.longitude, productName: product.name, price: product.price});
                    }

                }
            }
        }
        console.log("Result: ", result);
        return this.sortPointsByDistance(result, data.addressLat, data.addressLng);
    }
        
    sortPointsByDistance = async (points: any[], latitudeOrigin: number, longitudeOrigin: number) => {
        return points.sort((a, b) => {
            const distanceA = getDistance(
                { latitude: latitudeOrigin, longitude: longitudeOrigin },
                { latitude: a.storeLatitude, longitude: a.storeLongitude }
            );
            const distanceB = getDistance(
                { latitude: latitudeOrigin, longitude: longitudeOrigin },
                { latitude: b.storeLatitude, longitude: b.storeLongitude }
            );
            
            return distanceA - distanceB;
        });
    }
        

    getProducts = async (productsId: number[]) => {
        return await this._storeDataAccess.getProducts(productsId);
    }

    getProductsId = async (productsId: any[]) => {  //Receives array of pairs (name, brand)
        return await this._storeDataAccess.getProductsId(productsId);
    }

}

function notInResult(result: any[], name: string) {
    for (const e of result) {
        if(e.productName == name){
            return false;
        }
    }return true;
}
