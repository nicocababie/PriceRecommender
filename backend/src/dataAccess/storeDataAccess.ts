import { purchaseDto } from "../dtos/purchaseDto";
import { sequelize } from "../config/database";
import IpurchaseDataAccess from "../dataAccessInterface/IpurchaseDataAccess";
import Product from "../domain/product";
import Purchase from "../domain/purchase";
import PurchaseProduct from "../domain/PurchaseProduct";
import { injectable } from "inversify";
import IstoreDataAccess from "../dataAccessInterface/IstoreDataAccess";
import { storeDto } from "../dtos/storeDto";
import Store from "../domain/store";
import { UserErrorException } from "../exceptions/exceptions/UserErrorException";
import StoreProduct from "../domain/storeProduct";

@injectable()
export class storeDataAccess implements IstoreDataAccess {
    async addStore(data: storeDto): Promise<string> {
        let transaction = await sequelize.transaction();
        try{
            const store = await Store.create(
                {
                    name: data.name,
                    addressName: data.addressName,
                    latitude: data.latitude,
                    longitude: data.longitude
                },
                {transaction}
            );

            for (const productData of data.products) {
                // Buscar el producto por ID o crear uno nuevo si no existe
                let product = await Product.findByPk(productData.id);
                if (!product) {
                    throw new UserErrorException("Product does not exist", 404);
                }

                // Usar la tabla intermedia PurchaseProduct para insertar el 'amount'
                await StoreProduct.create(
                    {
                        productId: product.id,
                        storeAddress: data.addressName,
                    },
                    {transaction}
                );
            }

            await transaction.commit();
            return `Store created`;
        } catch (error) {
            await transaction.rollback();
            throw new Error("Error creating store: " + error.message);
        }
    }
}
