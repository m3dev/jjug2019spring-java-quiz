package com.m3.jjug2019spring.quiz;

import java.util.Random;
import java.util.stream.IntStream;

public class Q2 {

  public static void main(String[] args) {
    for (int countToGenerate : new int[]{ 1, 100, 1000, 100000 }) {
      int instances = countInstancesForRandomIntegers(countToGenerate, 100);
      System.out.println(String.format("%,7d 回の乱数生成で得られた Integer インスタンス数:\t%,7d", countToGenerate, instances));
    }
    /* 実行結果 (JDK 11.0.3):
      1 回の乱数生成で得られた Integer インスタンス数:	      1
    100 回の乱数生成で得られた Integer インスタンス数:	     67
  1,000 回の乱数生成で得られた Integer インスタンス数:	    100
100,000 回の乱数生成で得られた Integer インスタンス数:	    100
     */
  }

  /**
   * 指定された回数だけ乱数を生成し、一意な Integer インスタンスがいくつ得られたかを返す
   *
   * @param countToGenerate 乱数の生成回数
   * @param max 乱数の最大値 (exclusive)
   * @return 一意な Integer インスタンスの数
   */
  public static int countInstancesForRandomIntegers(int countToGenerate, int max) {
    return ObjectIdentityUtil.countIdentity(IntStream.range(0, countToGenerate).mapToObj((i) -> randomInteger(max)));
  }

  /**
   * ランダムな整数を Integer (not int) で返す
   * @param max 乱数の最大値 (exclusive)
   */
  public static Integer randomInteger(int max) {
    return (Integer)(new Random().nextInt(max));
  }
}
