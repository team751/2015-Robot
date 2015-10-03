#include <roborio_connection.h>

int main(int argc, char** argv) {
  RoboRIOConnection connection;
  connection.start("127.0.0.1", "9999");
  connection.send(TotePosePacket(TotePosePacket::ToteEndpoint(4, 3), TotePosePacket::ToteEndpoint(10, 3), 50));
}