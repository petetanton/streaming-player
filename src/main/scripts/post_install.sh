#!/usr/bin/env bash
sudo cp /etc/sr-lbp/streaming-player-service-wrapper.sh /etc/init.d/streaming-player
sudo chmod +x /etc/init.d/streaming-player
sudo service httpd stop
sudo service streaming-player stop
sudo service streaming-player start
sudo service httpd start
