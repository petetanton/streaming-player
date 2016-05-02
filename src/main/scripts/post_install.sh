#!/usr/bin/env bash
sudo cp /etc/sr-lbp/streaming-player/streaming-player-service-wrapper.sh /etc/init.d/streaming-player
sudo cp /etc/sr-lbp/streaming-player/httpd.conf /etc/httpd/conf/httpd.conf
sudo cp /etc/sr-lbp/streaming-player/default.vcl /etc/varnish/default/vcl
sudo chmod +x /etc/init.d/streaming-player
sudo service httpd stop
sudo service varnish stop
sudo service streaming-player stop
sudo service streaming-player start
sudo service varnish start
sudo service httpd start
sudo chkconfig --add httpd
sudo chkconfig httpd on
sudo chkconfig --add streaming-player
sudo chkconfig streaming-player on
sudo chkconfig --add varnish
sudo chkconfig varnish on