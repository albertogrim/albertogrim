#include <iostream>
#include "opencv2/core.hpp"
#include "opencv2/calib3d.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/imgproc.hpp"
#include "obj_detc.h"


using namespace std;
using namespace cv;

//SIFT detector implementation
Mat objDetecUtils::objDetec_SIFT(const Mat& image_object, const Mat& image_scene, const double threshold)
{
	Mat result;

	//Detects the keypoints using SIFT Detector, compute the descriptors
	Ptr<SIFT> detector = SIFT::create(0, 3, 0.04, 10, 1.6);
	vector<KeyPoint> keypoints_object, keypoints_scene;
	Mat descriptors_object, descriptors_scene;
	detector->detectAndCompute(image_object, noArray(), keypoints_object, descriptors_object);
	detector->detectAndCompute(image_scene, noArray(), keypoints_scene, descriptors_scene);

	//Matching descriptor vectors with a Brute Force based matcher using Euclidean distance
	Ptr<BFMatcher> matcher = BFMatcher::create(NORM_L2, true);
	vector< DMatch > matches;
	matcher->match(descriptors_object, descriptors_scene, matches);

	double min_dist = 100;

	// Calculation of min distances between keypoints
	for (int i = 0; i < matches.size(); i++)
	{
		double dist = matches[i].distance;
		if (dist < min_dist) { min_dist = dist; }
	}

	//Keeps only "good" matches that is those whose distance is less than threshold * min_dist
	std::vector< DMatch > good_matches;
	for (int i = 0; i < matches.size(); i++)
	{
		if (matches[i].distance < threshold * min_dist)
		{
			good_matches.push_back(matches[i]);
		}
	}

	//Draws matches
	drawMatches(image_object, keypoints_object, image_scene, keypoints_scene, good_matches, result, Scalar::all(-1),
		Scalar::all(-1), std::vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS);
	
	//For the object localization
	std::vector<Point2f> obj;
	std::vector<Point2f> scene;
	
	//Gets the keypoints from the good matches
	for (size_t i = 0; i < good_matches.size(); i++)
	{
		obj.push_back(keypoints_object[good_matches[i].queryIdx].pt);
		scene.push_back(keypoints_scene[good_matches[i].trainIdx].pt);
	}
	Mat H = findHomography(obj, scene, RANSAC);

	//Gets the corners from the object to be detected
	std::vector<Point2f> obj_corners(4);
	obj_corners[0] = Point2f(0, 0);
	obj_corners[1] = Point2f((float)image_object.cols, 0);
	obj_corners[2] = Point2f((float)image_object.cols, (float)image_object.rows);
	obj_corners[3] = Point2f(0, (float)image_object.rows);
	std::vector<Point2f> scene_corners(4);
	perspectiveTransform(obj_corners, scene_corners, H);

	//Draws lines between the corners on the mapped object in the scene 
	line(result, scene_corners[0] + Point2f((float)image_object.cols, 0), scene_corners[1] + Point2f((float)image_object.cols, 0), Scalar(0, 255, 0), 4);
	line(result, scene_corners[1] + Point2f((float)image_object.cols, 0), scene_corners[2] + Point2f((float)image_object.cols, 0), Scalar(0, 255, 0), 4);
	line(result, scene_corners[2] + Point2f((float)image_object.cols, 0), scene_corners[3] + Point2f((float)image_object.cols, 0), Scalar(0, 255, 0), 4);
	line(result, scene_corners[3] + Point2f((float)image_object.cols, 0), scene_corners[0] + Point2f((float)image_object.cols, 0), Scalar(0, 255, 0), 4);

	return result;
}

