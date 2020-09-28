#include "org_opencv_AgeGenderCheck.h"
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/dnn.hpp>
#include <tuple>
#include <iostream>
#include <opencv2/opencv.hpp>
using namespace cv;
using namespace cv::dnn;
using namespace std;


Net Face;
Net Age;
Net Gender;
std::string jstring2str(JNIEnv* env, jstring jstr)
{
    char*   rtn   =   NULL;
    jclass   clsstring   =   env->FindClass("java/lang/String");
    jstring   strencode   =   env->NewStringUTF("utf-8");
    jmethodID   mid   =   env->GetMethodID(clsstring,   "getBytes",   "(Ljava/lang/String;)[B");
    jbyteArray   barr=   (jbyteArray)env->CallObjectMethod(jstr,mid,strencode);
    jsize   alen   =   env->GetArrayLength(barr);
    jbyte*   ba   =   env->GetByteArrayElements(barr,JNI_FALSE);
    if(alen   >   0)
    {
        rtn   =   (char*)malloc(alen+1);
        memcpy(rtn,ba,alen);
        rtn[alen]=0;
    }
    env->ReleaseByteArrayElements(barr,ba,0);
    std::string stemp(rtn);
    free(rtn);
    return   stemp;
}
JNIEXPORT void JNICALL Java_org_opencv_AgeGenderCheck_NetInit
  (JNIEnv *env, jclass, jstring ageModel, jstring ageProto, jstring genderModel, jstring  genderProto, jstring faceModel, jstring faceProto){
          // Load Network
          String ageModelStr= jstring2str(env,ageModel);
          String ageProtoStr= jstring2str(env,ageProto);
          String genderModelStr= jstring2str(env,genderModel);
          String genderProtoStr= jstring2str(env,genderProto);
          String faceModelStr= jstring2str(env,faceModel);
          String faceProtoStr= jstring2str(env,faceProto);
    Age = readNet(ageModelStr, ageProtoStr);
    Gender = readNet(genderModelStr, genderProtoStr);
    Face = readNet(faceModelStr,faceProtoStr);
  }
tuple<Mat, vector<vector<int>>> getFaceBox(Net net, Mat &frame, double conf_threshold)
{
    Mat frameOpenCVDNN = frame.clone();
    int frameHeight = frameOpenCVDNN.rows;
    int frameWidth = frameOpenCVDNN.cols;
    double inScaleFactor = 1.0;
    Size size = Size(300, 300);
    // std::vector<int> meanVal = {104, 117, 123};
    Scalar meanVal = Scalar(104, 117, 123);

    cv::Mat inputBlob;
    inputBlob = cv::dnn::blobFromImage(frameOpenCVDNN, inScaleFactor, size, meanVal, true, false);

    net.setInput(inputBlob, "data");
    cv::Mat detection = net.forward("detection_out");

    cv::Mat detectionMat(detection.size[2], detection.size[3], CV_32F, detection.ptr<float>());

    vector<vector<int>> bboxes;

    for(int i = 0; i < detectionMat.rows; i++)
    {
        float confidence = detectionMat.at<float>(i, 2);

        if(confidence > conf_threshold)
        {
            int x1 = static_cast<int>(detectionMat.at<float>(i, 3) * frameWidth);
            int y1 = static_cast<int>(detectionMat.at<float>(i, 4) * frameHeight);
            int x2 = static_cast<int>(detectionMat.at<float>(i, 5) * frameWidth);
            int y2 = static_cast<int>(detectionMat.at<float>(i, 6) * frameHeight);
            vector<int> box = {x1, y1, x2, y2};
            bboxes.push_back(box);
            cv::rectangle(frameOpenCVDNN, cv::Point(x1, y1), cv::Point(x2, y2), cv::Scalar(0, 255, 0),2, 4);
        }
    }

    return make_tuple(frameOpenCVDNN, bboxes);
}




JNIEXPORT void JNICALL Java_org_opencv_AgeGenderCheck_GetAgeAndSex
  (JNIEnv *jenv, jclass jcls, long jarg, long matPtr){

try{
    Scalar MODEL_MEAN_VALUES = Scalar(78.4263377603, 87.7689143744, 114.895847746);

    vector<string> ageList = {"(0-2)", "(4-6)", "(8-12)", "(15-20)", "(25-32)",
      "(38-43)", "(48-53)", "(60-100)"};

    vector<string> genderList = {"Male", "Female"};

  Net ageNet=Age;
  Net faceNet=Face;
  Net genderNet=Gender;


    int padding = 20;


      Mat frame=*((Mat *)jarg);


      vector<vector<int>> bboxes;
      Mat frameFace;
      tie(frameFace, bboxes) = getFaceBox(faceNet, frame, 0.7);

      if(bboxes.size() == 0) {
        cout << "No face detected, checking next frame." << endl;
      }
      for (auto it = begin(bboxes); it != end(bboxes); ++it) {
        Rect rec(it->at(0) - padding, it->at(1) - padding, it->at(2) - it->at(0) + 2*padding, it->at(3) - it->at(1) + 2*padding);
        Mat face = frame(rec); // take the ROI of box on the frame

        Mat blob;
        blob = blobFromImage(face, 1, Size(227, 227), MODEL_MEAN_VALUES, false);
        genderNet.setInput(blob);

        vector<float> genderPreds = genderNet.forward();

        int max_index_gender = std::distance(genderPreds.begin(), max_element(genderPreds.begin(), genderPreds.end()));
        string gender = genderList[max_index_gender];
        cout << "Gender: " << gender << endl;



        ageNet.setInput(blob);
        vector<float> agePreds = ageNet.forward();

        int max_indice_age = std::distance(agePreds.begin(), max_element(agePreds.begin(), agePreds.end()));
        string age = ageList[max_indice_age];
        cout << "Age: " << age << endl;
        string label = gender + ", " + age; // label
        cv::putText(frameFace, label, Point(it->at(0), it->at(1) -15), cv::FONT_HERSHEY_SIMPLEX, 0.9, Scalar(0, 255, 255), 2, cv::LINE_AA);
        // imshow("Frame", frameFace);

      }
      Mat* mat = (Mat*) matPtr;
      mat->create(frameFace.rows, frameFace.cols, frameFace.type());

      memcpy(mat->data, frameFace.data, mat->step * mat->rows);
   }catch(exception e){
   cout<<e.what()<<endl;
     }
  }