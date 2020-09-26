package age.server;


import org.opencv.AgeGenderCheck;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AgeServer {
    AgeServer(){

        // Read into byte-array

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("加载opencv动态库成功");
    }

    public Mat readInputStreamIntoMat(InputStream inputStream) throws IOException {
        // Read into byte-array
        byte[] temporaryImageInMemory = readStream(inputStream);

        // Decode into mat. Use any IMREAD_ option that describes your image appropriately
        Mat outputImage = Imgcodecs.imdecode(new MatOfByte(temporaryImageInMemory), Imgcodecs.IMREAD_COLOR);
        System.out.println(outputImage.channels());
        return outputImage;
    }
    public byte[] readStream(InputStream stream) throws IOException {
        // Copy content of the image to byte-array
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] temporaryImageInMemory = buffer.toByteArray();
        buffer.close();
        stream.close();
        return temporaryImageInMemory;
    }
    public void writeJpg(OutputStream stream, Mat img) throws IOException {
        MatOfByte buf = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, buf);
        byte[] imageBytes = buf.toArray();
//        stream.write(("Content-type: image/jpeg\r\n" +
//                "Content-Length: " + imageBytes.length + "\r\n" +
//                "\r\n").getBytes());
        stream.write(imageBytes);
//        stream.write(("\r\n--" + boundary + "\r\n").getBytes());
    }
    public Mat processPicture(MultipartFile filedata) {
        try {

                Mat orginal =  readInputStreamIntoMat(filedata.getInputStream());
                Mat reponse_mat = new Mat();
                long a= AgeGenderCheck.GetAgeAndSex(orginal .getNativeObjAddr(),reponse_mat.getNativeObjAddr());
                Imgcodecs.imwrite("this.jpg",reponse_mat);
                return reponse_mat;
        }catch ( IOException e){
            return null;
        }
    }
}
