package com.jn.lst.utils.encrypt;

import java.util.Random;

/**
 * @Description: 生成随机数工具类
 * @Author: yangxiaohu
 * @CreateDate: 2022/7/18 6:39 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2022/7/18 6:39 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RandomUtil {


    /**
     * 常量池
     */
    public static final String[] POOL = new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

    /**
     * 生成字符串
     * @return 生成的32位长度的16进制字符串
     */
    public static String generateNiceString(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            sb.append(POOL[random.nextInt(POOL.length)]);
        }
        return sb.toString();
    }


    /**
     * 字符串转换成为16进制字符串(大写)
     * @explain 因为java转义字符串在java中有着特殊的意义，
     * 所以当字符串中包含转义字符串，并将其转换成16进制后，16进制再转成String时，会出问题：
     * java会将其当做转义字符串所代表的含义解析出来
     * @param str 字符串(去除java转义字符)
     * @return 16进制字符串
     * @throws Exception
     */
    public static String toHexString(String str) throws Exception {
        // 用于接收转换结果
        String hexString = "";
        // 1.校验是否包含特殊字符内容
        // java特殊转义符
        // String[] escapeArray = {"\b","\t","\n","\f","\r","\'","\"","\\"};
        String[] escapeArray = {"\b","\t","\n","\f","\r"};
        // 用于校验参数是否包含特殊转义符
        boolean flag = false;
        // 迭代
        for (String esacapeStr : escapeArray) {
        // 一真则真
            if (str.contains(esacapeStr)) {
                flag = true;
                break;// 终止循环
            }
        }
        // 包含特殊字符
        if (flag) throw new Exception("参数字符串不能包含转义字符！");
        // 16进制字符
        char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        StringBuilder sb = new StringBuilder();
        // String-->byte[]
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(hexArray[bit]);
            bit = bs[i] & 0x0f;
            sb.append(hexArray[bit]);
        }
        hexString = sb.toString();
        return hexString;
    }


    /**
     * 生成指定长度随机数
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        //1. 定义一个字符串（A-Z，a-z，0-9）即62个数字字母；
        String str="0123456789ABCDEF";
        //2. 由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //3. 长度为几就循环几次
        for(int i=0; i<length; ++i){
            //从62个的数字或字母中选择
            int number=random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }


}
