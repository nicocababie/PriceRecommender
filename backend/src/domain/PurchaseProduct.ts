// purchaseProduct.ts
import { DataTypes } from "sequelize";
import Product from "./product";
import Purchase from "./purchase";
import { sequelize } from "../config/database";

// Definir la tabla intermedia con el campo 'amount'
export const PurchaseProduct = sequelize.define("PurchaseProduct", {
    purchaseId: {
        type: DataTypes.INTEGER,
        references: {
            model: Purchase,
            key: 'id'
        },
        primaryKey: true
    },
    productId: {
        type: DataTypes.INTEGER,
        references: {
            model: Product,
            key: 'id'
        },
        primaryKey: true
    },
    amount: {
        type: DataTypes.INTEGER,
        allowNull: false 
    }
});

Purchase.belongsToMany(Product, {
    through: "PurchaseProduct",  
    as: "products",              
    foreignKey: "purchaseId"
});

Product.belongsToMany(Purchase, {
    through: "PurchaseProduct",
    as: "purchases",
    foreignKey: "productId"
});

export default PurchaseProduct;
