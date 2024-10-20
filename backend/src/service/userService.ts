import { injectable, inject } from "inversify";
import { IuserService } from "../serviceInterface/IuserService";
import generateApiKey from 'generate-api-key';

@injectable()
export class userService implements IuserService {
  constructor() {
    
  }

  createUser = async (): Promise<string> => {
    let userId : string = generateApiKey({method: 'string',
        pool: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', length: 6}).toString();
    return userId;
  }
}