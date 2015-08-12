#!/usr/bin/env bash
spark-submit  --properties-file conf/devdefaults.conf output/logdata-demo.jar \
  --hdfssource=hdfs://192.168.16.105 \
  --filename=/data/elasticsearch-prod/*  \
  --outfilename=/data/sparkgrep
