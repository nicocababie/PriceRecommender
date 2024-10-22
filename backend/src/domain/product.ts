import { DataTypes, Model, Optional } from "sequelize";
import { sequelize } from "../config/database";
import Store from "./store";

// Definir la interfaz para Product
interface ProductAttributes {
    id?: number;
    name: string;
    price: number;
    brand: string;
    store: string;
}

// Definir la interfaz para la creación de un nuevo Product
interface ProductCreationAttributes extends Optional<ProductAttributes, 'id'> {}

export class Product extends Model<ProductAttributes, ProductCreationAttributes>
    implements ProductAttributes {
    public id!: number;
    public name!: string;
    public price!: number;
    public brand!: string;
    public store!: string;

    // Timestamps
    public readonly createdAt!: Date;
    public readonly updatedAt!: Date;
}

Product.init(
    {
        id: {
            type: DataTypes.INTEGER,
            autoIncrement: true,
            primaryKey: true
        },
        name: {
            type: DataTypes.STRING,
            allowNull: false
        },
        price: {
            type: DataTypes.FLOAT,
            allowNull: false
        },
        brand: {
            type: DataTypes.STRING,
            allowNull: false
        },
        store: {        //StoreAddress
            type: DataTypes.STRING,
            allowNull: false,
            references: {
                model: Store,
                key: 'addressName'
            }
        }
    },
    {
        sequelize,
        tableName: 'products',
    }
);

export default Product;
