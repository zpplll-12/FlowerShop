package com.flower.util;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class VerifyCodeUtil {
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final Random RANDOM = new Random();
    private static final String CODES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generateCode(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append(CODES.charAt(RANDOM.nextInt(CODES.length())));
        return sb.toString();
    }

    public static void generateImage(String code, OutputStream os) throws IOException {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);g.fillRect(0,0,WIDTH,HEIGHT);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        for (int i = 0; i < code.length(); i++) {
            g.setColor(new Color(RANDOM.nextInt(200), RANDOM.nextInt(200), RANDOM.nextInt(200)));
            g.drawString(code.charAt(i)+"",20*i+10,30);
        }
        for (int i = 0; i <5; i++) {
            g.setColor(new Color(RANDOM.nextInt(255),RANDOM.nextInt(255),RANDOM.nextInt(255)));
            g.drawLine(RANDOM.nextInt(WIDTH),RANDOM.nextInt(HEIGHT),RANDOM.nextInt(WIDTH),RANDOM.nextInt(HEIGHT));
        }
        ImageIO.write(image,"JPEG",os);
    }
}