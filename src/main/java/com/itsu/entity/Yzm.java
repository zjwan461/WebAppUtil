package com.itsu.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 苏犇
 * @date 2019/7/14 21:48
 */

public final class Yzm {

    private static final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final int SIZE = 4;
    private static final int LINES = 5;
    private static final int WIDTH = 75;
    private static final int HEIGHT = 30;
    private static final int FONT_SIZE = 25;

    public static Map<String, BufferedImage> createImage() {
        StringBuffer sb = new StringBuffer();
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = image.getGraphics();
        graphic.setColor(Color.LIGHT_GRAY);
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        Random ran = new Random();

        for (int i = 1; i <= SIZE; i++) {
            int r = ran.nextInt(chars.length);
            graphic.setColor(getRandomColor());
            graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
            graphic.drawString(chars[r] + "", (i - 1) * WIDTH / SIZE, HEIGHT - 5);
            sb.append(chars[r]);
        }

        for (int i = 1; i <= LINES; i++) {
            graphic.setColor(getRandomColor());
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
        }
        Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
        map.put(sb.toString(), image);
        return map;
    }

    public static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
        return color;
    }

    public static void main(String[] args) {
        Map<String, BufferedImage> imageMap = Yzm.createImage();
        String code = imageMap.keySet().iterator().next();
        System.out.println(code);
        BufferedImage image = imageMap.get(code);
//        ImageIO.write(image, "png", ));
        try {
            ImageIO.write(image, "png", new FileOutputStream(new File("D:/Yzm.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}