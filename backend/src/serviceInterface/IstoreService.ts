import { storeDto } from "../dtos/storeDto"

export interface IstoreService {
    addStore: (data: storeDto) => Promise<string>;

}