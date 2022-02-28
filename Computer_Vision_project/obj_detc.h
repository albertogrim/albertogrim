
#ifndef LAB4___OBJDETC__UTILS__H
#define LAB4___OBJDETC__UTILS__H

#include <memory>
#include <iostream>

#include <opencv2/core.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/calib3d.hpp>

class objDetecUtils
{
public:
	static
		cv::Mat objDetec_SIFT(
			const cv::Mat& image_object,
			const cv::Mat& image_scene,
			const double threshold);
public:
	static
		cv::Mat objDetec_ORB(
			const cv::Mat& image_object,
			const cv::Mat& image_scene,
			const double threshold);
};
#endif //LAB4___OBJDETC__UTILS__H