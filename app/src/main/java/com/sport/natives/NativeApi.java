package com.sport.natives;

/**
 * User: bizehao
 * Date: 2019-04-27
 * Time: 上午6:30
 * Description:
 */
public class NativeApi {
    /**
     * 获取图片数据
     * @param imgPath 图片路径
     * @param weight 宽度的缩放比例
     * @param height 高度的缩放比例
     * @return json字符串 需要解析
     */
    public native static String formatImg(String imgPath, int weight, int height);

    /**
     * 获取图片数据
     * @param videoPath 图片路径
     * @param weight 宽度的缩放比例
     * @param height 高度的缩放比例
     * @return json字符串 需要解析
     */
    public native static String formatVideo(String videoPath, int weight, int height);


    public native static void testLevelDB();
}
