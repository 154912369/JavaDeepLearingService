# 语音识别
基于[kaldi](https://github.com/kaldi-asr/kaldi.git)的模型识别. 

java的接口是借助[vosk](https://github.com/alphacep/vosk-api)，所以需要编译java的动态链接库。

录音用的是[Recorderjs](https://github.com/mattdiamond/Recorderjs)项目，但对于手机浏览器支持有点差，后续需要修改。

##编译方式
编译kaldi文件夹下的Makefile文件，编译方法看Makefile文件。

# 年龄和性别识别
jni代码改变自[learnopencv](https://github.com/spmallick/learnopencv)。

##编译方式
编译opencv文件夹下的CMakefile文件，编译方法看CMakefile文件。