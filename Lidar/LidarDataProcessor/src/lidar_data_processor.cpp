#include "../inc/lidar_data_processor.h"

#include <vector>

#include <pcl/io/pcd_io.h>
#include <pcl/kdtree/kdtree_flann.h>

#include "../inc/constants.h"
#include "../inc/coordinate_conversion.h"
#include "../inc/point_cloud_conversion.h"
#include "../inc/line_properties.h"

TotePose LidarDataProcessor::processLidarData(std::array<double, 360> lidarData) {
    // Create Mats
    cv::Mat lidarImage = PointCloudConversion::createImageWithLidarData(lidarData, 280, 400, 1, cv::Scalar(255), CV_8UC1, 1000, 1000);
    cv::Mat outputImage = PointCloudConversion::createImageWithLidarData(lidarData, 280, 400, 1, cv::Scalar(255, 0, 0), CV_8UC3, 1000, 1000);

    std::vector<cv::Vec4i> houghLinesFound;
    cv::HoughLinesP(lidarImage, houghLinesFound, Constants::kRho, Constants::kTheta, Constants::kThreshold, Constants::kMinLineLength, Constants::kMaxLineGap);

    cv::Vec4i discoveredLine;
    double minimumLengthDelta = UINT_MAX;

    for (size_t i = 0; i < houghLinesFound.size(); i++) {
        cv::Vec4i line                        = houghLinesFound[i];
        cv::Vec4i lineInImageCoordinateSystem = LidarDataCoordinateConversion::convertCoordinateSystem(line,
                                                                                                       LidarDataCoordinateConversion::CoordinateSystem::LidarCoordinateSystem,
                                                                                                       LidarDataCoordinateConversion::CoordinateSystem::ImageCoordinateSystem);

        double distance          = LineProperties::distanceFromLidar(line);
        double length            = LineProperties::lengthOfLine(line);
        double angle             = LineProperties::angleOfLine(line);
        double theoreticalLength = LineProperties::theoreticalLength(line);

        double lengthDelta = fabs(theoreticalLength - length);

        if (lengthDelta < Constants::kLengthDeltaThreshold && lengthDelta < minimumLengthDelta) {
            minimumLengthDelta = lengthDelta;
            discoveredLine     = line;
        }

        cv::line(outputImage, cv::Point(line[0], line[1]), cv::Point(line[2], line[3]), cv::Scalar(0, 0, 255), 3, CV_AA);
    }

    // If a line meeting out standards was found
    if (minimumLengthDelta != UINT_MAX) {
        // Create point cloud
        pcl::PointCloud<pcl::PointXYZ>::Ptr cloud = PointCloudConversion::createPointCloudFromLidarData(lidarData, 280, 400);

        // Create Kd Tree
        pcl::KdTreeFLANN<pcl::PointXYZ> kdtree;
        kdtree.setInputCloud(cloud);

        // Find actual points
        cv::Vec4i actualLine = LineProperties::fitLineToPoints(kdtree, cloud, discoveredLine);

        // Draw line
        cv::line(outputImage, cv::Point(actualLine[0], actualLine[1]), cv::Point(actualLine[2], actualLine[3]), cv::Scalar(0, 255, 0), 3, CV_AA);
    }

    cv::imshow("Output", outputImage);
}