#代码编译方式
# `export JAVA_HOME=<JAVA_HOME>`
#  cmake .
#  make
#将运行生成的libage_gender.so放入Djava.library.path
cmake_minimum_required(VERSION 2.8.12)
PROJECT(age_gender)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")


set(OpenCV_DIR "这里需要输入opencv编译后的位置")
set(JNI_INCLUDE "$ENV{JAVA_HOME}/include") #这里需要输入导入JAVA_HOME


include_directories(${JNI_INCLUDE})
include_directories("${JNI_INCLUDE}/linux")
find_package(JNI)
find_package( OpenCV REQUIRED )
set(AGE_SRC org_opencv_AgeGenderCheck.cpp org_opencv_AgeGenderCheck.h)
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED TRUE)

include_directories( ${OpenCV_INCLUDE_DIRS})
add_library(age_gender SHARED ${AGE_SRC})
target_link_libraries(age_gender ${OpenCV_LIBS})
MACRO(add_example name)
  ADD_EXECUTABLE(${name} ${name}.cpp)
  TARGET_LINK_LIBRARIES(${name} ${OpenCV_LIBS})
ENDMACRO()

#add_library(age_gender SHARED ${AGE_SRC})
#INSTALL(TARGETS age_gender LIBRARY DESTINATION .)