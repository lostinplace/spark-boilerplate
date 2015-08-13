#!/usr/bin/env bash
spark-submit  --properties-file conf/devdefaults.conf output/`cat .projectname`.jar \
  --inpath:/Users/cwheeler/dev/git/registration-api-processing/data/sum.jsonl  \
  --outpath:./dataout
