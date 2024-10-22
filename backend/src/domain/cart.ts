import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database';
import Product from './product';


export const Cart = sequelize.define("Cart", {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true
    },
    userId: {
        type: DataTypes.STRING,
        allowNull: false
    },
    productId: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: Product,
            key: 'id'
        }
    },
    productAmount: {
        type: DataTypes.FLOAT,
        allowNull: false
    },
},
    {
        tableName: 'Carts',
        timestamps: false,
        indexes: [
            {
                unique: false,
                fields: ['userId']
            }
        ]
    },
);

export default Cart;
