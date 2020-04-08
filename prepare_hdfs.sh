#!/usr/bin/env bash

echo "copying data/ to /tmp/pubmed/ on HDFS"
hadoop fs -put data/tp.ncbi.nlm.nih.gov/pubmed/updatefiles/*.gz /tmp/pubmed/
echo "copying target/scala-2.11/*.jar to /tmp/pubmed on HDFS"
hadoop fs -put -f target/scala-2.11/*.jar /tmp/pubmed/
echo "preparation has been done!"