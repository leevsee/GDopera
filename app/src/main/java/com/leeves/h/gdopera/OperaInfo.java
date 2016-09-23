package com.leeves.h.gdopera;

/**
 * Function：
 * Created by h on 2016/9/7.
 *
 * @author Leeves
 */
public class OperaInfo {
    private String mOperaTitle;
    private String mOperavideoUrl;
    private Object mBitMap;
    private String mPictureName;
    private String mPictureUrl;
    private String mPictureUri;

    public OperaInfo(String operaTitle, String operavideoUrl, Object bitMap, String pictureName, String pictureUrl, String pictureUri) {
        mOperaTitle = operaTitle;
        mOperavideoUrl = operavideoUrl;
        mBitMap = bitMap;
        mPictureName = pictureName;
        mPictureUrl = pictureUrl;
        mPictureUri = pictureUri;
    }

    public String getPictureUri() {
        return mPictureUri;
    }

    public void setPictureUri(String pictureUri) {
        mPictureUri = pictureUri;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public String getPictureName() {
        return mPictureName;
    }

    public void setPictureName(String pictureName) {
        mPictureName = pictureName;
    }

    public String getOperaTitle() {
        return mOperaTitle;
    }

    public void setOperaTitle(String operaTitle) {
        mOperaTitle = operaTitle;
    }

    public String getOperavideoUrl() {
        return mOperavideoUrl;
    }

    public void setOperavideoUrl(String operavideoUrl) {
        mOperavideoUrl = operavideoUrl;
    }

    public Object getBitMap() {
        return mBitMap;
    }

    public void setBitMap(Object bitMap) {
        mBitMap = bitMap;
    }
}
