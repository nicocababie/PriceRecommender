import { IError } from "./IError";

export class UserErrorException extends Error implements IError {
    public status = 400;
    public fields: { name: { message: string } };

    constructor(msg: string, statusCode: number, name: string = "User Error") {
        super(msg);
        this.name = "UserErrorException";
        this.message = msg;
        this.status = statusCode;
        Object.setPrototypeOf(this, UserErrorException.prototype);
    }

    exceptionMessage() {
        return "Error: " + this.message;
    }
}
