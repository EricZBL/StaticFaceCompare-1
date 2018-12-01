#!/usr/bin/env bash
#/bin/bash

exec java -jar app.jar \
--elasticseatch.host=${es.host} \
--zk.address=${zk.host}
