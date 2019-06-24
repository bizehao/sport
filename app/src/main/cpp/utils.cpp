#include <android/log.h>
#include "my/utils.h"
#include <cmath>
#include <opencv2/opencv.hpp>

#define LOG_TAG  "C_TAG"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

static std::vector<std::vector<unsigned int>> imgToArray(cv::Mat &img) {
    std::vector<std::vector<unsigned int>> imgArray;

    imgArray.reserve(static_cast<unsigned long>(img.rows));
    int charValue = 0;
    for (int i = 0; i < img.rows; i++) {

        std::vector<unsigned int> imgs;
        imgs.reserve(static_cast<unsigned long>(img.cols));

        for (int j = 0; j < img.cols; j++) {
            charValue = img.at<unsigned char>(i, j);

            if (charValue <= 50) {
                imgs.push_back(0);
            } else if (charValue > 50 && charValue <= 100) {
                imgs.push_back(1);
            } else if (charValue > 100 && charValue <= 150) {
                imgs.push_back(2);
            } else {
                imgs.push_back(3);
            }
        }
        imgArray.push_back(imgs);
    }
    img.release();
    return imgArray;
}

std::vector<std::vector<unsigned int>> *imageData(const std::string &imgPath, int weight, int height) {

    LOGD("native图片路径: %s", imgPath.data());

    cv::Mat img = cv::imread(imgPath, cv::ImreadModes::IMREAD_GRAYSCALE);

    LOGD("图片宽度 %i", img.cols);
    LOGD("图片高度 %i", img.rows);

    cv::resize(img, img, cv::Size(weight, height), 0, 0, cv::InterpolationFlags::INTER_LINEAR);

    auto *imgArray = new std::vector<std::vector<unsigned int>>;

    *imgArray = imgToArray(img);

    return imgArray;
}

std::vector<std::vector<std::vector<unsigned int>>> *videoData(const std::string &videoPath, int weight, int height) {
    cv::VideoCapture videoCapture(videoPath);
    double totalFrameNumber = videoCapture.get(cv::VideoCaptureProperties::CAP_PROP_FRAME_COUNT);
    double rate = videoCapture.get(cv::VideoCaptureProperties::CAP_PROP_FPS);
    int time = static_cast<int>(1000 / rate);

    std::vector<cv::Mat> mats;
    mats.reserve((unsigned long) totalFrameNumber);
    cv::Mat frame;
    //滤波器的核
    int kernel_size = 3;
    cv::Mat kernel = cv::Mat::ones(kernel_size, kernel_size, CV_32F) / (float) (kernel_size * kernel_size);

    while (videoCapture.read(frame)) {
        cv::resize(frame, frame, cv::Size(weight, height), 0, 0, cv::INTER_LINEAR);
        cvtColor(frame, frame, cv::ColorConversionCodes::COLOR_BGR2GRAY);
        mats.push_back(frame);
    }
    videoCapture.release();

    auto *fuImgs = new std::vector<std::vector<std::vector<unsigned int>>>;


    for (cv::Mat &img : mats) {
        fuImgs->push_back(imgToArray(img));
    }
    return fuImgs;
}