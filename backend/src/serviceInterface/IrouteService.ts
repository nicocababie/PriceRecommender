import { routeDto } from "../dtos/routeDto";

export interface IrouteService {
  calculateRoute(data: routeDto): Promise<string>;
}