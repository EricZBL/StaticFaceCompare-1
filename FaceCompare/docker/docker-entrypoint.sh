#!/usr/bin/env bash
#/bin/bash

exec java -jar app.jar \
--elasticsearch.host=${ES_HOST} \
--zk.address=${ZOOKEEPER_HOST} >> /srv/test.log
