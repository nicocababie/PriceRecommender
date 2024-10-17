import { DataTypes, Model, Optional } from "sequelize";
import { sequelize } from "../config/database";
import Product from './product';

// Definir la interfaz para Purchase
interface PurchaseAttributes {
    id?: number;
    storeName: string;
    storeAddress: string;
}

interface PurchaseCreationAttributes extends Optional<PurchaseAttributes, 'id'> {}

export class Purchase extends Model<PurchaseAttributes, PurchaseCreationAttributes>
    implements PurchaseAttributes {
    public id!: number;
    public storeName!: string;
    public storeAddress!: string;

    // Timestamps
    public readonly createdAt!: Date;
    public readonly updatedAt!: Date;

    // Métodos de asociación
    public getProducts!: () => Promise<Product[]>;
    public addProduct!: (product: Product, options?: any) => Promise<void>;
}

Purchase.init(
    {
        id: {
            type: DataTypes.INTEGER,
            autoIncrement: true,
            primaryKey: true
        },
        storeName: {
            type: DataTypes.STRING,
            allowNull: false
        },
        storeAddress: {
            type: DataTypes.STRING,
            allowNull: false
        }
    },
    {
        sequelize,
        tableName: 'purchases',
    }
);

export default Purchase;
