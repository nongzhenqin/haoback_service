package com.haoback.common.utils;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.UUID;

/**
 * 工具类 - 图片处理
 */

public class ImageUtil {

    public static float quality = 0.8f;

    /**
     * base64图片写入到指定目录，并且压缩
     * @param base64
     * @param filePath
     * @param quality
     */
    public static void imageBase64Save(String base64, String filePath, float quality) {
        File file = new File(filePath);

        byte[] bytes = ImageUtil.imageBase64ToByte(base64, quality);

        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * base64字符串转化成图片字节数组
     * @param imgStr
     * @return
     */
    public static byte[] imageBase64ToByte(String imgStr)  {
        if (StringUtils.isBlank(imgStr)){ //图像数据为空
            return null;
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * base64字符串转化成图片字节数组 并且压缩
     * @param imgStr
     * @param quality 压缩质量 0-1
     * @return
     */
    public static byte[] imageBase64ToByte(String imgStr, float quality)  {
        byte[] bytes = imageBase64ToByte(imgStr);
        if(bytes == null || bytes.length == 0) return null;

        return compress(bytes, quality);
    }

	/**
	 * 按原分辨率压缩图片
	 * @param imageByte
	 * @param quality
	 * @return
	 */
	public static byte[] compress(byte[] imageByte, float quality) {
        byte[] inByte = null;
        try {

            BufferedImage image = ImageUtil.toBufferedImage(imageByte);

            // 如果图片空，返回空
            if (image == null) {
                return null;
            }

            // 得到指定Format图片的writer
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");// 得到迭代器
            ImageWriter writer = iter.next(); // 得到writer

            // 得到指定writer的输出参数设置(ImageWriteParam )
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 设置可否压缩
            iwp.setCompressionQuality(quality); // 设置压缩质量参数

            iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

            ColorModel colorModel = ColorModel.getRGBdefault();
            // 指定压缩时使用的色彩模式
            iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
                    colorModel.createCompatibleSampleModel(16, 16)));

            // 开始打包图片，写入byte[]
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 取得内存输出流
            IIOImage iIamge = new IIOImage(image, null, null);

            // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
            // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
            writer.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
            writer.write(null, iIamge, iwp);
            inByte = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.out.println("write errro");
            e.printStackTrace();
        }
        return inByte;

    }

    /**
     * 把原图转换成二进制
     * @param input
     * @return
     */
    private static byte[] toByteArray(InputStream input) {
        if (input == null) {
            return null;
        }
        ByteArrayOutputStream output = null;
        byte[] result = null;
        try {
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 100];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            result = output.toByteArray();
            if (output != null) {
                output.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 把二进制转换成图片
     * @param imagedata
     * @return
     */
    private static BufferedImage toBufferedImage(byte[] imagedata) {
        Image image = Toolkit.getDefaultToolkit().createImage(imagedata);
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    /**
     * 下载图片
     * @param imageUrl 图片URL
     * @param savePath 保存地址
     * @return
     */
    public static String downLoadImage(String imageUrl, String savePath){
        try {
            URL url = new URL(imageUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String fileId = UUID.randomUUID().toString();
            String path = savePath + "upload" + File.separator + fileId + ".jpg";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            dataInputStream.close();
            fileOutputStream.close();

            return fileId;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 下载图片并且压缩
     * @param imageUrl 图片URL
     * @param savePath 保存地址
     * @param quality 压缩质量 0~1
     * @return
     */
    public static String downLoadImageAndCompress(String imageUrl, String savePath, float quality){
        try {
            URL url = new URL(imageUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String fileId = UUID.randomUUID().toString();

            String path = savePath + "upload" + File.separator + fileId + ".jpg";
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] bytes = ImageUtil.toByteArray(dataInputStream);
            bytes = ImageUtil.compress(bytes, quality);

            fileOutputStream.write(bytes);

            dataInputStream.close();
            fileOutputStream.close();

            return fileId;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}