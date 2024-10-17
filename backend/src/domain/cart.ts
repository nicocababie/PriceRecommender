import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database';


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
    productName: {
        type: DataTypes.STRING,
        allowNull: false
    },
    productAmount: {
        type: DataTypes.FLOAT,
        allowNull: false
    },
    productBrand: {
        type: DataTypes.STRING,
        allowNull: true
    },
},
    {
        tableName: 'Carts',
        timestamps: false,
        indexes: [
            {
                unique: true,
                fields: ['userId']
            }
        ]
    },
);

export default Cart;
