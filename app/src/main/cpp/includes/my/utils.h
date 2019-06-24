
#ifndef SPORT_UTILS_H
#define SPORT_UTILS_H

#include <string>
#include <iostream>
#include <vector>

std::vector<std::vector<unsigned int>> *imageData(const std::string &imgPath, int weight, int height);

std::vector<std::vector<std::vector<unsigned int>>> *videoData(const std::string &videoPath, int weight, int height);


#endif //SPORT_UTILS_H
