package com.example.quacksbag.gamematerial;

import static com.example.quacksbag.gamematerial.BubbleFactory.createBubble;
import static com.example.quacksbag.gamematerial.BubbleFactory.createRubyBubble;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class Claudron {
    private ArrayList<Bubble> bubbles;

    public Claudron() {
        bubbles=new ArrayList<>();
        bubbles.add(createBubble(0));
        bubbles.add(createBubble(1));
        bubbles.add(createBubble(2));
        bubbles.add(createBubble(3));
        bubbles.add(createBubble(4));
        bubbles.add(createRubyBubble(5));
        bubbles.add(createBubble(6,1));
        bubbles.add(createBubble(7,1));
        bubbles.add(createBubble(8,1));
        bubbles.add(createRubyBubble(9,1));
        bubbles.add(createBubble(10,2));
        bubbles.add(createBubble(11,2));
        bubbles.add(createBubble(12,2));
        bubbles.add(createRubyBubble(13,2));
        bubbles.add(createBubble(14,3));
        bubbles.add(createBubble(15,3));
        bubbles.add(createRubyBubble(15,3));
        bubbles.add(createBubble(16,3));
        bubbles.add(createBubble(16,4));
        bubbles.add(createBubble(17,4));
        bubbles.add(createRubyBubble(17,4));
        bubbles.add(createBubble(18,4));
        bubbles.add(createBubble(18,5));
        bubbles.add(createBubble(19,5));
        bubbles.add(createRubyBubble(19,5));
        bubbles.add(createBubble(20,5));
        bubbles.add(createBubble(20,6));
        bubbles.add(createBubble(21,6));
        bubbles.add(createRubyBubble(21,6));
        bubbles.add(createBubble(22,7));
        bubbles.add(createRubyBubble(22,7));
        bubbles.add(createBubble(23,7));
        bubbles.add(createBubble(23,8));
        bubbles.add(createBubble(24,8));
        bubbles.add(createRubyBubble(24,8));
        bubbles.add(createBubble(25,9));
        bubbles.add(createRubyBubble(25,9));
        bubbles.add(createBubble(26,9));
        bubbles.add(createBubble(26,10));
        bubbles.add(createBubble(27,10));
        bubbles.add(createRubyBubble(27,10));
        bubbles.add(createBubble(28,11));
        bubbles.add(createRubyBubble(28,11));
        bubbles.add(createBubble(29,11));
        bubbles.add(createBubble(29,12));
        bubbles.add(createBubble(30,12));
        bubbles.add(createRubyBubble(30,12));
        bubbles.add(createBubble(31,12));
        bubbles.add(createBubble(31,13));
        bubbles.add(createBubble(32,13));
        bubbles.add(createRubyBubble(32,13));
        bubbles.add(createBubble(33,14));
        bubbles.add(createRubyBubble(33,14));
        bubbles.add(createBubble(35,15));
    }

    public Bubble getBubbleForPosition(int pos){
        if (pos >= bubbles.size()) {
            return bubbles.get(53);
        }
       return bubbles.get(pos);
    }


}
