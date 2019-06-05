cmake_minimum_required(VERSION 3.4.1)

#file(GLOB HEADER_FILES ${CMAKE_SOURCE_DIR}/src/main/cpp/includes/*.h??)
#${HEADER_FILES}

include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/includes)

link_directories(${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})

message(${CMAKE_SOURCE_DIR}/jniLibs/${ANDROID_ABI})

add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp/native-lib.cpp

        src/main/cpp/utils.cpp
        )

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

target_link_libraries( # Specifies the target library.
        native-lib

        ${log-lib}

        libopencv_java4.so
        libopencv_core.a
        libopencv_calib3d.a
        libopencv_dnn.a
        libopencv_features2d.a
        libopencv_flann.a
        libopencv_highgui.a
        libopencv_imgcodecs.a
        libopencv_imgproc.a
        libopencv_ml.a
        libopencv_objdetect.a
        libopencv_photo.a
        libopencv_stitching.a
        libopencv_video.a
        libopencv_videoio.a
        libleveldb.a
        libsnappy.a
        )
