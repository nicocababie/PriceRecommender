import { productDto } from "./productDto";

export class storeDto {
  name: string;
  addressName: string;
  latitude: number;
  longitude: number;
  products : productDto[];
}