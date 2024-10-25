
import { sequelize  } from "../config/database";
import Product from "../domain/product";
import { injectable } from "inversify";
import IstoreDataAccess from "../dataAccessInterface/IstoreDataAccess";
import { storeDto } from "../dtos/storeDto";
import Store from "../domain/store";
import { UserErrorException } from "../exceptions/exceptions/UserErrorException";
import { Sequelize, QueryTypes  } from "sequelize";
import { productDto, productNameBrand } from "../dtos/productDto";

@injectable()
export class storeDataAccess implements IstoreDataAccess {
    async addStore(data: storeDto): Promise<string> {
        let transaction = await sequelize.transaction();
        try{
            let storeExists = await Store.findByPk(data.addressName);
            if (!storeExists) {
                const store = await Store.create(
                    {
                        name: data.name,
                        addressName: data.addressName,
                        latitude: data.latitude,
                        longitude: data.longitude
                    },
                    {transaction}
                );

                await transaction.commit();
            }
            return `Store created`;
        } catch (error) {
            await transaction.rollback();
            throw new Error("Error creating store: " + error.message);
        }
    }

    async getStoresInArea(targetLat: number, targetLng: number, radius: number): Promise<storeDto[]> {
        const earthRadiusInKm = 6371;
        const varTargetLat = targetLat;
        const varTargentLng = targetLng;
        // Haversine formula to get stores inside radius
        const query = `
            SELECT 
                *, 
                (
                    ${earthRadiusInKm} * ACOS(
                        COS(RADIANS(${varTargetLat})) * COS(RADIANS(latitude)) * COS(RADIANS(longitude) - RADIANS(${varTargentLng})) + 
                        SIN(RADIANS(${varTargetLat})) * SIN(RADIANS(latitude))
                    )
                ) AS distance 
            FROM Stores
            HAVING distance <= :radius
            ORDER BY distance ASC;
        `;
        
        const stores = await Store.sequelize?.query(query, {
            replacements: {
                targetLat,
                targetLng,
                radius: radius / 1000
            },
            type: QueryTypes.SELECT
        });

        const storeDtos: storeDto[] = (stores as any[]).map(store => ({
            name: store.name,
            addressName: store.addressName,
            latitude: store.latitude,
            longitude: store.longitude,
            products : []
        }));

    return storeDtos;
    }

    async getProductsIdInStore(storeAddress: string): Promise<any[]>{
        const products = await Product.findAll({ where: { store: storeAddress } });
        //Return array of ids
        return products.map((product) => {
            return product.getDataValue("id");
        });

    }

    async getProducts(productsId: number[]): Promise<productDto[]>{
        const products = await Product.findAll({ where: { id: productsId } });
        return products.map((product) => {
            return {
                id: product.getDataValue("id"),
                name: product.getDataValue("name"),
                price: product.getDataValue("price"),
                brand: product.getDataValue("brand"),
                amount: -1,
                store: "unknown"
            }
        });
    }
    async getProductsId(products: productNameBrand[]): Promise<number[]>{ 
        const productsId = await Product.findAll({ where: { name: products.map(product => product.name), brand: products.map(product => product.brand) } });
        if (productsId.length !== products.length) {
            throw new UserErrorException("Some of the products you added were not registered in the system", 404);
        }
        return productsId.map((product) => {
            return product.getDataValue("id");
        });

    }

}
