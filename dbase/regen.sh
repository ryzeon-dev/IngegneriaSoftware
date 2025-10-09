sudo docker container rm swedb
sudo docker image rm swedb

sudo docker build . -t swedb
bash ./generate.sh