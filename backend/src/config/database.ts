
import { Sequelize } from 'sequelize';
import dotenv from 'dotenv';

dotenv.config();

let DB_SYNC = process.env.DB_SYNC;

const sequelize = new Sequelize(process.env.MYSQL_DATABASE, process.env.MYSQL_USER, process.env.MYSQL_PASSWORD, {
  host: process.env.MYSQL_HOST || 'mysql',
  port: Number(process.env.MYSQL_PORT) || 3306,
  dialect: 'mysql'
});

const syncTables = async () => {
  try {
    if (DB_SYNC === 'true') {
      await sequelize.sync();
      console.log('Models have been synchronized successfully!');
    }
  } catch (error) {
    console.error('Unable to sync models:', error);
  }
}
const dbSync = async () => {
  let Sequelize = require('sequelize');
  await syncTables();
};

export { sequelize, dbSync };
