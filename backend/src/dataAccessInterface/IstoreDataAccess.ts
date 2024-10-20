import { storeDto } from "../dtos/storeDto";

export interface IstoreDataAccess {
    addStore: (data: storeDto) => Promise<string>;
}

export default IstoreDataAccess;
