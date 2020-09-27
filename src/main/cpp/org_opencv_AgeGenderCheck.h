#include <jni.h>
/* Header for class org_opencv_AgeGenderCheck */

#ifndef _Included_org_opencv_AgeGenderCheck
#define _Included_org_opencv_AgeGenderCheck
#ifdef __cplusplus
extern "C" {
#endif
/*
 *  * Class:     org_opencv_AgeGenderCheck
 *   * Method:    GetAgeAndSex
 *    * Signature: (J)J
 *     */
JNIEXPORT void JNICALL Java_org_opencv_AgeGenderCheck_GetAgeAndSex
  (JNIEnv *jenv, jclass jcls, long jarg, long matPtr);


JNIEXPORT void JNICALL Java_org_opencv_AgeGenderCheck_NetInit
  (JNIEnv *, jclass, jstring ageModel, jstring ageProto, jstring genderModel, jstring  genderProto, jstring faceModel, jstring faceProto);

#ifdef __cplusplus
}
#endif
#endif