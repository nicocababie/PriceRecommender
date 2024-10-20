import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database';


export const Store = sequelize.define("Store", {
    name: {
        type: DataTypes.STRING,
        allowNull: false
    },
    addressName: {
        type: DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    latitude: {
        type: DataTypes.FLOAT,
        allowNull: false
    },
    longitude: {
        type: DataTypes.FLOAT,
        allowNull: true
    },
},
    {
        tableName: 'Stores',
        timestamps: false,
        indexes: [
            {
                unique: true,
                fields: ['addressName']
            }
        ]
    },
);

export default Store;
