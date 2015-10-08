//
// Created by Sam Baumgarten on 10/7/15.
//

#include "../inc/line_properties.h"

#include "../inc/constants.h"

double LineProperties::distanceFromLidar(cv::Vec4i line) {
    double averageX = (line[0] + line[2]) / 2;
    double averageY = (line[1] + line[3]) / 2;

    return sqrt(averageX * averageX + averageY * averageY);
}

double LineProperties::lengthOfLine(cv::Vec4i line) {
    double deltaX = line[2] - line[0];
    double deltaY = line[3] - line[1];

    return sqrt(deltaX * deltaX + deltaY * deltaY);
}

double LineProperties::angleOfLine(cv::Vec4i line) {
    double angle = M_PI_2 - atan((line[3] - line[1]) / (line[2] - line[0]));

    if (angle > M_PI_2) angle = angle - M_PI;

    return angle;
}

double LineProperties::theoreticalLength(cv::Vec4i line) {
    // ViewedSize = (ActSize * FL) / distance
    // ActSize = ((cos(angle) * distance) / length * FL)
    // cos(angle) = a * h

    double distance = distanceFromLidar(line);
    double angle = angleOfLine(line);

    return (Constants::kActualLength * distance) / (Constants::getFocalLength(distance) * cos(angle));
}

cv::Vec4i LineProperties::fitLineToPoints(pcl::KdTreeFLANN<pcl::PointXYZ> kdtree, pcl::PointCloud<pcl::PointXYZ>::Ptr cloud, cv::Vec4i line) {
    // Allocate vectors for results
    std::vector<int>   pointIndexSearchResults1(10);
    std::vector<float> pointDistanceSearchResults1(10);
    std::vector<int>   pointIndexSearchResults2(10);
    std::vector<float> pointDistanceSearchResults2(10);

    // Setup points
    pcl::PointXYZ startPoint;
    startPoint.x = line[0];
    startPoint.y = line[1];
    startPoint.z = 0;

    pcl::PointXYZ endPoint;
    endPoint.x = line[2];
    endPoint.y = line[3];
    endPoint.z = 0;

    // Find points for each approx point
    cv::Vec4i result;

    if (kdtree.nearestKSearch(startPoint, 10, pointIndexSearchResults1, pointDistanceSearchResults1) > 0) {
        result[0] = cloud->points[pointIndexSearchResults1[0]].x;
        result[1] = cloud->points[pointIndexSearchResults1[0]].y;
    }

    if (kdtree.nearestKSearch(endPoint, 10, pointIndexSearchResults2, pointDistanceSearchResults2) > 0) {
        result[2] = cloud->points[pointIndexSearchResults2[0]].x;
        result[3] = cloud->points[pointIndexSearchResults2[0]].y;
    }

    return result;
}