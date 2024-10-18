
export interface IuserDataAccess {
    getUser(userId: string): Promise<any>;
    createUser(data: any): Promise<string>;
}