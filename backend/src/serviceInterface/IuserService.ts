
export interface IuserService {
    createUser: (data: any) => Promise<string>;
    getUser: (userId: string) => Promise<any>;
}