package com.m3.jjug2019spring.quiz;

import java.util.Objects;

public class Q1 {
  public static void main(String[] args) throws Exception {
    System.out.println(StringsInSameClass.isSameInstance() + ": A と B が同じ .class ファイルにある");
    System.out.println(StringsInDifferentClass.isSameInstance() + ": A と B が異なる class, 同じ jar にある");
    System.out.println(StringsInDifferentClassLoader.isSameInstance() + ": A と B が異なる ClassLoader にある");
  }

  private static class StringsInSameClass {
    public static final String A = "hoge";
    public static final String B = "hoge";

    public static boolean isSameInstance() {
      return A == B;
    }
  }

  private static class StringsInDifferentClass {
    private static class ContainerA {
      public static final String A = "hoge";
    }
    private static class ContainerB {
      public static final String B = "hoge";
    }

    public static boolean isSameInstance() {
      return ContainerA.A == ContainerB.B;
    }
  }

  public static class StringsInDifferentClassLoader {
    /**
     * 相異なる ClassLoader にロードした、同じ .class file の文字列定数を取得し、それが同じインスタンスかどうかを返す。
     */
    public static boolean isSameInstance() throws Exception {
      final Class<?> klassA = loadClassInSeparateClassLoader();
      final Class<?> klassB = loadClassInSeparateClassLoader();
      if (Objects.equals(klassA, klassB)) throw new AssertionError("Should load different class");
      if (Objects.equals(klassA.getClassLoader(), klassB.getClassLoader())) throw new AssertionError("Should use different ClassLoader");

      return stringOf(klassA) == stringOf(klassB);
    }

    /**
     * 新規 {@see ClassLoader} を作成し、そこにロードした <code>Container</code> の Class を返す。
     * @return
     */
    private static Class<?> loadClassInSeparateClassLoader() throws Exception {
      // Q1TestContainer の .class ファイルをロードする
      final ClassLoader currentClassLoader = StringsInDifferentClassLoader.class.getClassLoader();
      final String className = Q1TestContainer.class.getName();
      final byte[] classFile = currentClassLoader.getResourceAsStream(className.replace('.', '/') + ".class").readAllBytes();

      // SystemClassLoader を親と「しない」ClassLoader を作る(parent == null)ことで、
      // JVM の classPath でロードされている Q1TestContainer ではなく、都度ロードしている class が得られるようになる
      final ClassLoader temporaryClassLoader = new ClassLoader(null) {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
          return defineClass(className, classFile, 0, classFile.length);
        }
      };
      return temporaryClassLoader.loadClass(className);
    }

    private static String stringOf(Class<?> containerClass) throws Exception {
      return (String) containerClass.getDeclaredField("STRING").get(null);
    }
  }
}

