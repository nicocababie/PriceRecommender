import { purchaseDto } from "../dtos/purchaseDto";
import { sequelize } from "../config/database";
import IpurchaseDataAccess from "../dataAccessInterface/IpurchaseDataAccess";
import Product from "../domain/product";
import Purchase from "../domain/purchase";
import PurchaseProduct from "../domain/PurchaseProduct";
import { injectable } from "inversify";

@injectable()
export class PurchaseDataAccess implements IpurchaseDataAccess {
    async createPurchase(data: purchaseDto): Promise<string> {
        const transaction = await sequelize.transaction();
        try {
            // Crear la compra
            const purchase = await Purchase.create(
                {
                    storeName: data.storeName,
                    storeAddress: data.storeAddress,
                    userId: data.userId,
                    date: new Date()
                },
                { transaction }
            );

            // Asociar productos a la compra con la cantidad
            for (const productData of data.listProducts) {
                // Buscar el producto por ID o crear uno nuevo si no existe
                let product = await Product.findByPk(productData.id, { transaction });
                if (!product) {
                    product = await Product.create(
                        {
                            name: productData.name,
                            price: productData.price,
                            brand: productData.brand,
                            store: productData.store
                        },
                        { transaction }
                    );
                }

                // Usar la tabla intermedia PurchaseProduct para insertar el 'amount'
                await PurchaseProduct.create(
                    {
                        purchaseId: purchase.id,
                        productId: product.id,
                        amount: productData.amount
                    },
                    { transaction }
                );
            }

            await transaction.commit();
            return `Purchase created with ID: ${purchase.id}`;
        } catch (error) {
            await transaction.rollback();
            throw new Error("Error creating purchase: " + error.message);
        }
    }

    async getPurchase(purchaseId: string): Promise<purchaseDto> {
        try {
            // Incluir productos asociados con el campo 'amount' desde la tabla intermedia
            const purchase = await Purchase.findByPk(purchaseId, {
                include: [
                    {
                        model: Product,
                        as: "products",
                        through: {
                            attributes: ["amount"] // No necesitamos especificar 'model' aquí
                        }
                    }
                ]
            });

            if (!purchase) {
                throw new Error(`Purchase with ID ${purchaseId} not found`);
            }

            // Usar el método getProducts en lugar de acceder directamente a la propiedad 'products'
            const products = await purchase.getProducts();

            // Mapear los productos a productDto incluyendo la cantidad
            const listProducts = products.map((product: any) => ({
                id: product.id,
                name: product.name,
                price: product.price,
                brand: product.brand,
                store: product.store,
                amount: product.PurchaseProduct.amount // Obtener la cantidad desde la tabla intermedia
            }));

            return {
                storeName: purchase.storeName,
                storeAddress: purchase.storeAddress,
                listProducts,
                userId: purchase.userId,
                date: purchase.date
            };
        } catch (error) {
            throw new Error("Error retrieving purchase: " + error.message);
        }
    }

    async getAllPurchases(): Promise<purchaseDto[]> {
        try {
            // Incluir productos asociados con el campo 'amount' desde la tabla intermedia
            const purchases = await Purchase.findAll({
                include: [
                    {
                        model: Product,
                        as: "products",
                        through: {
                            attributes: ["amount"] // No necesitamos especificar 'model' aquí
                        }
                    }
                ]
            });

            // Mapear las compras a purchaseDto
            return purchases.map((purchase: any) => {
                // Usar el método getProducts en lugar de acceder directamente a la propiedad 'products'
                const products = purchase.getProducts();
                const listProducts = products.map((product: any) => ({
                    id: product.id,
                    name: product.name,
                    price: product.price,
                    brand: product.brand,
                    store: product.store,
                    amount: product.PurchaseProduct.amount // Obtener la cantidad desde la tabla intermedia
                }));

                return {
                    storeName: purchase.storeName,
                    storeAddress: purchase.storeAddress,
                    listProducts,
                    userId: purchase.userId,
                    date: purchase.date
                };
            });
        } catch (error) {
            throw new Error("Error retrieving purchases: " + error.message);
        }
    }
}
