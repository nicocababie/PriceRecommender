import { DataTypes } from "sequelize";
import Product from "./product";
import Purchase from "./purchase";
import { sequelize } from "../config/database";
import Store from "./store";

// Define la tabla intermedia que une las tiendas con los productos
export const StoreProduct = sequelize.define("StoreProduct", {
    storeAddress: {
        type: DataTypes.STRING,
        references: {
            model: Store,
            key: 'addressName'
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
    }
},
{
  tableName: 'StoreProducts',
  timestamps: false,
  indexes: [
      {
          unique: true,
          fields: ['storeAddress', 'productId']
      }
  ]
},);


export default StoreProduct;
