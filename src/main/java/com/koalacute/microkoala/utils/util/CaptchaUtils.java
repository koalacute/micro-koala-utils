package com.koalacute.microkoala.utils.util;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

/**
 * 图形验证码
 */
public class CaptchaUtils {

    public static byte[] createRandomImage(String value) {
        try {
            DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
            Properties properties = new Properties();
            // 是否有边框 可选yes 或者 no
            properties.put(Constants.KAPTCHA_BORDER, "yes");
            // 边框颜色
            properties.put(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");
            //验证码文本字符颜色
            properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
            // 验证码文本字符大小
            properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, 45);
            // 验证码图片的宽度 默认200
            properties.put(Constants.KAPTCHA_IMAGE_WIDTH, 125);
            // < !--验证码图片的高度 默认50-- >
            properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, 45);
            // 验证码文本字符长度 默认为5
            properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, value.length());
            // 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
            // properties.put("kaptcha.textproducer.font.names", "宋体, 楷体, 微软雅黑");
            Config config = new Config(properties);
            defaultKaptcha.setConfig(config);
            BufferedImage img = defaultKaptcha.createImage(value);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(img, "JPEG", output);
            return output.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("write random fail", e);
        }
    }
}
