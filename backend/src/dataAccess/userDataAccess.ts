import { injectable } from "inversify";
import { User } from "../domain/user";
import { userDto } from "../dtos/userDto";
import { IuserDataAccess } from "../dataAccessInterface/IuserDataAccess";
import { sequelize } from "../config/database";

@injectable()
export class UserDataAccess implements IuserDataAccess {
    async createUser(data: userDto): Promise<string> {
        const transaction = await sequelize.transaction();
        try {
            const user = await User.create(
                {
                    name: data.name,
                    password: data.password
                },
                { transaction }
            );

            await transaction.commit();
            return `User created with ID: ${user.id}`;
        } catch (error) {
            await transaction.rollback();
            throw new Error("Error creating user: " + error.message);
        }
    }

    async getUser(userId: string): Promise<userDto> {
        try {
            const user = await User.findByPk(userId);
            if (!user) {
                throw new Error("User not found");
            }

            return {
                id: user.id,
                name: user.name,
                password: user.password
            };
        } catch (error) {
            throw new Error("Error getting user: " + error.message);
        }
    }
}