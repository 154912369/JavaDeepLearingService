package org.opencv;

/**
 * @author ：renweijie
 * @date ：Created in 20/9/22 21:43
 * @description：
 */
public class AgeGenderCheck {
    static {
        System.loadLibrary("age_gender");
    }

    public final static native void GetAgeAndSex(long origin,long matPtr);
    public final static native void NetInit(String ageModel,String  ageProto,String genderModel,String  genderProto,String faceModel,String  faceProto);
}