//ORB detector implementation
Mat objDetecUtils::objDetec_ORB(const Mat& image_object, const Mat& image_scene, const double threshold)
{
	Mat result;

	//Detects the keypoints using ORB Detector, compute the descriptors
	Ptr<ORB> detector = ORB::create(500, 1.2f, 8, 31, 0, 2, ORB::HARRIS_SCORE, 31, 20);
	vector<KeyPoint> keypoints_object, keypoints_scene;
	Mat descriptors_object, descriptors_scene;
	detector->detectAndCompute(image_object, noArray(), keypoints_object, descriptors_object);
	detector->detectAndCompute(image_scene, noArray(), keypoints_scene, descriptors_scene);

	//Matching descriptor vectors with a Brute Force based matcher using Hamming distance
	Ptr<BFMatcher> matcher = BFMatcher::create(NORM_HAMMING, true);
	vector< DMatch > matches;
	matcher->match(descriptors_object, descriptors_scene, matches);

	double min_dist = 100;

	//Calculation of min distances between keypoints
	for (int i = 0; i < matches.size(); i++)
	{
		double dist = matches[i].distance;
		if (dist < min_dist) { min_dist = dist; }
	}

	//Keeps only "good" matches that is those whose distance is less than threshold * min_dist
	std::vector< DMatch > good_matches;
	for (int i = 0; i < matches.size(); i++)
	{
		if (matches[i].distance < threshold * min_dist)
		{
			good_matches.push_back(matches[i]);
		}
	}

	//Draws matches
	drawMatches(image_object, keypoints_object, image_scene, keypoints_scene, good_matches, result, Scalar::all(-1),
		Scalar::all(-1), std::vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS);

	//For the object localization
	std::vector<Point2f> obj;
	std::vector<Point2f> scene;
	
	//Gets the keypoints from the good matches
	for (size_t i = 0; i < good_matches.size(); i++)
	{
		obj.push_back(keypoints_object[good_matches[i].queryIdx].pt);
		scene.push_back(keypoints_scene[good_matches[i].trainIdx].pt);
	}
	Mat H = findHomography(obj, scene, RANSAC);

	//Gets the corners from the object to be detected
	std::vector<Point2f> obj_corners(4);
	obj_corners[0] = Point2f(0, 0);
	obj_corners[1] = Point2f((float)image_object.cols, 0);
	obj_corners[2] = Point2f((float)image_object.cols, (float)image_object.rows);
	obj_corners[3] = Point2f(0, (float)image_object.rows);
	std::vector<Point2f> scene_corners(4);
	perspectiveTransform(obj_corners, scene_corners, H);

	//Draws lines between the corners on the mapped object in the scene
	line(result, scene_corners[0] + Point2f((float)image_object.cols, 0), scene_corners[1] + Point2f((float)image_object.cols, 0), Scalar(0, 255, 0), 4);
	line(result, scene_corners[1] + Point2f((float)image_object.cols, 0), scene_corners[2] + Point2f((float)image_object.cols, 0), Scalar(0, 255, 0), 4);
	line(result, scene_corners[2] + Point2f((float)image_object.cols, 0), scene_corners[3] + Point2f((float)image_object.cols, 0), Scalar(0, 255, 0), 4);
	line(result, scene_corners[3] + Point2f((float)image_object.cols, 0), scene_corners[0] + Point2f((float)image_object.cols, 0), Scalar(0, 255, 0), 4);

	return result;
}


int main(int argc, char* argv[])
{
	//To select the correct folder you can modify path1 and path2
    String path1("dataset4/obj*");
    String path2("dataset4/scene*");
    vector<String> fn1;
    vector<String> fn2;
    vector<Mat> img_object;
    vector<Mat> img_scene;
    
    //Reads all the object images from the dataset
    glob(path1, fn1, true); // recurse
    for (size_t k = 0; k < fn1.size(); ++k)
    {
        Mat im = imread(fn1[k], IMREAD_GRAYSCALE);
        if (im.empty()) continue; //only proceed if sucsessful
        img_object.push_back(im);
    }
    
    //Reads all the scene images from the dataset
    glob(path2, fn2, true); // recurse
    for (size_t k = 0; k < fn2.size(); ++k)
    {
        Mat im = imread(fn2[k], IMREAD_GRAYSCALE);
        if (im.empty()) continue; //only proceed if sucsessful
        img_scene.push_back(im);
    }

    //Compares each obect with each scene applying the features matching function desired
	//and shows us the results
    for (int i = 0; i < img_object.size(); i++)
    {
        for (int j = 0; j < img_scene.size(); j++)
        {
            Mat img_matches= objDetecUtils::objDetec_SIFT(img_object[i], img_scene[j], 3);
            
            namedWindow("Good Matches & Object detection", WINDOW_NORMAL);
            imshow("Good Matches & Object detection", img_matches);
            waitKey();
        }
    }
    return 0;
}