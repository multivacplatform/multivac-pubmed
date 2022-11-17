#!/usr/bin/env bash
echo "start downloading updated articles"
cd "$(dirname "$0")"
./download_daily.sh
echo "start pulling latest code, cleaning and creating fat JAR"
./build_lib.sh
#change the following base on your environment: local / cluster
echo "start preparing everything for HDFS"
./prepare_hdfs.sh
echo "start Spark job on YARN cluster"
./start_spark.sh -m yarn -i hdfs:///tmp/pubmed/*.xml -o hdfs:///path-to-output -j hdfs:///tmp/pubmed/multivac-pubmed-assembly-1.0.0.jar