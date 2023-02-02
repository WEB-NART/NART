package com.nart.util;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: ProgressBar
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2023-01-31 5:20 p.m.
 */
public class ProgressBar {

    private int index = 0;
    private String finish;
    private String unFinish;
    private String target;


    // 进度条粒度
    private final int PROGRESS_SIZE = 50;
    private int BITE = 2;

    private String getNChar(int num, char ch){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < num; i++){
            builder.append(ch);
        }
        return builder.toString();
    }

    public void printProgress(){
        System.out.print("Progress:");

        finish = getNChar(index / BITE, '█');
        unFinish = getNChar(PROGRESS_SIZE - index / BITE, '─');
        target = String.format("%3d%%[%s%s]", index, finish, unFinish);
        System.out.print(target);
    }

    public void load() {
        if (index <= 100){
            finish = getNChar(index / BITE, '█');
            unFinish = getNChar(PROGRESS_SIZE - index / BITE, '─');

            target = String.format("%3d%%├%s%s┤", index, finish, unFinish);
            System.out.print(getNChar(PROGRESS_SIZE + 6, '\b'));
            System.out.print(target);
            index++;
        }
    }

    public static void main(String[] args) {
        ProgressBar pb = new ProgressBar();
        pb.printProgress();
        for (int i = 0; i <= 100; i++) {
            pb.load();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
