/**
 * $Id: Emotions.java Aug 30, 2015 6:19:40 PM hdp
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shopping.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
public class Emotions {
	 /**
     * 移动端表情字符串数组
     */
    private static String[] mobile = { "[/微笑]", "[/瘪嘴]", "[/好色]", "[/发呆]",
            "[/得意]", "[/流泪]", "[/害羞]", "[/闭嘴]", "[/睡觉]", "[/尴尬]",
            "[/愤怒]", "[/调皮]", "[/呲牙]", "[/惊讶]", "[/难过]", "[/装酷]", "[/冷汗]",
            "[/抓狂]", "[/呕吐]", "[/偷笑]", "[/可爱]", "[/白眼]", "[/傲慢]", "[/饥饿]",
            "[/困]", "[/恐惧]", "[/流汗]", "[/憨笑]", "[/大兵]", "[/奋斗]", "[/咒骂]",
            "[/疑问]", "[/嘘嘘]", "[/晕]", "[/折磨]", "[/衰]", "[/骷髅]", "[/敲打]",
            "[/再见]" , "[/擦汗]", "[/抠鼻]", "[/鼓掌]", "[/糗大了]", "[/坏笑]", 
            "[/坏笑]", "[/左哼哼]", "[/右哼哼]", "[/哈欠]", "[/鄙视]", "[/委屈]", 
            "[/快哭了]", "[/阴险]", "[/亲亲]", "[/吓]", "[/可怜]"};
    /**
     * 网页端表情前缀
     */
    private static String prefix = "<img src='/emotion/";
    /**
     * 网页端表情后缀
     */
    private static String end = ".gif'>";
     
    private static Map<String, String> mobileToWebEmotions = new HashMap<String, String>();
    private static Map<String, String> webToMobileEmotions = new HashMap<String, String>();
 
    static {
        for (int i = 0; i < mobile.length; i++) {
            mobileToWebEmotions.put(mobile[i], prefix + i + end);
            webToMobileEmotions.put(prefix + i + end, mobile[i]);
        }
    }
     
    public static enum Dev {
        MOBILE(prefix + "[0-9]+" + end, webToMobileEmotions), 
        WEB("\\[\\/[\u4E00-\u9FA5]*\\]", mobileToWebEmotions);
 
        private String regex;
        private Map<String, String> map;
         
        Dev(String regex, Map<String, String> map) {
            this.regex = regex;
            this.map = map;
        }
    }
    /**
     * 表情转换
     * 
     * @param message
     * @param dev 目标设备
     * @return
     */
    private static String formatEmotion(String message, Dev dev) {
        Pattern pattern = null;
        Matcher matcher = null;
        StringBuffer buffer = new StringBuffer();       
 
        pattern = Pattern.compile(dev.regex);
        matcher = pattern.matcher(message);
        while (matcher.find()) {
            matcher.appendReplacement(buffer,
                    dev.map.get(matcher.group()));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        String msg = "[/微笑]Hello[/憨笑][/憨笑2]";
        System.out.println(Emotions.formatEmotion(msg, Dev.WEB));
    }
}
