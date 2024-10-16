import { IError } from "./IError";

export class InternalException extends Error implements IError {
    public status = 500;
    public fields: { name: { message: string } };

    constructor(msg: string, statusCode: number, name: string = "InternalException") {
        super(msg);
        this.name = "InternalException";
        this.message = msg;
        this.status = statusCode;
        Object.setPrototypeOf(this, InternalException.prototype);
    }

    exceptionMessage() {
        return "Error: " + this.message;
    }
}
