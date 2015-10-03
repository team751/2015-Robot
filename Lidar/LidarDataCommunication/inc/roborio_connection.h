// Handles the communication between the RoboRIO and the processor running the
// lidar code by assembling and sending requests with tote pose information
//
// Example:
//    Communication *roboRIOCommuncation;
//    if (!roboRIOCommuncation.start("127.0.0.1".to_cstr(), "9999".to_cstr()) {
//      cerr << "Unable to start connection with RoboRIO" << "\n";
//    }
//
//    if (!roboRIOCommuncation.send(Communication::ToteEndpoint(x, y))) {
//      cerr << "Failed to send packet to RoboRIO" << "\n";
//    }
//
//    roboRIOCommuncation.stop()

#ifndef LIDAR_LIDARDATACOMMUNICATION_ROBORIOCONNECTION_H_
#define LIDAR_LIDARDATACOMMUNICATION_ROBORIOCONNECTION_H_

#include "tote_pose_packet.h"

#include <iostream>

class RoboRIOConnection {
  public:
    /* METHODS */
    
    // Opens a connection to the server if one doesn't already exist
    // Returns (boolean): returns true if the connection was successfully
    // established or false if an error occurred
    bool start(std::string host, std::string port);
    
    // Closes the connection to the server
    // Returns (boolean): returns true if the connection was successfully
    // closed
    void stop();
    
    // Returns (boolean): returns true if the a connection to the server is
    // already open
    bool isOpen();
    
    // Sends a datapoint to the RoboRIO
    // Returns (boolean): returns true if the message successfully sent
    bool send(TotePosePacket totePacket);
    
  private:
    /* VARIABLES */
    
    // Stores the state of the connection
    bool connectionOpen;
    
    // Stores the current socket
    int currentSocket;
    
    // The hostname of the RoboRIO
    const char* host;
    
    // The port the RoboRIO is listening on
    const char* port;
};

#endif // LIDAR_LIDARDATACOMMUNICATION_ROBORIOCONNECTION_H_