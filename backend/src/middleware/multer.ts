import fs from 'fs-extra';
import multer from 'multer';
import path from 'path';
import { MulterErrorException } from '../exceptions/exceptions/MulterErrorException';
import dotenv from 'dotenv';
import sharp from 'sharp';
dotenv.config();

const storage = multer.memoryStorage();

const upload = multer({
    storage: storage,
    fileFilter: (req, file, callback) => {
        try {
            if (!file.originalname.match(/\.(jpg|jpeg|png|gif)$/)) {
                throw new MulterErrorException('Only image files are allowed.', 400);
            }
            callback(null, true); // Accept the file if there is no error
        } catch (error) {
            callback(error);
        }
    }
});

export async function saveImage(file): Promise<string> {
    fs.ensureDirSync(path.join(__dirname, '../../data/pictures'));
    try {
        let imageName = file.originalname;
        let newPath = path.join(__dirname, '../../data/pictures', imageName);
        let resizedImageBuffer = await sharp(file.buffer)
            .resize({ fit: 'inside', width: 800, height: 800 })
            .jpeg({ quality: 80 })
            .toBuffer();

        await fs.promises.writeFile(newPath, await resizedImageBuffer);

        return imageName;
    } catch (error) {
        throw new MulterErrorException(error.message, error.status);
    }
}

export default upload;
