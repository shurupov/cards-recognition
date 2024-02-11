package ru.shurupov.cards.recognition.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ResourceUtils {

  public static BufferedImage readImage(String path) {
    try {
      InputStream imageStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
      return ImageIO.read(imageStream);
    } catch (Throwable e) {
      return null;
    }
  }

  public static List<String> getResourceFiles(String path) {
    List<String> filenames = new ArrayList<>();
    try (
        InputStream in = getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
      String resource;
      while ((resource = br.readLine()) != null) {
        filenames.add(resource);
      }
    } catch (Throwable e) {
      return List.of();
    }
    return filenames;
  }
  private static InputStream getResourceAsStream(String resource) {
    final InputStream in
        = getContextClassLoader().getResourceAsStream(resource);
    return in == null ? ResourceUtils.class.getResourceAsStream(resource) : in;
  }
  private static ClassLoader getContextClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }
}
