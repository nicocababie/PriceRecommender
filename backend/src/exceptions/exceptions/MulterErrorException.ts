import { IError } from "./IError";

export class MulterErrorException extends Error implements IError {
    public status = 400;
    public fields: { name: { message: string } };

    constructor(msg: string, statusCode: number, name: string = 'MulterError') {
        super(msg);
        this.name = "MulterErrorException";
        this.message = msg;
        this.status = statusCode;
        Object.setPrototypeOf(this, MulterErrorException.prototype);
    }

    exceptionMessage() {
        return "Error: " + this.message;
    }
}
